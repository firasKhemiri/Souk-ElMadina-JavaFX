package Views.modules.Article;

import Model.DAO.ArticlesDao;
import Model.DAO.Upload;
import Model.Entities.Article;
import Model.Entities.Categorie;
import Model.Entities.Image;
import Model.Entities.Vendeur;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddArticle implements Initializable{
    public JFXTextField txtNom;
    public JFXTextField txtDesc;
    public JFXTextField txtPrix;
    public JFXTextField txtQte;
    public JFXComboBox<String> comboCat;
    public ImageView img;
    public GridPane gridImg;

    private int vendeur;

    private List<Image> listImg ;

    private File file;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listImg = new ArrayList<>();

        ArticlesDao daoArt = new ArticlesDao();
        List<Categorie> categories = daoArt.SelectCategories();

        for (Categorie categorie : categories)
            comboCat.getItems().addAll(categorie.getNom());
        comboCat.getSelectionModel().select("Choisir categorie");


    }


    public void intiData(int vendeur)
    {
        this.vendeur = vendeur;
    }

    @FXML
    public void saveArticle()
    {

        ArticlesDao daoArt = new ArticlesDao();

        Article a = new Article();

        a.setNom(txtNom.getText());
        a.setDescription(txtDesc.getText());
        a.setPrix(Integer.valueOf(txtPrix.getText()));
        a.setOrder_qte(Integer.valueOf(txtQte.getText()));
        a.setCategorie(comboCat.getValue());
        a.setImages(listImg);

        daoArt.addArticle(a,vendeur);
    }

    @FXML
    public void clearFields()
    {

    }



    @FXML
    public void addImg()
    {
        if (listImg.size()>3)
        {
            System.out.println("many images");
        }
        else if (listImg.size() < 1)
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

                listImg.add(imgArt);

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

            int size = listImg.size();

            int k = 0;
            int i = size;

            if (size>1) {
                k = 1;
                i = size - 2;
            }

            BufferedImage bf;
            try {
                bf = ImageIO.read(file);
                javafx.scene.image.Image image = SwingFXUtils.toFXImage(bf, null);
                img.setImage(image);

                imgArt.setFile(file);

                listImg.add(imgArt);
                bf = ImageIO.read(listImg.get(0).getFile());
                image = SwingFXUtils.toFXImage(bf, null);
                img.setImage(image);


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




            } catch (IOException ex) {
                Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
