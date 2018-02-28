package Model.DAO;

import Model.Entities.Avis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Controller.LoginController.statCuser;

public class CommentsDao {


    private static Connection connecter() {
        return Connexion.getCon();
    }



    public List<Avis> SelectAvis(int id) {
        Connection con;
        List<Avis> listAvis = new ArrayList<>();
        if ((con = connecter()) != null) {
            try {
                String requete = "SELECT *"
                        + "         FROM avis WHERE article_id = '"+id+"' ORDER BY date_pub DESC ";

                PreparedStatement ps = con.prepareStatement(requete);


                ResultSet res = ps.executeQuery(requete);
                while (res.next())
                {
                    Avis avis = new Avis();
                    avis.setId(res.getInt("id"));
                    avis.setAcheteur(new UserDAO().ChercherAcheteurId(res.getInt("acheteur_id")));
                    avis.setAvis(res.getString("avis"));
                    avis.setDate_pub(res.getTimestamp("date_pub"));
                    avis.setNote(res.getDouble("note"));

                    System.out.println("avis "+ avis.getAvis());

                    if (avis.getAcheteur().getId() == statCuser.getId())
                        avis.setMine(true);
                    else
                        avis.setMine(false);

                    listAvis.add(avis);
                }

            } catch (Exception e) {
                System.err.println("probleme avec la requete " + e.getMessage());
            }

        }

        return listAvis;
    }



    public void ajoutAvis(int idUser, int idArt,String avis,double note)
    {
        Connection con = connecter();
        try {
            String req = "INSERT INTO avis(acheteur_id, article_id, avis , note , date_pub ) VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, idUser);
            ps.setInt(2, idArt);
            ps.setString(3,avis);
            ps.setDouble(4,note);

            ps.setTimestamp(5, new Timestamp(new java.util.Date().getTime()));

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ex.getMessage()).log(Level.SEVERE, null, ex);
        }

    }



    public void deleteAvis(int id)
    {
        Connection con = connecter();
        try {
            String req = "DELETE FROM avis WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ex.getMessage()).log(Level.SEVERE, null, ex);
        }

    }


}
