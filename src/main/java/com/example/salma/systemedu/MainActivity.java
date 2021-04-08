package com.example.salma.systemedu;

import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.salma.systemedu.offline_data.course;
import com.example.salma.systemedu.offline_data.DatabaseHelper;
import com.example.salma.systemedu.requestdata.RequestCourse;
import com.example.salma.systemedu.rsglogsign.LoginActivity;
import com.example.salma.systemedu.rsglogsign.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppNavActivity {
    public static final String id_course="IDCOURSE";
    private boolean loggedIn = false;
    //define the offline base
    DatabaseHelper mydb;
    //waiting symbol
    ProgressDialog progress;
    private RequestQueue requestqueue;
    private ArrayList<course> arraycourse = new ArrayList<>();
    private  Button logout;
    courseAdapter mAdapter;
    private Cursor res;
    RecyclerView recyclerview;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        TextView emailuser=(TextView)findViewById(R.id.emailuser);
        TextView nameuser=(TextView)findViewById(R.id.nameuser);
        nameuser.setText(sharedPreferences.getString(config.NAME_SHARED_PREF,"user"));
        emailuser.setText(sharedPreferences.getString(config.EMAIL_SHARED_PREF,"user@systemedu.com"));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        recyclerview =(RecyclerView)findViewById(R.id.RecyclerViewcourses);
        //In onresume fetching value from sharedpreference

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(config.LOGGEDIN_SHARED_PREF, false);
        if(!loggedIn) {


            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        }



            if(Connectiontest.isConnectedToNetwork(this)){
                //  Log.v("connct","true");

                loadallcourse();
            }
            else
            {
                Toast.makeText(MainActivity.this,"you are offline!!!", Toast.LENGTH_SHORT).show();
                getofflinedata();

            }







    }


    /// load course to list
    private void loadallcourse() {

        final RequestCourse requestCourse=RequestCourse.getinstance();
        if(requestCourse.getRespone()=="course") {

            String URL_course = "http://orninaart.000webhostapp.com/androidcourse/api/getcourse.php";

            requestqueue = Volley.newRequestQueue(this);
            final StringRequest jsnr = new StringRequest(Request.Method.POST, URL_course, new Response.Listener<String>() {
                JSONObject object;

                @Override
                public void onResponse(String response) {

                    sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    Toast.makeText(getApplication(), "loading courses", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(config.COURSEDATA_SHARED_PREF, response);
                    Log.v("funnn1", response);
                    editor.commit();
                    requestCourse.setRespone(response);


                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(MainActivity.this, "error!!!...check your internet connect and try again", Toast.LENGTH_SHORT).show();

                    //  Log.v("funn", error + "");
                }
            });


            requestqueue.add(jsnr);
        }

        getofflinedata();
    }



    private void display_course() {


        mAdapter=new courseAdapter(arraycourse);
        //   course cc =arraycourse.get(1);
//    Log.v("coures", cc.getName());
        recyclerview.setAdapter(mAdapter);

        LinearLayoutManager lmanger= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        recyclerview.setLayoutManager(lmanger);

    }



    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }

    public void getofflinedata() {
        Log.v("funn","getoffline");

            String respone = sharedPreferences.getString(config.COURSEDATA_SHARED_PREF, "   n");
            JSONObject object = null;
        Log.v("funnn", respone);
            try {
                object = new JSONObject(respone);
                 Log.v("funnn", respone);
                JSONArray jsonArray = object.getJSONArray("course");
                int sizearray = jsonArray.length();
                for (int i = 0; i < sizearray; i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    //    Log.v("cccc", item.toString());
                    int id = item.getInt("id");
                    String namecourse = item.getString("name");
                    String discrp = item.getString("discrp");
                    int numlevel = item.getInt("numlevel");
                    Log.v("nnnnnn", namecourse);
                    course itemcourse = new course(id, namecourse, discrp, numlevel);

                    arraycourse.add(itemcourse);

                }
                display_course();
            } catch (JSONException e) {
                e.printStackTrace();
            }

    }


    public class courseAdapter extends RecyclerView.Adapter<recyclerview_course_holder> {
        private ArrayList<course> courseadapter;

        public courseAdapter(ArrayList<course> courseadapter) {
            this.courseadapter = courseadapter;
        }

        @Override
        public recyclerview_course_holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View card= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_course,parent,false);
            return new recyclerview_course_holder(card);
        }
        @Override
        public int getItemCount() {
            return courseadapter.size();
        }
        @Override
        public void onBindViewHolder(recyclerview_course_holder holder, int position) {
            course c=courseadapter.get(position);

            Log.v("couresholder", c.getName());
            holder.updateui(c);
        }


    }





    public class recyclerview_course_holder extends RecyclerView.ViewHolder{
        private ImageView icon;
        private TextView lname_course;
        private TextView ldescrip_course;
        private TextView lnum_course;
        private LinearLayout linearLayout_course;

        public recyclerview_course_holder(View itemView) {
            super(itemView);
            lname_course=(TextView)itemView.findViewById(R.id.list_itemname);
            ldescrip_course=(TextView)itemView.findViewById(R.id.list_itemdescription);
            lnum_course=(TextView)itemView.findViewById(R.id.list_itemnum);
            linearLayout_course=(LinearLayout)itemView.findViewById(R.id.layout_btn);

        }
        public  void updateui(final course ccc){

            Log.v("updateui", ccc.getName());
            lname_course.setText(ccc.getName());
            ldescrip_course.setText(ccc.getDescription());
            lnum_course.setText("level: " + Integer.toString(ccc.getNumlevel()));
            linearLayout_course.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String id=String.valueOf( ccc.getid());
                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(config.COURSE_SHARED_PREF, String.valueOf(ccc.getid()));
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                    intent.putExtra(MainActivity.id_course,id);
                    startActivity(intent);
                }
            });
        }
    }



}
