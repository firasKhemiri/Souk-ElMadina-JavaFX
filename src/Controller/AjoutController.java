/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Entities.CurrentUser;
import souk.*;
import Model.DAO.UserDAO;
import Model.Entities.Acheteur;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 *
 * @author FIRAS
 */
public class AjoutController implements Initializable {

    @FXML
    private Label userId;
    @FXML
    private TextField firstName;
    
    @FXML
    private TextField username;
    
    @FXML
    private TextField lastName;
    @FXML
    private DatePicker dob;
    @FXML
    private RadioButton rbMale;
    @FXML
    private ToggleGroup gender;
    @FXML
    private RadioButton rbFemale;
    @FXML
    private ComboBox<String> cbRole;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button reset;
    @FXML
    private Button saveUser;
    @FXML
    private Button btnLogout;
    @FXML
    private TableView<?> userTable;
    @FXML
    private MenuItem deleteUsers;
    @FXML
    private TableColumn<?, ?> colUserId;
    @FXML
    private TableColumn<?, ?> colFirstName;
    @FXML
    private TableColumn<?, ?> colLastName;
    @FXML
    private TableColumn<?, ?> colDOB;
    @FXML
    private TableColumn<?, ?> colGender;
    @FXML
    private TableColumn<?, ?> colRole;
    @FXML
    private TableColumn<?, ?> colEmail;
    @FXML
    private TableColumn<?, ?> colEdit;
    
    
    
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        
        UserDAO userdao = new UserDAO();
        
        Acheteur a = new Acheteur();
        
       // userdao.Ajouter(a);
        
        
        List<Acheteur> users = new ArrayList<>();
        users = userdao.Select();
        
        for (int i = 0; i < users.size(); i++) {
    
            System.out.println(users.get(i).getId()+" "+users.get(i).getUsername()+" ");
    // fruit is an element of the `fruits` array.
}
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
        cbRole.getItems().addAll(
            "Acheteur",
            "Vendeur"
        );
        cbRole.getSelectionModel().select("Select");
        
    }    

    @FXML
    private void exit(ActionEvent event) {
    }

    @FXML
    private void reset(ActionEvent event) {
    }

    @FXML
    private void saveUser(ActionEvent event) {
        
        UserDAO userdao = new UserDAO();
        
        CurrentUser a = new CurrentUser();
        
        /*if(validate("First Name", firstName.getText(), "[a-zA-Z]+") &&
    	   validate("Last Name", lastName.getText(), "[a-zA-Z]+") &&
    	   emptyValidation("DOB", dob.getEditor().getText().isEmpty()) && 
    	   emptyValidation("Role", getRole() == null) ){
    		
    		if(userId.getText() == null || userId.getText() == ""){
    			if(validate("Email", getEmail(), "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+") &&
    				emptyValidation("Password", getPassword().isEmpty())){
    				*/
        
    			
        a.setUsername(username.getText());
        a.setPrenom(firstName.getText());
        a.setNom(lastName.getText());
        a.setPassword(password.getText());
        a.setEmail(email.getText());
        a.setType(cbRole.getValue());
       
                                
        userdao.Ajouter(a);
      
    }

    @FXML
    private void logout(ActionEvent event) {
    }

    @FXML
    private void deleteUsers(ActionEvent event) {
    }

  
}
