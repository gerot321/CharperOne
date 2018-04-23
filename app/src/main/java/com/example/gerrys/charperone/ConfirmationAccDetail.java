package com.example.gerrys.charperone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerrys.charperone.Model.Confirmation;
import com.example.gerrys.charperone.Model.ReqAcc;
import com.example.gerrys.charperone.Model.Upload;
import com.example.gerrys.charperone.ViewHolder.ConfirmationViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ConfirmationAccDetail extends AppCompatActivity {
    private StorageReference fileRef;
    FirebaseDatabase database;
    FirebaseStorage storage;
    DatabaseReference docReq,request,prodReq,prod,merchant;
    String ID, imgUrl, docUrl, docPath;
    ReqAcc prodss;
    Upload doc;
    FirebaseRecyclerAdapter<Confirmation, ConfirmationViewHolder> adapter;
    TextView reqId, prodName, pass, urlPhoto, urlDoc, add, gen, mail;
    ImageView image, imageView;
    CardView ConfirmButton;
    Button downPic, downDoc, prevPic;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_detail);
        ID = getIntent().getStringExtra("Phone");
        add = (TextView)findViewById(R.id.reqAdd);
        gen = (TextView)findViewById(R.id.reqGen);
        mail = (TextView)findViewById(R.id.reqEmail);
        reqId = (TextView)findViewById(R.id.reqNum);
        prodName = (TextView)findViewById(R.id.reqName);
        pass = (TextView)findViewById(R.id.reqPass);
        urlDoc = (TextView)findViewById(R.id.url_doc);
        urlPhoto = (TextView)findViewById(R.id.url_photo);
        progressDialog = new ProgressDialog(this);
        imageView = (ImageView)findViewById(R.id.ktp_view);

        ConfirmButton = (CardView)findViewById(R.id.confirmAcc);
        downDoc = (Button)findViewById(R.id.down_doc);
        downPic = (Button)findViewById(R.id.down_pic);
        prevPic = (Button)findViewById(R.id.prev_pic);

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        prodReq = database.getReference("ReqAcc");
        docReq = database.getReference("Proposal");
        prodReq.child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                prodss = dataSnapshot.getValue(ReqAcc.class);

                reqId.setText("Phone Number : "+prodss.getPhone());
                prodName.setText("Name : "+prodss.getName());
                pass.setText("Password : "+prodss.getPass());
                add.setText("Address : "+prodss.getAddress());
                gen.setText("Gender : "+prodss.getGender());
                mail.setText("Email : "+prodss.getEmail());
                imgUrl = prodss.getImage();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        docReq.child(ID).addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                doc = dataSnapshot.getValue(Upload.class);
                docUrl = doc.getUrl();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        prevPic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                fileRef = storage.getReferenceFromUrl(imgUrl);
                progressDialog.setTitle("Downloading...");
                progressDialog.setMessage(null);
                progressDialog.show();

                try {
                    final File localFile = File.createTempFile("images", "jpg");

                    fileRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bmp = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            imageView.setImageBitmap(bmp);
                            progressDialog.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(ConfirmationAccDetail.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            // percentage in progress dialog
                            progressDialog.setMessage("Downloaded " + ((int) progress) + "%...");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        downPic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse(imgUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        downDoc.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse(docUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }

        });


        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prodReq.child(ID).child("Status").setValue("Approved");
                Toast.makeText(ConfirmationAccDetail.this, "Account Approved", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
