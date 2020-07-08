package com.gaurav.robotics_society.Models;

/**
 * Created by GAURAV on 11-02-2019.
 */

public class project_Model {

    private String head;
    private String image;
    private String url;

    public project_Model(String head, String image, String url) {
        this.head = head;
        this.image = image;
        this.url = url;
    }

    public project_Model() {
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
