<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Inclusion de l'en-tÃªte -->
<%@ include file="/WEB-INF/template/header.jsp" %>

<!-- DÃ©claration des styles -->
<style>
  main {
    width: 80%;
    margin: 0 auto;
    text-align: center;
  }
  h1 {
    font-size: 3em;
    margin-bottom: 1em;
  }
  p {
    font-size: 1.5em;
    margin-bottom: 1em;
  }
  ul {
    font-size: 1.5em;
    margin-bottom: 1em;
  }
  li {
    margin-bottom: 0.5em;
  }
</style>

<!-- Contenu de la page d'accueil -->
<main>
  <h1>Bienvenue sur notre catalogue de produits</h1>
  <p>Notre projet consiste en un <a href="/liste-produits">catalogue de produits en ligne</a>, dÃ©veloppÃ© avec les technologies suivantes :</p>
  <ul>
    <li>Frontend : JSP, JSLT (optionnel), JavaScript (optionnel)</li>
    <li>Backend : Postgresql 12 (base de donnÃ©es), Jakarta EE 10 (servlets, JPA, Widfly 27)</li>
  </ul>
  <p>La solution finale est un fichier WAR qui peut Ãªtre dÃ©ployÃ© sur un serveur d'application compatible avec Jakarta EE 10.</p>
</main>

<!-- Inclusion du pied de page -->
<%@ include file="/WEB-INF/template/footer.jsp" %>
