package com.example.gerrys.charperone;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerrys.charperone.Model.Category;
import com.example.gerrys.charperone.Model.Confirmation;
import com.example.gerrys.charperone.Model.ReimburseReq;
import com.example.gerrys.charperone.ViewHolder.ConfirmationViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;

public class ConfirmationReimburseDetail extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    FirebaseDatabase database;
    DatabaseReference reimburseReq,category;
    String ID;
    private StorageTask mUploadTask;
    private ProgressBar mProgressBar;
    FirebaseRecyclerAdapter<Confirmation, ConfirmationViewHolder> adapter;
    TextView merchId,AN,bankID;
    ImageView image;
    CardView ConfirmButton;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_reimburse_detail);
        ID = getIntent().getStringExtra("ConfirmationId");
        merchId = (TextView)findViewById(R.id.Merchant);
        bankID = (TextView) findViewById(R.id.Ovoid);
        AN = (TextView)findViewById(R.id.AN);
        image = (ImageView)findViewById(R.id.bukti);
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        ConfirmButton = (CardView)findViewById(R.id.conf);


        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar21);
        database = FirebaseDatabase.getInstance();
        reimburseReq = database.getReference("ReimbureseReq");
        category = database.getReference("Category");

        reimburseReq.child(ID.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ReimburseReq confirmss = dataSnapshot.getValue(ReimburseReq.class);
                merchId.setText("Merchant ID : "+confirmss.getMerchantId().toString());
                bankID.setText("Bank ID : "+confirmss.getBankId().toString());
                AN.setText("Atas Nama : "+confirmss.getAn().toString());

                //new DownLoadImageTask(image).execute(confirmss.getImage());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mImageUri != null) {
                    StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                            + "." + getFileExtension(mImageUri));

                    mUploadTask = fileReference.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mProgressBar.setProgress(0);
                                        }
                                    }, 0);
                                    Toast.makeText(ConfirmationReimburseDetail.this, "Upload successful", Toast.LENGTH_SHORT).show();
                                    reimburseReq.child(ID).child("image").setValue(taskSnapshot.getDownloadUrl().toString());
                                    reimburseReq.child(ID).child("status").setValue("Transfered");
                                    reimburseReq.child(ID.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            final ReimburseReq req = dataSnapshot.getValue(ReimburseReq.class);
                                            category.child(req.getMerchantId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    Category cate = dataSnapshot.getValue(Category.class);
                                                    category.child(req.getMerchantId()).child("saldo").setValue(String.valueOf(Integer.valueOf(cate.getSaldo())-Integer.valueOf(req.getAmount())));
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    final Intent intent = new Intent(ConfirmationReimburseDetail.this,ConfirmationReimburseMerchant.class);
                                    intent.putExtra("phoneId",ID);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ConfirmationReimburseDetail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    mProgressBar.setProgress((int) progress);
                                }
                            });
                } else {

                    Toast.makeText(ConfirmationReimburseDetail.this, "No file selected", Toast.LENGTH_SHORT).show();
                }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(image);
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
