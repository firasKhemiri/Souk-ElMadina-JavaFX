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
public class Notification {
    
    private int id;
    
    private User user;
    
    private String message_notif;
    
    private String image_url;
    
    private String type;
    
    private Article article;
    
    private Vendeur vendeur;
    
    private BonPlan bonplan;
    
    private Enchere enchere;

    public Notification() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage_notif() {
        return message_notif;
    }

    public void setMessage_notif(String message_notif) {
        this.message_notif = message_notif;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public BonPlan getBonplan() {
        return bonplan;
    }

    public void setBonplan(BonPlan bonplan) {
        this.bonplan = bonplan;
    }

    public Enchere getEnchere() {
        return enchere;
    }

    public void setEnchere(Enchere enchere) {
        this.enchere = enchere;
    }
    
    
    
    
}
