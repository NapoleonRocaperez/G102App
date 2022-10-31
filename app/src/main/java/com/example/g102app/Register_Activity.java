package com.example.g102app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register_Activity extends AppCompatActivity {

    CircleImageView mCircleImageView;
    TextInputEditText mTexInputUsername;
    TextInputEditText mTexInputEmail;
    TextInputEditText mTexInputPassword;
    TextInputEditText mTexInputConfirmPassword;
    Button mButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mCircleImageView=findViewById(R.id.circleimageback);
        mTexInputUsername=findViewById(R.id.textImputUsername);
        mTexInputPassword=findViewById(R.id.textImputPassword);
        mTexInputConfirmPassword=findViewById(R.id.textImputPassword);
        mButtonRegister=findViewById(R.id.btnregister);
        mTexInputEmail=findViewById(R.id.textInputEmail);

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }

        });

        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void register() {

        String username=mTexInputUsername.getText().toString();
        String email=mTexInputEmail.getText().toString();
        String password=mTexInputPassword.getText().toString();
        String confirmpassword=mTexInputConfirmPassword.getText().toString();

        if(!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmpassword.isEmpty()){

            if (isEmailValid(email)){
                Toast.makeText(this,"insterto todos los campos  y el email es valido",Toast.LENGTH_SHORT).show();
            }

            else {
                Toast.makeText(this,"insterto todos los campos pero el email no es valido",Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(this,"para continuar insterta todos los campos",Toast.LENGTH_SHORT).show();
        }
    }



    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}