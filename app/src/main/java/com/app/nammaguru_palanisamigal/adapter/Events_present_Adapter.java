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

public class Events_present_Adapter extends RecyclerView.Adapter<Events_present_Adapter.ViewHolder> {

    Context context;
    private CompletedEvents completedEvents;
    List<Upcoming_List_Model> upcoming_list_models = new ArrayList<>();

    public Events_present_Adapter(Context context) {
        this.context = context;
    }

    public void setOnClickListener(CompletedEvents completedEvents) {
        this.completedEvents = completedEvents;
    }


    @NonNull
    @Override
    public Events_present_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_present_items, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Events_present_Adapter.ViewHolder holder, int position) {

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
        holder.txt_date.setText(items.getEventDate());
        holder.txt_content.setText(items.getTitle());


        holder.layout_image_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                completedEvents.onCityClickListener(v, position, items, upcoming_list_models);
            }
        });


    }

    @Override
    public int getItemCount() {
        return upcoming_list_models.size();
    }

    public interface CompletedEvents {
        void onCityClickListener(View view, int position, Upcoming_List_Model item, List<Upcoming_List_Model> upcoming_list_models);
    }

    public void setAddress(List<Upcoming_List_Model> upcoming_list_modelss) {

        upcoming_list_models.clear();
        upcoming_list_models.addAll(upcoming_list_modelss);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_content;
        private ImageView img_guru;
        private ImageView img_arrow;
        private TextView txt_date;
        private ImageView img_play;
        private LinearLayout layout_image_click;
        private GifTextView loading;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_content = itemView.findViewById(R.id.txt_content);
            img_guru = itemView.findViewById(R.id.img_guru);
            img_arrow = itemView.findViewById(R.id.img_arrow);
            txt_date = itemView.findViewById(R.id.txt_date);
            img_play = itemView.findViewById(R.id.img_play);
            layout_image_click = itemView.findViewById(R.id.layout_image_click);
            loading=itemView.findViewById(R.id.loading);
        }
    }
}
