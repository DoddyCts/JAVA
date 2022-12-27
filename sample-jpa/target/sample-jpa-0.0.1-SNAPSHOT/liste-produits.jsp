<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<!-- Inclusion de l'en-tête -->
<%@ include file="/WEB-INF/template/header.jsp" %>

<style>
table {
  border-collapse: collapse;
  width: 100%;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}

th {
  background-color: #333;
  color: #fff;
}

a {
  color: #0088cc;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}
table {
  border-collapse: collapse;
  width: 100%;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}

th {
  background-color: #333;
  color: #fff; 
}

a {
  color: #0088cc;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}

</style>

<!-- Requête SQL pour récupérer la liste des produits -->
<sql:query var="produits" dataSource="jdbc/ma-ds">
  SELECT id, nom, description, prix, categorie_id FROM produit
</sql:query>

<!-- Contenu de la page -->
<main>
  <h1>Liste des produits</h1>
  <table>
    <thead>
      <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Description</th>
        <th>Prix</th>
        <th>Catégorie</th>
        <th>Modifier</th>
        <th>Supprimer</th>
      </tr>
    </thead>
    <tbody>
      <!-- Boucle sur les produits -->
      <c:forEach var="produit" items="${produits.rows}">
        <tr>
          <!-- Affichage des données du produit -->
          <td><c:out value="${produit.id}"/></td>
          <td><c:out value="${produit.nom}"/></td>
          <td><c:out value="${produit.description}"/></td>
          <td><c:out value="${produit.prix}"/></td>
          <td><c:out value="${produit.categorie_id}"/></td>
          <!-- Liens pour modifier et supprimer le produit -->
          <td>
            <a href="/modifier-produit?id=${produit.id}">Modifier</a>
          </td>
          <td>
            <a href="/supprimer-produit?id=${produit.id}">Supprimer</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</main>

<!-- Inclusion du pied de page -->
<%@ include file="/footer.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<!-- Inclusion de l'en-tête -->
<%@ include file="/header.jsp" %>

<!-- Requête SQL pour récupérer la liste des produits -->
<sql:query var="produits" dataSource="jdbc/ma-ds">
  SELECT id, nom, description, prix, categorie_id FROM produit
</sql:query>

<!-- Contenu de la page -->
<main>
  <h1>Liste des produits</h1>
  <table>
    <thead>
      <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Description</th>
        <th>Prix</th>
        <th>Catégorie</th>
        <th>Modifier</th>
        <th>Supprimer</th>
      </tr>
    </thead>
    <tbody>
      <!-- Boucle sur les produits -->
      <c:forEach var="produit" items="${produits.rows}">
        <tr>
          <!-- Affichage des données du produit -->
          <td><c:out value="${produit.id}"/></td>
          <td><c:out value="${produit.nom}"/></td>
          <td><c:out value="${produit.description}"/></td>
          <td><c:out value="${produit.prix}"/></td>
          <td><c:out value="${produit.categorie_id}"/></td>
          <!-- Liens pour modifier et supprimer le produit -->
          <td>
            <a href="/modifier-produit?id=${produit.id}">Modifier</a>
          </td>
          <td>
            <a href="/supprimer-produit?id=${produit.id}">Supprimer</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</main>

<!-- Inclusion du pied de page -->
<%@ include file="/WEB-INF/template/footer.jsp" %>
