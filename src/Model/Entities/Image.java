/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Entities;


import java.io.File;
import java.sql.Blob;

/**
 *
 * @author FIRAS
 */
public class Image {
    
    private int id;
    
    private Blob image;
    
    private Article article;

    private String url;

    private File file;

    public Image() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
    
    
    
}
