package Views.modules.Article;

import Controller.AccueilController;
import Controller.LoginController;
import Model.DAO.ArticlesDao;
import Model.DAO.CommentsDao;
import Model.DAO.PanierDao;
import Model.DAO.VendeurDao;
import Model.Entities.Article;
import Model.Entities.Avis;
import Model.Entities.Panier;
import Model.Entities.Vendeur;
import Views.modules.Comments.CommentItem;
import Views.modules.ItemController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Rating;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Controller.LoginController.statCuser;
import static Controller.LoginController.statPanier;

public class ArticleDetails implements Initializable{
    public Text txtPrix;
    public Text txtNom;
    public Text txtCat;
    public Text txtAdr;
    public Text txtDesc;
    public JFXButton btnPanier;
    public ImageView imageVen;
    public Text note;
    public Text nomVen;
    public JFXButton btnContact;
    public ImageView imageArt;
    public GridPane greadpane;
    public GridPane gridComm;
    public TextArea txtComment;

    public RowConstraints rowMokrez;
    public Rating rating;
    public ImageView imgMe;
    public GridPane gridImages;
    public Text txtStock;
    public JFXTextField txtQte;
    public Text txtDatePub;

    Article carticle;
    private Panier panier = new Panier(statCuser, new Date(new java.util.Date().getTime()), new ArrayList<Article>());


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        carticle = new Article();

        panier.setArticleList(statPanier.getArticleList());

    }

    public void setArticle(int id)
    {

        txtQte.setText("1");

        ArticlesDao daoArt = new ArticlesDao();
        VendeurDao daoVen = new VendeurDao();


        Vendeur vendeur = daoVen.SelectVenArt(id);
        Article article = daoArt.SelectArticle(id);

        carticle = article;

        txtPrix.setText(String.valueOf(article.getPrix()+" DT"));
        txtNom.setText(article.getNom());
        txtDesc.setText(article.getDescription());

        txtStock.setText("Il reste "+ article.getQuantity() +" elements en stock");

        if (article.getQuantity()<1)
        {
            txtStock.setFill(Color.rgb(150,0,0,1));
            btnPanier.setDisable(true);
        }


        txtAdr.setText(vendeur.getAdresse());

        txtCat.setText(article.getCategorie());

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH : mm");
        java.util.Date date = article.getDate_pub();
        txtDatePub.setText(dateFormat.format(date)); //2016/11/16 12:08:43



        nomVen.setText(vendeur.getNom_boutique());

        Blob blob = article.getImages().get(0).getImage();
        byte byteImage[];

        if (article.getImages().size() > 0) {

            blob = article.getImages().get(0).getImage();
            try {
                byteImage = blob.getBytes(1, (int) blob.length());
                Image img = new Image(new ByteArrayInputStream(byteImage));
                imageArt.setImage(img);


                gridImages.setPadding(new Insets(15, 0, 5, 0));
                gridImages.setVgap(24);

                try {

                    int j = 0;

                    for (Model.Entities.Image image : article.getImages()) {


                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Article/ArtGridImgItem.fxml"));

                        Parent root = fxmlLoader.load();
                        Node node = (Node) root;

                        ArtGridImgItem controller = fxmlLoader.getController();
                        controller.setArticleMed(image.getImage());

                        gridImages.add(node, 0, j);

                        j++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }





            } catch (SQLException e) {
                e.printStackTrace();
            }



        }

        if (vendeur.getPhotoprof() != null) {
            blob = vendeur.getPhotoprof();
            try {
                byteImage = blob.getBytes(1, (int) blob.length());
                Image img = new Image(new ByteArrayInputStream(byteImage));
                imageVen.setImage(img);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        imageVen.setFitHeight(200);
        imageVen.setFitWidth(278);



        if (statCuser.getPhotoprof() != null) {
            blob = statCuser.getPhotoprof();
            try {
                byteImage = blob.getBytes(1, (int) blob.length());
                Image img = new Image(new ByteArrayInputStream(byteImage));
                imgMe.setImage(img);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        setComments(article.getId());




        for (Node element : gridImages.getChildren()) {
            element.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("Row: " + GridPane.getRowIndex(element));
                    System.out.println("Column: " + GridPane.getColumnIndex(element));

                    Blob blob1;
                    byte byteImage1[];

                    if (carticle.getImages().size() > 0) {

                        blob1 = carticle.getImages().get(GridPane.getRowIndex(element)).getImage();
                        try {
                            byteImage1 = blob1.getBytes(1, (int) blob1.length());
                            Image img = new Image(new ByteArrayInputStream(byteImage1));
                            imageArt.setImage(img);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }


        txtQte.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (!newValue.isEmpty()) {
                    if (!newValue.matches("\\d*")) {
                        txtQte.setText(newValue.replaceAll("[^\\d]", ""));
                }
                    int ord = Integer.valueOf(txtQte.getText());
                    if (ord <= carticle.getQuantity() && ord != 0) {
                        btnPanier.setDisable(false);
                    } else
                        btnPanier.setDisable(true);
                }

            }
        });

        if (statCuser.getType().equals("Vendeur"))
        {
            btnPanier.setDisable(true);
        }

    }

   // pic.setfill(new image pattern(new image))


    public void setComments(int id)
    {
        List<Avis> listAvis = new ArrayList<>();

        listAvis = new CommentsDao().SelectAvis(id);

        int size = listAvis.size();
        gridComm.setVgap(25);

        if (size!=0) {
            if (size < 3) {
                rowMokrez.setPrefHeight(220 * size);
            } else
                rowMokrez.setPrefHeight(150 * size);
        }
      //  rowMokrez.setPrefHeight(200*size);

        for (int i=0;i<size;i++)
        {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Comments/CommentItem.fxml"));

                Parent root = null;
                root = fxmlLoader.load();

                Node node = (Node) root;

                CommentItem controller = fxmlLoader.getController();
                controller.setComment(listAvis.get(i));

                gridComm.add(node, 1, i+3);



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





    public void Contact()
    {

    }

    @FXML
    void addToPanier(ActionEvent event) {

        int ord = Integer.valueOf(txtQte.getText());

        carticle.setOrder_qte(ord);

        panier.getArticleList().add(carticle);

        statPanier.setArticleList(panier.getArticleList());

        txtStock.setText("Il reste "+ (carticle.getQuantity() - ord) +" elements en stock");


        PanierDao daoPan = new PanierDao();

        daoPan.addSingleArticleToPanier(statPanier,carticle);

    }

    @FXML
    public void AddComment()
    {
        CommentsDao daoComm = new CommentsDao();
        daoComm.ajoutAvis(statCuser.getId(),carticle.getId(), txtComment.getText(),rating.getRating());

        txtComment.clear();
    }

    public void onImgGridClick(MouseEvent mouseEvent) {

       /* int i = GridPane.getRowIndex(gridImages.getChildren().get());

        System.out.println("grid "+ i);

        Blob blob = carticle.getImages().get(0).getImage();
        byte byteImage[];

        if (carticle.getImages().size() > 0) {

            blob = carticle.getImages().get(i).getImage();
            try {
                byteImage = blob.getBytes(1, (int) blob.length());
                Image img = new Image(new ByteArrayInputStream(byteImage));
                imageArt.setImage(img);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/

    }

    public void openHome(ActionEvent event) {

        try {
            Scene scene = btnPanier.getParent().getScene();

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

    public void Profile(ActionEvent event) {
    }

    public void Vendeur(ActionEvent event) {
    }

    @FXML
    private void logOff(ActionEvent event) {

        btnPanier.getScene().getWindow().hide();
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
