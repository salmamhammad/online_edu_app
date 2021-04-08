package com.example.salma.systemedu.requestdata;

/**
 * Created by salma on 2019-05-03.
 */
public class RequestLevel {
    private String respone;
    private static RequestLevel rl;

    private RequestLevel(){
       respone="level";
    }

    public String getRespone() {
        return respone;
    }

    public void setRespone(String respone) {
        this.respone = respone;
    }
    public static synchronized RequestLevel getinstance(){
        if(rl==null){
           rl=new RequestLevel();
        }
        return rl;
    }
}
