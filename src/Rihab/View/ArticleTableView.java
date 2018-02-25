package Rihab.View;

import Model.Entities.Article;
import javafx.beans.property.*;

public class ArticleTableView {
    private IntegerProperty id;
    private StringProperty categorie;
    private StringProperty nom;
    private StringProperty description;
    private FloatProperty prix;


    public ArticleTableView() {
    }

    public ArticleTableView(Article article) {
        this.id = new SimpleIntegerProperty(article.getId());
        this.categorie = new SimpleStringProperty(article.getCategorie());
        this.nom = new SimpleStringProperty(article.getNom());
        this.description = new SimpleStringProperty(article.getDescription());
        this.prix = new SimpleFloatProperty(article.getPrix());
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getCategorie() {
        return categorie.get();
    }

    public void setCategorie(String categorie) {
        this.categorie.set(categorie);
    }

    public StringProperty categorieProperty() {
        return categorie;
    }

    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public float getPrix() {
        return prix.get();
    }

    public void setPrix(float prix) {
        this.prix.set(prix);
    }

    public FloatProperty prixProperty() {
        return prix;
    }
}
