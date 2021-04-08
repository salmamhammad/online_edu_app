package com.example.salma.systemedu.offline_data;

/**
 * Created by salma on 2019-04-22.
 */
public class Result {
    private int id;
    private int id_level;
    private int mark;
    private int numquestion;

    public Result( int id_level, int mark,int numquestion) {
        this.numquestion = numquestion;

        this.id_level = id_level;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumquestion() {
        return numquestion;
    }

    public void setNumquestion(int numquestion) {
        this.numquestion = numquestion;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getlevel() {
        return id_level;
    }

    public void setlevel(int id_level) {
        this.id_level = id_level;
    }
}
