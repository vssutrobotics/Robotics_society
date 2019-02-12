package com.gaurav.robotics_society.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
        final events_Model product = products.get(position);
        holder.text_title.setText(product.getTitle());
        holder.text_body.setText(product.getBody());

        holder.setItemClickListener(new Events_Click_listener() {
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

        TextView text_body, text_title;
        private Events_Click_listener itemClick;

        public ViewHolder(View itemView) {
            super(itemView);
            text_body = (TextView) itemView.findViewById(R.id.events_body);
            text_title = (TextView) itemView.findViewById(R.id.events_title);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(Events_Click_listener itemClickListener) {
            this.itemClick = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClick.onClick(v, getAdapterPosition());
        }
    }
}
