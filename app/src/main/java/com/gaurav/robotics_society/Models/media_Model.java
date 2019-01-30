package com.gaurav.robotics_society.Models;

/**
 * Created by GAURAV on 20-01-2019.
 */

public class media_Model {
    String channel;
    String date;
    String image;
    String project;

    public media_Model(String channel, String date, String image, String project) {
        this.channel = channel;
        this.date = date;
        this.image = image;
        this.project = project;
    }
    public media_Model(){

    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
