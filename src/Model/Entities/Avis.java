/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Entities;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author FIRAS
 */
public class Avis {
    
    private int id;
    
    private Timestamp date_pub;
    
    private String avis;
    
    private Acheteur acheteur;

    private boolean mine;

    private double note;

    public Avis() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isMine() {
        return mine;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public Timestamp getDate_pub() {
        return date_pub;
    }

    public void setDate_pub(Timestamp date_pub) {
        this.date_pub = date_pub;
    }

    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public Acheteur getAcheteur() {
        return acheteur;
    }

    public void setAcheteur(Acheteur acheteur) {
        this.acheteur = acheteur;
    }
    
    
}
