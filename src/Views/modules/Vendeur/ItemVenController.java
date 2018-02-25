package Views.modules.Vendeur;

import Controller.CompteVenController;
import Model.Entities.Article;
import Model.Entities.Vendeur;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Rating;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemVenController implements Initializable {


    public Text txtName;
    public Text txtNbrArt;
    public Rating rating;
    @FXML
    private ImageView boxImage;

    @FXML
    private Label lblName;

    private Vendeur vendeur;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //  File file = new File("../src/Views/icons/pic.png");
        //   Image image = new Image(file.toURI().toString());
    }

    public void setVendeur(Vendeur vendeur)
    {
        this.vendeur = vendeur;

        Blob blob = vendeur.getPhotoprof();
        byte byteImage[];

        if (blob != null)
        try {
            byteImage = blob.getBytes(1,(int)blob.length());
            Image img = new Image(new ByteArrayInputStream(byteImage));
            boxImage.setImage(img);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        boxImage.setFitHeight(140);
        boxImage.setFitWidth(170);

        txtName.setText(vendeur.getNom_boutique());

        rating.setRating(vendeur.getNote());
    }


    public void onClick()
    {
        System.out.println(vendeur.getId()+" vendeur");

        try {
            /*
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Views/modules/CompteVen.fxml"));
            node = fxmlLoader.load();
*/
            Scene scene = boxImage.getParent().getScene();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../CompteVen.fxml"));

            Parent root = fxmlLoader.load();

            scene.setRoot(root);

            CompteVenController controller = fxmlLoader.getController();
            controller.setCompte(vendeur.getId());


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
