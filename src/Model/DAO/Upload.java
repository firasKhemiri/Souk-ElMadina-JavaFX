package Model.DAO;



import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import java.sql.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;

import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

public class Upload extends Application
{
    PreparedStatement ps;
    Connection conn;
    ResultSet rs;


    public static void main(String[]args)
    {
        Application.launch(args);

    }

    @Override
    public void start(Stage primaryStage)
    {
        BorderPane bp = new BorderPane();
        Scene scene = new Scene(bp,800,600);

        ImageView imgView = new ImageView();//setting imageview where we will set our uploaded picture before taking it to the database
        imgView.setFitWidth(200);
        imgView.setFitHeight(200);

        //creating button to help us upload our photo
        Button uploadB = new Button("Upload");

        bp.setBottom(uploadB);//placing our upload button on the button of our borderpane
        bp.setCenter(imgView);//placing our imageview at the center of our borderpane

        //setting the upload action for our photo
        uploadB.setOnAction((ActionEvent t) -> {
            FileChooser fc = new FileChooser();
            FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");
            FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)","*.PNG");
            fc.getExtensionFilters().addAll(ext1,ext2);
            File file = fc.showOpenDialog(primaryStage);

            BufferedImage bf;
            try {
                bf = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bf, null);
                imgView.setImage(image);

                UserDAO dao = new UserDAO();
                int status = dao.uploadPic(file);

                if(status>0)
                {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Information dialog");
                    alert.setContentText("Photo saved successfully");
                    alert.showAndWait();

                }
                else
                {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Dialog.");
                    alert.setHeaderText("Error Information");
                    alert.showAndWait();
                }
            } catch (IOException ex) {
                Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        primaryStage.setScene(scene);
        primaryStage.setTitle("Insert Image Into Database");
        primaryStage.show();
    }


    /*
     @Override
    public void start(Stage primaryStage)
    {
        BorderPane bp = new BorderPane();
        Scene scene = new Scene(bp,800,600);

        ImageView imgView = new ImageView();//setting imageview where we will set our uploaded picture before taking it to the database
        imgView.setFitWidth(200);
        imgView.setFitHeight(200);

        //creating button to help us upload our photo
        Button uploadB = new Button("Upload");

        bp.setBottom(uploadB);//placing our upload button on the button of our borderpane
        bp.setCenter(imgView);//placing our imageview at the center of our borderpane

        //setting the upload action for our photo
        uploadB.setOnAction((ActionEvent t) -> {
            FileChooser fc = new FileChooser();
            FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");
            FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)","*.PNG");
            fc.getExtensionFilters().addAll(ext1,ext2);
            File file = fc.showOpenDialog(primaryStage);

            BufferedImage bf;
            try {
                bf = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bf, null);
                imgView.setImage(image);
                FileInputStream fin = new FileInputStream(file);
                int len = (int)file.length();
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/souk", "root", "");
                PreparedStatement ps = conn.prepareStatement("INSERT INTO images (image) VALUES(?)");
                ps.setBinaryStream(1,fin,len);
                int status = ps.executeUpdate();
                if(status>0)
                {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Information dialog");
                    alert.setContentText("Photo saved successfully");
                    alert.showAndWait();

                }
                else
                {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Dialog.");
                    alert.setHeaderText("Error Information");
                    alert.showAndWait();
                }
                conn.close();
            } catch (IOException ex) {
                Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        primaryStage.setScene(scene);
        primaryStage.setTitle("Insert Image Into Database");
        primaryStage.show();
    }

     */


}



