package Views.modules.Comments;

import Model.Entities.Avis;
import Tools.classes.Prefs;
import com.sun.xml.internal.bind.v2.TODO;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.controlsfx.control.Rating;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

       // txtDate.setText(String.valueOf(avis.getDate_pub()));

       // DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH : mm");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = avis.getDate_pub();
       // txtDate.setText(dateFormat.format(date)); //2016/11/16 12:08:43


        try {
            String s = new Prefs().convertDate(dateFormat.format(date));
            txtDate.setText(s); //2016/11/16 12:08:43

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

}
