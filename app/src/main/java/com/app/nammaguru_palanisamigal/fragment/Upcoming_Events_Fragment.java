package com.app.nammaguru_palanisamigal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.app.nammaguru_palanisamigal.adapter.Events_present_Adapter;
import com.app.nammaguru_palanisamigal.adapter.Events_upcoming_Adapter;
import com.app.nammaguru_palanisamigal.model.Upcoming_List_Model;
import com.app.nammaguru_palanisamigal.model.Upcoming_model;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import pl.droidsonroids.gif.GifTextView;

public class Upcoming_Events_Fragment extends Fragment {
    private View view;
    private RecyclerView recycler_upcoming_list;
    private Events_upcoming_Adapter events_upcoming_adapter;
    private GifTextView loading;
    private List<Upcoming_List_Model> upcoming_list_models;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.activity_upcoming_eve, container, false);


        initialView(view);

        return view;
    }

    private void initialView(View view) {
        loading = view.findViewById(R.id.loading);
        recycler_upcoming_list = view.findViewById(R.id.recycler_upcoming_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycler_upcoming_list.setLayoutManager(linearLayoutManager);

        upcoming_list_models = new ArrayList<>();

        recentAdapterLoad();
        getRecentEvents();
    }


    public void recentAdapterLoad() {
        events_upcoming_adapter = new Events_upcoming_Adapter(getActivity());
        recycler_upcoming_list.setAdapter(events_upcoming_adapter);
    }

    public void getRecentEvents() {
        Request_params loginCredentials = new Request_params();
        loginCredentials.lang = "ta";
        NetworkClient.getRetrofit().create(ApiCall.class).upcomingEvents(loginCredentials)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Upcoming_model>() {
                    @Override
                    public void onSuccess(Upcoming_model value) {
                        if (value.getStatus() == 1) {

                            loading.setVisibility(View.GONE);
                            upcoming_list_models = value.getData();
                            events_upcoming_adapter.setAddress(upcoming_list_models);
                            adapterClick();

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


    public void adapterClick() {


        events_upcoming_adapter.setOnClickListener(new Events_upcoming_Adapter.UpcomingList() {
            @Override
            public void onCityClickListener(View view, int position, Upcoming_List_Model items, List<Upcoming_List_Model> upcoming_list_models) {

                switch (view.getId()) {

                    case R.id.layout_image_click:

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
}
