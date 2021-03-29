package com.app.nammaguru_palanisamigal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nammaguru_palanisamigal.R;
import com.app.nammaguru_palanisamigal.model.Images_List_Model;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Images_Adapter extends RecyclerView.Adapter<Images_Adapter.ViewHolder> {

    List<Images_List_Model> images_list_models = new ArrayList<>();
    Context context;
    private EventsClick eventsClick;

    public Images_Adapter(Context context) {
        this.context = context;
    }
    public void setOnClickListener(EventsClick eventsClick)
    {
        this.eventsClick = eventsClick;
    }

    public interface EventsClick {
        void onCityClickListener(View view, int position,Images_List_Model items, List<Images_List_Model> images_list_models);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_image_items, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Images_List_Model items=images_list_models.get(position);


        Glide.with(context)
                .load(items.getImages().get(0))
                .error(R.drawable.ic_image_black_24dp)
                .centerCrop().into(holder.img_guru);

        holder.txt_content.setText(items.getTitle());
        holder.layout_image_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eventsClick.onCityClickListener(v,position,items,images_list_models);

            }
        });

    }

    @Override
    public int getItemCount() {
        return images_list_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_guru;
        private TextView txt_content;
        private LinearLayout layout_image_click;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_guru=itemView.findViewById(R.id.img_guru);
            txt_content=itemView.findViewById(R.id.txt_content);
            layout_image_click=itemView.findViewById(R.id.layout_image_click);
        }
    }

    public void setAddress(List<Images_List_Model> images_list_modelss) {

        images_list_models.clear();
        images_list_models.addAll(images_list_modelss);
        notifyDataSetChanged();

    }

}
