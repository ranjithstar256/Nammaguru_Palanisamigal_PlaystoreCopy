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
import com.app.nammaguru_palanisamigal.adapter.Video_Adapter;
import com.app.nammaguru_palanisamigal.model.Images_List_Model;
import com.app.nammaguru_palanisamigal.model.Images_Model;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import pl.droidsonroids.gif.GifTextView;

public class Guru_Video_Fragment extends Fragment {

    private View view;
    private RecyclerView recycler_videos;
    private Video_Adapter video_adapter;
    private GifTextView loading;

    private List<Images_List_Model> images_list_models;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_gallery_video, container, false);

        initialView(view);

        return view;
    }

    private void initialView(View view) {
        loading=view.findViewById(R.id.loading);
        recycler_videos=view.findViewById(R.id.recycler_videos);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recycler_videos.setLayoutManager(linearLayoutManager);

        images_list_models = new ArrayList<>();

        loadAdapter();
        getVideo();

    }


    public void loadAdapter(){

        video_adapter=new Video_Adapter(getActivity());
        recycler_videos.setAdapter(video_adapter);

    }

    public void adapterClick(){

        video_adapter.setOnClickListener(new Video_Adapter.EventsClick() {
            @Override
            public void onCityClickListener(View view, int position, Images_List_Model items,List<Images_List_Model> video_list_models) {

                switch (view.getId()){

                    case R.id.layout_image_click:

                        Intent intent=new Intent(getActivity(), Detail_View_Activity.class);
                        intent.putExtra("flag","1");
                        intent.putExtra("Thumbnail",items.getThumbnail_image());
                        intent.putExtra("url",items.getVideos());
                        intent.putExtra("title",items.getTitle());
                        intent.putExtra("description",items.getDescription());
                        intent.putExtra("date",items.getEventFrom());
                        startActivity(intent);

                        break;

                }
            }
        });
    }



    public void getVideo() {

        Request_params loginCredentials = new Request_params();
        loginCredentials.lang = "ta";

        NetworkClient.getRetrofit().create(ApiCall.class).getVideos(loginCredentials)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Images_Model>() {
                    @Override
                    public void onSuccess(Images_Model value) {


                        if (value.getStatus() == 1) {
                            loading.setVisibility(View.GONE);
                            images_list_models = value.getData();
                            video_adapter.setAddress(images_list_models);

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
}
