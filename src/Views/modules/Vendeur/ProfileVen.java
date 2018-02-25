package Views.modules.Vendeur;

import Controller.ArticlesController;
import Model.Entities.CurrentUser;
import Rihab.Controller.PanierController;
import Views.modules.Article.AddArticle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Controller.LoginController.statCuser;
import static Controller.LoginController.statPanier;

public class ProfileVen implements Initializable {


    public Group ajoutArt;

    @FXML
    private JFXTextField txtDepartment;

    @FXML
    private StackPane deptStackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initData(CurrentUser cuser)
    {

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




    public void ajoutArt(MouseEvent mouseEvent) {

        try {
            Scene scene = ajoutArt.getParent().getScene();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Article/AddArticle.fxml"));

            Parent root = fxmlLoader.load();

            scene.setRoot(root);

            AddArticle articleController = fxmlLoader.getController();
            articleController.intiData(statCuser.getId());


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


    public void venArts(MouseEvent mouseEvent) {

        try {
            /*
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Views/modules/CompteVen.fxml"));
            node = fxmlLoader.load();
*/
            Scene scene = ajoutArt.getParent().getScene();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Articles.fxml"));

            Parent root = fxmlLoader.load();

            scene.setRoot(root);

          /*  ArticleDetails controller = fxmlLoader.getController();
            controller.setArticle(article.getId());*/

            ArticlesController articleController = fxmlLoader.getController();
            articleController.venArts(statCuser.getId());


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

    public void modifVen(ActionEvent event) {
        Scene scene = ajoutArt.getParent().getScene();
        try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Vendeur/ModifierVenProfile.fxml"));

        Parent root = null;

            root = fxmlLoader.load();

        scene.setRoot(root);

        ModifierVenProfileController articleController = fxmlLoader.getController();
        articleController.IntitData(statCuser.getId());


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

}
