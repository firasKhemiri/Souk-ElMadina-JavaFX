package Model.DAO;


import Model.Entities.Article;
import Model.Entities.Panier;
import Model.Entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.out;

public class PanierDao {


    private Connection connection;

    public PanierDao() {
        connection = Connexion.getCon();
    }

    public void add(Panier t) {
        try {
            String req = "INSERT INTO panier (acheteur_id, date_modif) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, t.getAcheteur().getId());
            ps.setDate(2, t.getDate_modif());

            System.out.println(ps+" req");
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ex.getMessage()).log(Level.SEVERE, null, ex);
        }
    }

    public void DeleteArticle(Article p) {
        try {

            Statement st = connection.createStatement();
            String requete = "DELETE FROM panier_articles"
                    + "         WHERE (((article_id)='" + p.getId() + "'))";

            if (st.executeUpdate(requete) > 0) {
                //recupérer le numéro sequentiel données par le SGBD
                out.print("success");
            }


        } catch (Exception e) {
            System.err.println("probleme avec la requete " + e.getMessage());
        }

    }



    public void DeleteAllArticles(Panier p) {
        try {

            Statement st = connection.createStatement();
            String requete = "DELETE FROM panier_articles"
                    + "         WHERE (((panier_id)='" + p.getId() + "'))";

            if (st.executeUpdate(requete) > 0) {
                //recupérer le numéro sequentiel données par le SGBD
                out.print("success");
            }


        } catch (Exception e) {
            System.err.println("probleme avec la requete " + e.getMessage());
        }

    }




    public void addPanier(User user,Date date) {
        try {
            String req = "INSERT INTO `panier`(`acheteur_id`, `date_modif`) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, user.getId());
            ps.setDate(2, date);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ex.getMessage()).log(Level.SEVERE, null, ex);
        }
    }


    public void addArticleToPanier(Panier t) {
        for (Article article : t.getArticleList()) {
            try {
                String req = "INSERT INTO `panier_articles`(`panier_id`, `article_id`) VALUES (?,?)";
                PreparedStatement ps = connection.prepareStatement(req);
                ps.setInt(1, t.getId());
                ps.setInt(2, article.getId());
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(ex.getMessage()).log(Level.SEVERE, null, ex);
            }
        }
    }



    public void addSingleArticleToPanier(Panier t , Article article) {
        try {
                String req = "INSERT INTO panier_articles(panier_id, article_id,order_qte) VALUES (?,?,?)";
                PreparedStatement ps = connection.prepareStatement(req);
                ps.setInt(1, t.getId());
                ps.setInt(2, article.getId());
                ps.setInt(3, article.getOrder_qte());

                System.out.println(t.getId()+" art"+article.getId()+" qte " +article.getOrder_qte());
            if (ps.executeUpdate() > 0) {
                //recupérer le numéro sequentiel données par le SGBD
                out.print("success panier");

                new ArticlesDao().ModifArticleQte(article);

            }
            } catch (SQLException ex) {
                Logger.getLogger(ex.getMessage()).log(Level.SEVERE, null, ex);
        }

    }

    public Panier findById(int id) {
        {
            Panier panier = new Panier();

            String requete = "select * from panier where id=?";
            try {
                PreparedStatement ps = connection.prepareStatement(requete);
                ps.setInt(1, id);

                ResultSet resultat = ps.executeQuery();
                while (resultat.next()) {
                    panier.setId(resultat.getInt("id"));
                    panier.setAcheteur(new UserDAO().ChercherAcheteurId(resultat.getInt("acheteur_id")));
                    panier.setDate_modif(resultat.getDate("date_modif"));
                }
                panier.setArticleList(findAllArticlePanier(id));
                return panier;

            } catch (SQLException ex) {
                Logger.getLogger(ex.getMessage()).log(Level.SEVERE, null, ex);
                return null;
            }
        }

    }


    public List<Article> findAllArticlePanier(int id) {
        List<Article> articleList = new ArrayList<>();
        String requete = "SELECT a.id , a.categorie , a.nom , a.description, a.prix, pa.order_qte " +
                "FROM article a " +
                "JOIN panier_articles pa ON a.id = pa.article_id " +
                "JOIN panier p ON pa.panier_id = p.id " +
                "WHERE p.id=" + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultat = statement.executeQuery(requete);
            while (resultat.next()) {
                Article article = new Article();
                article.setId(resultat.getInt(1));
                article.setCategorie(resultat.getString(2));
                article.setNom(resultat.getString(3));
                article.setDescription(resultat.getString(4));
                article.setPrix(resultat.getFloat(5));
                article.setOrder_qte(resultat.getInt(6));

                articleList.add(article);

             //   System.out.println("ff hello");
            }
            return articleList;
        } catch (SQLException ex) {
            Logger.getLogger(ex.getMessage()).log(Level.SEVERE, null, ex);
            return null;
        }
    }





    public Panier findUserPanier(User user)
    {
        Panier panier = new Panier();

        List<Article> articles = new ArrayList<>();

        String requete = "SELECT * FROM panier p " +
                "JOIN panier_articles pa ON pa.panier_id = p.id " +
                "JOIN article a ON pa.article_id = a.id " +
                "WHERE acheteur_id=? GROUP BY a.id,p.id";
        try {
            PreparedStatement ps = connection.prepareStatement(requete);
            ps.setInt(1, user.getId());

            ResultSet res = ps.executeQuery();

                System.out.println(res +" ");

                while (res.next()) {
                    panier.setId(res.getInt("p.id"));
                    panier.setAcheteur(new UserDAO().ChercherAcheteurId(res.getInt("p.acheteur_id")));
                    panier.setDate_modif(res.getDate("p.date_modif"));

                    Article r = new Article();
                    System.out.println(panier.getId()+" ");


                    r.setId(res.getInt("a.id"));
                    r.setNom(res.getString("a.nom"));
                    r.setDescription(res.getString("a.description"));
                    r.setCategorie(res.getString("a.categorie"));
                    r.setPrix(res.getFloat("a.prix"));
                    r.setOrder_qte(res.getInt("pa.order_qte"));

                    r.setImages(new ArticlesDao().SelectArticleImg(r.getId()));

                    articles.add(r);
                }
                if (panier.getAcheteur() == null)
                    return findUserPanierNoArt(user);

                panier.setArticleList(articles);
                //  panier.setArticleList(findAllArticlePanier(id));
                return panier;

        } catch (SQLException ex) {
            Logger.getLogger(ex.getMessage()).log(Level.SEVERE, null, ex);
            return null;
        }
    }



        public Panier findUserPanierNoArt (User user)
        {
            Panier panier = new Panier();

            List<Article> articles = new ArrayList<>();

            String requete = "SELECT * FROM panier p " +
                    "WHERE acheteur_id=? ";
            try {
                PreparedStatement ps = connection.prepareStatement(requete);
                ps.setInt(1, user.getId());

                ResultSet res = ps.executeQuery();
                while (res.next()) {
                    panier.setId(res.getInt("p.id"));
                    panier.setAcheteur(new UserDAO().ChercherAcheteurId(res.getInt("p.acheteur_id")));
                    panier.setDate_modif(res.getDate("p.date_modif"));

                }

                panier.setArticleList(articles);
                //  panier.setArticleList(findAllArticlePanier(id));
                return panier;

            } catch (SQLException ex) {
                Logger.getLogger(ex.getMessage()).log(Level.SEVERE, null, ex);
                return null;
            }



    }



}
