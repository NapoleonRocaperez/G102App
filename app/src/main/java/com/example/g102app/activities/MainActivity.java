package com.example.g102app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g102app.R;
import com.example.g102app.models.User;
import com.example.g102app.providers.AuthProviders;
import com.example.g102app.providers.UsersProviders;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;


public class MainActivity extends AppCompatActivity {

    TextView mTextViewRegister;
    TextInputEditText mtextInputEmail;
    TextInputEditText mtextInputPassword;
    Button mButtonLogin;
    AuthProviders mAuthProvider;
    UsersProviders mUserProvider;
    private GoogleSignInClient mGoogleSignInClient;
    private final int REQUEST_CODE_GOOGLE=1;
    SignInButton mSignInButtonGooogle;
    AlertDialog mDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewRegister = findViewById(R.id.TextViewRegister);
        mtextInputEmail = findViewById(R.id.textInputEmail);
        mtextInputPassword = findViewById(R.id.textImputPassword);
        mButtonLogin = findViewById(R.id.btnlogin);
        mSignInButtonGooogle = findViewById(R.id.btnloginGoogle);


        mAuthProvider = new AuthProviders();
        mUserProvider = new UsersProviders();

        mDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un momento...")
                .setCancelable(false).build();


        mSignInButtonGooogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
    private void signInGoogle () {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == REQUEST_CODE_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("ERROR", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        mDialog.show();
        mAuthProvider.googlelogin(account)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String id=mAuthProvider.getUid();
                            checkUserExist(id);
                            // Sign in success, update UI with the signed-in user's information


                        } else {
                            mDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Log.w("ERROR", "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }

    private void checkUserExist(final String id) {
        mUserProvider.getUser(id).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    mDialog.dismiss();
                    Intent intent =new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent);
                }else {
                    String email= mAuthProvider.getEmail();
                    User user=new User();
                    user.setEmail(email);
                    user.setId(id);

                    mUserProvider.create(user)
                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mDialog.dismiss();
                            if (task.isSuccessful()){
                                Intent intent =new Intent(MainActivity.this,CompleteProfileActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(MainActivity.this, "No  se pudo almacenar en la base de datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    // [END auth_with_google]

    private void login() {
        String email=mtextInputEmail.getText().toString();
        String password=mtextInputPassword.getText().toString();
        mDialog.show();
        mAuthProvider.login(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mDialog.dismiss();
                    Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "el email y contraseña no son correctas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Log.d("Campo","email"+email);
        Log.d("Campo", "password"+password);

    }
}