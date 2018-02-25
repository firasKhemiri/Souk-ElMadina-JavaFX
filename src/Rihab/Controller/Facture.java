package Rihab.Controller;

import Model.Entities.Article;
import Model.Entities.Commande;
import Rihab.View.ArticleTableView;
import com.jfoenix.controls.JFXButton;
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

public class Facture implements Initializable {
    @FXML
    private JFXButton allArticlesBtn;
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
    private Label nomLbl;

    @FXML
    private Label prenomLbl;

    @FXML
    private Label emailLbl;

    @FXML
    private Label prixLbl;

    @FXML
    private Label tvaLbl;

    @FXML
    private Label pttcLbl;

    @FXML
    private Label adresseLbl;

    @FXML
    private Label livraisonLbl;

    @FXML
    private Label paimentLbl;

    private Commande commande;

    public void initData(Commande commande) {
        this.commande = commande;
        livraisonLbl.setText(commande.getMethode_livraison());
        adresseLbl.setText(commande.getAdress_liv());
        paimentLbl.setText(commande.getMethode_paiment());
        pttcLbl.setText(Float.toString(commande.getPrix() + commande.getPrix() * 0.18F) + " DT");
        prixLbl.setText(Float.toString(commande.getPrix()) + " DT");
        tvaLbl.setText("18%");
        emailLbl.setText(commande.getAcheteur().getEmail());
        prenomLbl.setText(commande.getAcheteur().getPrenom());
        nomLbl.setText(commande.getAcheteur().getNom());
        tableView.setItems(fillTable());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nomTable.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        categorieTable.setCellValueFactory(cellData -> cellData.getValue().categorieProperty());
        DescriptionTable.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        prixTable.setCellValueFactory(cellData -> cellData.getValue().prixProperty());
    }

    private ObservableList<ArticleTableView> fillTable() {
        ObservableList<ArticleTableView> articleData = FXCollections.observableArrayList();
        System.out.println(commande);
        for (Article a : commande.getPanier().getArticleList()) {

            ArticleTableView article = new ArticleTableView(a);
            articleData.add(article);
        }
        return articleData;
    }

    @FXML
    void goToAllArticles(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/Accueil.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
