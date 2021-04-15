package com.app.nammaguru_palanisamigal.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nammaguru_palanisamigal.APIcalls.ApiCall;
import com.app.nammaguru_palanisamigal.APIcalls.NetworkClient;
import com.app.nammaguru_palanisamigal.APIcalls.Request_params;
import com.app.nammaguru_palanisamigal.R;
import com.app.nammaguru_palanisamigal.activities.Detail_View_Activity;
import com.app.nammaguru_palanisamigal.activities.DetailsImage_Activity;
import com.app.nammaguru_palanisamigal.adapter.Recent_Update_Adapter;
import com.app.nammaguru_palanisamigal.adapter.Upcoming_Events_Adapters;
import com.app.nammaguru_palanisamigal.model.Banner_List_Model;
import com.app.nammaguru_palanisamigal.model.Banner_Model;
import com.app.nammaguru_palanisamigal.model.Upcoming_List_Model;
import com.app.nammaguru_palanisamigal.model.Upcoming_model;
import com.app.nammaguru_palanisamigal.notification.MyFirebaseMessagingService;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.indicators.PagerIndicator;
import com.glide.slider.library.slidertypes.BaseSliderView;
import com.glide.slider.library.slidertypes.TextSliderView;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class Home_Fragment extends Fragment {

    private View view;
    private String str_language;
    private SliderLayout slider;
    private RecyclerView recycler_recent_updates;
    private Recent_Update_Adapter recent_update_adapter;
    private Upcoming_Events_Adapters upcoming_events;
    private RecyclerView recycler_upcoming_events;
    private LinearLayout layout_recent;
    private LinearLayout layout_upcoming;
    private LinearLayout layout_visible;

    private List<Upcoming_List_Model> upcoming_list_models;

    int[] image = {R.drawable.guru_image1, R.drawable.guru_image2, R.drawable.guru_image3, R.drawable.guru_image5, R.drawable.guru_images8, R.drawable.guru_images9};

    int[] images = {R.drawable.guru_images9, R.drawable.guru_images8, R.drawable.guru_image5, R.drawable.guru_image3, R.drawable.guru_image2, R.drawable.guru_image1};

    List<Banner_List_Model> banner_list_models;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        str_language = getDefaults("Language", getActivity());
        setAppLocale(str_language);
        view = inflater.inflate(R.layout.activity_home, container, false);

        if (getDefaults("Token", getActivity()).equalsIgnoreCase("")) {
            getDeviceToken();
        }

        initialView(view);

        return view;
    }

    public void initialView(View view) {

        slider = view.findViewById(R.id.slider);
        layout_recent = view.findViewById(R.id.layout_recent);
        layout_upcoming = view.findViewById(R.id.layout_upcoming);
        layout_visible = view.findViewById(R.id.layout_visible);

        recycler_recent_updates = view.findViewById(R.id.recycler_recent_updates);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycler_recent_updates.setLayoutManager(linearLayoutManager);


        recycler_upcoming_events = view.findViewById(R.id.recycler_upcoming_events);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycler_upcoming_events.setLayoutManager(linearLayoutManager1);


        banner_list_models = new ArrayList<>();
        upcoming_list_models = new ArrayList<>();

        getBanner();


    }

    public void upcomingAdapterLoad() {
        upcoming_events = new Upcoming_Events_Adapters(getActivity());
        recycler_upcoming_events.setAdapter(upcoming_events);
    }

    public void recentAdapterLoad() {
        recent_update_adapter = new Recent_Update_Adapter(getActivity());
        recycler_recent_updates.setAdapter(recent_update_adapter);
    }

    private void setAppLocale(String localeCode) {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            config.locale = new Locale(localeCode.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
    }

    public static String getDefaults(String key, Context context) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = preferences.getString(key, "");

        return value;
    }

    public void sliderData(List<Banner_List_Model> banner_list_models) {

        slider.removeAllSliders();
        for (int i = 0; i < banner_list_models.size(); i++) {

            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView
                    .image(banner_list_models.get(i).getUrl());

            slider.addSlider(textSliderView);

        }
        slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider.setCustomIndicator((PagerIndicator) view.findViewById(R.id.custom_indicator));
        slider.getPagerIndicator().setDefaultIndicatorColor(getResources().getColor(R.color.color_dark_green), getResources().getColor(R.color.color_white));

        slider.setDuration(50000);

    }

    public void getBanner() {
        Request_params loginCredentials = new Request_params();
        loginCredentials.lang = "ta";
        NetworkClient.getRetrofit().create(ApiCall.class).getBanner(loginCredentials)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Banner_Model>() {
                    @Override
                    public void onSuccess(Banner_Model value) {


                        if (value.getStatus() == 1) {
                            banner_list_models = value.getData();
                            Log.d("sdfsefsafaASGD", banner_list_models.toString());
                            sliderData(banner_list_models);

                            recentAdapterLoad();
                            getRecentEvents();
                            layout_recent.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getActivity(), value.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("DATA", e.getMessage());
                    }
                });
    }

    public void getUpcoming() {

        Request_params loginCredentials = new Request_params();
        loginCredentials.lang = "ta";

        NetworkClient.getRetrofit().create(ApiCall.class).upcomingEvents(loginCredentials)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Upcoming_model>() {
                    @Override
                    public void onSuccess(Upcoming_model value) {
                        if (value.getStatus() == 1) {
                            upcoming_list_models = value.getData();
                            upcoming_events.setAddress(upcoming_list_models);
                            upcomingClickAdapter();

                        } else {
                            Toast.makeText(getActivity(), value.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("DATA", e.getMessage());
                    }
                });
    }

    public void getRecentEvents() {

        Request_params loginCredentials = new Request_params();
        loginCredentials.lang = "ta";

        NetworkClient.getRetrofit().create(ApiCall.class).recentEvents(loginCredentials)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Upcoming_model>() {
                    @Override
                    public void onSuccess(Upcoming_model value) {
                        if (value.getStatus() == 1) {
                            upcoming_list_models = value.getData();
                            recent_update_adapter.setAddress(upcoming_list_models);
                            upcomingAdapterLoad();
                            getUpcoming();
                            layout_upcoming.setVisibility(View.VISIBLE);
                            layout_visible.setVisibility(View.GONE);
                            clickRecentAdapter();
                        } else {
                            Toast.makeText(getActivity(), value.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("DATA", e.getMessage());
                    }
                });
    }

    public void clickRecentAdapter() {

        recent_update_adapter.setOnClickListener(new Recent_Update_Adapter.RecentList() {
            @Override
            public void onCityClickListener(View view, int position, Upcoming_List_Model items, List<Upcoming_List_Model> upcoming_list_models) {

                switch (view.getId()) {

                    case R.id.card_view:

                        if (!items.getVideos().equalsIgnoreCase("")) {

                            Intent intent = new Intent(getActivity(), Detail_View_Activity.class);
                            intent.putExtra("flag", "1");
                            intent.putExtra("Thumbnail", items.getThumbnailImage());
                            intent.putExtra("url", items.getVideos());
                            intent.putExtra("title", items.getTitle());
                            intent.putExtra("description", items.getDescription());
                            intent.putExtra("date", items.getEventDate());
                            startActivity(intent);

                        } else {

                            Intent intent = new Intent(getActivity(), DetailsImage_Activity.class);
                            intent.putExtra("flag", "1");
                            intent.putExtra("Thumbnail", items.getThumbnailImage());
                            intent.putExtra("url", "" + items.getImages());
                            intent.putExtra("title", items.getTitle());
                            intent.putExtra("description", items.getDescription());
                            intent.putExtra("date", items.getEventDate());
                            startActivity(intent);

                        }


                        break;

                }

            }
        });
    }

    public void upcomingClickAdapter() {

        upcoming_events.setOnClickListener(new Upcoming_Events_Adapters.UpcomingList() {
            @Override
            public void onCityClickListener(View view, int position, Upcoming_List_Model items, List<Upcoming_List_Model> upcoming_list_models) {
                switch (view.getId()) {

                    case R.id.card_view:

                        if (!items.getVideos().equalsIgnoreCase("")) {

                            Intent intent = new Intent(getActivity(), Detail_View_Activity.class);
                            intent.putExtra("flag", "1");
                            intent.putExtra("Thumbnail", items.getThumbnailImage());
                            intent.putExtra("url", items.getVideos());
                            intent.putExtra("title", items.getTitle());
                            intent.putExtra("description", items.getDescription());
                            intent.putExtra("date", items.getEventDate());
                            startActivity(intent);

                        } else {

                            Intent intent = new Intent(getActivity(), DetailsImage_Activity.class);
                            intent.putExtra("flag", "1");
                            intent.putExtra("Thumbnail", items.getThumbnailImage());
                            intent.putExtra("url", "" + items.getImages());
                            intent.putExtra("title", items.getTitle());
                            intent.putExtra("description", items.getDescription());
                            intent.putExtra("date", items.getEventDate());
                            startActivity(intent);

                        }

                        break;

                }

            }
        });
    }

    public void getDeviceToken() {

        Request_params loginCredentials = new Request_params();
        String str_token = MyFirebaseMessagingService.getToken(getActivity());
        loginCredentials.device_token = MyFirebaseMessagingService.getToken(getActivity());

        NetworkClient.getRetrofit().create(ApiCall.class).Notification(loginCredentials)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Upcoming_model>() {
                    @Override
                    public void onSuccess(Upcoming_model value) {
                        if (value.getStatus() == 1) {

                            setDefaults("Token", "Success", getActivity());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("DATA", e.getMessage());
                    }
                });
    }

    public static void setDefaults(String key, String value, Context context) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();

    }

    public static String getDefaultsToken(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = preferences.getString(key, "");
        return value;
    }
}
