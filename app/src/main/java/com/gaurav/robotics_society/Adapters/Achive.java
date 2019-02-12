package com.gaurav.robotics_society.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
        final Achivements_Model product = productList.get(position);
        holder.textTitle.setText(product.getTitle());
        holder.textDesc.setText(product.getDesc());
        Picasso.get().load(product.getImage()).into(holder.imageView);

        holder.setItemClickListener(new Achie_Click_listener() {
            @Override
            public void onClick(View view, int position) {
                Context contect = view.getContext();
                Intent yint = new Intent(Intent.ACTION_VIEW, Uri.parse(product.getUrl()));
                //Intent chooser = Intent.createChooser(yint, "Open with:");
                contect.startActivity(yint);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textDesc, textTitle;
        ImageView imageView;
        private Achie_Click_listener itemClick;

        public viewholder(View itemView) {
            super(itemView);

            textDesc = (TextView) itemView.findViewById(R.id.description);
            textTitle = (TextView) itemView.findViewById(R.id.heading);
            imageView = (ImageView) itemView.findViewById(R.id.ImageV);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(Achie_Click_listener itemClickListener) {
            this.itemClick = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClick.onClick(v, getAdapterPosition());
        }
    }
}
