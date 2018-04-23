package com.example.gerrys.charperone;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gerrys.charperone.Model.Promo;
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
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

public class addPromo extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    MaterialEditText Id, Name, Value,Description, Code;
    private Button mButtonChooseImage;
    Button btnSignUp;
    private Uri mImageUri;
    private StorageTask mUploadTask;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    String mercId = " ";
    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);

        mButtonChooseImage = (Button) findViewById(R.id.button_choose_image);
        Id = (MaterialEditText) findViewById(R.id.Id);
        Name = (MaterialEditText) findViewById(R.id.promoName);
        Code = (MaterialEditText) findViewById(R.id.promoCode);
        Value = (MaterialEditText) findViewById(R.id.Value);
        Description = (MaterialEditText) findViewById(R.id.decription);
        btnSignUp = (Button)findViewById(R.id.btnAddPromo);
        mImageView = (ImageView) findViewById(R.id.image_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        // I
        // nitialize firebase
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("Promos");
        mercId =  getIntent().getStringExtra("merch");
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  final ProgressDialog mDialog = new ProgressDialog(addProduct.this);
                // mDialog.setMessage("loading...");
                //  mDialog.show();
                if (mImageUri != null) {
                    StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                            + "." + getFileExtension(mImageUri));

                    mUploadTask = fileReference.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mProgressBar.setProgress(0);
                                        }
                                    }, 1000);
                                    Toast.makeText(addPromo.this, "Upload successful", Toast.LENGTH_SHORT).show();
                                    table_user.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            // Check if already user phone
                                            if (dataSnapshot.child(Id.getText().toString()).exists()) {
                                                // mDialog.dismiss();

                                                //Toast.makeText(addProduct.this, "Account already exist!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                //mDialog.dismiss();

                                                Promo promos = new Promo(Id.getText().toString(), taskSnapshot.getDownloadUrl().toString(),Name.getText().toString() , Description.getText().toString(), Value.getText().toString(), Code.getText().toString(), "Available","http://test.uphbusinessweek.com/");
                                                table_user.child(Id.getText().toString()).setValue(promos);
                                                // Toast.makeText(addProduct.this, "Account successfully created!", Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(addPromo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(addPromo.this, "No file selected", Toast.LENGTH_SHORT).show();
                }



            }
        });
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(mImageView);
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
