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
 * Servlet implementation class DeleteCategorie
 */
@WebServlet("/deleteCategorie")
public class DeleteCategorie extends HttpServlet {
   private static final long serialVersionUID = 1L;
   @PersistenceContext(unitName = "sample-jpa")
   private EntityManager em;
   @Resource
   private UserTransaction userTransaction;

   /**
    * Default constructor.
    */
   public DeleteCategorie() {
      // TODO Auto-generated constructor stub
   }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // On définit l'URL de redirection en cas d'erreur
      String uriRedirect = "categories.jsp?errorMessage=%s";
      // On définit un message d'erreur vide par défaut
      String message = "";

      try {
         // On récupère l'ID de la catégorie à supprimer à partir de la requête
         Long idCategorie = Long.parseLong(request.getParameter("idCategorie"));

         // On cherche la catégorie en base de données
         Categorie categorie = em.find(Categorie.class, idCategorie);

         // Si la catégorie n'a pas été trouvée, on redirige avec un message d'erreur
         if (categorie == null) {
            message = "Aucune catégorie correspondant à cet ID";
            redirectWithErrorMessage(response, uriRedirect, message);
         } else {
            // On définit un booléen pour savoir si la transaction a réussi
            boolean transactionOk = false;
            try {
               // On démarre la transaction
               userTransaction.begin();

               // On supprime la catégorie de la base de données
               em.remove(em.merge(categorie));

               // Si on arrive ici, la transaction a réussi
               transactionOk = true;
            } catch (Exception e) {
               // Si une exception est levée, on affiche un message d'erreur
               System.out.println("Une erreur est survenue lors de la suppression");
            } finally {
               try {
                  // Si la transaction a réussi, on la commit et on redirige vers la page des catégories
                  if (transactionOk) {
                     userTransaction.commit();
                     response.sendRedirect("categories.jsp");
                  } else {
                     // Si la transaction a échoué, on la rollback et on redirige avec un message d'erreur
                     message = "Une erreur est survenue lors de la suppression, veuillez contacter l'administrateur";
                     userTransaction.rollback();
                     redirectWithErrorMessage(response, uriRedirect, message);
                  }
               }             // Si une exception est levée lors du commit ou du rollback, on redirige avec un message d'erreur
               catch (Exception e) {
                  message = "Une erreur est survenue lors de la suppression, veuillez contacter l'administrateur";
                  redirectWithErrorMessage(response, uriRedirect, message);
               }
            }
         }
      } catch (Exception ex) {
         // Si une exception est levée lors de la récupération de l'ID ou de la recherche de la catégorie, on redirige avec un message d'erreur
         message = "Une erreur est survenue lors de la suppression, veuillez contacter l'administrateur";
         redirectWithErrorMessage(response, uriRedirect, message);
      }
   }


   private void redirectWithErrorMessage(HttpServletResponse response, String uriRedirect,
         String message) throws IOException {
      // On formate l'URL en remplaçant le placeholder par le message d'erreur
      String urlRediret = String.format(uriRedirect, message);

      // On redirige vers l'URL avec le message d'erreur
      response.sendRedirect(urlRediret);
   }
}
