/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Entities.*;
import javafx.stage.FileChooser;

import static Controller.LoginController.statCuser;
import static Controller.LoginController.statPanier;
import static java.lang.System.out;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FIRAS
 */

public class UserDAO {


    private static Connection connecter() {
        Connection con = Connexion.getCon();
        return con;
    }

    public void Ajouter(User p) {

        Connection con = null;
        if ((con = connecter()) != null) {
            try {

                File file = p.getFile();
                int status = 0;

                if (file != null) {
                    FileInputStream fin = new FileInputStream(file);
                    int len = (int) file.length();

                    PreparedStatement st =
                            con.prepareStatement("INSERT INTO USER ( username, username_canonical , password, email " +
                                    ", email_canonical,type_utilisateur,nom,prenom,phone,gender,adresse,photoprof)  values (?,?,?,?,?,?,?,?,?,?,?,?)");
                    st.setString(1, p.getUsername());
                    st.setString(2, p.getUsername());
                    st.setString(3, p.getPassword());
                    st.setString(4, p.getEmail());
                    st.setString(5, p.getEmail());
                    st.setString(6, p.getType());
                    st.setString(7, p.getNom());
                    st.setString(8, p.getPrenom());
                    st.setString(9, p.getPhone());
                    st.setString(10, p.getGender());
                    st.setString(11, p.getAdresse());
                    st.setBinaryStream(12, fin, len);
                    status = st.executeUpdate();

                }

                else
                {
                    PreparedStatement st =
                            con.prepareStatement("INSERT INTO USER ( username, username_canonical , password, email " +
                                    ", email_canonical,type_utilisateur,nom,prenom,phone,gender,adresse)  values (?,?,?,?,?,?,?,?,?,?,?)");
                    st.setString(1, p.getUsername());
                    st.setString(2, p.getUsername());
                    st.setString(3, p.getPassword());
                    st.setString(4, p.getEmail());
                    st.setString(5, p.getEmail());
                    st.setString(6, p.getType());
                    st.setString(7, p.getNom());
                    st.setString(8, p.getPrenom());
                    st.setString(9, p.getPhone());
                    st.setString(10, p.getGender());
                    st.setString(11, p.getAdresse());
                    status = st.executeUpdate();
                }

                if (status > 0) {
                    //recupérer le numéro sequentiel données par le SGBD
                    out.print("success");

                    List<User> users = new ArrayList<User>();

                    users = ChercherUsername(p.getUsername());

                    User cuser = new User();

                    for (int i = 0; i < users.size(); i++) {
                        System.out.println(users.get(i).getId() + " " + users.get(i).getUsername() + " ");

                        cuser =  users.get(i);


                        statPanier = new Panier(cuser, new Date(new java.util.Date().getTime()), new ArrayList<Article>());

                        PanierDao daoPan = new PanierDao();

                        daoPan.add(statPanier);


                        if (p.getType() == "Vendeur") {
                            try {
                                PreparedStatement stv =
                                        con.prepareStatement("INSERT INTO vendeur (id)  values (?)");
                                stv.setInt(1, users.get(i).getId());

                                if (stv.executeUpdate() > 0) {
                                    out.print("success Vendeur");
                                    cuser = users.get(i);

                                }

                            } catch (Exception e) {
                                System.err.println("probleme avec la requete vendeur" + e.getMessage());
                            }


                        } else if (p.getType() == "Acheteur") {
                            try {
                                PreparedStatement stv =
                                        con.prepareStatement("INSERT INTO acheteur ( id)  values (?)");
                                stv.setInt(1, users.get(i).getId());

                                if (stv.executeUpdate() > 0) {
                                    out.print("success acheteur");
                                    cuser =  users.get(i);
                                }

                            } catch (Exception e) {
                                System.err.println("probleme avec la requete acheteur " + e.getMessage());
                            }

                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }

        }

    }


    public List<Acheteur> Select() {
        Acheteur r = null;
        Connection con = null;
        List<Acheteur> acheteurs = new ArrayList<Acheteur>();
        if ((con = connecter()) != null) {
            try {
                Statement st = con.createStatement();
                String requete = "SELECT *"
                        + "         FROM USER";

                ResultSet res = st.executeQuery(requete);
                while (res.next()) {
                    r = new Acheteur();
                    r.setId(res.getInt("id"));
                    r.setUsername(res.getString("username"));
                    r.setNom(res.getString("nom"));
                    r.setPrenom(res.getString("prenom"));
                    r.setEmail(res.getString("email"));
                    r.setPhotoprof(res.getBlob("photoprof"));
                    r.setAdresse(res.getString("adresse"));

                    acheteurs.add(r);
                }

            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }

        }

        return acheteurs;
    }






    public List<Acheteur> SelectAcheteurs(int id) {
        List<Acheteur> list = new ArrayList<>();
        Connection con;
        if ((con = connecter()) != null) {
            try {
                Statement st = con.createStatement();

                String requete = "select * from user , acheteur " +
                        "where user.id = acheteur.id ";

                System.out.println(requete+" ");
                ResultSet res = st.executeQuery(requete);
                while (res.next()) {
                    Acheteur r = new Acheteur();
                    r.setId(res.getInt("id"));
                    r.setNom(res.getString("username"));
                    r.setNom(res.getString("nom"));
                    r.setPrenom(res.getString("prenom"));
                    r.setEmail(res.getString("email"));
                    r.setPhotoprof(res.getBlob("photoprof"));
                    r.setAdresse(res.getString("adresse"));


                    //  System.out.println(res.getInt("id")+" "+res.getString("adresse"));

                    list.add(r);
                }


            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }

        }
        return list;
    }





    public Acheteur ChercherAcheteurId(int id) {
        Acheteur r = new Acheteur();
        Connection con;
        if ((con = connecter()) != null) {
            try {
                Statement st = con.createStatement();

                String requete = "select * from user , acheteur " +
                        "where user.id = acheteur.id and user.id= '"+id+"'";

                //       System.out.println(requete+" ");
                ResultSet res = st.executeQuery(requete);
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

                    //  System.out.println(res.getInt("id")+" "+res.getString("adresse"));

                }


            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }

        }
        return r;
    }







    public List<User> ChercherUsername(String username) {

        User r = null;
        Connection con = null;
        List<User> users = new ArrayList<User>();
        if ((con = connecter()) != null) {
            try {
                Statement st = con.createStatement();
                String requete = "SELECT *"
                        + "         FROM USER"
                        + "         WHERE (((username)='" + username + "'))";
                ResultSet res = st.executeQuery(requete);
                while (res.next()) {

                    r = new User();
                    r.setId(res.getInt("id"));
                    r.setNom(res.getString("username"));
                    r.setNom(res.getString("nom"));
                    r.setPrenom(res.getString("prenom"));
                    r.setEmail(res.getString("email"));
                    r.setPhotoprof(res.getBlob("photoprof"));
                    r.setAdresse(res.getString("adresse"));

                    users.add(r);
                }


            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }

        }
        return users;
    }


    public List<CurrentUser> Login(String username, String password) {

        CurrentUser r = null;
        Connection con = null;
        List<CurrentUser> users = new ArrayList<>();
        if ((con = connecter()) != null) {
            try {
                Statement st = con.createStatement();
                String requete = "SELECT *"
                        + "         FROM USER"
                        + "         WHERE ((username= '" + username +
                        "') AND (password ='" + password + "'));";
                ResultSet res = st.executeQuery(requete);
                while (res.next()) {

                    r = new CurrentUser();
                    r.setId(res.getInt("id"));
                    r.setUsername(res.getString("username"));
                    r.setNom(res.getString("nom"));
                    r.setPrenom(res.getString("prenom"));
                    r.setEmail(res.getString("email"));
                    r.setPhotoprof(res.getBlob("photoprof"));
                    r.setType(res.getString("type_utilisateur"));
                    r.setAdresse(res.getString("adresse"));
                    r.setPhone(res.getString("phone"));

                    users.add(r);
                }
            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }

        }
        return users;
    }


    public List<CurrentUser> SelectCurrentUser(CurrentUser cu) {
        CurrentUser r = null;
        Connection con = null;
        List<CurrentUser> cuser = new ArrayList<CurrentUser>();
        if ((con = connecter()) != null) {
            try {
                Statement st = con.createStatement();
                String requete = "SELECT *"
                        + "         FROM USER WHERE (((id)='" + cu.getId() + "'))";

                ResultSet res = st.executeQuery(requete);
                while (res.next()) {
                    r = new CurrentUser();
                    r.setId(res.getInt("id"));
                    r.setUsername(res.getString("username"));
                    r.setNom(res.getString("nom"));
                    r.setPrenom(res.getString("prenom"));
                    r.setEmail(res.getString("email"));
                    r.setPhotoprof(res.getBlob("photoprof"));
                    r.setType(res.getString("type_utilisateur"));

                    if (r.getType().equals("vendeur")) {
                        requete = "SELECT *"
                                + "         FROM vendeur WHERE (((id)='" + cu.getId() + "'))";

                        ResultSet result = st.executeQuery(requete);

                        while (result.next()) {
                            r = new CurrentUser();
                            r.setNote(res.getInt("note"));
                            r.setDescription(res.getString("description"));
                            r.setNom_boutique(res.getString("nom_boutique"));
                        }
                    } else if (r.getType().equals("acheteur")) {
                        requete = "SELECT *"
                                + "         FROM acheteur WHERE (((id)='" + cu.getId() + "'))";

                        ResultSet result = st.executeQuery(requete);

                        while (result.next()) {
                            r = new CurrentUser();
                            r.setId(res.getInt("id"));

                        }
                    }
                    cuser.add(r);
                }

            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }

        }
        return cuser;
    }









    public Boolean Modifier(CurrentUser p) {

        Connection con;
        if ((con = connecter()) != null) {
            try {
                PreparedStatement st =
                        con.prepareStatement("UPDATE USER SET username = ? , username_canonical = ? ,  email = ?  , email_canonical = ? " +
                                ", nom = ? , prenom = ? , adresse = ?  WHERE  (((id)='" + p.getId() + "'))");
                st.setString(1, p.getUsername());
                st.setString(2, p.getUsername());
                st.setString(3, p.getEmail());
                st.setString(4, p.getEmail());
                st.setString(5, p.getNom());
                st.setString(6, p.getPrenom());
                st.setString(7, p.getAdresse());


                if (st.executeUpdate() > 0) {
                    //recupérer le numéro sequentiel données par le SGBD
                    out.print("success user");

                    if (p.getType().equals("vendeur"))
                    {
                        PreparedStatement stv =
                                con.prepareStatement("UPDATE vendeur SET ( nom_boutique = ? AND description = ? )  WHERE  (((id)='" + p.getId() + "'))");
                        stv.setString(1, p.getNom_boutique());
                        stv.setString(2, p.getDescription());

                        if (stv.executeUpdate() > 0) {
                            //recupérer le numéro sequentiel données par le SGBD
                            out.print("success user");
                            return true;
                        }
                    }
                    return true;
                }
                else
                    return false;

            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }
        }
        return false;
    }



    public void Modifier(User p) {

        Connection con;
        if ((con = connecter()) != null) {
            try {
                File file = p.getFile();
                int status = 0;

                if (file != null) {

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
                    }
                }
            } catch (FileNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public boolean Delete(User p) {
        Connection con = null;
        if ((con = connecter()) != null) {
            try {
                    Statement stv = con.createStatement();
                    String requete = "DELETE FROM acheteur"
                            + "         WHERE (((id)='" + p.getId() + "'))";
                    if (stv.executeUpdate(requete) > 0) {

                        Statement st = con.createStatement();
                        requete = "DELETE FROM USER"
                                + "         WHERE (((id)='" + p.getId() + "'))";

                        if (st.executeUpdate(requete) > 0) {
                            //recupérer le numéro sequentiel données par le SGBD
                            out.print("success");
                            return true;
                        }

                }

            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
                return false;
            }

        }
        return false;
    }



    public boolean DeleteDepricated(User p) {
        Connection con = null;
        if ((con = connecter()) != null) {
            try {
                if (p.getType().equals("vendeur")) {
                    Statement stv = con.createStatement();
                    String requete = "DELETE FROM vendeur"
                            + "         WHERE (((id)='" + p.getId() + "'))";
                    if (stv.executeUpdate(requete) > 0) {
                        out.print("success vendeur");

                        Statement st = con.createStatement();
                        requete = "DELETE FROM USER"
                                + "         WHERE (((id)='" + p.getId() + "'))";

                        if (st.executeUpdate(requete) > 0) {
                            //recupérer le numéro sequentiel données par le SGBD
                            out.print("success");
                            return true;
                        }
                    }
                }
                else
                {
                    Statement stv = con.createStatement();
                    String requete = "DELETE FROM acheteur"
                            + "         WHERE (((id)='" + p.getId() + "'))";
                    if (stv.executeUpdate(requete) > 0) {

                        Statement st = con.createStatement();
                        requete = "DELETE FROM USER"
                                + "         WHERE (((id)='" + p.getId() + "'))";

                        if (st.executeUpdate(requete) > 0) {
                            //recupérer le numéro sequentiel données par le SGBD
                            out.print("success");
                            return true;
                        }
                    }
                }

            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
                return false;
            }

        }
        return false;
    }


    public int uploadPic(File file)
    {
        int status = 0;

        Connection conn = null;
        if ((conn = connecter()) != null) {
            try {

                FileInputStream fin = new FileInputStream(file);
                int len = (int) file.length();

                PreparedStatement ps = conn.prepareStatement("INSERT INTO images (image) VALUES(?)");
                ps.setBinaryStream(1, fin, len);
                status = ps.executeUpdate();

                if (status > 0) {
                    System.out.println("success");
                } else {
                    System.out.println("failure");
                }
                conn.close();
            } catch (IOException | SQLException ex) {
                Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return status;

    }










}