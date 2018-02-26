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
import Model.Entities.Vendeur;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

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

    private ToggleGroup filter;
    @FXML
    private JFXTextField txtSearch;

    @FXML
    private ImageView picVen;

    @FXML
    private JFXButton btnSearch;


    @FXML
    private AnchorPane holderPane;

    @FXML
    private ScrollPane acscroll;

    private boolean subbed = false;

    int id;

    VendeurDao daoVen;

    ArticlesDao daoArt;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        acscroll.setCenterShape(true);
        acscroll.setPadding(new Insets(20,0,0,30));

        acscroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        acscroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        this.daoVen = new VendeurDao();
        this.daoArt = new ArticlesDao();

        // setNode();

        // setCompte(42);

    }


    public void setCompte(int id)
    {
        this.id = id;


        Vendeur ven = daoVen.ChercherVen(id);


        List<Article> articles;

        articles = daoArt.SelectArticlesVen(id);


        lblName.setText(ven.getNom_boutique());
        lblEmail.setText(ven.getEmail());
        lblLocation.setText(ven.getAdresse());


        if (ven.getPhotoprof() != null)
        try {
            Blob blob = ven.getPhotoprof();
            byte byteImage[];
            byteImage = blob.getBytes(1,(int)blob.length());
            Image img = new Image(new ByteArrayInputStream(byteImage));
            picVen.setImage(img);

            picVen.setFitHeight(90);
            picVen.setFitWidth(90);

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

        String name = txtSearch.getText();

        articles = daoArt.SearchArticlesVen(id,name);

    //    System.out.println(name+" heyy");

        setNode(id,articles);

    }

    public void gesSubscribe(ActionEvent event) {

        if (subbed)
        {
            daoVen.unSubscribe(id);
            btnSub.setText("s'abonner");
            subbed = false;
        }
        else
        {
            daoVen.subscribe(id);
            btnSub.setText("désabonner");
            subbed = true;
        }

    }


}
