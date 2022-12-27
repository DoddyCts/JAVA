<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
    crossorigin="anonymous">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="css/main.css">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
  
</head>
<body>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- En-tête du site -->
<header>
  <nav>
    <a href="/">Accueil</a>
    <a href="/liste-produits">Catalogue de produits</a>
    <a href="/enregistrer-produit">Ajouter un produit</a>
  </nav>

  <!-- Liste des catégories -->
  <ul>
    <c:forEach items="${categories}" var="categorie">
      <li>
        <a href="/liste-produits?categorie_id=${categorie.id}">${categorie.nom}</a>
      </li>
    </c:forEach>
  </ul>
</header>

<style>
  /* Style de la barre de navigation */
  nav {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #333;
    color: #fff;
    padding: 0.5rem 1rem;
  }

  /* Style des liens de la barre de navigation */
  nav a {
    color: #fff;
    text-decoration: none;
  }

  /* Style de la liste des catégories */
  ul {
    display: flex;
    list-style: none;
    margin: 0;
    padding: 0;
  }

  /* Style des liens des catégories */
  ul a {
    display: block;
    padding: 0.5rem 1rem;
    color: #333;
    text-decoration: none;
  }

  /* Style des liens des catégories en surbrillance */
  ul a:hover {
    background-color: #eee;
  }
</style>
 

