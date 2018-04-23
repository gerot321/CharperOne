package com.example.gerrys.charperone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerrys.charperone.Model.Category;
import com.example.gerrys.charperone.Model.Confirmation;
import com.example.gerrys.charperone.Model.productRequest;
import com.example.gerrys.charperone.ViewHolder.ConfirmationViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class ConfirmationKurirDetail extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference confirm,request,prodReq,prod,merchant;
    String ID;
    productRequest prodss;
    FirebaseRecyclerAdapter<Confirmation, ConfirmationViewHolder> adapter;
    TextView reqId,prodId,prodName,prodQty,prodAddrs,prodPrice;
    ImageView image;
    CardView ConfirmButton;
    public final static int QRcodeWidth = 500 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_kurir_detail);
        ID = getIntent().getStringExtra("ConfirmationId");
        reqId = (TextView)findViewById(R.id.reqId);
        prodId = (TextView)findViewById(R.id.prodId);
        prodName = (TextView)findViewById(R.id.prodName);
        prodQty = (TextView)findViewById(R.id.prodQty);
        prodAddrs = (TextView)findViewById(R.id.addrs);
        prodPrice = (TextView)findViewById(R.id.prodPrice);
        Bitmap bitmap ;
        image = (ImageView)findViewById(R.id.qrcode);

        ConfirmButton = (CardView)findViewById(R.id.confirmPayment);

        database = FirebaseDatabase.getInstance();
        confirm = database.getReference("Confirmation");
        request = database.getReference("Requests");
        merchant = database.getReference("Category");
        prodReq = database.getReference("productReq");
        prod = database.getReference("Product");
        try {
            bitmap = TextToImageEncode(ID);

            image.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }

        prodReq.child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                prodss = dataSnapshot.getValue(productRequest.class);
                reqId.setText("Request ID : "+prodss.getRequestid());
                prodId.setText("Produk ID : "+prodss.getProductid());
                prodName.setText("Nama Produk : "+prodss.getProductname());
                prodQty.setText("Jumlah Pesanan : "+prodss.getQuantity());
                prodAddrs.setText("Alamat Pengiriman : "+prodss.getAddress());
                prodPrice.setText("Total Biaya : "+prodss.getTotalprice());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        prodReq.child(ID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                prodReq.child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final productRequest prod = dataSnapshot.getValue(productRequest.class);
                        merchant.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Category cate = dataSnapshot.getValue(Category.class);
                                merchant.child(prod.getMerchantid()).child("saldo").setValue
                                        (String.valueOf(Integer.valueOf(prod.getTotalprice().toString()))+Integer.valueOf(cate.getSaldo()));
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
                Toast.makeText(ConfirmationKurirDetail.this, "Shipping Order Has Been Confirmed by Costumer" , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ConfirmationKurirDetail.this,ConfirmationCourier.class);
                startActivity(intent);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
/*        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prodReq.child(ID).child("status").setValue("Order telah sampai pada tujuan");
                prodReq.child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        productRequest prod = dataSnapshot.getValue(productRequest.class);
                        merchant.child(prod.getMerchantid()).child("saldo").setValue(prod.getTotalprice().toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                Intent intent = new Intent(ConfirmationKurirDetail.this,ConfirmationAdmin.class);
                startActivity(intent);
            }
        });*/
    }

    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRCodeBlackColor):getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
