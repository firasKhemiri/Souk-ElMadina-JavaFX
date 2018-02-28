package Controller;

import Model.DAO.ArticlesDao;
import Model.DAO.VendeurDao;
import Model.Entities.Article;
import Model.Entities.Vendeur;
import Views.modules.ItemController;
import Views.modules.Vendeur.ItemVenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Screen;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ArticlesController implements Initializable
{

    private int id;
    @FXML
    private GridPane greadpane;

    @FXML
    private AnchorPane AnchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        final Label label = new Label("Address Book");

    }

    public void setCompte(int id, List<Article> articles)
    {
        this.id = id;

        greadpane.setPadding(new Insets(15, 0, 5, 0));
        greadpane.setVgap(24);
        greadpane.setHgap(54);

        try {

            int k = 0;
            int j = 0;

            for (Article article : articles) {

                if (j > 4) {
                    j = 0;
                    k++;
                }

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Views/modules/Item.fxml"));

                Parent root = fxmlLoader.load();
                Node node = (Node) root;

                ItemController controller = fxmlLoader.getController();
                controller.setArticle(article);

                greadpane.add(node, j, k);

                j++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public void AllArticles()
    {

        greadpane.setPadding(new Insets(15, 0, 5, 50));
        greadpane.setVgap(24);
        greadpane.setHgap(34);

        try {

            ArticlesDao daoArt = new ArticlesDao();

            List<Article> articles = new ArrayList<>();
            articles = daoArt.SelectArticles();

            int k = 0;
            int j = 0;

            GridPatternArticles(articles, k, j);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public void searchArtciles(String nomArt, String boutique,String gov, String categorie, String tri, double pmin, double pmax)
    {
        this.id = id;

        greadpane.setPadding(new Insets(15, 0, 5, 50));
        greadpane.setVgap(24);
        greadpane.setHgap(34);

        try {

            ArticlesDao daoArt = new ArticlesDao();

            System.out.println(" "+ boutique +" "+nomArt+" "+categorie+" "+tri+" "+gov+" "+pmin+" "+pmax);

            List<Article> articles;
            articles = daoArt.SearchArticlesForm(boutique,nomArt,categorie,tri,gov,pmin,pmax);

            int k = 0;
            int j = 0;

            GridPatternArticles(articles, k, j);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void GridPatternArticles(List<Article> articles, int k, int j) throws IOException {
        for (Article article : articles) {

            if (j > 4) {
                j = 0;
                k++;
            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Views/modules/Item.fxml"));

            Parent root = fxmlLoader.load();

            ItemController controller = fxmlLoader.getController();
            controller.setArticle(article);

            greadpane.add(root, j, k);

            j++;
        }
    }


    public void AllVendeurs()
    {
        this.id = id;

        greadpane.setPadding(new Insets(15, 0, 5, 50));
        greadpane.setVgap(24);
        greadpane.setHgap(34);

        try {

            VendeurDao daoVen = new VendeurDao();

            List<Vendeur> vendeurs;
            vendeurs = daoVen.SelectVendeurs();

            int k = 0;
            int j = 0;

            GridPatternVen(vendeurs, k, j);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




    public void ChercherVendeurs(String nom,String gov)
    {
        this.id = id;

        greadpane.setPadding(new Insets(15, 0, 5, 0));
        greadpane.setVgap(24);
        greadpane.setHgap(54);

        try {

            VendeurDao daoVen = new VendeurDao();

            List<Vendeur> vendeurs;
            vendeurs = daoVen.ChercherVendeursNomGov(nom,gov);

            int k = 0;
            int j = 0;

            GridPatternVen(vendeurs, k, j);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public void ChercherVendeursForm(String nom,String gov,String type)
    {
        this.id = id;

        greadpane.setPadding(new Insets(15, 0, 5, 50));
        greadpane.setVgap(24);
        greadpane.setHgap(34);

        try {

            VendeurDao daoVen = new VendeurDao();

            List<Vendeur> vendeurs;
            vendeurs = daoVen.ChercherVendeursForm(nom,gov,type);

            int k = 0;
            int j = 0;

            GridPatternVen(vendeurs, k, j);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void GridPatternVen(List<Vendeur> vendeurs, int k, int j) throws IOException {
        for (Vendeur vendeur : vendeurs) {

            if (j > 4) {
                j = 0;
                k++;
            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Views/modules/Vendeur/ItemVen.fxml"));

            Parent root = fxmlLoader.load();

            ItemVenController controller = fxmlLoader.getController();
            controller.setVendeur(vendeur);

            greadpane.add(root, j, k);

            j++;
        }
    }



    public void venArts(int id)
    {
        this.id = id;

        ArticlesDao daoArt = new ArticlesDao();

        List<Article> articles = new ArrayList<>();

        articles = daoArt.SelectArticlesVen(id);

        greadpane.setPadding(new Insets(15, 0, 5, 50));
        greadpane.setVgap(24);
        greadpane.setHgap(34);

        try {

            int k = 0;
            int j = 0;

            for (Article article : articles) {

                if (j > 4) {
                    j = 0;
                    k++;
                }

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Views/modules/Item.fxml"));

                Parent root = fxmlLoader.load();
                Node node = (Node) root;

                ItemController controller = fxmlLoader.getController();
                controller.setArticleModif(article);

                greadpane.add(node, j, k);

                j++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




    @FXML
    private void back()
    {

    }



}
