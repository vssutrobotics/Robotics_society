package com.gaurav.robotics_society.Models;

/**
 * Created by GAURAV on 09-01-2019.
 */

public class Achivements_Model {
    private String desc;
    private String image;
    private String title;
    private String url;

    public Achivements_Model(String desc, String image, String title, String url) {
        this.desc = desc;
        this.image = image;
        this.title = title;
        this.url = url;
    }
    public Achivements_Model(){

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setDesc(String description) {
        this.desc = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
