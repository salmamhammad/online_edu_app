package com.example.salma.systemedu;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.salma.systemedu.offline_data.DatabaseHelper;
import com.example.salma.systemedu.requestdata.RequestLevel;
import com.example.salma.systemedu.rsglogsign.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class endcourseActivity extends AppCompatActivity {
    public static final String level_param="IDLEVEL";
    private DatabaseHelper mydb;
    private Button finishbtn;
    private Button restartbtn;
    RequestQueue requestqueue;
    private SharedPreferences sharedPreferences;

    private  int id_course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endcourse);
        sharedPreferences = endcourseActivity.this.getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        id_course=Integer.parseInt(sharedPreferences.getString(config.COURSE_SHARED_PREF, "1"));
        Log.v("nnn","idcourse"+id_course);
        mydb = new DatabaseHelper(this);//bulider for create database

        endCourse();
    }

    private void endCourse() {
        Dialog finishcourse;
        Log.v("ssss", "eeee");
        finishcourse = new Dialog(endcourseActivity.this);
        finishcourse.requestWindowFeature(Window.FEATURE_NO_TITLE);
        finishcourse.setContentView(R.layout.layout_finish_course);
        finishcourse.setTitle("finish");
        finishbtn = (Button) finishcourse.findViewById(R.id.endcourse);
        finishbtn.setEnabled(true);
        finishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(endcourseActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        restartbtn=(Button)finishcourse.findViewById(R.id.restart);
        restartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartlevel(id_course);

                Intent intent = new Intent(endcourseActivity.this, LevelActivity.class);
                RequestLevel r1l=RequestLevel.getinstance();
                r1l.setRespone("level");
                        startActivity(intent);

            }
        });
        finishcourse.show();
    }



    private void restartlevel(int id) {
        Log.v("nnn","idcourse"+id);

        String URL_log="http://orninaart.000webhostapp.com/androidcourse/api/delstudentlog.php";

        requestqueue = Volley.newRequestQueue(this);
        final StringRequest jsnrs = new StringRequest(Request.Method.POST, URL_log, new Response.Listener<String>() {
            JSONObject object;
            @Override
            public void onResponse(String response) {
                String status = "failed";
                Log.v("sss", "request num"+response);
                try {

                    object = new JSONObject(response);
                    status = object.getString("status");



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }




        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(endcourseActivity.this, "error!!!...check your internet connect and try again", Toast.LENGTH_SHORT).show();

                //  Log.v("funn", error + "");
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                Log.v("idsttudent",""+sharedPreferences.getString(config.ID_SHARED_PREF,"1"));
                param.put("id_course",String.valueOf( id_course));
                param.put("id_student",sharedPreferences.getString(config.ID_SHARED_PREF,"1"));



                return param;
            }
        };


        requestqueue.add(jsnrs);

        int del=mydb.deleteres(String.valueOf(id));

    }
}
