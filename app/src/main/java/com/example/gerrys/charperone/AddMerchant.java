package com.example.gerrys.charperone;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gerrys.charperone.Model.Category;
import com.example.gerrys.charperone.Model.ReqAcc;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;

public class AddMerchant extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_PDF_CODE = 2342;
    MaterialEditText Phone, Name, Pass, Gender, Address, Email;
    private Button ktpImg, fileDoc;
    Button signUp;
    ReqAcc prodss;
    private Uri ktpImageUri, docUri;
    private StorageTask ktpUploadTask, docUploadTask;
    private ImageView kImageView;
    String mercId = " ";
    private StorageReference signUpStorageRef;
    private DatabaseReference signUpDatabase, approvedDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_merchant);
        Gender = (MaterialEditText)findViewById(R.id.etSaldo);
        Address = (MaterialEditText)findViewById(R.id.etOrigin);
        ktpImg = (Button) findViewById(R.id.button_Banner);
        Phone = (MaterialEditText) findViewById(R.id.etPhone);
        Name = (MaterialEditText) findViewById(R.id.etName);
        Pass = (MaterialEditText) findViewById(R.id.etPassword);
        kImageView = (ImageView) findViewById(R.id.image_view);
        signUp = (Button)findViewById(R.id.signUp);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mercId = getIntent().getStringExtra("ID");
        approvedDatabase = database.getReference("ReqAcc");
        approvedDatabase.child(mercId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                prodss = dataSnapshot.getValue(ReqAcc.class);

                Name.setText(prodss.getName());
                Pass.setText(prodss.getPass());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        signUpStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        signUpDatabase = database.getReference("Category");
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  final ProgressDialog mDialog = new ProgressDialog(addProduct.this);
                // mDialog.setMessage("loading...");
                //  mDialog.show();
                if (ktpImageUri != null) {
                    StorageReference imgReference = signUpStorageRef.child(System.currentTimeMillis()
                            + "." + getFileExtension(ktpImageUri));
                    ktpUploadTask = imgReference.putFile(ktpImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(AddMerchant.this, "Upload successful", Toast.LENGTH_SHORT).show();
                                    signUpDatabase.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            // Check if already user phone
                                            if (dataSnapshot.child(Phone.getText().toString()).exists()) {
                                                // mDialog.dismiss();

                                                //Toast.makeText(AddMerchant.this, "Account already exist!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                //mDialog.dismiss();

                                                Category category = new Category(Name.getText().toString(), taskSnapshot.getDownloadUrl().toString(),Address.getText().toString(), Pass.getText().toString(), Gender.getText().toString());
                                                signUpDatabase.child(Phone.getText().toString()).setValue(category);
                                                // Toast.makeText(addProduct.this, "Account successfully created!", Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }
                            });

                } else {

                    Toast.makeText(AddMerchant.this, "No file selected", Toast.LENGTH_SHORT).show();
                }



            }
        });
        ktpImg.setOnClickListener(new View.OnClickListener() {
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
            ktpImageUri = data.getData();
        }

    }



    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}