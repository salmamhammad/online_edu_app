package com.example.salma.systemedu.requestdata;

/**
 * Created by salma on 2019-05-03.
 */
public class RequestQuestion {
    private String respone;
    private static RequestQuestion rl;

    private RequestQuestion(){
       respone="question";
    }

    public String getRespone() {
        return respone;
    }

    public void setRespone(String respone) {
        this.respone = respone;
    }
    public static synchronized RequestQuestion getinstance(){
        if(rl==null){
           rl=new RequestQuestion();
        }
        return rl;
    }
}
