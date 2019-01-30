package com.gaurav.robotics_society.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaurav.robotics_society.Models.media_Model;
import com.gaurav.robotics_society.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by GAURAV on 20-01-2019.
 */

public class media_adp extends RecyclerView.Adapter<media_adp.ViewHolder> {

    Context mCtx;
    List<media_Model> products;

    public media_adp(Context mCtx, List<media_Model> products) {
        this.mCtx = mCtx;
        this.products = products;
    }

    @Override
    public media_adp.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.media_row, parent, false);
        ViewHolder holder = new media_adp.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull media_adp.ViewHolder holder, int position) {
        media_Model product = products.get(position);
        holder.Text_project.setText(product.getProject());
        holder.Text_media.setText(product.getChannel());
        holder.Text_date.setText(product.getDate());
        if(product.getImage() != "thumbnail") {
            Picasso.get().load(product.getImage()).into(holder.Text_image);
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Text_project, Text_media, Text_date;
        ImageView Text_image;

        public ViewHolder(View itemView) {
            super(itemView);

            Text_project = (TextView) itemView.findViewById(R.id.media_project);
            Text_media = (TextView) itemView.findViewById(R.id.media_media);
            Text_date = (TextView) itemView.findViewById(R.id.media_date);
            Text_image = (ImageView) itemView.findViewById(R.id.media_img);
        }
    }
}
