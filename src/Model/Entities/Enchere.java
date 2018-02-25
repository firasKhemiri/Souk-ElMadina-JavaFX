/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author FIRAS
 */
public class Enchere {
 
    private int id;
    
    private List<Acheteur> acheteurs = new ArrayList<>();
    
    private Acheteur winner;
    
    private Article article;
    
    private Date date_deb;
    
    private Date date_fin;
    
    private float prixActuel;

    public Enchere() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Acheteur> getAcheteurs() {
        return acheteurs;
    }

    public void setAcheteurs(List<Acheteur> acheteurs) {
        this.acheteurs = acheteurs;
    }

    public Acheteur getWinner() {
        return winner;
    }

    public void setWinner(Acheteur winner) {
        this.winner = winner;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Date getDate_deb() {
        return date_deb;
    }

    public void setDate_deb(Date date_deb) {
        this.date_deb = date_deb;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public float getPrixActuel() {
        return prixActuel;
    }

    public void setPrixActuel(float prixActuel) {
        this.prixActuel = prixActuel;
    }
    
    
}
