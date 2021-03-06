package com.example.gerrys.charperone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.gerrys.charperone.Interface.ItemClickListener;
import com.example.gerrys.charperone.Model.ReqAccCour;
import com.example.gerrys.charperone.ViewHolder.ConfAccCourViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfirmationAccCour extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference confirm;

    FirebaseRecyclerAdapter<ReqAccCour, ConfAccCourViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf_cour);


        database = FirebaseDatabase.getInstance();

        confirm = database.getReference("ReqAccCourier");

        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders();
    }

    private void loadOrders() {
        adapter = new FirebaseRecyclerAdapter<ReqAccCour, ConfAccCourViewHolder>(
                ReqAccCour.class,
                R.layout.cour_conf_layout,
                ConfAccCourViewHolder.class,
                confirm.orderByChild("Status").equalTo("Waiting for Confirmation")
        ) {

            protected void populateViewHolder(ConfAccCourViewHolder viewHolder, ReqAccCour model, int position) {

                viewHolder.Phone.setText(adapter.getRef(position).getKey());
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongCLick) {
                        Intent intent = new Intent(ConfirmationAccCour.this, ConfirmationAccCourDetail.class);
                        intent.putExtra("Phone", adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }
}
