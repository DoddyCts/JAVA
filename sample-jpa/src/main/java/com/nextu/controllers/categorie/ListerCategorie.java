package com.nextu.controllers.categorie;

import java.io.IOException;
import java.util.List;
import com.nextu.entities.Categorie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet qui gère l'affichage de la liste des catégories.
 */
@WebServlet("/")
public class ListerCategorie extends HttpServlet {
   private static final long serialVersionUID = 1L;

   // L'EntityManager permet de gérer les opérations de persistence en base de données
   @PersistenceContext(unitName = "sample-jpa")
   private EntityManager em;

   /**
    * Constructeur par défaut.
    */
   public ListerCategorie() {
      // TODO Auto-generated constructor stub
   }

   /**
    * Méthode qui gère la requête GET envoyée lors de l'affichage de la liste des catégories.
    *
    * @param request La requête HTTP envoyée par le client
    * @param response La réponse HTTP à envoyer au client
    * @throws ServletException Si une exception est levée lors de l'exécution de la méthode
    * @throws IOException Si une erreur d'entrée/sortie est survenue lors de l'exécution de la méthode
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // On récupère la liste des catégories en base de données
      TypedQuery<Categorie> query = em.createQuery("SELECT s FROM Categorie s", Categorie.class);
      List<Categorie> categories = query.getResultList();

      // On passe la liste des catégories à la JSP pour affichage
      request.setAttribute("categories", categories);
      this.getServletContext().getRequestDispatcher("/categories.jsp").forward(request, response);
   }
}

