package Model.DAO;

import Model.Entities.Article;
import Model.Entities.Categorie;
import Model.Entities.Image;
import Model.Entities.Vendeur;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Controller.LoginController.statPanier;
import static java.lang.System.in;
import static java.lang.System.out;

public class ArticlesDao {


    private static Connection connecter() {
        return Connexion.getCon();
    }




    public boolean addArticle(Article t, int idVen) {

        try {
            Connection con = connecter();
            String req = "INSERT INTO article(nom, description, prix, categorie, quantity, date_pub ) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(req,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, t.getNom());
            ps.setString(2, t.getDescription());
            ps.setFloat(3, t.getPrix());
            ps.setString(4, t.getCategorie());
            ps.setInt(5, t.getQuantity());
            ps.setDate(6, new Date(new java.util.Date().getTime()));

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {

                    //  user.setId(generatedKeys.getInt(1));


                    String reqVen = "INSERT INTO articles_vendeurs(article_id,vendeur_id ) VALUES (?,?)";
                    PreparedStatement psVen = con.prepareStatement(reqVen);
                    psVen.setInt(1, generatedKeys.getInt(1));
                    psVen.setInt(2, idVen);

                    psVen.executeUpdate();


                    Categorie categorie = SelectCategorie(t.getCategorie());

                    String reqCat = "INSERT INTO categories_articles(article_id,categorie_id ) VALUES (?,?)";
                    PreparedStatement psCat = con.prepareStatement(reqCat);
                    psCat.setInt(1, generatedKeys.getInt(1));
                    psCat.setInt(2, categorie.getId());

                    psCat.executeUpdate();


                    for (Image image : t.getImages())
                    {
                        FileInputStream fin = new FileInputStream(image.getFile());
                        int len = (int) image.getFile().length();

                        String reqImg = "INSERT INTO images(article_id,image ) VALUES (?,?)";
                        PreparedStatement psImg = con.prepareStatement(reqImg);
                        psImg.setInt(1, generatedKeys.getInt(1));
                        psImg.setBinaryStream(2, fin, len);

                        psImg.executeUpdate();

                    }



                }
                else {
                    throw new SQLException("Ajout produit echoué.");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ex.getMessage()).log(Level.SEVERE, null, ex);
        }
        return false;
    }





    public boolean ModifArticle(Article t) {

        try {
            Connection con = connecter();
            String req = "UPDATE article SET nom = ?, description = ? , prix = ? , categorie = ? , quantity = ?  WHERE id = ? ";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setString(1, t.getNom());
            ps.setString(2, t.getDescription());
            ps.setFloat(3, t.getPrix());
            ps.setString(4, t.getCategorie());
            ps.setInt(5, t.getQuantity());
            ps.setInt(6, t.getId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("no rows affected.");
            }

            //  user.setId(generatedKeys.getInt(1));

            //TODO for Admin maybe
                  /*  String reqVen = "UPDATE articles_vendeurs SET article_id = ? , vendeur_id = ? ) VALUES (?,?)";
                    PreparedStatement psVen = con.prepareStatement(reqVen);
                    psVen.setInt(1, t.getId);
                    psVen.setInt(2, idVen);

                    psVen.executeUpdate();
                    */

            Categorie categorie = SelectCategorie(t.getCategorie());

            String reqCat = "UPDATE categories_articles SET article_id = ? , categorie_id = ? WHERE article_id = ? AND categorie_id = ?";
            PreparedStatement psCat = con.prepareStatement(reqCat);
            psCat.setInt(1, t.getId());
            psCat.setInt(2, categorie.getId());

            psCat.setInt(3, t.getId());
            psCat.setInt(4, categorie.getId());


            psCat.executeUpdate();

            for (Image image : t.getImages())
            {
                FileInputStream fin = new FileInputStream(image.getFile());
                int len = (int) image.getFile().length();

                String reqImg = "INSERT INTO images(article_id,image ) VALUES (?,?)";
                PreparedStatement psImg = con.prepareStatement(reqImg);
                psImg.setInt(1, t.getId());
                psImg.setBinaryStream(2, fin, len);

                psImg.executeUpdate();

            }


            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ex.getMessage()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }



    public void DeleteImagesArt(Article p) {
        try {

            Statement st = connecter().createStatement();
            String requete = "DELETE FROM images"
                    + "         WHERE (((article_id)='" + p.getId() + "'))";

            if (st.executeUpdate(requete) > 0) {
                //recupérer le numéro sequentiel données par le SGBD
                out.print("success");
            }


        } catch (Exception e) {
            System.err.println("probleme avec la requete " + e.getMessage());
        }

    }




    public void DeleteArt(Article p) {
        try {

            if (p.getImages().size()>0)
            {
                DeleteImagesArt(p);
            }

            Statement st = connecter().createStatement();

            String requete = "DELETE FROM articles_vendeurs"
                    + "         WHERE (((article_id)='" + p.getId() + "'))";

            if (st.executeUpdate(requete) > 0) {
                //recupérer le numéro sequentiel données par le SGBD
                out.print("success");
            }

            requete = "DELETE FROM categories_articles"
                    + "         WHERE (((article_id)='" + p.getId() + "'))";

            if (st.executeUpdate(requete) > 0) {
                //recupérer le numéro sequentiel données par le SGBD
                out.print("success");
            }


            requete = "DELETE FROM commande_articles"
                    + "         WHERE (((article_id)='" + p.getId() + "'))";

            if (st.executeUpdate(requete) > 0) {
                //recupérer le numéro sequentiel données par le SGBD
                out.print("success");
            }


            requete = "DELETE FROM panier_articles"
                    + "         WHERE (((article_id)='" + p.getId() + "'))";

            if (st.executeUpdate(requete) > 0) {
                //recupérer le numéro sequentiel données par le SGBD
                out.print("success");
            }


            requete = "DELETE FROM bonplan_articles"
                    + "         WHERE (((article_id)='" + p.getId() + "'))";

            if (st.executeUpdate(requete) > 0) {
                //recupérer le numéro sequentiel données par le SGBD
                out.print("success");
            }

            requete = "DELETE FROM article"
                    + "         WHERE (((id)='" + p.getId() + "'))";

            if (st.executeUpdate(requete) > 0) {
                //recupérer le numéro sequentiel données par le SGBD
                out.print("success");
            }


        } catch (Exception e) {
            System.err.println("probleme avec la requete " + e.getMessage());
        }

    }





    public List<Article> SelectArticles() {
        Connection con;
        List<Article> articles = new ArrayList<>();
        if ((con = connecter()) != null) {
            try {
                Statement st = con.createStatement();
                String requete = "SELECT *"
                        + "         FROM article";

                ResultSet res = st.executeQuery(requete);
                ArticlesResultSet(articles, res);

            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }

        }

        return articles;
    }



    public List<Categorie> SelectCategories() {
        Connection con;
        List<Categorie> categories = new ArrayList<>();
        if ((con = connecter()) != null) {
            try {
                Statement st = con.createStatement();
                String requete = "SELECT *"
                        + "         FROM categorie";

                ResultSet res = st.executeQuery(requete);
                while (res.next())
                {
                    Categorie cat = new Categorie();
                    cat.setId(res.getInt("id"));
                    cat.setNom(res.getString("nom"));
                    cat.setDescrption(res.getString("description"));

                    categories.add(cat);
                }

            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }

        }

        return categories;
    }




    public List<Article> SelectArticlesVen(int id) {
        Connection con;
        List<Article> articles = new ArrayList<>();
        if ((con = connecter()) != null) {
            try {
                Statement st = con.createStatement();
                String requete = "select * from article a, articles_vendeurs av " +
                        "where av.vendeur_id = '" +id+"' and a.id = av.article_id ";

                ResultSet res = st.executeQuery(requete);
                ArticlesResultSet(articles, res);

            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }

        }

        return articles;
    }

    private void ArticlesResultSet(List<Article> articles, ResultSet res) throws SQLException {
        Article r;
        while (res.next()) {
            r = new Article();
            r.setId(res.getInt("id"));
            r.setNom(res.getString("nom"));
            r.setDescription(res.getString("description"));
            r.setCategorie(res.getString("categorie"));
            r.setPrix(res.getFloat("prix"));
            r.setQuantity(res.getInt("quantity"));

            r.setImages(SelectArticleImg(r.getId()));

            articles.add(r);

            System.out.println("success");
        }
    }


    public List<Article> SearchArticlesVen(int id,String name) {
        Connection con;
        List<Article> articles = new ArrayList<>();
        if ((con = connecter()) != null) {
            try {
                Statement st = con.createStatement();
                String requete = "select * from article a, articles_vendeurs av " +
                        "where av.vendeur_id = '" +id+"' and a.id = av.article_id and a.nom LIKE '%"+name+"%'";

                ResultSet res = st.executeQuery(requete);
                ArticlesResultSet(articles, res);

            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }

        }

        return articles;
    }



    public List<Image> SelectArticleImg(int id) {
        Image r ;
        Connection con;
        List<Image> images = new ArrayList<>();
        if ((con = connecter()) != null) {
            try {
                Statement st = con.createStatement();
                String requete = "select * from images " +
                        "where article_id= '" +id+"' ";

                ResultSet res = st.executeQuery(requete);
                while (res.next()) {
                    r = new Image();
                    r.setId(res.getInt("id"));
                    r.setImage(res.getBlob("image"));
                    images.add(r);
                }

            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }

        }

        return images;
    }






    public List<Article> SearchArticlesForm(String nom_boutique ,String nom_art,String cat,String type_tri,String gov,double pmin, double pmax) {
        Connection con;
        List<Article> articles = new ArrayList<>();
        if ((con = connecter()) != null) {
            try {
                Statement st = con.createStatement();
         /*       String requete2 = "select * from article a, articles_vendeurs av , vendeur v , user u, categories_articles ac , categorie c " +
                        "where v.nom_boutique LIKE '%"+nom_boutique+"%' and u.id = v.id AND u.state LIKE '%"+gov+"%'" +
                        " and a.id = av.article_id and a.nom LIKE '%"+nom_art+"%' AND c.nom LIKE '"+cat+"' AND c.id = ac.categorie_id AND ac.article_id = a.id" +
                        " AND a.prix BETWEEN"+pmin+" AND "+pmax;

*/

                String requete = " select * from article a " +
                        "JOIN articles_vendeurs av ON a.id = av.article_id " +
                        "JOIN vendeur v ON av.vendeur_id = v.id " +
                        "JOIN user u ON u.id = v.id "+
                        "JOIN categories_articles ac ON a.id = ac.article_id " +
                        "JOIN categorie c ON ac.categorie_id = c.id " +

                        "WHERE u.state LIKE '%"+gov+"%' " +
                        "AND v.nom_boutique LIKE '%"+nom_boutique+"%' " +
                        "AND a.nom LIKE '%"+nom_art+"%' "+
                        "AND c.nom LIKE '%"+cat+"%' "+
                        "AND a.prix BETWEEN '"+pmin+"' AND '"+pmax+"' ";

                switch (type_tri) {
                    case "Prix croissant":
                        requete = requete + "ORDER BY a.prix ASC";
                        break;
                    case "Prix decroissant":
                        requete = requete + "ORDER BY a.prix Desc";
                        break;
                    case "Date pub croissant":
                        requete = requete + "ORDER BY a.date_pub ASC";
                        break;
                    case "date pub decroissant":
                        requete = requete + "ORDER BY a.date_pub DESC";
                        break;
                    case "Alphabetique":
                        requete = requete + "ORDER BY a.nom ASC";
                        break;
                   /* case "":
                        requete = requete + "ORDER BY a.nom DESC";
                        break;*/
                }


                ResultSet res = st.executeQuery(requete);
                ArticlesResultSet(articles, res);

            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }

        }

        return articles;
    }



    public Article SelectArticle(int id) {
        Connection con;
        List<Article> articles = new ArrayList<>();
        if ((con = connecter()) != null) {
            try {
                Statement st = con.createStatement();
                String requete = "SELECT *"
                        + "         FROM article a WHERE a.id = '"+id+"'" ;

                ResultSet res = st.executeQuery(requete);
                ArticlesResultSet(articles, res);

            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }
        }
        return articles.get(0);
    }



    public Categorie SelectCategorie(String nom) {
        Connection con;

        con = connecter();

        Categorie categorie = new Categorie();

        String requete = "select * from categorie where nom=?";
        try {
            PreparedStatement ps = con.prepareStatement(requete);

            ps.setString(1, nom);

            ResultSet resultat = ps.executeQuery();
            while (resultat.next()) {
                categorie.setId(resultat.getInt("id"));
                categorie.setNom(resultat.getString("nom"));
                categorie.setDescrption(resultat.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorie;
    }


















}
