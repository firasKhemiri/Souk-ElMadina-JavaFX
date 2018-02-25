package Views.modules.Admin;

import Model.DAO.ArticlesDao;
import Model.DAO.UserDAO;
import Model.Entities.Article;
import Model.Entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ArticlesGes implements Initializable {


    @FXML
    private TableView<Article> articles;
    @FXML
    private TableColumn<Article, String> colId;
    @FXML
    private TableColumn<Article,Float> colNom;
    @FXML
    private TableColumn<Article, Integer> colPrix;
    @FXML
    private TableColumn<Article,String> colVendeur;
    @FXML
    private TableColumn<Article,String> colCat;
    @FXML
    private TableColumn<Article,String> colDesc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        ArticlesDao dao = new ArticlesDao();

        ObservableList<Article> articleList =  FXCollections.observableArrayList(dao.SelectArticles());


        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCat.setCellValueFactory(new PropertyValueFactory<>("categorie"));

      /*  colVendeur.setCellValueFactory(new PropertyValueFactory<>("ville"));
        colCat.setCellValueFactory(new PropertyValueFactory<>("etat"));*/


        colDesc.setEditable(true);
        colId.setEditable(true);

        articles.setItems(null);
        articles.setItems(articleList);

    }

    @FXML
    private void delete(Event event)
    {
        Article selectedItem = articles.getSelectionModel().getSelectedItem();
        articles.getItems().remove(selectedItem);


        ArticlesDao dao = new ArticlesDao();

        dao.DeleteArt(selectedItem);
    }


}
