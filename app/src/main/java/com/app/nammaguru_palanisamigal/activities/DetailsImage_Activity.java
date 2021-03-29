package com.app.nammaguru_palanisamigal.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.nammaguru_palanisamigal.R;
import com.app.nammaguru_palanisamigal.adapter.Recent_Update_Adapter;
import com.app.nammaguru_palanisamigal.model.Banner_List_Model;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.droidsonroids.gif.GifTextView;

public class DetailsImage_Activity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recycler_recent_updates;
    private Recent_Update_Adapter recent_update_adapter;
    int[] image = {R.drawable
            .guru_image1, R.drawable.guru_image2, R.drawable.guru_image3,
            R.drawable.guru_image5, R.drawable.guru_images8, R.drawable.guru_images9};

    private String str_flag = "";
    private String str_url = "";
    private String str_thumbnail = "";
    private String str_title = "";
    private String str_description = "";
    private String str_event_date = "";

    private TextView txt_title;
    private TextView txt_date;
    private TextView txt_content;
    private SliderLayout slider;
    private List<String> stringList;
    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_image_);
        getBundle();
        initialView();
    }

    public void initialView() {
        slider = findViewById(R.id.slider);
        txt_title=findViewById(R.id.txt_title);
        txt_date=findViewById(R.id.txt_date);
        txt_content=findViewById(R.id.txt_content);
        stringList=new ArrayList<>();

        if (str_url.contains(",")) {
            ArrayList<String> list = new ArrayList<String>(Arrays.asList(str_url.split(",")));
            stringList.clear();
            stringList.addAll(list);

        } else {
            stringList.clear();
            stringList.add(str_url.trim().replace("[","").replace("]",""));

        }
        sliderData(stringList);

        txt_title.setText(str_title);
        txt_content.setText(str_description);
        txt_date.setText(str_event_date);
        img_back=findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        recycler_recent_updates = findViewById(R.id.recycler_recent_updates);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler_recent_updates.setLayoutManager(linearLayoutManager);
    }

    public void getBundle() {

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            str_flag = bundle.getString("flag");
            str_url = bundle.getString("url").replace("[","").replace("]","");
            str_thumbnail = bundle.getString("Thumbnail");
            str_title = bundle.getString("title");
            str_description = bundle.getString("description");
            str_event_date = bundle.getString("date");
        }

    }


    public void sliderData(List<String> banner_list_models) {

        slider.removeAllSliders();
        for (int i = 0; i < banner_list_models.size(); i++) {

            TextSliderView textSliderView = new TextSliderView(DetailsImage_Activity.this);
            textSliderView
                    .image(image[i])
                    //.image(banner_list_models.get(i))
                    .error(R.drawable.ic_placeholder_story)
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);

            slider.addSlider(textSliderView);

        }
        slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));
        slider.getPagerIndicator().setDefaultIndicatorColor(getResources().getColor(R.color.color_dark_green), getResources().getColor(R.color.color_white));

        slider.setDuration(4000);

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
