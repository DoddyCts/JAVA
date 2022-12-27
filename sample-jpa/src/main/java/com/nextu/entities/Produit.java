package com.nextu.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// Classe entity pour représenter un produit dans la base de données
@Entity
@Table(name = "produit")
public class Produit {
   // ID du produit, généré automatiquement par la base de données
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;
   // Nom du produit
   @Column(name = "nom")
   private String nom;
   // Description du produit
   @Column(name = "description")
   private String description;
   // Prix du produit
   @Column(name = "price")
   private double price;
   // Catégorie à laquelle appartient le produit
   @JoinColumn(name = "categorie_id")
   @ManyToOne(optional = false)
   private Categorie categorie;

   // Getter et setter pour l'ID du produit
   public Long getId() {
      return id;
   }
   public void setId(Long id) {
      this.id = id;
   }

   // Getter et setter pour le nom du produit
   public String getName() {
      return nom;
   }
   public void setName(String nom) {
      this.nom = nom;
   }

   // Getter et setter pour la description du produit
   public String getDescription() {
      return description;
   }
   public void setDescription(String description) {
      this.description = description;
   }

   // Getter et setter pour le prix du produit
   public double getPrice() {
      return price;
   }
   public void setPrice(double price) {
      this.price = price;
   }

   // Getter et setter pour la catégorie du produit
   public Categorie getCategorie() {
      return categorie;
   }
   public void setCategorie(Categorie categorie) {
      this.categorie = categorie;
   }
}
