package com.gaurav.robotics_society.Models;

/**
 * Created by GAURAV on 17-01-2019.
 */

public class events_Model {
    String posts;
    String date;

    public events_Model(String posts, String date) {
        this.posts = posts;
        this.date = date;
    }
    public events_Model(){
    }

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
