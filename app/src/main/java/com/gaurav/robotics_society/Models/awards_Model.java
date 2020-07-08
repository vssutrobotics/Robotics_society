package com.gaurav.robotics_society.Models;

public class awards_Model {
    private String head;
    private String desc;

    public awards_Model(String head, String image, String url) {
        this.head = head;
        this.desc = image;
    }

    public awards_Model() {
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
