/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.modules.Vendeur;

import Model.DAO.Upload;
import Model.DAO.UserDAO;
import Model.DAO.VendeurDao;
import Model.Entities.Acheteur;
import Model.Entities.CurrentUser;
import Model.Entities.User;
import Model.Entities.Vendeur;
import Tools.classes.DbHandler;
import com.jfoenix.controls.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;

import static Controller.LoginController.statCuser;

/**
 * FXML Controller class
 *
 * @author danml
 */
public class ModifierVenProfileController implements Initializable {


    public JFXTextField txtNomBout;
    public JFXTextField txtDescBout;
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

    Vendeur vendeur = new Vendeur();

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

        VendeurDao daoVen = new VendeurDao();

        vendeur = daoVen.ChercherVen(id);

        txtUsername.setText(vendeur.getUsername());
        txtPassword.setText(vendeur.getPassword());
        txtEmail.setText(vendeur.getEmail());
        txtFname.setText(vendeur.getPrenom());
        txtLname.setText(vendeur.getNom());
        txtLocation.setText(vendeur.getAdresse());
        txtMobile.setText(vendeur.getPhone());

        txtNomBout.setText(vendeur.getNom_boutique());
        txtDescBout.setText(vendeur.getDescription());

        if (vendeur.getGender().equals("Male"))
        {
            rdMale.setSelected(true);
        }
        else
        {
            rdFemale.setSelected(true);
        }

        Blob blob = vendeur.getPhotoprof();
        byte byteImage[];

        if (blob != null)
            try {
                byteImage = blob.getBytes(1,(int)blob.length());
                Image image = new Image(new ByteArrayInputStream(byteImage));
                img.setImage(image);

            } catch (SQLException e) {
                e.printStackTrace();
            }



        vendeur.setId(id);

//        statCuser = (CurrentUser) vendeur;


    }


    @FXML
    public void deleteUser()
    {
        VendeurDao userdao = new VendeurDao();

        userdao.DeleteVen(vendeur);

    }

    @FXML
    private void ModifUser(ActionEvent event) {

        VendeurDao userdao = new VendeurDao();


        vendeur.setUsername(txtUsername.getText());
        vendeur.setPrenom(txtFname.getText());
        vendeur.setNom(txtLname.getText());
        vendeur.setPassword(txtPassword.getText());
        vendeur.setEmail(txtEmail.getText());
        vendeur.setAdresse(txtLocation.getText());

        vendeur.setGender(getGender());
        vendeur.setPhone(txtMobile.getText());

        vendeur.setNom_boutique(txtNomBout.getText());
        vendeur.setDescription(txtDescBout.getText());

        //  a.setBirthday(dob.get);

        System.out.println(vendeur.getPhone()+"ph  FILE");

        if (file != null) {

            System.out.println(file+" FILE");
            vendeur.setFile(file);
        }

        if (userdao.Modifier(vendeur))
        {
            txtDescBout.getScene().getWindow().hide();
        }

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
