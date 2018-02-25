/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Entities;

import java.sql.Date;

/**
 *
 * @author FIRAS
 */
public class Publicity {
    
    private int id;
    
    private Article article;
    
    private Vendeur vendeur;
    
    private String pub_type;
    
    private String image_url;
    
    private Date date_deb;
    
    private Date date_fin;

    public Publicity() {
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

    public Vendeur getVendeur() {
        return vendeur;
    }

    public void setVendeur(Vendeur vendeur) {
        this.vendeur = vendeur;
    }

    public String getPub_type() {
        return pub_type;
    }

    public void setPub_type(String pub_type) {
        this.pub_type = pub_type;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
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
    
    
}
