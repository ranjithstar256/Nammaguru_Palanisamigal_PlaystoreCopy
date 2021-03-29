package com.app.nammaguru_palanisamigal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.app.nammaguru_palanisamigal.MainActivity;
import com.app.nammaguru_palanisamigal.R;
import com.google.firebase.messaging.FirebaseMessaging;

public class SpalshScreen_Activity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private String str_language="ta";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen_);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                str_language="ta";
                str_language=getDefaults("Language",getApplicationContext());
//                if(str_language==null || str_language.equalsIgnoreCase("")) {
//
//                    Intent mainIntent = new Intent(SpalshScreen_Activity.this, Language_Selection_Activity.class);
//                    startActivity(mainIntent);
//                    finish();
//
//                }else{

                    Intent mainIntent = new Intent(SpalshScreen_Activity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();

//                }
            }
        }, SPLASH_DISPLAY_LENGTH);

    }


    public static String getDefaults(String key, Context context) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = preferences.getString(key, "");

        return value;
    }
}
