package Views.modules.Vendeur;

import Controller.ArticlesController;
import Model.DAO.CommandeDao;
import Model.DAO.VendeurDao;
import Model.Entities.Article;
import Model.Entities.Commande;
import Model.Entities.CurrentUser;
import Model.Entities.Vendeur;
import Rihab.Controller.PanierController;
import Views.modules.Article.AddArticle;
import com.jfoenix.controls.*;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Rating;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Controller.LoginController.statCuser;
import static Controller.LoginController.statPanier;

public class ProfileVen implements Initializable {


    public Group ajoutArt;
    public JFXListView listCommandes;
    public Pane stack;
    public StackPane artStackPane;
    public ImageView imageVen;
    public Label lblUsername;
    public Label lblType;
    public Label lblAdresse;
    public Label lblName;
    public Label lblEmail;
    public Label lblPhone;
    public Label lblCreated;
    public Label lblSubs;
    public Rating rating;
    public Label lblNomBout;


    private List<Commande> commandes = new ArrayList<>();

    private VendeurDao daoVen ;

    private boolean res = false ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initData(CurrentUser cuser)
    {
        String articlesNames = " ";

        CommandeDao daoComm = new CommandeDao();

        daoVen = new VendeurDao();

        Vendeur vendeur = daoVen.ChercherVen(statCuser.getId());

        commandes = daoComm.vendeurCommandes(statCuser.getId());

        for (Commande commande : commandes) {
            int sizeArt = commande.getArticles().size();
            articlesNames ="( ";
            for (int i = 0 ; i<sizeArt;i++) {

                Article art = commande.getArticles().get(i);
                articlesNames =  articlesNames +" "+ art.getOrder_qte() +" " +art.getNom()+" )" ;
                if (i < sizeArt - 1 || sizeArt == 2 && i == 0)
                {
                    articlesNames = articlesNames + " et ( ";
                }
            }

            listCommandes.getItems().add("delivrer "+articlesNames+" a "+commande.getAcheteur().getNom()+" "+commande.getAcheteur().getPrenom()+ " a l'adresse: " + commande.getAcheteur().getAdresse() +
                    " ( num mobile: "+commande.getAcheteur().getPhone()+" )");
        }


        listCommandes.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                System.out.print(listCommandes.getSelectionModel().getSelectedIndex());

                if (ArtDelivred(commandes.get(listCommandes.getSelectionModel().getSelectedIndex()).getId()))
                {
                    listCommandes.getItems().remove(listCommandes.getSelectionModel().getSelectedIndex());
                }
            }
        });


        lblName.setText(vendeur.getNom()+" "+vendeur.getPrenom());
        lblEmail.setText(vendeur.getEmail());
        // lblType.setText(vendeur.getType());
        lblPhone.setText(vendeur.getPhone());

        lblAdresse.setText(vendeur.getAdresse());
        lblUsername.setText(vendeur.getUsername());

        rating.setRating(vendeur.getNote());
        rating.setDisable(true);

        lblSubs.setText(String.valueOf(daoVen.selectSubs(vendeur.getId()).size()) + " sont abonnés a ce vendeur");

        lblNomBout.setText(vendeur.getNom_boutique());

        if (vendeur.getPhotoprof() != null)
            try {
                Blob blob = vendeur.getPhotoprof();
                byte byteImage[];
                byteImage = blob.getBytes(1,(int)blob.length());
                Image img = new Image(new ByteArrayInputStream(byteImage));
               // setImage(img);

                imageVen.setImage(img);

                imageVen.setFitWidth(131);
                imageVen.setFitHeight(131);

            } catch (SQLException e) {
                e.printStackTrace();
            }

    }


    private boolean ArtDelivred(int id) {
        //Body Input text
        res = false;

        Text t = new Text("Commande validé ?");
        t.setStyle("-fx-font-size:14px;");

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(t);

        JFXDialog dialog = new JFXDialog(artStackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        // close button

        JFXButton closeButton = new JFXButton("Non");
        closeButton.setStyle("-fx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-font-size: 14px;-fx-text-fill: WHITE;");
        //Add button

        JFXButton addBtn = new JFXButton("Oui");
        addBtn.setStyle("-fx-button-type: RAISED;-fx-background-color: rgb(77,102,204);-fx-font-size: 14px;-fx-text-fill: WHITE;"
                + "");

        closeButton.setOnAction((ActionEvent event1) -> {
            dialog.close();
            res = false;

        });
        addBtn.setOnAction((ActionEvent event1) -> {
     //       System.out.println(txtDepartment.getText());

            new CommandeDao().updateEtat(id);
            dialog.close();
            res = true;

           // return true;
        });

        HBox box=new HBox();
        box.setSpacing(20);
        box.setPrefSize(200, 50);
        box.setAlignment(Pos.CENTER_RIGHT);
        box.getChildren().addAll(addBtn,closeButton);

        dialogLayout.setActions(box);

        dialog.show();

        return res;
    }




    public void ajoutArt(MouseEvent mouseEvent){

        try {
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Article/AddArticle.fxml"));

            Parent root = fxmlLoader.load();
            AddArticle controller = fxmlLoader.getController();
            controller.intiData(statCuser.getId());

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
            Logger.getLogger(ProfileVen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /* {

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
    }*/


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

        try {
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("");
            // Parent root = FXMLLoader.load(getClass().getResource("/Views/Accueil.fxml"));

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Vendeur/ModifierVenProfile.fxml"));

            Parent root = fxmlLoader.load();
            ModifierVenProfileController controller = fxmlLoader.getController();
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
            Logger.getLogger(ProfileVen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*    ajoutArt.getScene().getWindow().hide();
        Stage dashboardStage = new Stage();
        dashboardStage.setTitle("");
        // Parent root = FXMLLoader.load(getClass().getResource("/Views/Accueil.fxml"));

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Vendeur/ModifierVenProfile.fxml"));

        ModifierVenProfileController articleController = fxmlLoader.getController();
        articleController.IntitData(statCuser.getId());

        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        dashboardStage.setScene(scene);
        dashboardStage.show();


     /*   Scene scene = ajoutArt.getParent().getScene();
        try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Vendeur/ModifierVenProfile.fxml"));

        Parent root = null;

            root = fxmlLoader.load();

        scene.setRoot(root);

        ModifierVenProfileController articleController = fxmlLoader.getController();
        articleController.IntitData(statCuser.getId());

*/
/*
        } catch (IOException e) {
            e.printStackTrace();
        }*/


    @FXML
    public void setImage(Image image) {
        // set a clip to apply rounded border to the original image.

/*
        Rectangle clip = new Rectangle(
                imageVen.getFitWidth(), imageVen.getFitHeight()
        );
        clip.setArcWidth(10);
        clip.setArcHeight(10);
        imageVen.setClip(clip);

        // snapshot the rounded image.
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        //   WritableImage image = imageView.snapshot(parameters, null);

        // remove the rounding clip so that our effect can show through.
        imageVen.setClip(null);

        // apply a shadow effect.
        imageVen.setEffect(new DropShadow(20, Color.BLACK));

        // store the rounded image in the imageView.
      imageVen.setImage(image);

        imageVen.setFitWidth(100);
        imageVen.setFitHeight(100);
  */  }

}




