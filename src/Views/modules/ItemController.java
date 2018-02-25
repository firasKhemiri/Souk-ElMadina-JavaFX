/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.modules;

import Controller.CompteVenController;
import Model.Entities.Article;
import Views.modules.Article.ArticleDetails;
import Views.modules.Article.ModifArticle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import javax.xml.soap.Text;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class ItemController implements Initializable {

    public javafx.scene.text.Text txtName;
    public javafx.scene.text.Text txtPrix;
    @FXML
    private ImageView boxImage;


    private Article article;

    private Boolean modif = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

      //  File file = new File("../src/Views/icons/pic.png");
     //   Image image = new Image(file.toURI().toString());
    }

    public void setArticle(Article article)
    {
        this.article = article;

        getBlob(article);


        boxImage.setFitHeight(140);
        boxImage.setFitWidth(170);

        txtName.setText(article.getNom());

        txtPrix.setText(String.valueOf(article.getPrix())+" DT");
    }



    public void setArticleModif(Article article)
    {
        this.modif = true;

        this.article = article;

        getBlob(article);

        boxImage.setFitHeight(140);
        boxImage.setFitWidth(170);

        txtName.setText(article.getNom());

        txtPrix.setText(String.valueOf(article.getPrix())+" DT");
    }

    private void getBlob(Article article) {
        if (article.getImages().size()>0) {

            Blob blob = article.getImages().get(0).getImage();
            byte byteImage[];

            try {
                byteImage = blob.getBytes(1, (int) blob.length());
                Image img = new Image(new ByteArrayInputStream(byteImage));
                boxImage.setImage(img);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void onClick()
    {
        System.out.println(article.getId()+" article");

        if (!modif) {
            try {

                Scene scene = boxImage.getParent().getScene();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../modules/Article/ArticleDetails.fxml"));

                Parent root = fxmlLoader.load();

                scene.setRoot(root);

                ArticleDetails controller = fxmlLoader.getController();
                controller.setArticle(article.getId());


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

        else
        {
            try {

                Scene scene = boxImage.getParent().getScene();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../modules/Article/ModifArticle.fxml"));

                Parent root = fxmlLoader.load();

                scene.setRoot(root);

                ModifArticle controller = fxmlLoader.getController();
                controller.intiData(article.getId());


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

}
