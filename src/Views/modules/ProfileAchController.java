/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.modules;

import Model.DAO.UserDAO;
import Model.Entities.Acheteur;
import Model.Entities.CurrentUser;
import Rihab.Controller.PanierController;
import Views.modules.Article.ArticleDetails;
import Views.modules.ProfileModules.ModifAcheteur;
import Views.modules.Vendeur.ModifierVenProfileController;
import com.jfoenix.controls.*;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Controller.LoginController.statCuser;
import static Controller.LoginController.statPanier;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class ProfileAchController implements Initializable {

    public Label lblBirthdate;
    public Label lblGender;
    @FXML
    private Label lblName;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblPhone;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblAdresse;
    @FXML
    private Label lblCreated;
    @FXML
    private Label lblLastLog;
    @FXML

    private HBox groupHolder;
    @FXML
    private Group groupRegistered;
    @FXML
    private Group groupTarget;
    @FXML
    private Group groupCart;
    @FXML
    private Group groupLadies;

    @FXML
    private JFXTextField txtDepartment;
    @FXML
    private StackPane deptStackPane;


    @FXML
    private ImageView imageView;


    private UserDAO dao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setRipples();

        dao = new UserDAO();
    }


    public void setCompte()
    {
        CurrentUser cuser = statCuser;
        System.out.println(cuser.getPhone()+" lala "+ cuser.getUsername());

        Acheteur ach = dao.ChercherAcheteurId(statCuser.getId());

        lblName.setText(ach.getNom()+" "+ach.getPrenom());
        lblEmail.setText(ach.getEmail());
        lblPhone.setText(ach.getPhone());

        lblAdresse.setText(ach.getAdresse());
        lblUsername.setText(ach.getUsername());

        lblBirthdate.setText(String.valueOf(ach.getBirthday()));

        lblGender.setText(ach.getGender());


        if (ach.getPhotoprof() != null)
            try {
                Blob blob = ach.getPhotoprof();
                byte byteImage[];
                byteImage = blob.getBytes(1,(int)blob.length());
                Image img = new Image(new ByteArrayInputStream(byteImage));
             //   setImage(img);

                imageView.setImage(img);
                imageView.setFitWidth(131);
                imageView.setFitHeight(131);


            } catch (SQLException e) {
                e.printStackTrace();
            }

    }



    private void setRipples() {
        JFXRippler rippler1 = new JFXRippler(groupRegistered);
        JFXRippler rippler2 = new JFXRippler(groupCart);
        JFXRippler rippler3 = new JFXRippler(groupLadies);
        JFXRippler rippler4 = new JFXRippler(groupTarget);
        rippler1.setMaskType(JFXRippler.RipplerMask.RECT);
        rippler2.setMaskType(JFXRippler.RipplerMask.RECT);
        rippler3.setMaskType(JFXRippler.RipplerMask.RECT);
        rippler4.setMaskType(JFXRippler.RipplerMask.RECT);

        rippler1.setRipplerFill(Paint.valueOf("#1564C0"));
        rippler2.setRipplerFill(Paint.valueOf("#00AACF"));
        rippler3.setRipplerFill(Paint.valueOf("#00B3A0"));
        rippler4.setRipplerFill(Paint.valueOf("#F87951"));

        groupHolder.getChildren().addAll(rippler1, rippler2, rippler3, rippler4);
    }


    @FXML
    private void addDepartment(ActionEvent event) {
        //Body Input text
        txtDepartment = new JFXTextField();
        txtDepartment.setPromptText("Insérer nom d'article");
        txtDepartment.setLabelFloat(false);
        txtDepartment.setPrefSize(150, 50);
        txtDepartment.setPadding(new Insets(10, 5, 10, 5));
        txtDepartment.setStyle("-fx-font-size:13px; -fx-font-weight:bold;-fx-text-fill:#00B3A0");
        // Heading text
        Text t = new Text("Ajouter un article");
        t.setStyle("-fx-font-size:14px;");

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(t);
        dialogLayout.setBody(txtDepartment);

        JFXDialog dialog = new JFXDialog(deptStackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        // close button
        JFXButton closeButton = new JFXButton("Cacher");
        closeButton.setStyle("-fx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-font-size: 14px;-fx-text-fill: WHITE;");
        //Add button
        JFXButton addBtn = new JFXButton("Ajouter");
        addBtn.setStyle("-fx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-font-size: 14px;-fx-text-fill: WHITE;"
                + "");
        closeButton.setOnAction((ActionEvent event1) -> {
            dialog.close();
        });       
        addBtn.setOnAction((ActionEvent event1) -> {
            System.out.println(txtDepartment.getText());
            dialog.close();
        });
        
        HBox box=new HBox();
        box.setSpacing(20);
        box.setPrefSize(200, 50);
        box.setAlignment(Pos.CENTER_RIGHT);
        box.getChildren().addAll(addBtn,closeButton);

        dialogLayout.setActions(box);
        
        dialog.show();
    }



    @FXML
    private void goCart()
    {

        try {
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../Rihab/View/Panier.fxml"));

            Parent root = fxmlLoader.load();

            PanierController panierController = fxmlLoader.getController();
            panierController.intiData(statPanier);

            Scene scene = new Scene(root);
            dashboardStage.setScene(scene);
            dashboardStage.show();

            FadeTransition ft = new FadeTransition(Duration.millis(1500));
            ft.setNode(root);
            ft.setFromValue(0.1);
            ft.setToValue(1);
            ft.setCycleCount(1);
            ft.setAutoReverse(false);
            ft.play();

        } catch (IOException ex) {
            Logger.getLogger(ProfileAchController.class.getName()).log(Level.SEVERE, null, ex);
        }


      /*  try {

            Scene scene = imageView.getParent().getScene();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../Rihab/View/Panier.fxml"));

            Parent root = fxmlLoader.load();

            scene.setRoot(root);


            PanierController panierController = fxmlLoader.getController();
            panierController.intiData(statPanier);


            FadeTransition ft = new FadeTransition(Duration.millis(1500));
            ft.setNode(root);
            ft.setFromValue(0.1);
            ft.setToValue(1);
            ft.setCycleCount(1);
            ft.setAutoReverse(false);
            ft.play();


        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }



    public void modifProfile(ActionEvent event) {

        try {
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../modules/ProfileModules/ModifAcheteur.fxml"));

            Parent root = fxmlLoader.load();
            ModifAcheteur controller = fxmlLoader.getController();
            controller.IntitData(statCuser.getId());

            Scene scene = new Scene(root);
            dashboardStage.setScene(scene);
            dashboardStage.show();

            FadeTransition ft = new FadeTransition(Duration.millis(1500));
            ft.setNode(root);
            ft.setFromValue(0.1);
            ft.setToValue(1);
            ft.setCycleCount(1);
            ft.setAutoReverse(false);
            ft.play();

        } catch (IOException ex) {
            Logger.getLogger(ProfileAchController.class.getName()).log(Level.SEVERE, null, ex);
        }

       /* Scene scene = lblUsername.getParent().getScene();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../modules/ProfileModules/ModifAcheteur.fxml"));

            Parent root = null;

            root = fxmlLoader.load();

            scene.setRoot(root);

            ModifAcheteur controller = fxmlLoader.getController();
            controller.IntitData(statCuser.getId());


            FadeTransition ft = new FadeTransition(Duration.millis(1500));
            ft.setNode(root);
            ft.setFromValue(0.1);
            ft.setToValue(1);
            ft.setCycleCount(1);
            ft.setAutoReverse(false);
            ft.play();

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }



    @FXML
    public void setImage(Image image) {/*
        // set a clip to apply rounded border to the original image.
        Rectangle clip = new Rectangle(
                imageView.getFitWidth(), imageView.getFitHeight()
        );
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        imageView.setClip(clip);

        // snapshot the rounded image.
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
     //   WritableImage image = imageView.snapshot(parameters, null);

        // remove the rounding clip so that our effect can show through.
        imageView.setClip(null);

        // apply a shadow effect.
        imageView.setEffect(new DropShadow(20, Color.BLACK));

        // store the rounded image in the imageView.
        imageView.setImage(image);*/
    }

}


