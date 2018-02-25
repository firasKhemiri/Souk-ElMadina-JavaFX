package Views.modules.Comments;

import Model.Entities.Avis;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.controlsfx.control.Rating;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Controller.LoginController.statCuser;

public class CommentItem implements Initializable {


    public ImageView imgUser;
    public Rating rating;
    public Text txtDate;
    public Text txtName;
    public Text txtComm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setComment(Avis avis)
    {

        if (avis.getAcheteur().getPhotoprof() != null) {
            byte byteImage[];
            Blob blob = avis.getAcheteur().getPhotoprof();
            try {
                byteImage = blob.getBytes(1, (int) blob.length());
                Image img = new Image(new ByteArrayInputStream(byteImage));
                imgUser.setImage(img);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        rating.setRating(avis.getNote());

        txtComm.setText(avis.getAvis());

        txtName.setText(avis.getAcheteur().getPrenom()+ " "+ avis.getAcheteur().getNom());

        //TODO
        txtDate.setText("1h");


    }

}
