package com.example.salma.systemedu.offline_data;

/**
 * Created by salma on 2019-04-17.
 */
public class Model {
    private int id;
    private String title;
    private String text;
    private int id_level;
    private int counter;

     public  Model (){
    this (0,"","",0,0);
     }
    public Model(int id, String title, String text, int id_level,int counter) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.id_level = id_level;
        this.counter = counter;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId_level() {
        return id_level;
    }

    public void setId_level(int l) {
        this.id_level = l;
    }
    public int getCounter() {
        return counter;
    }

    public void setCounter(int c) {
        this.counter = c;
    }
}
