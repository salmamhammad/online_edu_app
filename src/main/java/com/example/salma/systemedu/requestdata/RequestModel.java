package com.example.salma.systemedu.requestdata;

/**
 * Created by salma on 2019-05-03.
 */
public class RequestModel {
    private String respone;
    private static RequestModel rl;

    private RequestModel(){
       respone="model";
    }

    public String getRespone() {
        return respone;
    }

    public void setRespone(String respone) {
        this.respone = respone;
    }
    public static synchronized RequestModel getinstance(){
        if(rl==null){
           rl=new RequestModel();
        }
        return rl;
    }
}
