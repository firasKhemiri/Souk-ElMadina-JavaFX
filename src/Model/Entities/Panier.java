package Model.Entities;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Panier {

    private int id;
    private User acheteur;
    private Date date_modif;
    private List<Article> articleList;

    public Panier(User acheteur, Date date_modif, List<Article> articleList) {
        this.acheteur = acheteur;
        this.date_modif = date_modif;
        this.articleList = articleList;
    }

    public Panier(int id, User acheteur, Date date_modif, List<Article> articleList) {
        this.id = id;
        this.acheteur = acheteur;
        this.date_modif = date_modif;
        this.articleList = articleList;
    }

    public Panier() {
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

    public Date getDate_modif() {
        return date_modif;
    }

    public void setDate_modif(Date date_modif) {
        this.date_modif = date_modif;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Panier)) return false;
        Panier panier = (Panier) o;
        return getId() == panier.getId() &&
                Objects.equals(getAcheteur(), panier.getAcheteur()) &&
                Objects.equals(getDate_modif(), panier.getDate_modif());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getAcheteur(), getDate_modif());
    }

    @Override
    public String toString() {
        return "Panier{" +
                "id=" + id +
                ", acheteur=" + acheteur +
                ", date_modif=" + date_modif +
                ", articleList=" + articleList +
                '}';
    }
}
