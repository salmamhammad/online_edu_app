package com.example.salma.systemedu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.salma.systemedu.offline_data.DatabaseHelper;
import com.example.salma.systemedu.offline_data.LevelCourse;
import com.example.salma.systemedu.offline_data.Question;
import com.example.salma.systemedu.offline_data.UserData;
import com.example.salma.systemedu.requestdata.RequestQuestion;
import com.example.salma.systemedu.rsglogsign.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class QuestionActivity extends AppNavActivity {
    public static final String model_param="IDMODEL";


    private int id_model;
    private int numlevel;
    private int id_level;
    private int id_course;
    private int nummodel;

    private  Question question;
    RequestQueue requestqueue;
    private DatabaseHelper mydb;
    private boolean result;
    //xml interface
    private TextView textquestion;
    private Button continuebtn;
    private RadioGroup rg;
    private RadioButton rb;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private ProgressBar progressBar;
    private  SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        TextView emailuser=(TextView)findViewById(R.id.emailuser);
        TextView nameuser=(TextView)findViewById(R.id.nameuser);
        nameuser.setText(sharedPreferences.getString(config.NAME_SHARED_PREF, "user"));
        emailuser.setText(sharedPreferences.getString(config.EMAIL_SHARED_PREF,"user@systemedu.com"));

        numlevel = Integer.parseInt(sharedPreferences.getString(config.NUMLEVEL_SHARED_PREF, "1"));
        id_level = Integer.parseInt(sharedPreferences.getString(config.LEVEL_SHARED_PREF, "1"));
        id_course=Integer.parseInt(sharedPreferences.getString(config.COURSE_SHARED_PREF,"1"));
        progressBar = (ProgressBar) findViewById(R.id.progressBarq);

        id_model =Integer.parseInt( getIntent().getStringExtra(textActivity.idmodel_param));
        nummodel =Integer.parseInt( getIntent().getStringExtra(textActivity.model_param));

        mydb = new DatabaseHelper(this);//bulider for create database
        textquestion=(TextView)findViewById(R.id.textquestion);
        rg=(RadioGroup)findViewById(R.id.rgroup);
        rb1=(RadioButton)findViewById(R.id.rbtn1);
        rb2=(RadioButton)findViewById(R.id.rbtn2);
        rb3=(RadioButton)findViewById(R.id.rbtn3);
        continuebtn=(Button)findViewById(R.id.continuebtn1);

        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mark = 0;
                if (rpclick(rb)) {

                    String answer = (String) rb.getText();
                    if (answer == question.getTans()) {
                        ++mark;
                        Toast.makeText(getApplication(), "true answer", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplication(), "wrong answer", Toast.LENGTH_SHORT).show();

                    }

                }
                Log.v("nnn", "ADD TR" + id_level);
                UserData userData = new UserData(id_level, mark, id_model);
                result = mydb.insert_userdata(userData);

                Cursor resu = mydb.getuserdata(id_level);
                if (resu.getCount() > 0) {
                    while (resu.moveToNext()) {
                        UserData d = new UserData(Integer.parseInt(resu.getString(1)), Integer.parseInt(resu.getString(2)), Integer.parseInt(resu.getString(3)));
                        Log.v("nnn", "dd id " + resu.getString(0) + " idlevel " + resu.getString(1) + " idmodel " + resu.getString(3) + " ans " + resu.getString(2) + "dd\n");
                    }
                }
                Intent intent = new Intent(QuestionActivity.this, textActivity.class);
                intent.putExtra(QuestionActivity.model_param, String.valueOf(nummodel));
                startActivity(intent);

            }
        });
        Log.v("nnn","id mofelll "+id_model);
        if(Connectiontest.isConnectedToNetwork(this)){
            requestmodel(id_model);
        }
        else
        {
            getofflinedata();

        }


    }
    public void  getofflinedata(){

        Log.v("funnn","id_model shared"+ id_model);
        boolean findquestion=false;
        String respone = sharedPreferences.getString(config.QUESTINDATA_SHARED_PREF, "question");
        JSONObject object = null;
        Log.v("funnn","question shared"+ respone);
        try {

            object = new JSONObject(respone);
            JSONArray jsonArray = object.getJSONArray("question");
            int sizearray = jsonArray.length();

            for (int i = 0; i < sizearray; i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                int id = item.getInt("id");
                String s = item.getString("status");
                String qtext = item.getString("qtext");
                String tans = item.getString("tans");
                String fans = item.getString("fans");
                String fans2 = item.getString("fans2");
                int idmodel = item.getInt("id_model");
                if(idmodel==id_model) {
                    findquestion=true;
                    question = new Question(id, qtext, tans, fans, fans2, idmodel);
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(findquestion)
        updateUI();
        else{
            Toast.makeText(QuestionActivity.this, "error!!!.", Toast.LENGTH_SHORT).show();

        }
    }


    private void showBar(boolean b) {

        if(b==true){
            progressBar.setVisibility(View.VISIBLE);
            textquestion.setVisibility(View.GONE);
            rg.setVisibility(View.GONE);
            continuebtn.setVisibility(View.GONE);
        }
        else
        {
            progressBar.setVisibility(View.GONE);
            textquestion.setVisibility(View.VISIBLE);
            rg.setVisibility(View.VISIBLE);
            continuebtn.setVisibility(View.VISIBLE);}
    }

public  boolean rpclick(View v){
    if(rg.getCheckedRadioButtonId()==-1){
        return false;
    }
    int radiobtnid=rg.getCheckedRadioButtonId();
    rb=(RadioButton)findViewById(radiobtnid);
    return true;
}

    //requerst to web
    private void requestmodel(int idmodel) {

        showBar(true);
        final RequestQuestion requestQuestion=RequestQuestion.getinstance();
        Log.v("funnn1","question  send respone"+requestQuestion.getRespone());
        if(requestQuestion.getRespone()=="question") {
            final String idmodelrequest = String.valueOf(idmodel);
            String URL_course = "http://orninaart.000webhostapp.com/androidcourse/api/getquestion.php";

            Log.v("funnn1","question  send respone"+requestQuestion.getRespone());
            requestqueue = Volley.newRequestQueue(this);
            final StringRequest jsnrs = new StringRequest(Request.Method.POST, URL_course, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(config.QUESTINDATA_SHARED_PREF, response);
                    Log.v("funnn11111111","question"+ response);
                    editor.commit();
                    requestQuestion.setRespone(response);


                    getofflinedata();

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(QuestionActivity.this, "error!!!...check your internet connect and try again", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
                    startActivity(intent);

                }
            });


            requestqueue.add(jsnrs);
        }
        getofflinedata();
    }

    public void updateUI(){
        textquestion.setText(question.getQtext());
        Random ran = new Random();
        int num=ran.nextInt(100);
        if(num%3==0) {
            rb1.setText(question.getTans());
            rb2.setText(question.getFans());
            rb3.setText(question.getFans2());
        }
        else if (num%3==1){
            rb1.setText(question.getFans());
            rb2.setText(question.getTans());
            rb3.setText(question.getFans2());
        }
        else {
            rb1.setText(question.getFans2());
            rb2.setText(question.getFans());
            rb3.setText(question.getTans());
        }
        showBar(false);
    }
}
