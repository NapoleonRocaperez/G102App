package com.example.g102app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.g102app.R;
import com.example.g102app.models.User;
import com.example.g102app.providers.AuthProviders;
import com.example.g102app.providers.UsersProviders;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    CircleImageView mCircleImageView;
    TextInputEditText mTexInputUsername;
    TextInputEditText mTexInputEmail;
    TextInputEditText mTexInputPassword;
    TextInputEditText mTexInputConfirmPassword;
    Button mButtonRegister;
    AuthProviders mAutProvider;
    UsersProviders mUserProviders;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mCircleImageView=findViewById(R.id.circleimageback);
        mTexInputUsername=findViewById(R.id.textImputUsernameR);
        mTexInputPassword=findViewById(R.id.textImputPasswordR);
        mTexInputConfirmPassword=findViewById(R.id.textImputConfirmPassword);
        mButtonRegister=findViewById(R.id.btnregister);
        mTexInputEmail=findViewById(R.id.textInputEmailR);


       mAutProvider=new AuthProviders();
       mUserProviders=new UsersProviders();



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

        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmpassword.isEmpty()){
            if (isEmailValid(email)){
                if(password.equals(confirmpassword)){
                    if(password.length() >=6){
                        createUser(email,password,username);
                    }else {
                        Toast.makeText(this, "Las contraseñas debe tener 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(this, "Has insertado todos los campos pero el correo no es valido", Toast.LENGTH_SHORT).show();
            }


        }else {
            Toast.makeText(this,"para continuar insterta todos los campos",Toast.LENGTH_SHORT).show();
        }

    }

    private void createUser(final String email,String password, final String username) {
        mAutProvider.register(email, password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String id=mAutProvider.getUid();
                    User user=new User();
                    user.setId(id);
                    user.setEmail(email);
                    user.setUsername(username);
                    user.setPassword(password);
                    mUserProviders.create(user)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "El usuario se almaceno correctamente", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(RegisterActivity.this, "no se pudo almacenar en la base de datos", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    Toast.makeText(RegisterActivity.this, "El usuario se registro correctamente", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}