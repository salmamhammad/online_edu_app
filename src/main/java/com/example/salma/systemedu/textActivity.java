package com.example.salma.systemedu;

import android.app.Dialog;
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
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
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
import com.example.salma.systemedu.offline_data.Model;
import com.example.salma.systemedu.offline_data.Result;
import com.example.salma.systemedu.offline_data.UserData;
import com.example.salma.systemedu.offline_data.course;
import com.example.salma.systemedu.requestdata.RequestModel;
import com.example.salma.systemedu.rsglogsign.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class textActivity extends AppNavActivity {
    public static final String model_param="MODEL";
    public static final String idmodel_param="IDMODEL";
    public static final String level_param="LEVEL";
    public static final String numlevel_param="NUMLEVEL";
    RequestQueue requestqueue;

    private int id_level=1;
    private int id_course=1;
    private int numlevel=1;
    private SharedPreferences sharedPreferences;
    private int nummodel;
    private  Model model;
    private String status;
    //xml commponent
    Button continuebtn;
    TextView text_model;
    TextView title_model;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBarmodel);


        sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        TextView emailuser=(TextView)findViewById(R.id.emailuser);
        TextView nameuser=(TextView)findViewById(R.id.nameuser);
        nameuser.setText(sharedPreferences.getString(config.NAME_SHARED_PREF,"user"));
        emailuser.setText(sharedPreferences.getString(config.EMAIL_SHARED_PREF,"user@systemedu.com"));

        id_level=Integer.parseInt(sharedPreferences.getString(config.LEVEL_SHARED_PREF,"1"));
        id_course=Integer.parseInt(sharedPreferences.getString(config.COURSE_SHARED_PREF,"1"));
        numlevel=Integer.parseInt(sharedPreferences.getString(config.NUMLEVEL_SHARED_PREF,"1"));
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if( getIntent().getStringExtra(QuestionActivity.model_param)==null){
            nummodel =1;
        }else {
           nummodel =Integer.parseInt( getIntent().getStringExtra(QuestionActivity.model_param));
            nummodel=1+nummodel;
        }
        Log.v("sss",nummodel+ "vv course from shared "+id_course+" d dlevel from shared "+id_level+ " numlevel "+numlevel);


        text_model=(TextView)findViewById(R.id.textmodel);
        title_model=(TextView)findViewById(R.id.title_txt);


        continuebtn=(Button)findViewById(R.id.continuebtn);
        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(textActivity.this, QuestionActivity.class);
                intent.putExtra(textActivity.idmodel_param, String.valueOf(model.getId()));
                intent.putExtra(textActivity.model_param, String.valueOf(nummodel));
                startActivity(intent);
            }
        });

        if(Connectiontest.isConnectedToNetwork(this)){
            //  Log.v("connct","true");

            requestmodel(id_level, nummodel);
        }
        else
        {
            getofflinedata();

        }



    }
public void getofflinedata(){
    String respone = sharedPreferences.getString(config.MODELDATA_SHARED_PREF, "model");
    Log.v("nnn"," model"+respone);
    JSONObject object = null;
    boolean finishlevel=true;
    if(respone!="model") {
        try {
            object = new JSONObject(respone);
            JSONArray jsonArray = object.getJSONArray("model");
            int sizearray = jsonArray.length();

            for (int i = 0; i < sizearray; i++) {
                JSONObject itemlevel = jsonArray.getJSONObject(i);
                status = itemlevel.getString("status");
                int id = itemlevel.getInt("id");
                String titlemodel = itemlevel.getString("title");
                String textmodel = itemlevel.getString("text");

                int idlevel = itemlevel.getInt("id_level");
                int counter = itemlevel.getInt("counter");

                if (idlevel == id_level & counter == nummodel) {
                    Log.v("nnn", "find model in");
                    model = new Model(id, titlemodel, textmodel, idlevel, counter);
                    finishlevel = false;
                    break;
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!finishlevel) {
            Log.v("nnn", "find model");
            updateUI();

        } else {
            Intent intent = new Intent(textActivity.this, transforActivity.class);
            intent.putExtra(textActivity.level_param, String.valueOf(id_level));
            intent.putExtra(textActivity.numlevel_param, String.valueOf(numlevel));
            intent.putExtra(textActivity.model_param, String.valueOf(nummodel));

            startActivity(intent);
        }
    }

}
    private void showBar(boolean b) {
        if(b==true){
            progressBar.setVisibility(View.VISIBLE);
            text_model.setVisibility(View.GONE);
            title_model.setVisibility(View.GONE);
            continuebtn.setVisibility(View.GONE);
        }
        else
        {
            progressBar.setVisibility(View.GONE);
            text_model.setVisibility(View.VISIBLE);
            title_model.setVisibility(View.VISIBLE);
            continuebtn.setVisibility(View.VISIBLE);
        }
    }


    //requerst to web
    private void requestmodel(int id ,int idmodel) {

        final RequestModel requestModel =RequestModel.getinstance();
        if(requestModel.getRespone()=="model") {
            showBar(true);
            final String idlevelrequest = String.valueOf(id);
            final String idmodelrequest = String.valueOf(idmodel);
            Log.v("nnn", "model" + "b  idlevel" + idlevelrequest + "  idmodel" + idmodelrequest);
            String URL_course = "http://orninaart.000webhostapp.com/androidcourse/api/getmodel.php";
            requestqueue = Volley.newRequestQueue(this);
            final StringRequest jsnrs = new StringRequest(Request.Method.POST, URL_course, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {


                    Toast.makeText(getApplication(), "loading text", Toast.LENGTH_SHORT).show();
                    Log.v("nnn", "res model" + response);

                    sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    Toast.makeText(getApplication(), "loading courses", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(config.MODELDATA_SHARED_PREF, response);
                    editor.commit();
                    requestModel.setRespone(response);

                    getofflinedata();

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(textActivity.this, "error!!!...check your internet connect and try again", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(textActivity.this, MainActivity.class);
                    startActivity(intent);


                }
            });


            requestqueue.add(jsnrs);
        }
        getofflinedata();
    }

    public void updateUI(){
        title_model.setText(model.getTitle());
        text_model.setText(model.getText());
        showBar(false);

    }




}
