package com.example.salma.systemedu.requestdata;

/**
 * Created by salma on 2019-05-03.
 */
public class RequestCourse {
    private String respone;
    private static RequestCourse rl;

    private RequestCourse(){
       respone="course";
    }

    public String getRespone() {
        return respone;
    }

    public void setRespone(String respone) {
        this.respone = respone;
    }
    public static synchronized RequestCourse getinstance(){
        if(rl==null){
           rl=new RequestCourse();
        }
        return rl;
    }
}
