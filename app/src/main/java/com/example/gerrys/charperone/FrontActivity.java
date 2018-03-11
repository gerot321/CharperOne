package com.example.gerrys.charperone;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FrontActivity extends AppCompatActivity {

    Button btnSignUp, btnSignIn;
    TextView txtSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);



        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontActivity.this, SignIn.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontActivity.this, SignUp.class);
                startActivity(intent);
            }
        });

    }}