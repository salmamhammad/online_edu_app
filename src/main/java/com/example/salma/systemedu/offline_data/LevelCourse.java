package com.example.salma.systemedu.offline_data;

/**
 * Created by salma on 2019-04-06.
 */
public class LevelCourse  {
    private  int id;
    private String name;
    private int id_course;
    private  int numques;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId_course(int id_course) {
        this.id_course = id_course;
    }

    public void setNumques(int numques) {
        this.numques = numques;
    }

    public LevelCourse(int id ,String name, int id_course,int numques) {
        this.numques = numques;
        this.id_course = id_course;
         this.id=id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getNumques() {
        return numques;
    }

    public int getId_course() {
        return id_course;
    }

    public String getName() {
        return name;
    }


}
