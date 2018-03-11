package com.example.gerrys.charperone.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gerrys.charperone.Model.Product;
import com.example.gerrys.charperone.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class addProduct extends Fragment {

    MaterialEditText Id, Name, Price,Description;
    Button btnSignUp;
    String mercId = " ";
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_add, container, false);
        Id = (MaterialEditText)v.findViewById(R.id.Id);
        Name = (MaterialEditText)v.findViewById(R.id.nameProduct);
        Price = (MaterialEditText)v.findViewById(R.id.Price);
        Description = (MaterialEditText)v.findViewById(R.id.decription);
        btnSignUp = (Button) v.findViewById(R.id.btnSignUp);

        // I
        // nitialize firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("Product");
        //mercId = getIntent().getStringExtra("MerchantId");
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  final ProgressDialog mDialog = new ProgressDialog(addProduct.this);
               // mDialog.setMessage("loading...");
              //  mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Check if already user phone
                        if(dataSnapshot.child(Id.getText().toString()).exists()){
                           // mDialog.dismiss();

                            //Toast.makeText(addProduct.this, "Account already exist!", Toast.LENGTH_SHORT).show();
                        }else {
                            //mDialog.dismiss();

                            Product products = new Product(Id.getText().toString(), Name.getText().toString(),"https://cdn1.imggmi.com/uploads/2018/2/13/e31b558a76c14c47424518e021e78e4c-full.png", Description.getText().toString(),Price.getText().toString(),"0",mercId);
                            table_user.child(Id.getText().toString()).setValue(products);
                           // Toast.makeText(addProduct.this, "Account successfully created!", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        return v;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
