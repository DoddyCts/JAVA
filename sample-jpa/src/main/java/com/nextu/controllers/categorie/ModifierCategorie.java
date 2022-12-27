package com.nextu.controllers.categorie;

import java.io.IOException;
import com.nextu.entities.Categorie;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.UserTransaction;

/**
 * Servlet implementation class ModifierSport
 */
@WebServlet("/modifierCategorie")
public class ModifierCategorie extends HttpServlet {
   private static final long serialVersionUID = 1L;
   @PersistenceContext(unitName = "sample-jpa")
   private EntityManager em;
   @Resource
   private UserTransaction userTransaction;

   /**
    * Default constructor.
    */
   public ModifierCategorie() {
      // TODO Auto-generated constructor stub
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
   // On récupère le id de la catégorie à modifier à partir de la requête
   Long idCategorie = Long.valueOf(request.getParameter("idCategorie"));

   // On récupère la catégorie en base de données
   Categorie categorie = em.find(Categorie.class, idCategorie);

   // On passe la catégorie à la JSP pour affichage
   request.setAttribute("categorie", categorie);

   // On récupère le message d'erreur (s'il y en a un) à partir de la requête
   String errorMessage = request.getParameter("errorMessage");

   // On passe le message d'erreur à la JSP pour affichage
   request.setAttribute("errorMessage", errorMessage);

   // On affiche la page de modification de la catégorie
   this.getServletContext().getRequestDispatcher("/modifier-categorie.jsp").forward(request,
         response);
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
   // On récupère le nom de la catégorie à partir de la requête
   String nom = request.getParameter("nom");

   // On initialise le message d'erreur qui sera affiché en cas de problème
   String message = "";

   // On récupère le id de la catégorie à partir de la requête
   Long idCategorie = Long.valueOf(request.getParameter("idCategorie"));

   // On définit l'URL de redirection en cas d'erreur
   String uriRedirect = "modifierCategorie?idCategorie=" + idCategorie + "&errorMessage=%s";

   // Si le nom de la catégorie est vide ou n'existe pas, on redirige avec un message d'erreur
   if (nom == null || nom.isEmpty() || nom.isBlank()) {
      message = "Veuillez renseignez le nom de la catégorie";
      redirectWithErrorMessage(response, uriRedirect, message);
         } else {
      // On récupère la catégorie en base de données
      Categorie categorie = em.find(Categorie.class, idCategorie);

      // Si aucune catégorie n'a été trouvée, on redirige avec un message d'erreur
      if (categorie == null) {
         message = "Aucune categorie correspondant à ce id";
         redirectWithErrorMessage(response, uriRedirect, message);
      } else {
         // On initialise la variable qui indique si la transaction a réussi
         boolean transactionOk = false;

         // On essaie de mettre à jour la catégorie en base de données
         try {
            // On démarre la transaction
            userTransaction.begin();

            // On met à jour le nom de la catégorie
            categorie.setName(nom);

            // On enregistre les modifications en base de données
            em.merge(categorie);

            // Si on arrive ici, c'est que tout s'est bien passé, on met donc la variable à vrai
            transactionOk = true;
         } catch (Exception e) {
            // Si une exception est levée, on affiche un message d'erreur
            System.out.print("Une erreur est survenue lors de l'enregistrement");
         } finally {
            try {
               // Si la transaction a réussi, on la valide
               if (transactionOk) {
                  userTransaction.commit();

                  // On redirige vers la page des catégories
                  response.sendRedirect("");
               } else {
                  // Sinon, on annule la transaction et on redirige avec un message d'erreur
                  message = "Une erreur est survenue lors de l'enregistrement veuillez contactez l'administrateur";
                  userTransaction.rollback();
                  redirectWithErrorMessage(response, uriRedirect, message);
               }
            } catch (Exception e) {
               // Si une exception est levée, on redirige avec un message d'erreur
               message = "Une erreur est survenue lors de l'enregistrement veuillez contactez l'administrateur";
               redirectWithErrorMessage(response, uriRedirect, message);
            }
         }
      }
   }
}


   private void redirectWithErrorMessage(HttpServletResponse response, String uriRedirect,
         String message) throws IOException {
      String urlRediret = String.format(uriRedirect, message);
      response.sendRedirect(urlRediret);
   }

}
