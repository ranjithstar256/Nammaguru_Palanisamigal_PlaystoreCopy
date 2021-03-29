package com.app.nammaguru_palanisamigal.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class Events_upcoming_Adapter extends RecyclerView.Adapter<Events_upcoming_Adapter.ViewHolder> {

    private Context context;
    private List<Upcoming_List_Model> upcoming_list_models = new ArrayList<>();
    private UpcomingList upcomingList;

    public Events_upcoming_Adapter(Context context) {

        this.context = context;

    }

    public void setOnClickListener(UpcomingList upcomingList) {
        this.upcomingList = upcomingList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.activity_upcoming_items, viewGroup, false);

        return new ViewHolder(view);
    }

    public void setAddress(List<Upcoming_List_Model> upcoming_list_modelss) {

        upcoming_list_models.clear();
        upcoming_list_models.addAll(upcoming_list_modelss);
        notifyDataSetChanged();

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
                    .error(R.drawable.ic_image_black_24dp)
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
                    .error(R.drawable.ic_image_black_24dp)
                    .centerCrop().into(holder.img_guru);

        }

        holder.txt_content.setText(items.getTitle());
        holder.txt_date.setText(items.getEventDate());


        holder.layout_image_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upcomingList.onCityClickListener(v,position,items,upcoming_list_models);
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


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_content;
        private ImageView img_guru;
        private ImageView img_arrow;
        private TextView txt_date;
        private GifTextView loading;
        private ImageView img_play;
        private LinearLayout layout_image_click;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_content = itemView.findViewById(R.id.txt_content);
            img_guru = itemView.findViewById(R.id.img_guru);
            img_arrow = itemView.findViewById(R.id.img_arrow);
            txt_date = itemView.findViewById(R.id.txt_date);
            loading = itemView.findViewById(R.id.loading);
            img_play = itemView.findViewById(R.id.img_play);
            layout_image_click = itemView.findViewById(R.id.layout_image_click);
        }
    }
}