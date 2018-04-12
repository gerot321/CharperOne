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
import com.example.gerrys.charperone.Model.ReimburseReq;
import com.example.gerrys.charperone.ViewHolder.ConfirmationViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.URL;

public class ConfirmationReimburseDetail extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference confirm,request,reimburseReq,prod;
    String ID;
    ReimburseReq confirmss;
    FirebaseRecyclerAdapter<Confirmation, ConfirmationViewHolder> adapter;
    TextView merchId,AN,bankID;
    ImageView image;
    CardView ConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_detail);
        ID = getIntent().getStringExtra("ConfirmationId");
        merchId = (TextView)findViewById(R.id.Merchant);
        bankID = (TextView) findViewById(R.id.Ovoid);
        AN = (TextView)findViewById(R.id.AN);
        image = (ImageView)findViewById(R.id.image_view2);

        ConfirmButton = (CardView)findViewById(R.id.conf);



        database = FirebaseDatabase.getInstance();
        reimburseReq = database.getReference("ReimburseReq");

        confirm.child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                confirmss = dataSnapshot.getValue(ReimburseReq.class);
                merchId.setText("Merchant ID : "+confirmss.getMerchantId());
                bankID.setText("Bank ID : "+confirmss.getBankId());
                AN.setText("Atas Nama : "+confirmss.getAn());

                //new DownLoadImageTask(image).execute(confirmss.getImage());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reimburseReq.child(ID).child("status").setValue("Transfered");
                Intent intent = new Intent(ConfirmationReimburseDetail.this,ConfirmationAdmin.class);
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
