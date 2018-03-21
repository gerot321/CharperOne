package com.example.gerrys.charperone.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gerrys.charperone.Interface.ItemClickListener;

/**
 * Created by Cj_2 on 2017-11-12.
 */

public class ConfirmationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView confirmation_id;
    public ImageView shoe_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ConfirmationViewHolder(View itemView) {
        super(itemView);

        //confirmation_id = itemView.findViewById(R.id.shoe_name);
        //shoe_image = itemView.findViewById(R.id.shoe_image);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
