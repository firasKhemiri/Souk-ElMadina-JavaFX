package Model.DAO;

import Model.Entities.Article;
import Model.Entities.Commande;
import Model.Entities.Panier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Controller.LoginController.statPanier;

public class CommandeDao {

    private Connection connection;

    private Panier pan;

    public CommandeDao() {
        connection = Connexion.getCon();
    }

    public boolean add(Commande t) {

        try {
            String req = "INSERT INTO `commande`(`acheteur_id`, `adress_liv`, `meth_paiment`, `meth_livraison`, `panier_id`, `date`, `etat`) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, t.getAcheteur().getId());
            ps.setString(2, t.getAdress_liv());
            ps.setString(3, t.getMethode_paiment());
            ps.setString(4, t.getMethode_livraison());
            ps.setInt(5, statPanier.getId());
            ps.setDate(6, t.getDate());
            ps.setInt(7, t.getEtat());
            ps.executeUpdate();

            Commande cm = findCommandByDate(t.getDate());

        //    System.out.println(statPanier.getArticleList().get(0).getOrder_qte()+" size pan");

            for (Article article  : statPanier.getArticleList())
            {
                String req2 = "INSERT INTO commande_articles(commande_id, article_id, quantity) VALUES (?,?,?)";
                ps = connection.prepareStatement(req2);
                ps.setInt(1, cm.getId());
                ps.setInt(2, article.getId());
                ps.setInt(3, article.getOrder_qte());
                ps.executeUpdate();

            }

            statPanier.getArticleList().clear();
            new PanierDao().DeleteAllArticles(statPanier);

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ex.getMessage()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Commande findById(int id) {
        {
            Commande commande = new Commande();

            String requete = "select * from commande where id=?";
            try {
                PreparedStatement ps = connection.prepareStatement(requete);
                ps.setInt(1, id);

                ResultSet resultat = ps.executeQuery();
                while (resultat.next()) {
                    commande.setId(resultat.getInt("id"));
                    commande.setAcheteur(new UserDAO().ChercherAcheteurId(resultat.getInt("acheteur_id")));
                    commande.setAdress_liv(resultat.getString("adress_liv"));
                    commande.setMethode_paiment(resultat.getString("meth_paiment"));
                    commande.setMethode_livraison(resultat.getString("meth_livraison"));
                    commande.setPanier(new PanierDao().findById(resultat.getInt("panier_id")));
                    commande.setDate(resultat.getDate("date"));
                    commande.setEtat(resultat.getInt("etat"));

                }
                return commande;

            } catch (SQLException ex) {
                Logger.getLogger(ex.getMessage()).log(Level.SEVERE, null, ex);
                return null;
            }
        }

    }



    public Commande findCommandByDate(Date date) {
        {
            Commande commande = new Commande();

            String requete = "select * from commande where date=?";
            try {
                PreparedStatement ps = connection.prepareStatement(requete);
                ps.setDate(1, date);

                ResultSet resultat = ps.executeQuery();
                while (resultat.next()) {
                    commande.setId(resultat.getInt("id"));
                    commande.setAcheteur(new UserDAO().ChercherAcheteurId(resultat.getInt("acheteur_id")));
                    commande.setAdress_liv(resultat.getString("adress_liv"));
                    commande.setMethode_paiment(resultat.getString("meth_paiment"));
                    commande.setMethode_livraison(resultat.getString("meth_livraison"));
                    commande.setPanier(new PanierDao().findById(resultat.getInt("panier_id")));
                    commande.setDate(resultat.getDate("date"));
                    commande.setEtat(resultat.getInt("etat"));

                }
                return commande;

            } catch (SQLException ex) {
                Logger.getLogger(ex.getMessage()).log(Level.SEVERE, null, ex);
                return null;
            }
        }

    }

    public void update(Commande t) {
        try {
            String req = "UPDATE `commande` SET `adress_liv`=?,`meth_paiment`=?,`meth_livraison`=?,`date`=?,`etat`=? WHERE `id`=?";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, t.getAdress_liv());
            ps.setString(2, t.getMethode_paiment());
            ps.setString(3, t.getMethode_livraison());
            ps.setDate(4, t.getDate());
            ps.setInt(5, t.getEtat());
            ps.setInt(6, t.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CommandeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    public void updateEtat( int id) {
        try {
            String req = "UPDATE commande SET etat = FALSE WHERE id =?";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CommandeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int findTheId() {

        int id = 0;

        String requete = "SELECT `id` FROM `commande` WHERE `id` = (SELECT MAX(id) FROM `commande`)";
        try {
            Statement ps = connection.prepareStatement(requete);

            ResultSet resultat = ps.executeQuery(requete);
            while (resultat.next()) {
                id = resultat.getInt("id");

            }
            return id;

        } catch (SQLException ex) {
            Logger.getLogger(CommandeDao.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }




    public List<Commande> vendeurCommandes(int idVen)
    {
        List<Commande> commandes = new ArrayList<>();
        Commande commande = new Commande();

        List<Article> articles = new ArrayList<>();

        String requete = "select * from commande c " +
                "JOIN commande_articles ca ON c.id = ca.commande_id " +
                "JOIN article a ON ca.article_id = a.id " +
                "JOIN articles_vendeurs av ON a.id = av.article_id " +
                "JOIN vendeur v ON av.vendeur_id = v.id " +
                "where v.id=? AND c.etat = TRUE GROUP BY ca.commande_id";
        try {
            PreparedStatement ps = connection.prepareStatement(requete);
            ps.setInt(1, idVen);

            ResultSet res = ps.executeQuery();
            while (res.next()) {

                commande = new Commande();

                commande.setId(res.getInt("c.id"));
                commande.setAcheteur(new UserDAO().ChercherAcheteurId(res.getInt("c.acheteur_id")));
                commande.setAdress_liv(res.getString("c.adress_liv"));
                commande.setMethode_paiment(res.getString("c.meth_paiment"));
                commande.setMethode_livraison(res.getString("c.meth_livraison"));
                commande.setPanier(new PanierDao().findById(res.getInt("c.panier_id")));
                commande.setDate(res.getDate("c.date"));
                commande.setEtat(res.getInt("c.etat"));


                articles = new ArticlesDao().chercherCommandesArt(idVen,commande.getId());

                commande.setArticles(articles);

                commandes.add(commande);


            }

            for (Commande commande1 : commandes) {
                System.out.println("arttt ");

                for (Article article : commande1.getArticles())
                    System.out.println(article.getId() + " art " + commande1.getId() + " comm");
            }
            return commandes;

        } catch (SQLException ex) {
            Logger.getLogger(ex.getMessage()).log(Level.SEVERE, null, ex);
            return null;
        }
    }






}
