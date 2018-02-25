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
public class BonPlan {
    
    private int id;
    
    private List<Article> articles = new ArrayList<>();
    
    private String type;
    
    private String offre;
    
    private Date date_deb;
    
    private Date dete_fin;

    public BonPlan() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOffre() {
        return offre;
    }

    public void setOffre(String offre) {
        this.offre = offre;
    }

    public Date getDate_deb() {
        return date_deb;
    }

    public void setDate_deb(Date date_deb) {
        this.date_deb = date_deb;
    }

    public Date getDete_fin() {
        return dete_fin;
    }

    public void setDete_fin(Date dete_fin) {
        this.dete_fin = dete_fin;
    }
    
    
    
    
    
}
