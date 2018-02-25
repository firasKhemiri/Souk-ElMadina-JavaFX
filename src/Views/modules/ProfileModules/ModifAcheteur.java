/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.modules.ProfileModules;

import Model.DAO.Upload;
import Model.DAO.UserDAO;
import Model.DAO.VendeurDao;
import Model.Entities.User;
import Model.Entities.Vendeur;
import com.jfoenix.controls.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class ModifAcheteur implements Initializable {


    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXTextField txtFname;
    @FXML
    private JFXTextField txtLname;
    @FXML
    private JFXTextField txtMobile;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXDatePicker dob;

    @FXML
    private JFXTextField txtLocation;

    @FXML
    private ImageView img;

    @FXML
    private ProgressBar progressPersonal;
    @FXML
    private JFXRadioButton rdMale;
    @FXML
    private ToggleGroup gender;
    @FXML
    private JFXRadioButton rdFemale;

    private File file;

    User user = new User();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }


    @FXML
    public void IntitData(int id) {

        UserDAO dao = new UserDAO();

        user = dao.ChercherAcheteurId(id);

        txtUsername.setText(user.getUsername());
        txtPassword.setText(user.getPassword());
        txtEmail.setText(user.getEmail());
        txtFname.setText(user.getPrenom());
        txtLname.setText(user.getNom());
        txtLocation.setText(user.getAdresse());
        txtMobile.setText(user.getPhone());

        if (user.getGender().equals("Male"))
        {
            rdMale.setSelected(true);
        }
        else
        {
            rdFemale.setSelected(true);
        }

        Blob blob = user.getPhotoprof();
        byte byteImage[];

        if (blob != null)
            try {
                byteImage = blob.getBytes(1,(int)blob.length());
                Image image = new Image(new ByteArrayInputStream(byteImage));
                img.setImage(image);

            } catch (SQLException e) {
                e.printStackTrace();
            }



        user.setId(id);

//        statCuser = (CurrentUser) user;


    }


    @FXML
    public void deleteUser()
    {
        UserDAO userdao = new UserDAO();

        userdao.Delete(user);

    }

    @FXML
    private void ModifUser(ActionEvent event) {

        UserDAO userdao = new UserDAO();


        user.setUsername(txtUsername.getText());
        user.setPrenom(txtFname.getText());
        user.setNom(txtLname.getText());
        user.setPassword(txtPassword.getText());
        user.setEmail(txtEmail.getText());
        user.setAdresse(txtLocation.getText());

        user.setGender(getGender());
        user.setPhone(txtMobile.getText());


        System.out.println(user.getPhone()+"ph  FILE");

        if (file != null) {

            System.out.println(file+" FILE");

            user.setFile(file);
        }

        userdao.Modifier(user);


    }


    private String getGender() {
        String gdr = "";
        if (rdMale.isSelected()) {
            gdr = "Male";
        } else if (rdFemale.isSelected()) {
            gdr = "Female";
        }
        return gdr;

    }


    public void ChangeImg()
    {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");
        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)","*.PNG");
        fc.getExtensionFilters().addAll(ext1,ext2);
        file = fc.showOpenDialog(img.getScene().getWindow());

        BufferedImage bf;
        try {
            bf = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bf, null);
            img.setImage(image);

        } catch (IOException ex) {
            Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



}
