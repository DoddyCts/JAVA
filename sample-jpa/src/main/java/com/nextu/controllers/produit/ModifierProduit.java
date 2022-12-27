package com.nextu.controllers.produit;

import java.io.IOException;
import java.util.List;
import com.nextu.entities.Produit;
import com.nextu.entities.Categorie;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.UserTransaction;

/**
 * Servlet implementation class ModifierDiscipline
 */
@WebServlet("/modifierProduit")
public class ModifierProduit extends HttpServlet {
   private static final long serialVersionUID = 1L;
   @PersistenceContext(unitName = "sample-jpa")
   private EntityManager em;
   @Resource
   private UserTransaction userTransaction;

   /**
    * Default constructor.
    */
   public ModifierProduit() {
      // TODO Auto-generated constructor stub
   }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // Récupération de l'identifiant du produit à modifier
      String idProduitStr = request.getParameter("idProcduct");
      // Vérification que l'identifiant du produit est bien présent
      if (idProduitStr == null || idProduitStr.trim().isEmpty()) {
         // Redirection vers la liste des produits avec un message d'erreur
         response.sendRedirect("produits?errorMessage=L'identifiant du produit est absent ou vide");
         return;
      }
      // Conversion de l'identifiant du produit en entier
      Long idProduit = null;
      try {
         idProduit = Long.parseLong(idProduitStr);
      } catch (NumberFormatException e) {
         // Redirection vers la liste des produits avec un message d'erreur
         response.sendRedirect("produits?errorMessage=L'identifiant du produit n'est pas un entier valide");
         return;
      }
      // Récupération du produit à partir de son identifiant
      Produit produit = em.find(Produit.class, idProduit);
      // Vérification que le produit a été trouvé
      if (produit == null) {
         // Redirection vers la liste des produits avec un message d'erreur
         response.sendRedirect("produits?errorMessage=Le produit n'a pas été trouvé");
         return;
      }
      // Ajout du produit à la requête HTTP
      request.setAttribute("produit", produit);
      // Récupération du message d'erreur (s'il y en a un)
      String errorMessage = request.getParameter("errorMessage");
      // Ajout du message d'erreur à la requête HTTP
      request.setAttribute("errorMessage", errorMessage);
      // Création de la requête JPA pour récupérer la liste des catégories
      TypedQuery<Categorie> query = em.createQuery("SELECT c FROM Categorie c", Categorie.class);
      // Exécution de la requête et récupération de la liste des catégories
      List<Categorie> categories = query.getResultList();
      // Ajout de la liste des catégories à la requête HTTP
      request.setAttribute("categories", categories);
      // Affichage du formulaire de modification du produit
      this.getServletContext().getRequestDispatcher("/modifier-produit.jsp").forward(request, response);
   }




   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
   // On définit l'URL de redirection en cas d'erreur
   String uriRedirect = "modifierProduit?idProduit="
         + request.getParameter("idProduit") + "error=%s";

   // On récupère le produit et la catégorie associés à la modification
   Produit produit = getProduit(request);
   Categorie categorie = getCategorie(request);

   // Si le produit n'a pas été trouvé, on redirige avec un message d'erreur
   if (produit == null) {
      String message = "Aucun produit trouvé";
      redirectWithErrorMessage(response, uriRedirect, message);
   }

   // Si la catégorie n'a pas été trouvée, on redirige avec un message d'erreur
   if (categorie == null) {
      String message = "Aucune catégorie trouvée";
      redirectWithErrorMessage(response, uriRedirect, message);
   }

   // On récupère le nom du produit à partir de la requête
   String nom = request.getParameter("nom");

   // Si le nom est vide ou null, on redirige avec un message d'erreur
   if (nom == null || nom.isEmpty() || nom.isBlank()) {
      String message = "Veuillez renseigner le nom du produit";
      redirectWithErrorMessage(response, uriRedirect, message);
   } else {
      // On définit un booléen pour savoir si la transaction a réussi
      boolean transactionOk = false;
      try {
         // On démarre la transaction
         userTransaction.begin();

         // On met à jour le produit et la catégorie associée
         produit.setName(nom);
         produit.setCategorie(em.merge(categorie));
         em.merge(produit);

         // Si on arrive ici, la transaction a réussi
         transactionOk = true;
         } catch (Exception e) {
            System.out.print("Une erreur est survennue lors de l'enregistrement");
         } finally {
            try {
               // Si la transaction a réussi, on la commit et on redirige vers la page des produits
               if (transactionOk) {
                  userTransaction.commit();
                  response.sendRedirect("produits");
               } else {
                  // Si la transaction a échoué, on la rollback et on redirige avec un message d'erreur
                  String message = "Une erreur est survenue lors de l'enregistrement, veuillez contacter l'administrateur";
                  userTransaction.rollback();
                  redirectWithErrorMessage(response, uriRedirect, message);
               }
            } catch (Exception e) {
               // Si une exception est levée lors du commit ou du rollback, on redirige avec un message d'erreur
               String message = "Une erreur est survenue lors de l'enregistrement, veuillez contacter l'administrateur";
               redirectWithErrorMessage(response, uriRedirect, message);
            }
         }
      }
   }


   private Produit getProduit(HttpServletRequest request) {
   Produit produit = null;
   try {
      // Récupération de l'identifiant du produit passé dans la requête HTTP
      Long idProduit = Long.valueOf(request.getParameter("idProduit"));
      // Récupération du produit en utilisant l'identifiant
      produit = em.find(Produit.class, idProduit);
   } catch (NumberFormatException ex) {
      // L'identifiant du produit n'est pas un nombre
      System.out.println("L'identifiant du produit n'est pas un nombre : " + ex.getMessage());
   } catch (EntityNotFoundException ex) {
      // Aucun produit trouvé avec l'identifiant spécifié
      System.out.println("Aucun produit trouvé avec l'identifiant : " + ex.getMessage());
   } catch (Exception ex) {
      // Une erreur inattendue est survenue
      System.out.println("Erreur lors de la récupération du produit : " + ex.getMessage());
   }
   return produit;
   }


   private Categorie getCategorie(HttpServletRequest request) {
      Categorie categorie = null;
      try {
         // On récupère l'ID de la catégorie à partir de la requête
         Long idCategorie = Long.valueOf(request.getParameter("idCategorie"));

         // On cherche la catégorie correspondante en base de données
         categorie = em.find(Categorie.class, idCategorie);
      } catch (EntityNotFoundException ex) {
         // Si aucune catégorie n'a été trouvée, on ne fait rien
      }
      return categorie;
   }

   private void redirectWithErrorMessage(HttpServletResponse response, String uriRedirect,
         String message) throws IOException {
      // On formate l'URL en remplaçant le placeholder par le message d'erreur
      String urlRediret = String.format(uriRedirect, message);

      // On redirige vers l'URL avec le message d'erreur
      response.sendRedirect(urlRediret);
   }
}