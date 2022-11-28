package com.example.g102app.providers;

import android.content.Context;

import com.example.g102app.utils.CompressorBitmapImage;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Date;

public class AuthProviders {
    private FirebaseAuth mAuth;

    public AuthProviders(){
        mAuth=FirebaseAuth.getInstance();
    }


    public Task<AuthResult> login(String email, String password){
           return mAuth.signInWithEmailAndPassword(email,password);
    }

    public Task<AuthResult> register(String email, String password){
        return mAuth.createUserWithEmailAndPassword(email,password);
    }

    public Task<AuthResult> googlelogin(GoogleSignInAccount googleSignInAccount){
        AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
        return mAuth.signInWithCredential(credential);
    }

    public String getUid(){
        if (mAuth.getCurrentUser()!=null){
            return  mAuth.getCurrentUser().getUid();
        }else {
            return null;

        }
    }

    public  String getEmail(){
        if (mAuth.getCurrentUser()!=null){
            return mAuth.getCurrentUser().getEmail();
        }else {
            return null;
        }
    }



}
