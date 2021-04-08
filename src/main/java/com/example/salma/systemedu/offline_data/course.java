package com.example.salma.systemedu.offline_data;

/**
 * Created by salma on 2019-03-12.
 */
public class course  {
    private  int id;
    private String name;
    private String description;
   private  int numlevel;

    //constractor
    public course(int id,String name, String description, int numlevel) {
        this.id=id;
        this.name = name;
        this.description = description;
        this.numlevel = numlevel;
    }

    public String getName() {
        return name;
    }

    public int getNumlevel() {
        return numlevel;
    }

    public int getid() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumlevel(int numlevel) {
        this.numlevel = numlevel;
    }
    public void setid(int id) {
        this.id = id;
    }
}
