package com.app.nammaguru_palanisamigal.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nammaguru_palanisamigal.R;
import com.app.nammaguru_palanisamigal.model.Images_List_Model;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifTextView;

public class Video_Adapter extends RecyclerView.Adapter<Video_Adapter.ViewHolder> {

    private Context context;

    private EventsClick eventsClick;
    List<Images_List_Model> video_list_models=new ArrayList<>();

    public Video_Adapter(Context context) {

        this.context = context;

    }

    public void setOnClickListener(EventsClick eventsClick)
    {
        this.eventsClick = eventsClick;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.activity_video_items, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        Images_List_Model items= video_list_models.get(position);


        Glide.with(context)
                .load(items.getThumbnail_image())
                .error(R.drawable.ic_image_black_24dp)
                .centerCrop().into(holder.img_guru);

        holder.txt_content.setText(items.getTitle());

        holder.layout_image_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eventsClick.onCityClickListener(v,position,items,video_list_models);

            }
        });

    }

    public interface EventsClick {
        void onCityClickListener(View view, int position,Images_List_Model items, List<Images_List_Model> video_list_models);
    }

    @Override
    public int getItemCount() {
        return  video_list_models.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_content;
        private ImageView img_guru;
        private ImageView img_arrow;
        LinearLayout layout_image_click;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_content= itemView.findViewById(R.id.txt_content);
            img_guru = itemView.findViewById(R.id.img_guru);
            img_arrow= itemView.findViewById(R.id.img_arrow);
            layout_image_click=itemView.findViewById(R.id.layout_image_click);

        }
    }


    public void setAddress(List<Images_List_Model> video_list_modelss) {

        video_list_models.clear();
        video_list_models.addAll(video_list_modelss);
        notifyDataSetChanged();

    }
}