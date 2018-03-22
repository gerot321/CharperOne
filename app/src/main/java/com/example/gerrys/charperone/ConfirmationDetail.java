package com.example.gerrys.charperone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerrys.charperone.Model.Confirmation;
import com.example.gerrys.charperone.ViewHolder.ConfirmationViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;

public class ConfirmationDetail extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference confirm,request;
    String ID;
    Confirmation confirmss;
    FirebaseRecyclerAdapter<Confirmation, ConfirmationViewHolder> adapter;
    TextView ConID,Name,Phone;
    CardView ConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_detail);
        ID = getIntent().getStringExtra("ConfirmationId");
        ConID = (TextView)findViewById(R.id.RequestId);
        Phone = (TextView) findViewById(R.id.AccountId);
        Name = (TextView)findViewById(R.id.AccountName);
        ConfirmButton = (CardView)findViewById(R.id.confirmPayment);



        database = FirebaseDatabase.getInstance();
        confirm = database.getReference("Confirmation");
        request = database.getReference("Requests");
        confirm.child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                confirmss = dataSnapshot.getValue(Confirmation.class);
                ConID.setText("Request ID : "+confirmss.getId());
                Phone.setText("Account Number : "+confirmss.getPhone());
                Name.setText("Name : "+confirmss.getName());
                new DownloadImageFromInternet((ImageView) findViewById(R.id.image_view))
                        .execute(confirmss.getImage());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.child(ID).child("status").setValue("Confirmed");
                confirm.child(ID).child("status").setValue("Confirmed");
                Intent intent = new Intent(ConfirmationDetail.this,ConfirmationAdmin.class);
                startActivity(intent);
            }
        });
    }
    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
