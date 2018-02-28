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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Controller.LoginController.statPanier;

public class Facture implements Initializable {
    @FXML
    private JFXButton allArticlesBtn;


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


        ObservableList<Article> list = FXCollections.observableArrayList(commande.getPanier().getArticleList());


        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCat.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colQte.setCellValueFactory(new PropertyValueFactory<>("order_qte"));

        listView.setItems(list);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


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
    void exit(ActionEvent event) {

        prenomLbl.getScene().getWindow().hide();
    }
}
