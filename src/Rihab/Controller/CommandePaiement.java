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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CommandePaiement implements Initializable {

    @FXML
    private TableView<ArticleTableView> tableView;
    @FXML
    private TableColumn<ArticleTableView, String> nomTable;

    @FXML
    private TableColumn<ArticleTableView, String> categorieTable;

    @FXML
    private TableColumn<ArticleTableView, String> DescriptionTable;

    @FXML
    private TableColumn<ArticleTableView, Number> prixTable;

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
        commandeDao.add(commande);
        tableView.setItems(fillTable());
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
        nomTable.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        categorieTable.setCellValueFactory(cellData -> cellData.getValue().categorieProperty());
        DescriptionTable.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        prixTable.setCellValueFactory(cellData -> cellData.getValue().prixProperty());

    }


    @FXML
    void validerCommande(ActionEvent event) throws IOException {
        String paiment = paimentCombo.getValue();
        String liavraison = livraisonCombo.getValue();
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
                commandeDao.update(commande);

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


    private ObservableList<ArticleTableView> fillTable() {
        ObservableList<ArticleTableView> articleData = FXCollections.observableArrayList();
        for (Article a : commande.getPanier().getArticleList()) {

            ArticleTableView article = new ArticleTableView(a);
            articleData.add(article);
        }
        return articleData;
    }
}
