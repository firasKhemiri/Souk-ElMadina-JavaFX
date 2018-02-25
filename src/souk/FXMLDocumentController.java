/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk;

import Model.DAO.UserDAO;
import Model.Entities.Acheteur;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author FIRAS
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
        
        UserDAO userdao = new UserDAO();
        
        Acheteur a = new Acheteur();
        
        a.setUsername("user1");
        a.setPassword("pass");
        a.setEmail("email");
        
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
        // TODO
    }    
    
}
