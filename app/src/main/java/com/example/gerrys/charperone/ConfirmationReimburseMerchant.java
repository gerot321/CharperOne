package com.example.gerrys.charperone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.gerrys.charperone.Common.Common;
import com.example.gerrys.charperone.Interface.ItemClickListener;
import com.example.gerrys.charperone.Model.productRequest;
import com.example.gerrys.charperone.ViewHolder.ConfirmationViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfirmationReimburseMerchant extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference confirm,reimburseReq;
    String mercId;
    FirebaseRecyclerAdapter<productRequest, ConfirmationViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_status);


        database = FirebaseDatabase.getInstance();
        confirm = database.getReference("Requests");
        reimburseReq = database.getReference("ReimbureseReq");
        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(Common.currentUser.getPhone());

    }

    private void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<productRequest, ConfirmationViewHolder>(
                productRequest.class,
                R.layout.confirmation_layout,
                ConfirmationViewHolder.class,
                reimburseReq.orderByChild("status").equalTo("Waiting")
        ) {

            protected void populateViewHolder(final ConfirmationViewHolder viewHolder, productRequest model, final int position) {
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongCLick) {
                        Intent intent = new Intent(ConfirmationReimburseMerchant.this, ConfirmationReimburseDetail.class);
                        intent.putExtra("ConfirmationId", adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }

}
