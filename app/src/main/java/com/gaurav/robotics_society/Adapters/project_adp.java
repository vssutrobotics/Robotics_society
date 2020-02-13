package com.gaurav.robotics_society.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurav.robotics_society.Models.project_Model;
import com.gaurav.robotics_society.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by GAURAV on 12-02-2019.
 */

public class project_adp extends RecyclerView.Adapter<project_adp.ViewHolder> {

    Context mCtx;
    List<project_Model> products;

    public project_adp(Context mCtx, List<project_Model> products) {
        this.mCtx = mCtx;
        this.products = products;
    }

    @NonNull
    @Override
    public project_adp.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.project_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull project_adp.ViewHolder holder, int position) {
        final project_Model product = products.get(position);
        holder.text_pro.setText(product.getHead());
        Picasso.get().load(product.getImage()).into(holder.image_pro);

        holder.setItemClickListener(new project_Click_listener() {
            @Override
            public void onClick(View view, int position) {
                Context cont = view.getContext();

                if ((!product.getUrl().equalsIgnoreCase("")) && (product.getUrl() != null)) {
                    Intent chrome = new Intent(Intent.ACTION_VIEW, Uri.parse(product.getUrl()));
                    cont.startActivity(chrome);
                } else {
                    Toast.makeText(cont, "Nothing to describe", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView text_pro;
        private ImageView image_pro;
        private project_Click_listener itemClick;


        public ViewHolder(View itemView) {
            super(itemView);
            text_pro = itemView.findViewById(R.id.projects_head);
            image_pro = itemView.findViewById(R.id.projects_image);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(project_Click_listener itemClickListener) {
            this.itemClick = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClick.onClick(v, getAdapterPosition());
        }
    }
}
