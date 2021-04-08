package com.example.salma.systemedu.offline_data;

/**
 * Created by salma on 2019-04-07.
 */
public class UserData {
    private  int id;
    private  int id_level;
    private  int id_model;
    private  int ans;


    public UserData( int id_level, int ans,int id_model) {

        this.ans = ans;
        this.id_model = id_model;
        this.id_level = id_level;

    }


    public int getId_model() {
        return id_model;
    }

    public void setId_model(int id_model) {
        this.id_model = id_model;
    }




    public int isAns() {
        return ans;
    }

    public void setAns(int ans) {
        this.ans = ans;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setId_level(int id_level) {
        this.id_level = id_level;
    }

    public int getId() {
        return id;
    }


    public int getans() {
        return ans;
    }


    public int getId_level() {
        return id_level;
    }

}
