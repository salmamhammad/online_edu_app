package com.example.salma.systemedu.rsglogsign;

/**
 * Created by salma on 2019-03-05.
 */
public class config {

        //URL to our login.php file
        public static final String LOGIN_URL = "http://192.168.94.1/Android/LoginLogout/login.php";

        //Keys for email and password as defined in our $_POST['key'] in login.php
        public static final String KEY_EMAIL = "email";
        public static final String KEY_PASSWORD = "password";

        //If server response is equal to this that means login is successful
        public static final String LOGIN_SUCCESS = "seccessful log in";
        public static final String SIGN_SUCCESS = "Success";
        //Keys for Sharedpreferences
        //This would be the name of our shared preferences
        public static final String SHARED_PREF_NAME = "mylogin_app";

        //This would be used to store the email of current logged in user
        public static final String EMAIL_SHARED_PREF = "email";
        public static final String PASS_SHARED_PREF = "pass";
        public static final String COURSE_SHARED_PREF = "course";
        public static final String COURSEDATA_SHARED_PREF = "coursedata";
        public static final String LEVEL_SHARED_PREF = "level";
        public static final String LEVELDATA_SHARED_PREF = "datalevel";
        public static final String NUMLEVEL_SHARED_PREF = "numlevel";
        public static final String ID_SHARED_PREF = "id";
        public static final String NAME_SHARED_PREF = "name";
        public static final String QUESTINDATA_SHARED_PREF = "question";
        public static final String MODELDATA_SHARED_PREF = "model";

        //We will use this to store the boolean in sharedpreference to track user is loggedin or not
        public static final String LOGGEDIN_SHARED_PREF = "logged_in";
    }
