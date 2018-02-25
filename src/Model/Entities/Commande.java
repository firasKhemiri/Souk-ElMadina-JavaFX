package Model.Entities;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Commande {

    private int id;
    private User acheteur;
    private String adress_liv;
    private String methode_paiment;
    private String methode_livraison;
    private Panier panier;
    private Date date;
    private int etat;
    private List<Article> articles;


    public Commande(User acheteur, String adress_liv, String methode_paiment, String methode_livraison, Panier panier, Date date, int etat) {
        this.acheteur = acheteur;
        this.adress_liv = adress_liv;
        this.methode_paiment = methode_paiment;
        this.methode_livraison = methode_livraison;
        this.panier = panier;
        this.date = date;
        this.etat = etat;
    }

    public Commande(int id, User acheteur, String adress_liv, String methode_paiment, String methode_livraison, Panier panier, Date date, int etat) {
        this.id = id;
        this.acheteur = acheteur;
        this.adress_liv = adress_liv;
        this.methode_paiment = methode_paiment;
        this.methode_livraison = methode_livraison;
        this.panier = panier;
        this.date = date;
        this.etat = etat;
    }

    public Commande() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAcheteur() {
        return acheteur;
    }

    public void setAcheteur(User acheteur) {
        this.acheteur = acheteur;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public String getAdress_liv() {
        return adress_liv;
    }

    public void setAdress_liv(String adress_liv) {
        this.adress_liv = adress_liv;
    }

    public String getMethode_paiment() {
        return methode_paiment;
    }

    public void setMethode_paiment(String methode_paiment) {
        this.methode_paiment = methode_paiment;
    }

    public String getMethode_livraison() {
        return methode_livraison;
    }

    public void setMethode_livraison(String methode_livraison) {
        this.methode_livraison = methode_livraison;
    }

    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commande)) return false;
        Commande commande = (Commande) o;
        return getId() == commande.getId() &&
                getEtat() == commande.getEtat() &&
                Objects.equals(getAcheteur(), commande.getAcheteur()) &&
                Objects.equals(getAdress_liv(), commande.getAdress_liv()) &&
                Objects.equals(getMethode_paiment(), commande.getMethode_paiment()) &&
                Objects.equals(getMethode_livraison(), commande.getMethode_livraison()) &&
                Objects.equals(getPanier(), commande.getPanier()) &&
                Objects.equals(getDate(), commande.getDate());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getAcheteur(), getAdress_liv(), getMethode_paiment(), getMethode_livraison(), getPanier(), getDate(), getEtat());
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", acheteur=" + acheteur +
                ", adress_liv='" + adress_liv + '\'' +
                ", methode_paiment='" + methode_paiment + '\'' +
                ", methode_livraison='" + methode_livraison + '\'' +
                ", panier=" + panier +
                ", date=" + date +
                ", etat=" + etat +
                '}';
    }

    public float getPrix() {
        float prix = 0;
        for (Article article : panier.getArticleList()) {
            prix += article.getPrix();
        }
        return prix;
    }
}
