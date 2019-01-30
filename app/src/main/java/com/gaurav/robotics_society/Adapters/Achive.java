package com.gaurav.robotics_society.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaurav.robotics_society.Models.Achivements_Model;
import com.gaurav.robotics_society.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by GAURAV on 10-01-2019.
 */

public class Achive extends RecyclerView.Adapter<Achive.viewholder> {

    Context mCtx;
    List<Achivements_Model> productList;

    public Achive(Context mCtx, List<Achivements_Model> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public Achive.viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.row_achivements, parent, false);
        viewholder holder = new viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Achive.viewholder holder, int position) {
        Achivements_Model product = productList.get(position);
        holder.textTitle.setText(product.getTitle());
        holder.textDesc.setText(product.getDesc());
        Picasso.get().load(product.getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public class viewholder extends RecyclerView.ViewHolder{

        TextView textDesc, textTitle;
        ImageView imageView;

        public viewholder(View itemView) {
            super(itemView);

            textDesc = (TextView) itemView.findViewById(R.id.description);
            textTitle = (TextView) itemView.findViewById(R.id.heading);
            imageView = (ImageView) itemView.findViewById(R.id.ImageV);
        }
    }
}
