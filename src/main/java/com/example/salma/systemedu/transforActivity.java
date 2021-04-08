package com.example.salma.systemedu;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
import com.example.salma.systemedu.offline_data.Result;
import com.example.salma.systemedu.offline_data.UserData;
import com.example.salma.systemedu.requestdata.RequestLevel;
import com.example.salma.systemedu.rsglogsign.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class transforActivity extends AppNavActivity{
    public static final String numlevel_param="IDLEVEL";
    public static final String idcourseparam="IDCOURSE";
    private Dialog finishdialog;
    private Button finishbtn;
    private int id_level;
    private int nummodel;
    private int id_course;
    int sum=0;
    private SharedPreferences sharedPreferences;
    private int numlevel;
    private DatabaseHelper mydb;
    RequestQueue requestqueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfor);
        sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        id_course=Integer.parseInt(sharedPreferences.getString(config.COURSE_SHARED_PREF, "1"));
        id_level=Integer.parseInt(getIntent().getStringExtra(textActivity.level_param));
        numlevel=Integer.parseInt(getIntent().getStringExtra(textActivity.numlevel_param));
        nummodel=Integer.parseInt(getIntent().getStringExtra(textActivity.model_param));
        finishsetdialog();
    }
    public void finishsetdialog(){
        mydb = new DatabaseHelper(this);//bulider for create database

        Cursor result = mydb.getuserdata(id_level);
        finishdialog=new Dialog(transforActivity.this);

        finishdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        finishdialog.setContentView(R.layout.layout_dialog);
        finishdialog.setTitle("finish");
        TextView resview;
        resview=(TextView)finishdialog.findViewById(R.id.textdialog);
        String text="your result:\n";
        int i=1;

        while (result.moveToNext()){
            UserData userData=new UserData(Integer.parseInt(result.getString(1)),Integer.parseInt(result.getString(2)),Integer.parseInt(result.getString(3)));
            text=text+ "step "+i+"   "+userData.getans()+"\n";
            i++;
            sum=sum+userData.getans();

        }

        resview.setText(text);



        finishbtn=(Button)finishdialog.findViewById(R.id.finishbtn);
        finishbtn.setEnabled(true);
        finishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(config.NUMLEVEL_SHARED_PREF, String.valueOf(numlevel + 1));

                saveforweb(numlevel, sum);
                Intent intent = new Intent(transforActivity.this, LevelActivity.class);
                intent.putExtra(transforActivity.numlevel_param,String.valueOf(numlevel+1));
                intent.putExtra(transforActivity.idcourseparam,String.valueOf(sharedPreferences.getString(config.COURSE_SHARED_PREF,"1")));
                startActivity(intent);
                finishdialog.cancel();
            }
        });
        finishdialog.show();
    }


        private void saveforweb(int numlevel,int sum) {
            Log.v("ssss", sum + " sum "+" \n numlevel"+numlevel);
            final String resultsuccess="success";
            final Result result=new Result(numlevel,sum,nummodel-1);


                String URL_SEND= "http://orninaart.000webhostapp.com/androidcourse/api/studentaddlog.php";
                requestqueue = Volley.newRequestQueue(this);
                StringRequest jsnr = new StringRequest(Request.Method.POST, URL_SEND, new Response.Listener<String>() {
                    JSONObject object = null;
                    String status="failed";
                    @Override
                    public void onResponse(String response) {
                        Log.v("dddddnssssss", response + "");

                        try {
                            object = new JSONObject(response);
                            status=object.getString("status");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                            Toast.makeText(transforActivity.this, "your result saved", Toast.LENGTH_SHORT).show();
                            RequestLevel rl=RequestLevel.getinstance();
                            rl.setRespone("level");
                            Log.v("ddddeeee111",rl.getRespone());



                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(transforActivity.this, "error!!...we cannt save your result try agian", Toast.LENGTH_SHORT).show();
                        Log.v("funnssssss", error + "");
                        Intent intent = new Intent(transforActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<String, String>();

                        param.put("id_student",String.valueOf(sharedPreferences.getString(config.ID_SHARED_PREF,"1") ));
                        param.put("id_level",String.valueOf( result.getlevel()));
                        param.put("id_course",String.valueOf(id_course));
                        param.put("mark", String.valueOf(result.getMark()));
                        param.put("numquestion", String.valueOf(result.getNumquestion()));



                        return param;
                    }
                };
                requestqueue.add(jsnr);
            int dal=mydb.deletedatauser(id_level);
            }






}
