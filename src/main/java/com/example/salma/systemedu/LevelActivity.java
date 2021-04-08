package com.example.salma.systemedu;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
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
import android.widget.ImageView;
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
import com.example.salma.systemedu.offline_data.LevelCourse;
import com.example.salma.systemedu.offline_data.UserData;
import com.example.salma.systemedu.requestdata.RequestLevel;
import com.example.salma.systemedu.rsglogsign.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class LevelActivity extends AppNavActivity{
    public static final String level_param="IDLEVEL";
    public static final String idcourseparam="IDCOURSE";

    RequestQueue requestqueue;
    //interface
    private Button startbtn;
    private ImageView imglevel;
    private TextView titlelevel;
    private DatabaseHelper mydb;
    private  int id_course;
    private int id_level=0;
    private int id_student;
    private int numlevel=0;
    private Cursor result;
    private boolean finish=true;
    private ProgressBar progressbar;
    private SharedPreferences sharedPreferences;
    //dialog

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //interface
        setContentView(R.layout.activity_level);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sharedPreferences = LevelActivity.this.getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        TextView emailuser=(TextView)findViewById(R.id.emailuser);
        TextView nameuser=(TextView)findViewById(R.id.nameuser);
        nameuser.setText(sharedPreferences.getString(config.NAME_SHARED_PREF, "user"));
        emailuser.setText(sharedPreferences.getString(config.EMAIL_SHARED_PREF, "user@systemedu.com"));
        id_student=Integer.parseInt(sharedPreferences.getString(config.ID_SHARED_PREF, "0"));

        imglevel = (ImageView) findViewById(R.id.img_level);
        titlelevel = (TextView) findViewById(R.id.level_name);

        startbtn = (Button) findViewById(R.id.btn_start);
        progressbar = (ProgressBar) findViewById(R.id.progresslevel);

        //get id course
        //get id course
        if (getIntent().getStringExtra(MainActivity.id_course) != null) {
            id_course = Integer.parseInt(getIntent().getStringExtra(MainActivity.id_course));
            Log.v("sss", "id cpurse" + id_course);

        }
        else {
           id_course=Integer.parseInt(sharedPreferences.getString(config.COURSE_SHARED_PREF,"1"));

        }

        // if connect to internet online-----------------------------------
        if (Connectiontest.isConnectedToNetwork(this)) {
            requestlevel(id_course);

        }else{
            getdataoffline();
        }



    }
    public void getdataoffline()
    {

     String response =  sharedPreferences.getString(config.LEVELDATA_SHARED_PREF, "level");
        JSONObject object;
        LevelCourse level = null;

        Log.v("leeeenvel11 offline", response);
        try {


            object = new JSONObject(response);

            if ( getIntent().getStringExtra(transforActivity.numlevel_param)!= null ) {


                Log.v("eeeee","text numlevel");

                numlevel = Integer.parseInt(getIntent().getStringExtra(transforActivity.numlevel_param));

            }


            else {

                JSONArray levelArray=object.getJSONArray("student");
                int sizearray=levelArray.length();
                int idcourse=1;
                int num=1;
                for(int i=0;i<sizearray;i++) {
                    JSONObject student = levelArray.getJSONObject(i);
                   String status=student.getString("status");
                    Log.v("eeeee","read offline");
                    if(status.equalsIgnoreCase("success")){
                        idcourse = student.getInt("id_course");
                        num = student.getInt("id_level");

                        Log.v("eeee", "idcouese" + id_course + "  idlevel" + numlevel);
                        if (idcourse == id_course && num>=numlevel) {

                            numlevel = num;
                        }
                        Log.v("eeeee","form id first ...numlevel" +numlevel);
                    }
                    else {
                        Log.v("eeeee","error numlevel");
                        numlevel=0;
                        break;
                    }
                }
                numlevel=numlevel+1;
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }
        getlevel(response);



    }
    private void getlevel(String response){
        JSONObject object;
        LevelCourse level = null;
        try {


            object = new JSONObject(response);
            JSONArray levelArray = object.getJSONArray("level");
            int sizearray = levelArray.length();

            for (int i = 0; i < sizearray; i++) {
                JSONObject itemlevel = levelArray.getJSONObject(i);

                int id = itemlevel.getInt("id");
                String s = itemlevel.getString("status");
                String namelevel = itemlevel.getString("name");
                int idcourse = itemlevel.getInt("idcourse");
                int numques = itemlevel.getInt("numques");
                Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();
                level = new LevelCourse(id, namelevel, idcourse, numques);
                if (idcourse == id_course && numques == numlevel) {
                    id_level=id;
                    Log.v("eeeeff", "idcouese" + id_course + "  idlevel" + numlevel);
                    Log.v("eeeee","if course"+id_level);
                    finish = false;
                    break;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        display_level(level);
    }



    @Override
    public void onBackPressed() {
        Intent a = new Intent(LevelActivity.this,MainActivity.class);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }



    private void showBar(boolean b) {

        if(b==true){
            progressbar.setVisibility(View.VISIBLE);
            imglevel.setVisibility(View.GONE);
            titlelevel.setVisibility(View.GONE);
            startbtn.setVisibility(View.GONE);
        }
        else
        {
            progressbar.setVisibility(View.GONE);
            imglevel.setVisibility(View.VISIBLE);
            titlelevel.setVisibility(View.VISIBLE);
            startbtn.setVisibility(View.VISIBLE);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void display_level(LevelCourse level) {
        Log.v("nnnn","nnnnnnumlevel"+numlevel);



        if(finish==true){
            Intent intent = new Intent(LevelActivity.this, endcourseActivity.class);
            startActivity(intent);
        }
           else{
            setimglevel(numlevel);
            titlelevel.setText(level.getName());
            storedata(level.getId());

        }

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LevelActivity.this, textActivity.class);
                startActivity(intent);
            }
        });


        showBar(false);

    }

    private void storedata(int idlevel) {
        //Creating editor to store values to shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //add the value we need
        Log.v("sss","id levele"+id_level+"neumleel"+numlevel+"idcourse"+id_course);
        editor.putBoolean(config.LOGGEDIN_SHARED_PREF, true);
        editor.putString(config.LEVEL_SHARED_PREF, String.valueOf(idlevel));
        editor.putString(config.NUMLEVEL_SHARED_PREF, String.valueOf(numlevel));
        editor.putString(config.COURSE_SHARED_PREF, String.valueOf(id_course));
//to save this editor
        editor.commit();
    }

    //requerst to web
    private void requestlevel(final int id_course) {
        showBar(true);
        RequestLevel rl=RequestLevel.getinstance();
        String data=rl.getRespone();
        Log.v("ddddd respone  ", rl.getRespone());
        if(data=="level") {

            String URL_course = "http://orninaart.000webhostapp.com/androidcourse/api/getalllevel.php";

            requestqueue = Volley.newRequestQueue(this);
            final StringRequest jsnrs = new StringRequest(Request.Method.POST, URL_course, new Response.Listener<String>() {
                JSONObject object;
                RequestLevel r1l=RequestLevel.getinstance();
                @Override
                public void onResponse(String response) {

                    Toast.makeText(getApplication(), "loading level", Toast.LENGTH_SHORT).show();
                    sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(config.LEVELDATA_SHARED_PREF, response);
                    Log.v("eeee re", response);
                    editor.commit();
                    r1l.setRespone(response);
                    getdataoffline();
                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(LevelActivity.this, "error!!!..level.check your internet connect and try again", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LevelActivity.this, MainActivity.class);
                    startActivity(intent);
                    //  Log.v("funn", error + "");
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<String, String>();
                    Log.v("nnnn","student"+id_student);
                    param.put("id_student",String.valueOf(id_student ));


                    return param;
                }
            };


            requestqueue.add(jsnrs);
        }
      else
        getdataoffline();
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setimglevel(int idimglevel) {
       switch (idimglevel){
           case 1:
               imglevel.setImageDrawable(getResources().getDrawable(R.drawable.level1));break;
           case 2:
               imglevel.setImageDrawable(getResources().getDrawable(R.drawable.level2));break;
           case 3:
               imglevel.setImageDrawable(getResources().getDrawable(R.drawable.level3));break;
           case 4:
               imglevel.setImageDrawable(getResources().getDrawable(R.drawable.level4));break;
           case 5:
               imglevel.setImageDrawable(getResources().getDrawable(R.drawable.level5));break;



       }
    }


}
