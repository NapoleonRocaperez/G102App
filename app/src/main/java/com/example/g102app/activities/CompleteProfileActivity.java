package com.example.g102app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CompleteProfileActivity extends AppCompatActivity {
    TextInputEditText mTextInputEditTextUsername;
    Button mButtonConfirmar;
    UsersProviders mUserProvider;
    AuthProviders mAuthProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        mTextInputEditTextUsername=findViewById(R.id.textImputUsernameC);
        mButtonConfirmar=findViewById(R.id.btnConfirmar);

        mAuthProvider=new AuthProviders();
        mUserProvider=new UsersProviders();

        mButtonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }
    private void register() {
        String username=mTextInputEditTextUsername.getText().toString();

        if (!username.isEmpty()){
                updateUser(username);
            }else {
                Toast.makeText(this, "para continuar inserta todos los campos", Toast.LENGTH_SHORT).show();
            }
    }

    private void updateUser(final String username) {

                    String id=mAuthProvider.getUid();
                    User user=new User();
                    user.setUsername(username);
                    user.setId(id);


                    mUserProvider.update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent =new Intent(CompleteProfileActivity.this,HomeActivity.class);
                                startActivity(intent);
                                Toast.makeText(CompleteProfileActivity.this, "El usuario se almaceno correctamente", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(CompleteProfileActivity.this, "no se pudo almacenar en la base de datos", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


    }

}