package com.gaurav.robotics_society.Models;

/**
 * Created by GAURAV on 17-01-2019.
 */

public class events_Model {
    private String title;
    private String body;
    private String url;

    public events_Model(String title, String body, String url) {
        this.title = title;
        this.body = body;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public events_Model(){
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
