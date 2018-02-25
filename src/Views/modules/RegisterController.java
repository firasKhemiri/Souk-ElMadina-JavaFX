/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.modules;

import Model.DAO.Upload;
import Model.DAO.UserDAO;
import Model.Entities.Acheteur;
import Model.Entities.CurrentUser;
import Model.Entities.User;
import Tools.classes.DbHandler;
import com.jfoenix.controls.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
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

/**
 * FXML Controller class
 *
 * @author danml
 */
public class RegisterController implements Initializable {


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

    private static double progress1 = 0;
    private static double progress2 = 0;
    private static double progress3 = 0;
    private static double progress4 = 0;
    private static double progress5 = 0;
    private static double progress6 = 0;
    private static double progress7 = 0;
    private static double progress8 = 0;
    private static double progress9 = 0;
    private static double progress10 = 0;
    @FXML
    private ProgressBar progressPersonal;
    @FXML
    private JFXRadioButton rdMale;
    @FXML
    private ToggleGroup gender;
    @FXML
    private JFXRadioButton rdFemale;
    private ObservableList<String> depart_lists;
    @FXML
    private JFXTextField txtCourseName;
    @FXML
    private JFXRadioButton rdDegree;
    @FXML
    private ToggleGroup level;
    @FXML
    private JFXRadioButton rdDiploma;
    @FXML
    private JFXRadioButton rdCertificate;
    @FXML
    private TextField currentTimeTextfield;
    @FXML
    private JFXTextField txtAmount;
    @FXML
    private JFXTextField txtMobile111;
    @FXML
    private Label lblComplete;
    private Connection connection;
    private PreparedStatement pst;
    private DbHandler handler;
    @FXML
    private JFXComboBox<String> comboUserType;
    @FXML
    private JFXButton btnClear;
    @FXML
    private JFXButton btnEdit;
    @FXML
    private JFXButton btnSave;
    @FXML
    private AnchorPane parentPane;

    private File file;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        comboUserType.getItems().addAll(
                "Acheteur",
                "Vendeur"
        );
        comboUserType.getSelectionModel().select("Select");


        //       handler = new DbHandler();
        updateProgress();
//        setDepartmentsToCombo();
    }

    private void updateProgress() {
        DecimalFormat decimalFormat = new DecimalFormat("###.#");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

        //progressPersonal.setProgress(0.00);       
        double sum_progress = progress10 + progress1 + progress2 + progress3 + progress4 + progress5 + progress6 + progress7 + progress8 + progress9;

        txtFname.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    progress1 = 0.1;

                } else {
                    progress1 = 0.0;

                }

                double sum = (progress10 + progress1 + progress2 + progress3 + progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
                progressPersonal.setProgress(sum);
                lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
            }
        });

        txtEmail.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    progress2 = 0.1;

                } else {
                    progress2 = 0.0;

                }
                double sum = (progress10 + progress1 + progress2 + progress3 + progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
                progressPersonal.setProgress(sum);
                lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
            }
        });

        txtLname.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    progress3 = 0.1;

                } else {
                    progress3 = 0.0;

                }
                double sum = (progress10 + progress1 + progress2 + progress3 + progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
                progressPersonal.setProgress(sum);
                lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
            }
        });

        txtMobile.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() > 1) {
                    progress4 = 0.1;

                } else {
                    progress4 = 0.0;

                }
                double sum = (progress10 + progress1 + progress2 + progress3 + progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
                progressPersonal.setProgress(sum);
                lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
            }
        });

        txtLocation.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    progress5 = 0.1;

                } else {
                    progress5 = 0.0;

                }
                double sum = (progress10 + progress1 + progress2 + progress3 + progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
                progressPersonal.setProgress(sum);
                lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
            }
        });
        //Course name
        txtUsername.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    progress6 = 0.1;

                } else {
                    progress6 = 0.0;

                }
                double sum = (progress10 + progress1 + progress2 + progress3 + progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
                progressPersonal.setProgress(sum);
                lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
            }
        });
        // Amount paid
        txtPassword.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    progress7 = 0.1;

                } else {
                    progress7 = 0.0;

                }
                double sum = (progress10 + progress1 + progress2 + progress3 + progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
                progressPersonal.setProgress(sum);
                lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
            }
        });
        //Gender Radio buttons
        rdMale.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!oldValue) {
                    progress8 = 0.1;

                }
                double sum = (progress10 + progress1 + progress2 + progress3 + progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
                progressPersonal.setProgress(sum);
                lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
            }
        });
        rdFemale.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!oldValue) {
                    progress8 = 0.1;

                }

                double sum = (progress10 + progress1 + progress2 + progress3 + progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
                progressPersonal.setProgress(sum);
                lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
            }
        });


    /*    comboUserType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                if (!newValue.isEmpty()) {
                    progress10 = 0.1;
                } else {
                    progress10 = 0.0;
                }
                double sum = (progress10 + progress1 + progress2 + progress3 + progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
                progressPersonal.setProgress(sum);
                lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
            }
        });*/

    }


    @FXML
    public void clearFields() {
        txtUsername.setText(null);
        txtPassword.setText(null);
        txtEmail.setText(null);
        txtFname.setText(null);
        txtLname.setText(null);
        txtLocation.setText(null);
        txtMobile.setText(null);


    }


    @FXML
    private void saveUser(ActionEvent event) {

        UserDAO userdao = new UserDAO();

        User a = new User();

        a.setUsername(txtUsername.getText());
        a.setPrenom(txtFname.getText());
        a.setNom(txtLname.getText());
        a.setPassword(txtPassword.getText());
        a.setEmail(txtEmail.getText());
        a.setType(comboUserType.getValue());
        a.setAdresse(txtLocation.getText());

        a.setGender(getGender());
        a.setPhone(txtMobile.getText());

      //  a.setBirthday(dob.get);


        if (file != null) {

            System.out.println(file+" FILE");

            a.setFile(file);
            /*
            int status = userdao.uploadPic(file);

            if (status > 0) {
                System.out.println("success");
            } else {
                System.out.println("failure");
            }*/
        }

        userdao.Ajouter(a);


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
