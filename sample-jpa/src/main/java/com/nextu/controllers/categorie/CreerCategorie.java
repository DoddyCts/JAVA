package com.nextu.controllers.categorie;

import java.io.IOException;
import com.nextu.entities.Categorie;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.UserTransaction;

/**
 * Servlet de création de catégorie
 */
public class CreerCategorie extends HttpServlet {
   private static final long serialVersionUID = 1L;
   @Resource
   private UserTransaction userTransaction;
   @PersistenceContext(unitName = "sample-jpa")
   private EntityManager em;
   
   /**
    * @see HttpServlet#HttpServlet()
    */

   public CreerCategorie() {
      super();
      // TODO Auto-generated constructor stub
      }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
      // Récupération du libellé de la catégorie à créer
      String nom = request.getParameter("nom");
      // URI de redirection en cas d'erreur
      String uriRedirect = "form-categorie.jsp?errorMessage=%s";
      // Message d'erreur à afficher
      String errorMessage = "";

      // Vérification de l'unicité du libellé de la catégorie
      // Création d'une requête JPA pour récupérer une catégorie ayant le même nom
      TypedQuery<Categorie> query = em.createQuery("SELECT c FROM Categorie c WHERE c.nom = :nom", Categorie.class);
      // Passage du paramètre de la requête
      query.setParameter("nom", nom);
      // Récupération de la catégorie ayant le même nom
      Categorie existingCategorie = query.getSingleResult();

      // Vérification de la validité du libellé de la catégorie
      if (nom == null || nom.isEmpty() || nom.isBlank()) {
         // Si le libellé est vide, ajout d'un message d'erreur
         errorMessage = "Veuillez renseigner le libellé de la catégorie";
         // Redirection vers la page de formulaire avec le message d'erreur
         redirectWithErrorMessage(response, uriRedirect, errorMessage);
         // Vérification de l'unicité du libellé de la catégorie
      } 
      else if (existingCategorie != null) {
         // Si une catégorie avec le même nom existe déjà, ajout d'un message d'erreur
         errorMessage = "Une catégorie avec ce nom existe déjà";
         // Redirection vers la page de formulaire avec le message d'erreur
         redirectWithErrorMessage(response, uriRedirect, errorMessage);
      }
      else {
         // Si aucune catégorie avec le même nom n'a été trouvée, on peut continuer avec la création de la catégorie
         boolean transactionOk = false;
         try {
            // Début de la transaction
            userTransaction.begin();
            // Création de la nouvelle catégorie
            Categorie categorie = new Categorie();
            categorie.setName(nom);
            // Enregistrement de la nouvelle catégorie en base de données
            em.persist(categorie);
            // Indication que la transaction s'est déroulée correctement
            transactionOk = true;
         } 
         catch (Exception e) {
            // Si une exception est levée, ajout d'un message d'erreur
            errorMessage = "Une erreur est survenue lors de l'enregistrement de la catégorie";
            // Redirection vers la page de formulaire avec le message d'erreur
            redirectWithErrorMessage(response, uriRedirect, errorMessage);
         } 
         finally {
            try {
               if (transactionOk) {
                  // Si la transaction s'est déroulée correctement, validation de la transaction
                  userTransaction.commit();
                  // Redirection vers la page d'accueil
                  response.sendRedirect("");
               } 
               else {
                  errorMessage = "Une erreur est survenue lors de l'enregistrement veuillez contactez l'administrateur";
                  // Si la transaction a échoué, annulation de la transaction (rollback)
                  userTransaction.rollback();
                  // Redirection vers la page de formulaire avec le message d'erreur
                  redirectWithErrorMessage(response, uriRedirect, errorMessage);
               }
            } 
            catch (Exception e) {
               // Si une exception est levée lors de la validation ou de l'annulation de la transaction, ajout d'un message d'erreur
               errorMessage = "Une erreur est survenue lors de l'enregistrement de la catégorie";
               // Redirection vers la page de formulaire avec le message d'erreur
               redirectWithErrorMessage(response, uriRedirect, errorMessage);
            }
         }
      }
   }


   /**
   * Méthode de redirection vers la page de formulaire avec un message d'erreur
   * @param response Reponse HTTP
   * @param uri URI de redirection
   * @param errorMessage Message d'erreur
   * @throws IOException
   */

   private void redirectWithErrorMessage(HttpServletResponse response, String uri, String errorMessage) throws IOException {
      // Formatage de l'URI avec le message d'erreur
      String urlRedirect = String.format(uri, errorMessage);
      // Redirection vers l'URI
      response.sendRedirect(urlRedirect);
   }
}
