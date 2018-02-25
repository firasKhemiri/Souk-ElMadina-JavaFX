package Views.modules;


import Controller.ArticlesController;
import Model.DAO.ArticlesDao;
import Model.Entities.Categorie;
import com.jfoenix.controls.*;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Tabs implements Initializable {


    @FXML
    private JFXTextField txtNomArt;

    @FXML
    private JFXComboBox<String> cmbCategorie;

    @FXML
    private Label lblPrixMin;

    @FXML
    private JFXSlider sldPrixMin;

    @FXML
    private Label lblPrixMax;

    @FXML
    private JFXSlider sldPrixMax;

    @FXML
    private JFXTextField txtVen;

    @FXML
    private JFXComboBox<String> comboTriType;

    @FXML
    private JFXComboBox<String> comboArtGov;

    @FXML
    private JFXButton btnSearchArt;



    @FXML
    public JFXTextField txtNomVen;

    @FXML
    private JFXComboBox<String> comboVenGov;

    @FXML
    private JFXComboBox<String> comboVenType;
    @FXML
    private JFXButton btnSearchVen;




    @FXML
    private AnchorPane holderPane;

    @FXML
    private ScrollPane acscroll;

    @FXML
    private JFXTabPane tabPane;


    @FXML
    private AnchorPane holderPane2;

    @FXML
    private ScrollPane acscroll2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        acscroll.setFitToHeight(true);
        acscroll.setFitToWidth(true);

        acscroll.setCenterShape(true);
        acscroll.setPadding(new Insets(20,0,0,30));

        acscroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        acscroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);


     //   tabPane.getStylesheets().addArticle("/Views/modules/jfoenix-components.css");

        ArticlesDao daoArt = new ArticlesDao();
        List<Categorie> categories = daoArt.SelectCategories();


        cmbCategorie.getItems().addAll("Tout les categories");

        for (Categorie categorie : categories)
            cmbCategorie.getItems().addAll(categorie.getNom());

        comboArtGov.getItems().addAll("Tous les governorats","Ariana","Ben Arous","Tunis","Sousse","La Manouba","Sfax","Nabeul","Monastir","Béja","Bizerte","Gabès","Gafsa","Jendouba",
                "Kairouan","Kasserine","Kébili","Le Kef","Médenine","Sidi Bouzid","Siliana","Tataouine","Tozeur","Zaghouan");

        comboVenGov.getItems().addAll("Tous les governorats","Ariana","Ben Arous","Tunis","Sousse","Manouba","Sfax","Nabeul","Monastir","Béja","Bizerte","Gabès","Gafsa","Jendouba",
                "Kairouan","Kasserine","Kébili","Le Kef","Médenine","Sidi Bouzid","Siliana","Tataouine","Tozeur","Zaghouan");

        comboVenType.getItems().addAll("Tous les types","Amateur","Professionnel");

        comboTriType.getItems().addAll("Pas de tri","Prix croissant","Prix decroissant","Date pub croissant", "date pub decroissant","Alphabetique");



        cmbCategorie.getSelectionModel().select("Tout les categories");

        comboArtGov.getSelectionModel().select("Tous les governorats");

        comboVenGov.getSelectionModel().select("Tous les governorats");

        comboTriType.getSelectionModel().select("Pas de tri");

        comboVenType.getSelectionModel().select("Tous les types");

        sldPrixMin.setValue(0);

        sldPrixMax.setValue(1000);



        sldPrixMin.valueProperty().addListener((ChangeListener) (arg0, arg1, arg2) -> lblPrixMin.textProperty().setValue( "Prix min: "+
                String.valueOf((int) sldPrixMin.getValue())+ " dt"));

        sldPrixMax.valueProperty().addListener((ChangeListener) (arg0, arg1, arg2) -> lblPrixMax.textProperty().setValue( "Prix max: "+
                String.valueOf((int) sldPrixMax.getValue()) + " dt"));


        try {
            Node node;

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../modules/Articles.fxml"));
            node = fxmlLoader.load();

            ArticlesController controller = fxmlLoader.getController();
            controller.AllArticles();
            setNode(node ,holderPane);



            fxmlLoader = new FXMLLoader(getClass().getResource("../modules/Articles.fxml"));
            node = fxmlLoader.load();

            controller = fxmlLoader.getController();
            controller.AllVendeurs();
            setNode(node ,holderPane2);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void setNode(Node node , AnchorPane holderPane) {

        holderPane.getChildren().clear();
        holderPane.getChildren().add(node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }





    public void searchArticles()
    {
        try {
            Node node;

            String nom = txtNomArt.getText().trim();
            String boutique = txtVen.getText().trim();

            String gov = comboArtGov.getValue();
            String categorie = cmbCategorie.getValue();
            String tri = comboTriType.getValue();

            if (gov.equals("Tous les governorats")) {
                gov = "";
            }
            if (categorie .equals( "Tout les categories"))
                categorie = "";
            if (tri .equals( "Pas de tri"))
                tri = "";




            double pmin  = sldPrixMin.getValue();
            double pmax  = sldPrixMax.getValue();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../modules/Articles.fxml"));

            node = fxmlLoader.load();

            ArticlesController controller = fxmlLoader.getController();
            controller.searchArtciles(nom,boutique,gov,categorie,tri,pmin,pmax);
            setNode(node ,holderPane);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public void searchVendeurs()
    {
        try {
            Node node;



            String nom = txtNomVen.getText();
            String gov = comboVenGov.getValue();
            String type = comboVenType.getValue();

            if (gov.equals("Tous les governorats")) {
                gov = "";
            }
            if (type.equals( "Tous les types"))
                type = "";


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../modules/Articles.fxml"));

            node = fxmlLoader.load();

            ArticlesController controller = fxmlLoader.getController();
            controller.ChercherVendeursForm(nom,gov,type);
            setNode(node ,holderPane2);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}
