package Views.modules.Admin;

import Model.DAO.UserDAO;
import Model.Entities.CurrentUser;
import Model.Entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class UsersGes implements Initializable {
    @FXML
    public TextField txtAdresse;
    @FXML
    public TextField txtEmail;
    @FXML
    public TextField txtPreom;
    @FXML
    public TextField txtNom;
    @FXML
    public TextField txtUsername;
    @FXML
    private TableView<User> acheteurs;
    @FXML
    private TableColumn<User, String> colId;
    @FXML
    private TableColumn<User,Float> colNom;
    @FXML
    private TableColumn<User, Integer> colUsername;
    @FXML
    private TableColumn<User,String> colPrenom;
    @FXML
    private TableColumn<User,String> colEmail;
    @FXML
    private TableColumn<User,String> colAdresse;


    @FXML
    private TableColumn<User,String> colPhoto;
    @FXML
    private TableColumn<User,String> colType;
    @FXML
    private TableColumn<User,String> colTel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        UserDAO dao = new UserDAO();

        ObservableList<User> articleList =  FXCollections.observableArrayList(dao.Select());


        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<>("adressse"));
      //  colType.setCellValueFactory(new PropertyValueFactory<>("type_utilisateur"));



      /*  colVendeur.setCellValueFactory(new PropertyValueFactory<>("ville"));
        colCat.setCellValueFactory(new PropertyValueFactory<>("etat"));*/

        acheteurs.setItems(null);
        acheteurs.setItems(articleList);



    }

    @FXML
    private void delete(Event event)
    {
        User selectedItem = acheteurs.getSelectionModel().getSelectedItem();
        acheteurs.getItems().remove(selectedItem);

        UserDAO dao = new UserDAO();

        dao.DeleteDepricated(selectedItem);
    }

    @FXML
    private void edit(Event event)
    {

        User selectedItem = acheteurs.getSelectionModel().getSelectedItem();
        acheteurs.getItems().remove(selectedItem);

        CurrentUser cu = new CurrentUser();

        cu.setId(selectedItem.getId());
        cu.setUsername(txtUsername.getText());
        cu.setNom(txtNom.getText());
        cu.setPrenom(txtPreom.getText());
        cu.setEmail(txtEmail.getText());
        cu.setAdresse(txtAdresse.getText());

        UserDAO dao = new UserDAO();

        dao.Modifier(cu);


        ObservableList<User> articleList =  FXCollections.observableArrayList(dao.Select());


        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<>("adressse"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type_utilisateur"));


        acheteurs.setItems(null);
        acheteurs.setItems(articleList);


    }

    @FXML
    public void addImd(Event event)
    {
        String ImageName="MyImage.png";

        File file = new File("src\\Messages\\Attachements\\Images");

        try {
            if (!file.exists()) {
                file.mkdir();


            }

    } catch (Exception io) {
        io.printStackTrace();
    }
  //  Image image = new Image("/Messages/Attachements/Images/"+ImageName=
    //                        ImageReceived.setImage(image);

}

}

