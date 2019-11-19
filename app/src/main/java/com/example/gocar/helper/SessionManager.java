package com.example.gocar.helper;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.example.gocar.activity.LoginActivity;

import java.util.HashMap;

public class SessionManager {
        // LogCat tag
        private static String TAG = SessionManager.class.getSimpleName();
        public static final String KEY_userid = "userid";
        public static final String KEY_EMAIL = "email";


        // Shared Preferences
        SharedPreferences pref;

        Editor editor;
        Context _context;

        // Shared pref mode
        int PRIVATE_MODE = 0;

        // Shared preferences file name
        private static final String PREF_NAME = "AndroidHiveLogin";

        private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

        public SessionManager(Context context) {
            this._context = context;
            pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
        }

        public void setLogin(String user,boolean isLoggedIn) {

            editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

            // commit changes
            editor.commit();
            editor.putString(KEY_userid, LoginActivity.uid);
            editor.commit();
            Log.d(TAG, "User login session modified!");
        }

        public boolean isLoggedIn(){
            return pref.getBoolean(KEY_IS_LOGGEDIN, false);
        }
        public HashMap<String, String> getUserDetails(){
            HashMap<String, String> user = new HashMap<String, String>();
            // user name
            user.put(KEY_userid, pref.getString(KEY_userid, LoginActivity.uid));

            //user.put(KEY_NAME, pref.getString(KEY_NAME, null));

            // user email id
            user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

            // return user
            return user;
        }
    }

