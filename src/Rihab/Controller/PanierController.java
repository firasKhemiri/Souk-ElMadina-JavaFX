package Rihab.Controller;

import Model.DAO.PanierDao;
import Model.Entities.Article;
import Model.Entities.Commande;
import Model.Entities.Panier;
import Model.Entities.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import static Controller.LoginController.statPanier;

public class PanierController implements Initializable {

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
    private JFXButton enleverBtn;

    @FXML
    private JFXButton payementBtn;

    @FXML
    private JFXButton viderPanierBtn;


    @FXML
    private TableView<Article> listView;

    private PanierDao daoPan;


    private Panier panier;

    public void intiData(Panier panier) {
        this.panier = panier;
        /*for (Article article : panier.getArticleList()) {
            listView.getItems().addArticle(article);
        }*/

        daoPan = new PanierDao();

        ObservableList<Article> list = FXCollections.observableArrayList(panier.getArticleList());


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


    @FXML
    void enleverArticle(ActionEvent event) {
        Article item =listView.getSelectionModel().getSelectedItem();
        listView.getItems().remove(item);

        statPanier.getArticleList().remove(item);

        daoPan.DeleteArticle(item);
    }

    @FXML
    void payementAction(ActionEvent event) throws IOException {
        Commande commande = new Commande(panier.getAcheteur(), "", "", "", panier, new Date(new java.util.Date().getTime()), 0);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Rihab/View/CommandePaiement.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        CommandePaiement cmdPai = loader.getController();
        cmdPai.initData(commande);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();


    }

    @FXML
    void viderPanier(ActionEvent event) {
        listView.getItems().clear();
        panier.getArticleList().clear();
        statPanier.getArticleList().clear();

        daoPan.DeleteAllArticles(statPanier);

    }


    private HBox initializeHboxListView(Article article) {
        HBox hBox = new HBox();

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(15, 20, 10, 10));
        Label lblid = new Label(Integer.toString(article.getId()));
        Label lblNom = new Label(article.getNom());
        Label lblCategorie = new Label(article.getCategorie());
        Text lblDescription = new Text(article.getDescription());
        Label lblPrix = new Label(Float.toString(article.getPrix()));
        vBox.setSpacing(4);
        vBox.getChildren().addAll(lblid, lblNom, lblDescription, lblCategorie, lblPrix);
        hBox.getChildren().add(vBox);
        return hBox;
    }
}


