package com.app.nammaguru_palanisamigal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.app.nammaguru_palanisamigal.R;
import com.app.nammaguru_palanisamigal.adapter.Recent_Update_Adapter;

public class Detail_View_Activity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView recycler_recent_updates;
    private Recent_Update_Adapter recent_update_adapter;
    int[] image = {R.drawable.guru_image1, R.drawable.guru_image2, R.drawable.guru_image3,
            R.drawable.guru_image5, R.drawable.guru_images8, R.drawable.guru_images9};

    private VideoView vv;
    private MediaController mediacontroller;
    private Uri uri;
    private boolean isContinuously = false;
    private ProgressBar progressBar;
    private Button  btncontinuously, btnstop, btnplay;
    private ImageView btnonce;
    private String str_flag="";
    private String str_url="";
    private String str_thumbnail="";
    private String str_title = "";
    private String str_description = "";
    private String str_event_date = "";
    private TextView txt_title;
    private TextView txt_date;
    private TextView txt_content;
    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__view_);
        getBundle();
        initialView();
    }

    public void getBundle(){

        Bundle bundle=getIntent().getExtras();

        if(bundle != null){

            str_flag=bundle.getString("flag");
            str_url=bundle.getString("url");
            str_thumbnail=bundle.getString("Thumbnail");
            str_title = bundle.getString("title");
            str_description = bundle.getString("description");
            str_event_date = bundle.getString("date");
        }

    }

    public void initialView() {

        progressBar = (ProgressBar) findViewById(R.id.progrss);
        vv = (VideoView) findViewById(R.id.vv);
        btnonce = findViewById(R.id.btnonce);
        btncontinuously = (Button) findViewById(R.id.btnconti);
        btnstop = (Button) findViewById(R.id.btnstop);
        btnplay = (Button) findViewById(R.id.btnplay);
        img_back=findViewById(R.id.img_back);

        txt_title=findViewById(R.id.txt_title);
        txt_date=findViewById(R.id.txt_date);
        txt_content=findViewById(R.id.txt_content);

        txt_title.setText(str_title);
        txt_content.setText(str_description);
        txt_date.setText(str_event_date);

        img_back.setOnClickListener(this);
        mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(vv);
        String uriPath = str_url; //update package name
        uri = Uri.parse(uriPath);
        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(isContinuously){
                    vv.start();
                }
            }
        });

        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vv.pause();
            }
        });

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vv.start();
            }
        });

        btnonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnonce.setVisibility(View.INVISIBLE);
                isContinuously = false;
                progressBar.setVisibility(View.VISIBLE);
                vv.setMediaController(mediacontroller);
                vv.setVideoURI(uri);
                vv.requestFocus();
                vv.start();
            }
        });

        btncontinuously.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isContinuously = true;
                progressBar.setVisibility(View.VISIBLE);
                vv.setMediaController(mediacontroller);
                vv.setVideoURI(uri);
                vv.requestFocus();
                vv.start();
            }
        });

        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
            }
        });

        recycler_recent_updates = findViewById(R.id.recycler_recent_updates);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler_recent_updates.setLayoutManager(linearLayoutManager);

//        recent_update_adapter = new Recent_Update_Adapter(getApplicationContext(), image);
//        recycler_recent_updates.setAdapter(recent_update_adapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.img_back:

                finish();

                break;
        }
    }
}
