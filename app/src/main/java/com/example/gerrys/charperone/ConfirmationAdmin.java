package com.example.gerrys.charperone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.gerrys.charperone.Common.Common;
import com.example.gerrys.charperone.Interface.ItemClickListener;
import com.example.gerrys.charperone.Model.Confirmation;
import com.example.gerrys.charperone.ViewHolder.ConfirmationViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfirmationAdmin extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference confirm;

    FirebaseRecyclerAdapter<Confirmation, ConfirmationViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_status);


        database = FirebaseDatabase.getInstance();
        confirm = database.getReference("Confirmation");

        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(Common.currentUser.getPhone());
    }

    private void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<Confirmation  , ConfirmationViewHolder>(
                Confirmation.class,
                R.layout.confirmation_layout,
                ConfirmationViewHolder.class,
                confirm.orderByChild("status").equalTo("Waiting Admin Confirmation")
        ) {

            protected void populateViewHolder(ConfirmationViewHolder viewHolder, Confirmation model, int position) {

                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongCLick) {
                        Intent intent = new Intent(ConfirmationAdmin.this, ConfirmationDetail.class);
                        intent.putExtra("ConfirmationId", adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }

}
