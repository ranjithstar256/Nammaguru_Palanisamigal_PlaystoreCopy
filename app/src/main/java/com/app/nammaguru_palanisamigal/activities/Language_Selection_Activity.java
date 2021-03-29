package com.app.nammaguru_palanisamigal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.nammaguru_palanisamigal.MainActivity;
import com.app.nammaguru_palanisamigal.R;

public class Language_Selection_Activity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout layout_go;
    private LinearLayout layout_tamil;
    private LinearLayout layout_english;
    private TextView txt_tamil;
    private TextView txt_english;
    private ImageView img_tamil_tick;
    private ImageView img_english_tick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language__selection_);

        initialView();

    }

    public void initialView() {

        layout_go = findViewById(R.id.layout_go);
        layout_tamil = findViewById(R.id.layout_tamil);
        layout_english = findViewById(R.id.layout_english);

        txt_english = findViewById(R.id.txt_english);
        txt_tamil = findViewById(R.id.txt_tamil);

        img_tamil_tick = findViewById(R.id.img_tamil_tick);
        img_english_tick = findViewById(R.id.img_english_tick);

        layout_tamil.setOnClickListener(this);
        layout_english.setOnClickListener(this);
        layout_go.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.layout_tamil:

                layout_go.setVisibility(View.VISIBLE);
                setDefaults("Language","ta",getApplicationContext());
                tamilSelection();

                break;

            case R.id.layout_english:

                layout_go.setVisibility(View.VISIBLE);
                setDefaults("Language","en",getApplicationContext());
                englishSelection();

                break;

            case R.id.layout_go:

                Intent mainIntent = new Intent(Language_Selection_Activity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();

                break;

        }
    }


    public void tamilSelection() {

        img_tamil_tick.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            layout_tamil.setBackground(getResources().getDrawable(R.drawable.round_radius));
        }
        txt_tamil.setTextColor(getResources().getColor(R.color.color_white));

        img_english_tick.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            layout_english.setBackground(getResources().getDrawable(R.drawable.corner_white));
        }
        txt_english.setTextColor(getResources().getColor(R.color.color_dark_bg));
    }

    public void englishSelection() {

        img_english_tick.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            layout_english.setBackground(getResources().getDrawable(R.drawable.round_radius));
        }
        txt_english.setTextColor(getResources().getColor(R.color.color_white));

        img_tamil_tick.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            layout_tamil.setBackground(getResources().getDrawable(R.drawable.corner_white));
        }
        txt_tamil.setTextColor(getResources().getColor(R.color.color_dark_bg));
    }


    public static void setDefaults(String key, String value, Context context) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();

    }


}
