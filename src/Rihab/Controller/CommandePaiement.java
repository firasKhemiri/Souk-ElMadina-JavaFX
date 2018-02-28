package Rihab.Controller;
import Model.DAO.CommandeDao;
import Model.Entities.Article;
import Model.Entities.Commande;
import Rihab.View.ArticleTableView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Controller.LoginController.statPanier;

public class CommandePaiement implements Initializable {



    @FXML
    private TableColumn<Article, String> colNom;
    @FXML
    private TableColumn<Article, String> colDesc;
    @FXML
    private TableColumn<Article, String> colCat;
    @FXML
    private TableColumn<Article, Integer> colPrix;
    @FXML
    private TableColumn<Article, Integer> colQte;

    @FXML
    private TableView<Article> listView;

    @FXML
    private JFXTextField adresseTxt;

    @FXML
    private JFXComboBox<String> paimentCombo;

    @FXML
    private JFXComboBox<String> livraisonCombo;

    @FXML
    private JFXButton validerComanndeBtn;

    @FXML
    private Label ptLbl;

    @FXML
    private Label tvaLbl;

    @FXML
    private Label pttcLbl;

    @FXML
    private Label errorLbl;


    private Commande commande;

    private CommandeDao commandeDao = new CommandeDao();

    public void initData(Commande commande) {
        this.commande = commande;


        float prix = commande.getPrix();
        ptLbl.setText(Float.toString(prix));
        tvaLbl.setText("18%");
        float prixTva = prix + (prix * 0.18F);
        pttcLbl.setText(Float.toString(prixTva));
        adresseTxt.setText(commande.getAcheteur().getAdresse());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paimentCombo.getItems().addAll("Cheque", "Espece");
        livraisonCombo.getItems().addAll("Domicile", "Contact direct");


        ObservableList<Article> list = FXCollections.observableArrayList(statPanier.getArticleList());


        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCat.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colQte.setCellValueFactory(new PropertyValueFactory<>("order_qte"));

        listView.setItems(list);



    }


    @FXML
    void validerCommande(ActionEvent event) throws IOException {
        String paiment = paimentCombo.getValue();
        String liavraison = livraisonCombo.getValue();

        commande.getPanier().setArticleList(statPanier.getArticleList());

        if (paiment == null) {
            errorLbl.setText("Veuillez saisir la methode de paiment");
        } else {
            if (liavraison == null) {
                errorLbl.setText("Veuillez saisir la methode de livraison");
            } else {
                commande.setAdress_liv(adresseTxt.getText());
                commande.setId(commandeDao.findTheId());
                commande.setMethode_livraison(liavraison);
                commande.setMethode_paiment(paiment);
                commande.setEtat(1);

                commandeDao.add(commande);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Rihab/View/Facture.fxml"));
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                Facture facture = loader.getController();
                facture.initData(commande);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
    }


}
