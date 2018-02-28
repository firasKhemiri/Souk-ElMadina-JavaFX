/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO.ArticlesDao;
import Model.DAO.UserDAO;
import Model.DAO.VendeurDao;
import Model.Entities.Article;
import Model.Entities.Categorie;
import Model.Entities.Vendeur;
import com.jfoenix.controls.*;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Rating;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class CompteVenController implements Initializable {

    public JFXButton btnSub;
    public Label lblNbrArt;
    public Rating rating;
    public Label lblSubs;
    public ImageView imgVen;
    public Label lblType;
    public JFXComboBox<String> cmbCategorie;
    public Label lblPrixMin;
    public JFXSlider sldPrixMin;
    public Label lblPrixMax;
    public JFXSlider sldPrixMax;
    public JFXComboBox<String> comboTriType;
    @FXML
    private Label fabEdit;
    @FXML
    private AnchorPane fabPane;
    @FXML
    private AnchorPane holderAnchor;
    @FXML
    private Label lblName;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblPhone;
    @FXML
    private Label lblLocation;

    private int subs = 0;

    private Vendeur ven = new Vendeur();

    @FXML
    private ToggleGroup filter;
    @FXML
    private JFXTextField txtSearch;


    @FXML
    private JFXButton btnSearch;


    @FXML
    private AnchorPane holderPane;

    @FXML
    private ScrollPane acscroll;

    private boolean subbed = false;

    private int id;

    private VendeurDao daoVen;

    private ArticlesDao daoArt;

    @Override
    public void initialize(URL url, ResourceBundle rb) {


        daoArt = new ArticlesDao();
        List<Categorie> categories = daoArt.SelectCategories();


        cmbCategorie.getItems().addAll("Tout les categories");

        for (Categorie categorie : categories)
            cmbCategorie.getItems().addAll(categorie.getNom());

        comboTriType.getItems().addAll("Pas de tri","Prix croissant","Prix decroissant","Date pub croissant", "date pub decroissant","Alphabetique");

        cmbCategorie.getSelectionModel().select("Tout les categories");

        comboTriType.getSelectionModel().select("Pas de tri");


        sldPrixMin.setValue(0);

        sldPrixMax.setValue(1000);



        sldPrixMin.valueProperty().addListener((ChangeListener) (arg0, arg1, arg2) -> lblPrixMin.textProperty().setValue( "Prix min: "+
                String.valueOf((int) sldPrixMin.getValue())+ " dt"));

        sldPrixMax.valueProperty().addListener((ChangeListener) (arg0, arg1, arg2) -> lblPrixMax.textProperty().setValue( "Prix max: "+
                String.valueOf((int) sldPrixMax.getValue()) + " dt"));



        acscroll.setCenterShape(true);
        acscroll.setPadding(new Insets(20,0,0,30));

        acscroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        acscroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        this.daoVen = new VendeurDao();

        // setNode();

        // setCompte(42);

    }


    public void setCompte(int id)
    {
        this.id = id;


        ven = daoVen.ChercherVen(id);


        List<Article> articles;

        articles = daoArt.SelectArticlesVen(id);


        lblName.setText(ven.getNom_boutique());
        lblEmail.setText(ven.getEmail());
        lblLocation.setText(ven.getAdresse());


        lblPhone.setText(ven.getPhone());


        rating.setRating(ven.getNote());
        rating.setDisable(true);

        subs = daoVen.selectSubs(ven.getId()).size();
        lblSubs.setText(String.valueOf(subs) + " utilisateurs sont abonnés a "+ven.getNom_boutique());


        if (articles.size()>10) {
            lblType.setText("Professionnel");
        }
        else
            lblType.setText("Amateur");

        lblNbrArt.setText(ven.getNom_boutique()+" a "+ articles.size()+ " articles");



        if (ven.getPhotoprof() != null)
        try {
            Blob blob = ven.getPhotoprof();
            byte byteImage[];
            byteImage = blob.getBytes(1,(int)blob.length());
            Image img = new Image(new ByteArrayInputStream(byteImage));
            imgVen.setImage(img);

            imgVen.setFitHeight(180);
            imgVen.setFitWidth(320);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        subbed = daoVen.isSubbed(ven.getId());

        if (subbed) {
            btnSub.setText("Désabonner");
        }
        else {
            btnSub.setText("s'abonner");
        }

        setNode(id,articles);


    }


    private void setNode(int id, List<Article> articles) {

        AnchorPane node;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Views/modules/Articles.fxml"));

            node = fxmlLoader.load();

            ArticlesController controller = fxmlLoader.getController();
            controller.setCompte(id,articles);

            holderPane.getChildren().clear();
            holderPane.getChildren().add((Node) node);

            FadeTransition ft = new FadeTransition(Duration.millis(1500));
            ft.setNode(node);
            ft.setFromValue(0.1);
            ft.setToValue(1);
            ft.setCycleCount(1);
            ft.setAutoReverse(false);
            ft.play();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void setRipples() {
        JFXRippler fXRippler = new JFXRippler(fabEdit);
        fXRippler.setMaskType(JFXRippler.RipplerMask.CIRCLE);
        fXRippler.setRipplerFill(Paint.valueOf("#FF80AF"));
        fabPane.getChildren().add(fXRippler);

    }

    private void getStudentInfo(Integer id) {

    }

    @FXML
    private void search()
    {

        List<Article> articles;


        String nom = txtSearch.getText().trim();

        String categorie = cmbCategorie.getValue();
        String tri = comboTriType.getValue();

        if (categorie .equals( "Tout les categories"))
            categorie = "";
        if (tri .equals( "Pas de tri"))
            tri = "";

        double pmin  = sldPrixMin.getValue();
        double pmax  = sldPrixMax.getValue();

        articles = daoArt.SearchArticlesForm(ven.getNom_boutique(),nom,categorie,tri,"",pmin,pmax);

        setNode(id,articles);

    }

    public void gesSubscribe(ActionEvent event) {

        if (subbed)
        {
            daoVen.unSubscribe(id);
            btnSub.setText("s'abonner");
            subbed = false;

            subs--;
            lblSubs.setText(String.valueOf(subs) + " sont abonnés a "+ven.getNom_boutique());
        }
        else
        {
            daoVen.subscribe(id);
            btnSub.setText("désabonner");
            subbed = true;
            subs++;
            lblSubs.setText(String.valueOf(subs) + " sont abonnés a "+ven.getNom_boutique());
        }

    }


    public void openHome(ActionEvent event) {

        try {
            Scene scene = btnSub.getParent().getScene();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/Accueil.fxml"));

            Parent root = fxmlLoader.load();

            scene.setRoot(root);

            AccueilController controller = fxmlLoader.getController();

            //    controller.(vendeur.getId());


            FadeTransition ft = new FadeTransition(Duration.millis(1500));
            ft.setNode(root);
            ft.setFromValue(0.1);
            ft.setToValue(1);
            ft.setCycleCount(1);
            ft.setAutoReverse(false);
            ft.play();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void logOff(ActionEvent event) {

        btnSub.getScene().getWindow().hide();
        Stage dashboardStage = new Stage();
        dashboardStage.setTitle("");
        // Parent root = FXMLLoader.load(getClass().getResource("/Views/Accueil.fxml"));

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/Login.fxml"));

        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        dashboardStage.setScene(scene);
        dashboardStage.show();



    }

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

}
