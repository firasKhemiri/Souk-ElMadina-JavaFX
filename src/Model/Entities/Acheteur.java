/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Entities;

import java.util.List;

/**
 *
 * @author FIRAS
 */
public class Acheteur extends User{
    
    private List<Commande> commandes;
    private List<Vendeur> abonements;
    private List<Enchere> encheres;
    
    private List<Article> watchList;

    public Acheteur() {
    }

   
    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }

    public List<Vendeur> getAbonements() {
        return abonements;
    }

    public void setAbonements(List<Vendeur> abonements) {
        this.abonements = abonements;
    }

    public List<Enchere> getEncheres() {
        return encheres;
    }

    public void setEncheres(List<Enchere> encheres) {
        this.encheres = encheres;
    }

    public List<Article> getWatchList() {
        return watchList;
    }

    public void setWatchList(List<Article> watchList) {
        this.watchList = watchList;
    }
    
    
    
    
    
    
}
