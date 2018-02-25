/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author FIRAS
 */
public class Connexion {
    private static Connection con;
    static{
           try {
            //chargement du driver
            //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
          Class.forName("com.mysql.jdbc.Driver");
            
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur de chargement du driver  " + e.getMessage());
            
        }

        try {
            //connection à la base
            //con = DriverManager.getConnection("jdbc:odbc:commercial", "", "");
           con=DriverManager.getConnection("jdbc:mysql://localhost:3306/souk", "root", "");
            
        } catch (SQLException ex) {
            System.err.println("probleme de database connection: " + ex.getMessage());
            
        }
    }

    public static Connection getCon() {
        return con;
    }
    
}
