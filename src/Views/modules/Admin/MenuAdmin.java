package Views.modules.Admin;

import Controller.AccueilController;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuAdmin implements Initializable{

    @FXML
    private AnchorPane holderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    //Set selected node to a content holder
    private void setNode(Node node) {



        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    private void openHome(ActionEvent event) {
        try {
            Node node;
            node = FXMLLoader.load(getClass().getResource("../../modules/Articles.fxml"));
            setNode(node);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void openUser(ActionEvent event) {

        try {
            Node node;
            node = FXMLLoader.load(getClass().getResource("../Admin/UsersGes.fxml"));
            setNode(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openArticle(ActionEvent event) {

        try {
            Node node;
            node = FXMLLoader.load(getClass().getResource("../Admin/ArticlesGes.fxml"));
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
