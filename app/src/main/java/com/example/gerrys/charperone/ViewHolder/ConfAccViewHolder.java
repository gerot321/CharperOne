package com.example.gerrys.charperone.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.gerrys.charperone.Interface.ItemClickListener;
import com.example.gerrys.charperone.R;


public class ConfAccViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView Phone;

    private ItemClickListener itemClickListener;

    public ConfAccViewHolder(View itemView) {
        super(itemView);

        Phone = (TextView)itemView.findViewById(R.id.phone_num);

        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
