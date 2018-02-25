/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author FIRAS
 */
public class Vendeur extends User{
    
    private String nom_boutique;
    
    private String description;
    
    private int note;
    
    private List<Article> articles = new ArrayList<>();
    
    private List<Acheteur> abonnes = new ArrayList<>();

    public Vendeur() {
    }

    public String getNom_boutique() {
        return nom_boutique;
    }

    public void setNom_boutique(String nom_boutique) {
        this.nom_boutique = nom_boutique;
    }

    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<Acheteur> getAbonnes() {
        return abonnes;
    }

    public void setAbonnes(List<Acheteur> abonnes) {
        this.abonnes = abonnes;
    }
    
    
    
    
}
