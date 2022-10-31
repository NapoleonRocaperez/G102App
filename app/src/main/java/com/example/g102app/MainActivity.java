package com.example.g102app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextView mTextViewRegister;
    TextInputEditText mtextInputEmail;
    TextInputEditText mtextInputPassword;
    Button mButtonLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewRegister=findViewById(R.id.TextViewRegister);
        mtextInputEmail=findViewById(R.id.textInputEmail);
        mtextInputPassword=findViewById(R.id.textImputPassword);
        mButtonLogin=findViewById(R.id.btnlogin);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Register_Activity.class);
                startActivity(intent);
            }
        });

    }

    private void login() {
        String email=mtextInputEmail.getText().toString();
        String password=mtextInputPassword.getText().toString();

        Log.d("Campo","email"+email);
        Log.d("Campo", "password"+password);

    }
}