/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO.CommandeDao;
import Model.DAO.PanierDao;
import Model.DAO.UserDAO;
import Model.Entities.CurrentUser;
import Model.Entities.Panier;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author danml
 */
public class LoginController implements Initializable {

    private Label label;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private StackPane rootPane;
    @FXML
    private ImageView imgProgress;

    public static CurrentUser statCuser;

    public static Panier statPanier;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handleValidation();
        imgProgress.setVisible(false);


        CommandeDao daoComm = new CommandeDao();

        daoComm.vendeurCommandes(42);
    }

    @FXML
    private void login(ActionEvent event) {

        UserDAO dao= new UserDAO();

        PanierDao daoPan = new PanierDao();

        imgProgress.setVisible(true);

        List<CurrentUser> users;

        if (username.getText().equals("admin") && password.getText().equals("admin"))
            adminLogin();
        else
        {

            users = dao.Login(username.getText(), password.getText());

            for (int i = 0; i < users.size(); i++) {

            //    System.out.println(users.get(i).getId() + " " + users.get(i).getUsername() + " ");

                statCuser = users.get(i);

                if (statCuser.getType().equals("acheteur")) {
                    statPanier = daoPan.findUserPanier(statCuser);

                    System.out.println(statPanier.getId() + "panier " + statPanier.getAcheteur().getId());
                    //        +" userrr "+ statPanier.getArticleList().get(0).getOrder_qte()+" qte " );

                }
                completeLogin(statCuser);
            }
        }
    }


    @FXML
    private void inscrire(ActionEvent event)
    {
        btnLogin.getScene().getWindow().hide();
        Stage dashboardStage = new Stage();
        dashboardStage.setTitle("");
        // Parent root = FXMLLoader.load(getClass().getResource("/Views/Accueil.fxml"));

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/modules/Register.fxml"));

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


    private void handleValidation() {
        RequiredFieldValidator fieldValidator = new RequiredFieldValidator();
        fieldValidator.setMessage("Input required");
        fieldValidator.setIcon(new FontAwesomeIconView(FontAwesomeIcon.TIMES));
        username.getValidators().add(fieldValidator);
        username.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oldVal, Boolean newVal) -> {
            if (!newVal) {
                username.validate();

            }
        });
        RequiredFieldValidator fieldValidator2 = new RequiredFieldValidator();
        fieldValidator2.setMessage("Input required");
        fieldValidator2.setIcon(new FontAwesomeIconView(FontAwesomeIcon.TIMES));
        password.getValidators().add(fieldValidator2);
        password.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                password.validate();
            }
        });

    }

    private void adminLogin() {
        btnLogin.getScene().getWindow().hide();
        try {
            imgProgress.setVisible(false);
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("");
            // Parent root = FXMLLoader.load(getClass().getResource("/Views/Accueil.fxml"));

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/modules/Admin/MenuAdmin.fxml"));

            Parent root = fxmlLoader.load();

            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();

            dashboardStage.setX(bounds.getMinX());
            dashboardStage.setY(bounds.getMinY());
            dashboardStage.setWidth(bounds.getWidth());
            dashboardStage.setHeight(bounds.getHeight());


            Scene scene = new Scene(root);
            dashboardStage.setScene(scene);
            dashboardStage.show();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



    private void completeLogin(CurrentUser cuser) {
        btnLogin.getScene().getWindow().hide();
        try {
            imgProgress.setVisible(false);
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("");
            // Parent root = FXMLLoader.load(getClass().getResource("/Views/Accueil.fxml"));

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/Accueil.fxml"));

            Parent root = fxmlLoader.load();
            AccueilController controller = fxmlLoader.getController();

            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();

            dashboardStage.setX(bounds.getMinX());
            dashboardStage.setY(bounds.getMinY());
            dashboardStage.setWidth(bounds.getWidth());
            dashboardStage.setHeight(bounds.getHeight());


            Scene scene = new Scene(root);
            dashboardStage.setScene(scene);
            dashboardStage.show();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

