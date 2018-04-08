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
import com.example.gerrys.charperone.Model.Order;
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

public class ConfirmationDetail extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference confirm,request,prodReq,prod;
    String ID;
    Confirmation confirmss;
    FirebaseRecyclerAdapter<Confirmation, ConfirmationViewHolder> adapter;
    TextView ConID,Name,Phone;
    ImageView image;
    CardView ConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_detail);
        ID = getIntent().getStringExtra("ConfirmationId");
        ConID = (TextView)findViewById(R.id.RequestId);
        Phone = (TextView) findViewById(R.id.AccountId);
        Name = (TextView)findViewById(R.id.AccountName);
        image = (ImageView)findViewById(R.id.image_view);

        ConfirmButton = (CardView)findViewById(R.id.confirmPayment);



        database = FirebaseDatabase.getInstance();
        confirm = database.getReference("Confirmation");
        request = database.getReference("Requests");
        prodReq = database.getReference("productReq");
        prod = database.getReference("Product");
        confirm.child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                confirmss = dataSnapshot.getValue(Confirmation.class);
                ConID.setText("Request ID : "+confirmss.getId());
                Phone.setText("Account Number : "+confirmss.getPhone());
                Name.setText("Name : "+confirmss.getName());
                new DownLoadImageTask(image).execute(confirmss.getImage());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.child(ID).child("product").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child: dataSnapshot.getChildren()) {
                            final Order orders = child.getValue(Order.class);
                            prod.child(orders.getProductId().toString()).child("MerchantId").addValueEventListener(new ValueEventListener() {
                                @Override

                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    productRequest req= new productRequest(ID,dataSnapshot.getValue().toString(),orders.getProductId(),
                                            orders.getProductName().toString(),orders.getQuantity().toString()
                                            ,orders.getPrice().toString(),orders.getAddress(),"diteruskan ke merchant") ;
                                    prodReq.push().setValue(req);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                request.child(ID).child("status").setValue("Confirmed Order");
                confirm.child(ID).child("status").setValue("Confirmed Order");

                Intent intent = new Intent(ConfirmationDetail.this,ConfirmationAdmin.class);
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
