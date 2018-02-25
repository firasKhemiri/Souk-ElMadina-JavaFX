package Views.modules.Article;

import Model.Entities.Article;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ArtGridImgItem implements Initializable{


    public ImageView boxImage;
    private Article article;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void setArticle(File file)
    {


        BufferedImage bf;
        try {

            bf = ImageIO.read(file);
            javafx.scene.image.Image image = SwingFXUtils.toFXImage(bf, null);

            boxImage.setImage(image);

        } catch (IOException e) {
            e.printStackTrace();
        }


        boxImage.setFitHeight(80);
        boxImage.setFitWidth(80);

    }




    public void setArticle(Blob file)
    {


        byte byteImage[];

        try {
            byteImage = file.getBytes(1, (int) file.length());
            Image img = new Image(new ByteArrayInputStream(byteImage));
            boxImage.setImage(img);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        boxImage.setFitHeight(80);
        boxImage.setFitWidth(80);

    }


    public void setArticleMed(Blob file)
    {


        byte byteImage[];

        try {
            byteImage = file.getBytes(1, (int) file.length());
            Image img = new Image(new ByteArrayInputStream(byteImage));
            boxImage.setImage(img);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        boxImage.setFitHeight(115);
        boxImage.setFitWidth(145);

    }

}
