package com.app.nammaguru_palanisamigal.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nammaguru_palanisamigal.R;
import com.app.nammaguru_palanisamigal.model.Upcoming_List_Model;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifTextView;

public class Upcoming_Events_Adapters extends RecyclerView.Adapter<Upcoming_Events_Adapters.ViewHolder> {

    private Context context;

    private UpcomingList upcomingList;
    List<Upcoming_List_Model> upcoming_list_models = new ArrayList<>();


    public Upcoming_Events_Adapters(Context context) {

        this.context = context;

    }

    public void setOnClickListener(UpcomingList upcomingList) {
        this.upcomingList = upcomingList;
    }


    @NonNull
    @Override
    public Upcoming_Events_Adapters.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.activity_upcoming_events, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        Upcoming_List_Model items = upcoming_list_models.get(position);
        if (!items.getVideos().equalsIgnoreCase("")) {
            holder.img_play.setVisibility(View.VISIBLE);

            Glide.with(context)
                    .load(items.getThumbnailImage()).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.loading.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.loading.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .error(R.drawable.ic_placeholder_story)
                    .centerCrop().into(holder.img_guru);
        } else {

            holder.img_play.setVisibility(View.GONE);
            Glide.with(context)
                    .load(items.getImages().get(0)).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.loading.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.loading.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .error(R.drawable.ic_placeholder_story)
                    .centerCrop().into(holder.img_guru);
        }
        holder.txt_contents.setText(items.getTitle());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upcomingList.onCityClickListener(v, position, items, upcoming_list_models);
            }
        });
    }

    public interface UpcomingList {
        void onCityClickListener(View view, int position, Upcoming_List_Model items, List<Upcoming_List_Model> upcoming_list_models);
    }

    @Override
    public int getItemCount() {
        return upcoming_list_models.size();
    }


    public void setAddress(List<Upcoming_List_Model> upcoming_list_modelss) {

        upcoming_list_models.clear();
        upcoming_list_models.addAll(upcoming_list_modelss);
        notifyDataSetChanged();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_contents;
        private ImageView img_guru;
        private GifTextView loading;
        private CardView card_view;
        private ImageView img_play;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_contents = itemView.findViewById(R.id.txt_contents);
            img_guru = itemView.findViewById(R.id.img_guru);
            loading = itemView.findViewById(R.id.loading);
            card_view = itemView.findViewById(R.id.card_view);
            img_play = itemView.findViewById(R.id.img_play);
        }
    }
}