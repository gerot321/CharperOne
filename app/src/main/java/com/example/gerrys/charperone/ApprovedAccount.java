package com.example.gerrys.charperone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.gerrys.charperone.Interface.ItemClickListener;
import com.example.gerrys.charperone.Model.ReqAcc;
import com.example.gerrys.charperone.ViewHolder.ConfAccViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ApprovedAccount extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference confirm;
    FirebaseRecyclerAdapter<ReqAcc, ConfAccViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_acc);


        database = FirebaseDatabase.getInstance();

        confirm = database.getReference("ReqAcc");

        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders();
    }

    private void loadOrders() {
        adapter = new FirebaseRecyclerAdapter<ReqAcc, ConfAccViewHolder>(
                ReqAcc.class,
                R.layout.approved_acc_layout,
                ConfAccViewHolder.class,
                confirm.orderByChild("Status").equalTo("Approved")
        ) {

            protected void populateViewHolder(ConfAccViewHolder viewHolder, ReqAcc model, int position) {

                viewHolder.Phone.setText(adapter.getRef(position).getKey());
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongCLick) {
                        Intent intent = new Intent(ApprovedAccount.this, AddMerchant.class);
                        intent.putExtra("ID", adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }
}
