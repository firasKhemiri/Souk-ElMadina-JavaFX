/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Entities;

/**
 *
 * @author FIRAS
 */
public class Reclamation {
    
    private int id;
    
    private Article article;
    
    private Acheteur acheteur;
    
    private String reclamation;

    public Reclamation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Acheteur getAcheteur() {
        return acheteur;
    }

    public void setAcheteur(Acheteur acheteur) {
        this.acheteur = acheteur;
    }

    public String getReclamation() {
        return reclamation;
    }

    public void setReclamation(String reclamation) {
        this.reclamation = reclamation;
    }
    
    
    
}
