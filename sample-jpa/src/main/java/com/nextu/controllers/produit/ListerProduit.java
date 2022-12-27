package com.nextu.controllers.produit;

import java.io.IOException;
import java.util.List;
import com.nextu.entities.Produit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListerProduit
 */
@WebServlet("/produits")
public class ListerProduit extends HttpServlet {
   private static final long serialVersionUID = 1L;
   @PersistenceContext(unitName = "sample-jpa")
   private EntityManager em;

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // Création de la requête JPA pour récupérer la liste des produits
      TypedQuery<Produit> query = em.createQuery("SELECT p FROM Produit p", Produit.class);
      // Exécution de la requête et récupération de la liste des produits
      List<Produit> produits = query.getResultList();
      // Ajout de la liste des produits à la requête HTTP
      request.setAttribute("produits", produits);
      // Transfert de la requête vers la page JSP
      this.getServletContext().getRequestDispatcher("/produits.jsp").forward(request, response);
   }
}
