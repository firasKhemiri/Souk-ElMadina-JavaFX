/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Entities.CurrentUser;
import Views.modules.ProfileAchController;
import Views.modules.Vendeur.ProfileVen;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXToolbar;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Controller.LoginController.statCuser;

/**
 *
 * @author FIRAS
 */
public class AccueilController implements Initializable
{

    private Label lblDash;
    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane holderPane;
    @FXML
    private AnchorPane sideAnchor;
    @FXML
    private Label lblMenu;
    @FXML
    private HBox toolBarRight;
    @FXML
    private VBox overflowContainer;

    @FXML
    private HBox acbox;

    @FXML
    private ScrollPane acscroll;


    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXRippler fXRippler = new JFXRippler(lblDash);
        JFXRippler fXRippler2 = new JFXRippler(lblMenu);
        fXRippler2.setMaskType((JFXRippler.RipplerMask.RECT));
        sideAnchor.getChildren().add(fXRippler);
        toolBarRight.getChildren().add(fXRippler2);
        openMenus();
     //   createPages();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        acbox.setFillHeight(true);

        acscroll.setFitToHeight(true);
        acscroll.setFitToWidth(true);

        acscroll.setCenterShape(true);
        acscroll.setPadding(new Insets(20,0,0,30));

        acscroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        acscroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        acbox.setPrefSize(bounds.getMaxY(),bounds.getHeight());

        try {
            Node node;
            node = FXMLLoader.load(getClass().getResource("../Views/modules/Tabs.fxml"));
            setNode(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openMenus() {
        JFXPopup popup = new JFXPopup();
        popup.setContent(overflowContainer);
        popup.setPopupContainer(stackPane);
        popup.setSource(lblMenu);
        lblMenu.setOnMouseClicked((MouseEvent e) -> {
            popup.show(JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, -10, 40);
        });

    }

    //Set selected node to a content holder
    private void setNode(Node node) {



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

    //Load all fxml files to a cahce for swapping
   /* private void createPages() {
        try {
            home = FXMLLoader.load(getClass().getResource("../Views/modules/Articles.fxml"));
        //    list = FXMLLoader.load(getClass().getResource("../Views/modules/Profile.fxml"));
       //     addArticle = FXMLLoader.load(getClass().getResource("../Views/modules/Register.fxml"));

            //set up default node on page load
            setNode(home);
        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }*/

    @FXML
    private void openHome(ActionEvent event) {
        try {
            Node node;
            node = FXMLLoader.load(getClass().getResource("../Views/modules/Tabs.fxml"));
            setNode(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Profile(ActionEvent event) {

        if (statCuser.getType().equals("Acheteur") ) {
            try {
                Node node;

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Views/modules/ProfileAch.fxml"));
                node = fxmlLoader.load();

                ProfileAchController controller = fxmlLoader.getController();
                controller.setCompte();

                setNode(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                Node node;

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Views/modules/Vendeur/ProfileVen.fxml"));
                node = fxmlLoader.load();

                ProfileVen controller = fxmlLoader.getController();
                controller.initData(statCuser);

                setNode(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void Vendeur(ActionEvent event) {
        try {
            Node node;

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Views/modules/CompteVen.fxml"));
            node = fxmlLoader.load();

            CompteVenController controller = fxmlLoader.getController();
            controller.setCompte(42);

            setNode(node);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logOff(ActionEvent event) {

        holderPane.getScene().getWindow().hide();
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
/*
    private void Grid(int rows){

        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        grid.addArticle(label, 0, 0);

        System.out.println(statCuser.getId()+" ");

        Button button1 = new Button("Button 1");
        grid.addArticle(button1, 0, 0, 1, 1);

/*

        for(int i=0; i<rows; i++){
            TextField textField = new TextField();
            textField.setAlignment(Pos.CENTER);
            CheckBox checkBox = new CheckBox("Check Box");
            checkBox.setTextFill(Color.WHITE);
            checkBox.setAlignment(Pos.CENTER);
            DatePicker datePicker = new DatePicker();

            //addArticle them to the GridPane
            table.addArticle(textField, 0, i); //  (child, columnIndex, rowIndex)
            table.addArticle(checkBox , 1, i);
            table.addArticle(datePicker,2, i);

            // margins are up to your preference
            GridPane.setMargin(textField, new Insets(5));
            GridPane.setMargin(checkBox, new Insets(5));
            GridPane.setMargin(datePicker, new Insets(5));
        }
        table.setAlignment(Pos.CENTER);*/

       // return gridPane;
