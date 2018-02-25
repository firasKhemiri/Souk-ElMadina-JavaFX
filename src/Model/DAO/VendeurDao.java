package Model.DAO;

import Model.Entities.Vendeur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class VendeurDao {



    private static Connection connecter() {
        return Connexion.getCon();
    }


    public List<Vendeur> ChercherVendeursNomGov(String nom, String gov) {
        List<Vendeur> list = new ArrayList<>();
        Connection con;
        if ((con = connecter()) != null) {
            try {
                Statement st = con.createStatement();

                String requete = "select * from user , vendeur " +
                        "where user.id = vendeur.id and vendeur.nom_boutique LIKE '%"+nom+"%' and user.state LIKE '%"+gov+"%'";

                System.out.println(requete+" ");
                ResultSet res = st.executeQuery(requete);
                VendeurResultSet(list, res);


            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }

        }
        return list;
    }




    public List<Vendeur> ChercherVendeursForm(String nom, String gov,String type) {
        List<Vendeur> list = new ArrayList<>();
        Connection con;
        if ((con = connecter()) != null) {
            try {
                Statement st = con.createStatement();

                String requete = "select * from user , vendeur " +
                        "where user.id = vendeur.id and vendeur.nom_boutique LIKE '%"+nom+"%' and user.state LIKE '%"+gov+"%' AND vendeur.type LIKE '%"+type+"%'";

                System.out.println(requete+" ");
                ResultSet res = st.executeQuery(requete);
                VendeurResultSet(list, res);


            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }

        }
        return list;
    }



    private void VendeurResultSet(List<Vendeur> list, ResultSet res) throws SQLException {
        while (res.next()) {
            Vendeur r = new Vendeur();
            r.setId(res.getInt("id"));
            r.setUsername(res.getString("username"));
            r.setNom(res.getString("nom"));
            r.setPrenom(res.getString("prenom"));
            r.setEmail(res.getString("email"));
            r.setPhotoprof(res.getBlob("photoprof"));
            r.setAdresse(res.getString("adresse"));
            r.setPhone(res.getString("phone"));
            r.setGender(res.getString("gender"));

            r.setNom_boutique(res.getString("nom_boutique"));
            r.setNote(res.getInt("note"));
            r.setDescription(res.getString("description"));

            System.out.println(res.getInt("id")+" "+res.getString("nom_boutique"));

            list.add(r);
        }
    }

/*
    public List<Vendeur> ChercherVendeursParGov(String gov) {
        List<Vendeur> list = new ArrayList<>();
        Connection con;
        if ((con = connecter()) != null) {
            try {
                Statement st = con.createStatement();
                String requete = "SELECT *"
                        + " FROM user"
                        + " WHERE (((id)='" + id + "'))";


                String requete = "select * from user , vendeur " +
                        "where user.id = vendeur.id and user.state = '"+gov+"'";

                System.out.println(requete+" ");
                ResultSet res = st.executeQuery(requete);
                VendeurResultSet(list, res);


            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }

        }
        return list;
    }
*/




    public Vendeur ChercherVen(int id) {
        Vendeur r = new Vendeur();
        Connection con;
        if ((con = connecter()) != null) {
            try {
                Statement st = con.createStatement();
                /*String requete = "SELECT *"
                        + " FROM user"
                        + " WHERE (((id)='" + id + "'))";
*/

                String requete = "select * from user , vendeur " +
                        "where user.id = vendeur.id and " +
                        "user.id = '"+ id +"'";

                System.out.println(requete+" ");
                ResultSet res = st.executeQuery(requete);
                SingleVen(r, res);


            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }

        }
        return r;
    }



    public List<Vendeur> SelectVendeurs() {
        List<Vendeur> list = new ArrayList<>();
        Connection con;
        if ((con = connecter()) != null) {
            try {
                Statement st = con.createStatement();
                /*String requete = "SELECT *"
                        + " FROM user"
                        + " WHERE (((id)='" + id + "'))";
*/

                String requete = "select * from user , vendeur " +
                        "where user.id = vendeur.id ";

                System.out.println(requete+" ");
                ResultSet res = st.executeQuery(requete);
                VendeurResultSet(list, res);


            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }

        }
        return list;
    }






    public Vendeur SelectVenArt(int id) {
        Vendeur r = new Vendeur();
        Connection con;
        if ((con = connecter()) != null) {
            try {
                Statement st = con.createStatement();

                String requete = "SELECT *"
                        + "         FROM vendeur v " +
                        "JOIN articles_vendeurs av ON v.id = av.vendeur_id " +
                        "JOIN article a ON av.article_id = a.id " +
                        "JOIN user u ON v.id = u.id " +

                        "WHERE a.id = '"+id+"'" ;

                System.out.println(requete+" ");
                ResultSet res = st.executeQuery(requete);
                SingleVen(r, res);


            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }

        }
        return r;
    }

    private void SingleVen(Vendeur r, ResultSet res) throws SQLException {
        while (res.next()) {
            r.setId(res.getInt("id"));
            r.setUsername(res.getString("username"));
            r.setNom(res.getString("nom"));
            r.setPrenom(res.getString("prenom"));
            r.setEmail(res.getString("email"));
            r.setPhotoprof(res.getBlob("photoprof"));
            r.setAdresse(res.getString("adresse"));
            r.setPassword(res.getString("password"));
            r.setGender(res.getString("gender"));
            r.setPhone(res.getString("phone"));

            r.setNom_boutique(res.getString("nom_boutique"));
            r.setNote(res.getInt("note"));
            r.setDescription(res.getString("description"));

            System.out.println(res.getInt("id")+" "+res.getString("nom_boutique"));
        }
    }



    public void DeleteVen(Vendeur p) {
        try {


            Statement st = connecter().createStatement();

            // ArticlesDao daoArt = new ArticlesDao();


            String requete = "DELETE FROM articles_vendeurs"
                    + "         WHERE (((vendeur_id)='" + p.getId() + "')) DELETE ";

            if (st.executeUpdate(requete) > 0) {
                //recupérer le numéro sequentiel données par le SGBD
                out.print("success");
            }

            requete = "DELETE FROM vendeur"
                    + "         WHERE (((id)='" + p.getId() + "'))";

            if (st.executeUpdate(requete) > 0) {
                //recupérer le numéro sequentiel données par le SGBD
                out.print("success");
            }


            requete = "DELETE FROM users_conversations"
                    + "         WHERE (((user_id)='" + p.getId() + "'))";

            if (st.executeUpdate(requete) > 0) {
                //recupérer le numéro sequentiel données par le SGBD
                out.print("success");
            }


            requete = "DELETE FROM user"
                    + "         WHERE (((id)='" + p.getId() + "'))";

            if (st.executeUpdate(requete) > 0) {
                //recupérer le numéro sequentiel données par le SGBD
                out.print("success");
            }


        } catch (Exception e) {
            System.err.println("probleme avec la requete " + e.getMessage());
        }

    }


/*
    public Boolean Modifier(Vendeur p) {

        Connection con;
        if ((con = connecter()) != null) {
            try {


                System.out.println(p.getId()+" idven");
                File file = p.getFile();
                int status = 0;

                if (file!=null) {

                    FileInputStream fin = new FileInputStream(file);
                    int len = (int) file.length();

                    PreparedStatement st =
                            con.prepareStatement("UPDATE user SET username = ? , username_canonical = ?, password = ? ,  email = ?  , email_canonical = ? " +
                                    ", nom = ? , prenom = ? , adresse = ? , photoProf = ? , phone = ? , gender = ? WHERE  (((id)='" + p.getId() + "'))");
                    st.setString(1, p.getUsername());
                    st.setString(2, p.getUsername());
                    st.setString(3, p.getPassword());
                    st.setString(4, p.getEmail());
                    st.setString(5, p.getEmail());
                    st.setString(6, p.getNom());
                    st.setString(7, p.getPrenom());
                    st.setString(8, p.getAdresse());
                    st.setBinaryStream(9, fin, len);
                    st.setString(10, p.getPhone());
                    st.setString(11, p.getGender());

                    System.out.print(st+" ");
                    status = st.executeUpdate();
                }

                else {

                    PreparedStatement st =
                            con.prepareStatement("UPDATE user SET username = ? , username_canonical = ?, password = ? ,  email = ?  , email_canonical = ? " +
                                    ", nom = ? , prenom = ? , adresse = ? , phone = ? , gender = ? WHERE  (((id)='" + p.getId() + "'))");
                    st.setString(1, p.getUsername());
                    st.setString(2, p.getUsername());
                    st.setString(3, p.getPassword());
                    st.setString(4, p.getEmail());
                    st.setString(5, p.getEmail());
                    st.setString(6, p.getNom());
                    st.setString(7, p.getPrenom());
                    st.setString(8, p.getAdresse());
                    st.setString(9, p.getPhone());
                    st.setString(10, p.getGender());
                    status = st.executeUpdate();
                    System.out.print(st+" ");

                }

                if (status > 0) {


                    //recupérer le numéro sequentiel données par le SGBD
                    System.out.print("success user modif user 12");

                    if (p.getType().equals("Vendeur"))
                    {
                        PreparedStatement stv =
                                con.prepareStatement("UPDATE vendeur SET nom_boutique = ? , description = ?   WHERE  (((id)='" + p.getId() + "'))");
                        stv.setString(1, p.getNom_boutique());
                        stv.setString(2, p.getDescription());

                        if (stv.executeUpdate() > 0) {
                            //recupérer le numéro sequentiel données par le SGBD
                            out.print("success user modif ven");
                            return true;
                        }
                    }
                    return true;
                }
                else
                    return false;

            } catch (Exception e) {
                System.err.println("probleme avec la requete modif" + e.getMessage());
            }
        }
        return false;
    }
*/


    public void Modifier(Vendeur p) {

        Connection con;
        if ((con = connecter()) != null) {
            try {
                File file = p.getFile();
                int status = 0;

                if (file!=null) {

                    FileInputStream fin = new FileInputStream(file);
                    int len = (int) file.length();

                    PreparedStatement st =
                            con.prepareStatement("UPDATE user SET username = ? , username_canonical = ?, password = ? ,  email = ?  , email_canonical = ? " +
                                    ", nom = ? , prenom = ? , adresse = ? , photoProf = ? , phone = ? , gender = ? WHERE  (((id)='" + p.getId() + "'))");
                    st.setString(1, p.getUsername());
                    st.setString(2, p.getUsername());
                    st.setString(3, p.getPassword());
                    st.setString(4, p.getEmail());
                    st.setString(5, p.getEmail());
                    st.setString(6, p.getNom());
                    st.setString(7, p.getPrenom());
                    st.setString(8, p.getAdresse());
                    st.setBinaryStream(9, fin, len);
                    st.setString(10, p.getPhone());
                    st.setString(11, p.getGender());

                    if (st.executeUpdate() > 0) {
                        //recupérer le numéro sequentiel données par le SGBD
                        out.print("success user");

                        st = con.prepareStatement("UPDATE vendeur SET nom_boutique = ? , description = ? " +
                                "WHERE  (((id)='" + p.getId() + "'))");
                        st.setString(1, p.getNom_boutique());
                        st.setString(2, p.getDescription());

                        if (st.executeUpdate() > 0) {
                            //recupérer le numéro sequentiel données par le SGBD
                            out.print("success vendeur");
                        }
                    }
                }

                else
                {

                    PreparedStatement st =
                            con.prepareStatement("UPDATE user SET username = ? , username_canonical = ?, password = ? ,  email = ?  , email_canonical = ? " +
                                    ", nom = ? , prenom = ? , adresse = ? , phone = ? , gender = ? WHERE  (((id)='" + p.getId() + "'))");
                    st.setString(1, p.getUsername());
                    st.setString(2, p.getUsername());
                    st.setString(3, p.getPassword());
                    st.setString(4, p.getEmail());
                    st.setString(5, p.getEmail());
                    st.setString(6, p.getNom());
                    st.setString(7, p.getPrenom());
                    st.setString(8, p.getAdresse());
                    st.setString(9, p.getPhone());
                    st.setString(10, p.getGender());

                    if (st.executeUpdate() > 0) {


                        out.print("success user");

                        st = con.prepareStatement("UPDATE vendeur SET nom_boutique = ? , description = ? " +
                                "WHERE  (((id)='" + p.getId() + "'))");
                        st.setString(1, p.getNom_boutique());
                        st.setString(2, p.getDescription());

                        if (st.executeUpdate() > 0) {
                            out.print("success vendeur 2");
                        }
                    }
                }

                } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }



}
