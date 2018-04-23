package com.example.gerrys.charperone.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.gerrys.charperone.Interface.ItemClickListener;
import com.example.gerrys.charperone.R;

/**
 * Created by Cj_2 on 2017-11-12.
 */

public class ViewHolderHistory extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView orderID;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ViewHolderHistory(View itemView) {
        super(itemView);

        orderID = itemView.findViewById(R.id.order_id);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
