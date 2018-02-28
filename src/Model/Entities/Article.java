/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Entities;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author FIRAS
 */
public class Article {
    
    private int id;
    
    private String nom;
    
    private String description;
    
    private float prix;
    
    private List<Image> images = new ArrayList<>();
    
    private String categorie;
    
    private List<Avis> avis = new ArrayList<>();

    private int quantity;

    private int order_qte;

    private Timestamp date_pub;



    public Article() {
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Timestamp getDate_pub() {
        return date_pub;
    }

    public void setDate_pub(Timestamp date_pub) {
        this.date_pub = date_pub;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrder_qte() {
        return order_qte;
    }

    public void setOrder_qte(int order_qte) {
        this.order_qte = order_qte;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public List<Avis> getAvis() {
        return avis;
    }

    public void setAvis(List<Avis> avis) {
        this.avis = avis;
    }
    
    
    
    
    
    
    
}
