package com.example.salma.systemedu.offline_data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by salma on 2019-03-19.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public  static final String DATABASE_NAME ="student.db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 12);
        SQLiteDatabase db= this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " +Coursetable.NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,DESCRIPTION TEXT,NUMLEVEL INTEGER,TIME DATETIME DEFAULT CURRENT_TIMESTAMP)");
        db.execSQL("create table " + Leveltable.NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,ID_COURSE INTEGER,NUMQUESTION INTEGER)");
        db.execSQL("create table " + Usertable.NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,IDLEVEL INTEGER,ANS INTEGER,IDMODEL INTEGER)");
        db.execSQL("create table " + Resulttable.NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NUMLEVEL INTEGER,IDCOURSE INTEGER,MARK INTEGER,NUMQUEST INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Coursetable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Leveltable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Usertable.NAME);
        onCreate(db);
    }
    public Integer deletedata (String id,String nametable){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(nametable,"ID = ?",new String[]{id});

    }

    public Cursor getdata (int id,String nametable){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + nametable + " where ID= " + id, null);//null??
        return res;
    }
    public Cursor getalldata (String nametable){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + nametable, null);//null??
        return res;
    }
    //course
    public boolean insert_course(course c){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues =new ContentValues();
        contentvalues.put(Coursetable.COL_1,c.getid());
        contentvalues.put(Coursetable.COL_2,c.getName());
        contentvalues.put(Coursetable.COL_3, c.getDescription());
        contentvalues.put(Coursetable.COL_4, c.getNumlevel());
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
        Date currentLocalTime = cal.getTime();
        contentvalues.put(Coursetable.COL_5, String.valueOf(currentLocalTime));
        long result =db.insert(Coursetable.NAME,null,contentvalues); //long??null??
        if(result==-1)
            return  false;
        else
            return  true;
    }


    public boolean update_course(String id,String name,String descrp,String num){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(Coursetable.COL_1,id);
        contentvalues.put(Coursetable.COL_2,name);
        contentvalues.put(Coursetable.COL_3,descrp);
        contentvalues.put(Coursetable.COL_4,num);
        db.update(Coursetable.NAME, contentvalues, "ID = ?", new String[]{id});
        return true;
    }
    //level
    public Integer deletallelevel (String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Leveltable.NAME,"ID_COURSE = ?",new String[]{id});

    }
    public Cursor getlevels (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + Leveltable.NAME+ " where ID_COURSE= " + id, null);//null??
        return res;
    }
    public Cursor getnamelevels (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select NAME from " + Leveltable.NAME+ " where ID= " + id, null);//null??
        return res;
    }
    public boolean insert_level(LevelCourse levelCourse){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues =new ContentValues();
        contentvalues.put(Leveltable.COL_1,levelCourse.getId());
        contentvalues.put(Leveltable.COL_2,levelCourse.getName());
        contentvalues.put(Leveltable.COL_3, levelCourse.getId_course());
        contentvalues.put(Leveltable.COL_4, levelCourse.getNumques());
        long result =db.insert(Leveltable.NAME,null,contentvalues); //long??null??
        if(result==-1)
            return  false;
        else
            return  true;
    }
    public boolean update_level(String id,LevelCourse levelCourse){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(Leveltable.COL_1,levelCourse.getId());
        contentvalues.put(Leveltable.COL_2,levelCourse.getName());
        contentvalues.put(Leveltable.COL_3,levelCourse.getId_course());
        contentvalues.put(Leveltable.COL_4,levelCourse.getNumques());
        db.update(Leveltable.NAME,contentvalues,"ID = ?",new String[]{id});
        return true;
    }



///user
public Cursor getuserdata (int id_level){
    Log.v("sss", "dfffffffffffffdd");
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor res = db.rawQuery("select * from " + Usertable.NAME + " where IDLEVEL =" + id_level, null);//null??
    return res;
}
    public int deletedatauser (int id_level){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Usertable.NAME,"IDLEVEL = ?",new String[]{String.valueOf(id_level)});

    }
    public boolean insert_userdata(UserData u){
        Log.v("sss", "ddddddddd");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues =new ContentValues();
        contentvalues.put(Usertable.COL_2,u.getId_level());
        contentvalues.put(Usertable.COL_3,u.getans());
        contentvalues.put(Usertable.COL_4, u.getId_model());
        long result =db.insert(Usertable.NAME, null, contentvalues); //long??null??
        if(result==-1)
            return  false;
        else
            return  true;
    }

    public boolean update_userdata( String id,UserData userdata){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(Usertable.COL_2,userdata.getId_level());
        contentvalues.put(Usertable.COL_3, userdata.getans());
        contentvalues.put(Usertable.COL_4, userdata.getId_model());
        db.update(Usertable.NAME, contentvalues, "ID = ?", new String[]{id});
        return true;
    }
//result
public boolean insert_result(Result r){
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentvalues =new ContentValues();
    contentvalues.put(Resulttable.COL_2,r.getlevel());
    contentvalues.put(Resulttable.COL_3,r.getMark());
    contentvalues.put(Resulttable.COL_4,r.getNumquestion());
    long result =db.insert(Resulttable.NAME,null,contentvalues); //long??null??
    if(result==-1)
        return  false;
    else
        return  true;
}
    public Cursor getresult (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + Resulttable.NAME+ " where IDCOURSE =" + id, null);//null??
        return res;
    }
    public Integer deleteres (String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Resulttable.NAME,"IDCOURSE = ?",new String[]{id});

    }
    ////////////////////////////////////////////////////////////////////
    private class Coursetable {
        public  static final String NAME ="course";
        public  static final String COL_1 ="ID";
        public  static final String COL_2 ="NAME";
        public  static final String COL_3 ="DESCRIPTION";
        public  static final String COL_4 ="NUMLEVEL";
        public  static final String COL_5 ="TIME";
    }
    private class Leveltable {
        public  static final String NAME ="level";
        public  static final String COL_1 ="ID";
        public  static final String COL_2 ="NAME";
        public  static final String COL_3 ="ID_COURSE";
        public  static final String COL_4 ="NUMQUESTION";
    }
    private class Usertable {
        public  static final String NAME ="USER";
        public  static final String COL_1 ="ID";
        public  static final String COL_2 ="IDLEVEL";
        public  static final String COL_3 ="ANS";
        public  static final String COL_4 ="IDMODEL";

    }
    private class Resulttable {
        public  static final String NAME ="RESULT";
        public  static final String COL_1 ="ID";
        public  static final String COL_2 ="NUMLEVEL";
        public  static final String COL_3 ="IDCOURSE";
        public  static final String COL_4 ="MARK";
        public  static final String COL_5 ="NUMQUEST";

    }
}
