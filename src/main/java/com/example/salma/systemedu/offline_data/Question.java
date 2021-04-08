package com.example.salma.systemedu.offline_data;

/**
 * Created by salma on 2019-04-17.
 */
public class Question {
    private int id;
    private String qtext;
    private String tans;
    private String fans;
    private String fans2;
    private int id_model;

    public Question(int id, String qtext, String tans, String fans, String fans2,int id_model) {

        this.id = id;
        this.qtext = qtext;
        this.tans = tans;
        this.fans = fans;
        this.fans2 = fans2;
        this.id_model = id_model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQtext() {
        return qtext;
    }

    public void setQtext(String qtext) {
        this.qtext = qtext;
    }

    public String getTans() {
        return tans;
    }

    public void setTans(String tans) {
        this.tans = tans;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getFans2() {
        return fans2;
    }

    public void setFans2(String fans2) {
        this.fans2 = fans2;
    }
    public int getId_model() {
        return id_model;
    }

    public void setId_model(int id) {
        this.id_model = id;
    }
}
