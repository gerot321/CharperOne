package com.example.gerrys.charperone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gerrys.charperone.Model.Confirmation;
import com.example.gerrys.charperone.Model.productRequest;
import com.example.gerrys.charperone.ViewHolder.ConfirmationViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.URL;

public class ConfirmationKurirDetail extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference confirm,request,prodReq,prod;
    String ID;
    productRequest prodss;
    FirebaseRecyclerAdapter<Confirmation, ConfirmationViewHolder> adapter;
    TextView reqId,prodId,prodName,prodQty,prodAddrs,prodPrice;
    ImageView image;
    CardView ConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmationadmin_detail);
        ID = getIntent().getStringExtra("ConfirmationId");
        reqId = (TextView)findViewById(R.id.reqId);
        prodId = (TextView)findViewById(R.id.prodId);
        prodName = (TextView)findViewById(R.id.prodName);
        prodQty = (TextView)findViewById(R.id.prodQty);
        prodAddrs = (TextView)findViewById(R.id.addrs);
        prodPrice = (TextView)findViewById(R.id.prodPrice);



        ConfirmButton = (CardView)findViewById(R.id.confirmPayment);



        database = FirebaseDatabase.getInstance();
        confirm = database.getReference("Confirmation");
        request = database.getReference("Requests");
        prodReq = database.getReference("productReq");
        prod = database.getReference("Product");
        prodReq.child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                prodss = dataSnapshot.getValue(productRequest.class);
                reqId.setText("Request ID : "+prodss.getRequestid());
                prodId.setText("Produk ID : "+prodss.getProductid());
                prodName.setText("Nama Produk : "+prodss.getProductname());
                prodQty.setText("Jumlah Pesanan : "+prodss.getProductname());
                prodAddrs.setText("Alamat Pengiriman : "+prodss.getAddress());
                prodPrice.setText("Total Biaya : "+prodss.getTotalprice());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prodReq.child(ID).child("status").setValue("Order telah sampai pada tujuan");
                Intent intent = new Intent(ConfirmationKurirDetail.this,ConfirmationAdmin.class);
                startActivity(intent);
            }
        });
    }
    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }

}
