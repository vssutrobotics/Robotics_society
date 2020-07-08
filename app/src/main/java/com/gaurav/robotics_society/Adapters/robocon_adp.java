package com.gaurav.robotics_society.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaurav.robotics_society.Models.awards_Model;
import com.gaurav.robotics_society.R;

import java.util.List;

public class robocon_adp extends RecyclerView.Adapter<robocon_adp.ViewHolder> {

    Context mctx;
    List<awards_Model> products;

    public robocon_adp(Context mctx, List<awards_Model> products) {
        this.mctx = mctx;
        this.products = products;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.awards_inside_row, parent, false);
        robocon_adp.ViewHolder holder = new robocon_adp.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final awards_Model product = products.get(position);
        holder.textDesc.setText(product.getDesc());
        holder.textHead.setText(product.getHead());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textHead, textDesc;

        public ViewHolder(View itemView) {
            super(itemView);

            textHead = itemView.findViewById(R.id.awards_head);
            textDesc = itemView.findViewById(R.id.awards_desc);

        }
    }
}
