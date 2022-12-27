package com.nextu.entities;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// Classe entity pour représenter une catégorie de produits dans la base de données
@Entity
@Table(name = "categorie")
public class Categorie {
   // ID de la catégorie, généré automatiquement par la base de données
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   // Nom de la catégorie
   @Column(name = "nom")
   private String nom;
   // Ensemble de produits associés à cette catégorie
   @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "category")
   private Set<Produit> produits;

   // Getter et setter pour l'ID de la catégorie
   public Long getId() {
      return id;
   }
   public void setId(Long id) {
      this.id = id;
   }

   // Getter et setter pour le nom de la catégorie
   public String getName() {
      return nom;
   }
   public void setName(String nom) {
      this.nom = nom;
   }

   // Méthode pour ajouter un produit à cette catégorie
   public void addProduit(Produit produit) {
      if (this.produits == null) {
         this.produits = new HashSet<>();
      }
      this.produits.add(produit);
   }

   // Méthode pour supprimer un produit de cette catégorie
   public void removeProduit(Produit produit) {
      if (this.produits != null)
         produits.remove(produit);
   }

   // Getter pour l'ensemble de produits associés à cette catégorie
   public Set<Produit> getProduits() {
      return produits;
   }
}
