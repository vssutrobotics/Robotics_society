package com.gaurav.robotics_society.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaurav.robotics_society.Models.Achivements_Model;
import com.gaurav.robotics_society.Models.events_Model;
import com.gaurav.robotics_society.R;

import java.util.List;

/**
 * Created by GAURAV on 17-01-2019.
 */

public class events_adp extends RecyclerView.Adapter<events_adp.ViewHolder> {

    Context mCtx;
    List<events_Model> products;

    public events_adp(Context mCtx, List<events_Model> products) {
        this.mCtx = mCtx;
        this.products = products;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.events_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        events_Model product = products.get(position);
        holder.text_posts.setText(product.getPosts());
        holder.text_date.setText(product.getDate());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_posts, text_date;

        public ViewHolder(View itemView) {
            super(itemView);
            text_posts = (TextView) itemView.findViewById(R.id.events_info);
            text_date = (TextView) itemView.findViewById(R.id.events_date);
        }
    }
}
