package com.nextu.controllers.produit;

import java.io.IOException;
import java.util.List;
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
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;

import com.nextu.entities.Categorie;
import com.nextu.entities.Produit;

@WebServlet("/createProduit")
public class CreerProduit extends HttpServlet {
   private static final long serialVersionUID = 1L;
   @PersistenceContext(unitName = "example-jpa")
   private EntityManager em;
   @Resource
   private UserTransaction userTransaction;

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      TypedQuery<Categorie> query = em.createQuery("SELECT c FROM Categorie c", Categorie.class);
      List<Categorie> categories = query.getResultList();
      request.setAttribute("categories", categories);
      this.getServletContext().getRequestDispatcher("/form-produit.jsp").forward(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
      // URI de redirection en cas d'erreur
      String uriRedirect = "form-produit.jsp?errorMessage=%s";
      // Message d'erreur à afficher
      String errorMessage = "";
      
      // Récupération des informations
      String nom = request.getParameter("nom");
      String description = request.getParameter("description");
      String priceStr = request.getParameter("prix");
      double price = 0;
      
      // Vérification de la validité des données
      if (nom == null || nom.trim().isEmpty()) {
         errorMessage = "Le nom de l'article est obligatoires.";  
         // Redirection vers la page de formulaire avec le message d'erreur
         redirectWithErrorMessage(response, uriRedirect, errorMessage);
         return;
      }

      if (description == null) {
         errorMessage = "La description est obligatoire.";  
         // Redirection vers la page de formulaire avec le message d'erreur
         redirectWithErrorMessage(response, uriRedirect, errorMessage);
         return;
      }
  
      try {
         price = Double.parseDouble(priceStr);
      } catch (NumberFormatException e) {
         errorMessage = "Le prix doit être un nombre valide.";  
         // Redirection vers la page de formulaire avec le message d'erreur
         redirectWithErrorMessage(response, uriRedirect, errorMessage);
         return;
      }
      
      if (price <= 0){
         errorMessage = "Le prix ne peut pas être inférieur ou égal à 0 !";  
         // Redirection vers la page de formulaire avec le message d'erreur
         redirectWithErrorMessage(response, uriRedirect, errorMessage);
         return;
      }
      
      Categorie categorie = this.findCategorie(request.getParameter("categorie"));
      if (categorie == null) {
         errorMessage = "Aucun enregistrement de catégories trouvé";
         // Redirection vers la page de formulaire avec le message d'erreur
         redirectWithErrorMessage(response, uriRedirect, errorMessage);
         return;
      }
      
      // Si aucune erreur n'a été détectée, enregistrement du produit
      saveProduit(response, request, nom, description, price, categorie);
   }


   private void saveProduit(HttpServletResponse response, HttpServletRequest request, String nom, String description, double price, Categorie categorie) throws IOException {
   // URI de redirection en cas d'erreur
   String uriRedirect = "form-produit.jsp?errorMessage=%s";
   // Message d'erreur à afficher
   String errorMessage = "";
   try {
      userTransaction.begin();
      Produit produit = new Produit();
      produit.setName(nom);
      produit.setDescription(description);
      produit.setPrice(price);
      produit.setCategorie(em.merge(categorie));
      em.persist(produit);
      userTransaction.commit();
      response.sendRedirect("produits");
   } catch (Exception e) {
      // Annulation de la transaction en cas d'erreur
      try {
		userTransaction.rollback();
	} catch (IllegalStateException | SecurityException | SystemException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
      errorMessage = "Une erreur est survenue lors de l'enregistrement. Veuillez contacter l'administrateur.";
      // Redirection vers la page de formulaire avec le message d'erreur
      redirectWithErrorMessage(response, uriRedirect, errorMessage);
   }
}
	

private Categorie findCategorie(String idCategorie) throws IOException {
   Categorie categorie = null;
   try {
      Long id = Long.valueOf(idCategorie);
      categorie = em.find(Categorie.class, id);
      if (categorie == null) {
         throw new EntityNotFoundException("Aucune catégorie trouvée avec l'ID " + id);
      }
   } catch (EntityNotFoundException e) {
      System.out.println(e.getMessage());
   }  catch (Exception ex) {
      System.out.println("Une erreur est survenue lors de la recherche de la catégorie. Veuillez contacter l'administrateur.");
   }
   return categorie;
}


	private void redirectWithErrorMessage(HttpServletResponse response, String uriRedirect,
	        String message) throws IOException {
	     String urlRediret = String.format(uriRedirect, message);
	     response.sendRedirect(urlRediret);
	  }

}
