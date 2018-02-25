package Views.modules.Article;

import Model.DAO.ArticlesDao;
import Model.DAO.Upload;
import Model.Entities.Article;
import Model.Entities.Categorie;
import Model.Entities.Image;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModifArticle implements Initializable{
    public JFXTextField txtNom;
    public JFXTextField txtDesc;
    public JFXTextField txtPrix;
    public JFXTextField txtQte;
    public JFXComboBox<String> comboCat;
    public ImageView img;
    public GridPane gridImg;



    private List<Image> listImgRem;

    private List<Image> listImgAdd;

    private List<Image> listImg ;

    private ArticlesDao daoArt = new ArticlesDao();

    private Article article = new Article();

    private File file;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ArticlesDao daoArt = new ArticlesDao();
        List<Categorie> categories = daoArt.SelectCategories();

        listImg = new ArrayList<>();

        listImgAdd = new ArrayList<>();

        listImgRem = new ArrayList<>();

        for (Categorie categorie : categories)
            comboCat.getItems().addAll(categorie.getNom());
        comboCat.getSelectionModel().select("Choisir categorie");


    }


    public void intiData(int article_id)
    {
        this.article = daoArt.SelectArticle(article_id);


        txtNom.setText(article.getNom());
        txtDesc.setText(article.getDescription());
        txtPrix.setText(String.valueOf(article.getPrix()));
        txtQte.setText(String.valueOf(article.getQuantity()));


        comboCat.getSelectionModel().select(article.getCategorie());

        if (article.getImages().size()>0) {

            Blob blob = article.getImages().get(0).getImage();
            byte byteImage[];

            try {
                byteImage = blob.getBytes(1, (int) blob.length());
                javafx.scene.image.Image image = new javafx.scene.image.Image(new ByteArrayInputStream(byteImage));
                img.setImage(image);

                int i = 0;
                int k = 0;

                for (Image image1 : article.getImages())
                {
                    gridImg.setPadding(new Insets(15, 0, 5, 0));
                    gridImg.setVgap(14);
                    gridImg.setHgap(14);

                    listImg.add(image1);


                    try {

                        Blob blob1 = image1.getImage();

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Article/ArtGridImgItem.fxml"));

                        Parent root = fxmlLoader.load();
                        Node node = (Node) root;

                        ArtGridImgItem controller = fxmlLoader.getController();
                        controller.setArticle(blob1);

                        gridImg.add(node, i, k);

                        i++;

                        if (i>1)
                        {
                            k++;
                            i=0;
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }




    }


    @FXML
    public void deleteArticle()
    {

        daoArt.DeleteArt(article);

    }

    @FXML
    public void modifArticle()
    {

        ArticlesDao daoArt = new ArticlesDao();

        Article a;

        a = article;

        a.setNom(txtNom.getText());
        a.setDescription(txtDesc.getText());
        a.setPrix(Float.valueOf(txtPrix.getText()));
        a.setOrder_qte(Integer.valueOf(txtQte.getText()));
        a.setCategorie(comboCat.getValue());
        a.setImages(listImgAdd);

        System.out.println(listImgAdd.size()+" size added");

        daoArt.ModifArticle(a);
    }

    @FXML
    public void clearFields()
    {

    }



    @FXML
    public void addImg()
    {
        int size = listImg.size()+listImgAdd.size();
        if (size>3)
        {
            System.out.println("many images");
        }
        else if (size < 1)
        {
            Image imgArt = new Image();

            FileChooser fc = new FileChooser();
            FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
            fc.getExtensionFilters().addAll(ext1, ext2);
            file = fc.showOpenDialog(img.getScene().getWindow());

            BufferedImage bf;
            try {
                bf = ImageIO.read(file);
                javafx.scene.image.Image image = SwingFXUtils.toFXImage(bf, null);
                img.setImage(image);

                imgArt.setFile(file);

             //   listImg.add(imgArt);

                listImgAdd.add(imgArt);

                gridImg.setPadding(new Insets(15, 0, 5, 0));
                gridImg.setVgap(14);
                gridImg.setHgap(14);

                try {

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Article/ArtGridImgItem.fxml"));

                    Parent root = fxmlLoader.load();
                    Node node = (Node) root;

                    ArtGridImgItem controller = fxmlLoader.getController();
                    controller.setArticle(file);

                    gridImg.add(node, 0, 0);

                } catch (IOException e) {
                    e.printStackTrace();
                }



            } catch (IOException ex) {
                Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        else {
            Image imgArt = new Image();

            FileChooser fc = new FileChooser();
            FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
            fc.getExtensionFilters().addAll(ext1, ext2);
            file = fc.showOpenDialog(img.getScene().getWindow());

            imgArt.setFile(file);

            listImgAdd.add(imgArt);

            int k = 0;
            int i = size;

            if (size>1) {
                k = 1;
                i = size - 2;
            }


                gridImg.setPadding(new Insets(15, 0, 5, 0));
                gridImg.setVgap(14);
                gridImg.setHgap(14);

                try {

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Article/ArtGridImgItem.fxml"));

                    Parent root = fxmlLoader.load();
                    Node node = (Node) root;

                    ArtGridImgItem controller = fxmlLoader.getController();
                    controller.setArticle(file);

                    gridImg.add(node, i, k);

                } catch (IOException e) {
                    e.printStackTrace();
                }


        }

        System.out.println(listImgAdd.size()+" size added");
    }

}
