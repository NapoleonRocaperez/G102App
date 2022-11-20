package com.example.g102app.utils;

import android.content.Context;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Date;


public class ImageProvider {
    StorageReference mStorage;

    public ImageProvider(){
        mStorage=FirebaseStorage.getInstance().getReference();
    }

    public UploadTask save(Context context, File file){
        /* arreglo */
        byte [] imageByte= CompressorBitmapImage.getImage(context,file.getPath(), 500,500);
        StorageReference  storage=FirebaseStorage.getInstance().getReference().child(new Date()+".jpg");
        UploadTask task=storage.putBytes(imageByte);
        mStorage=storage;
        return task;

    }

    public  StorageReference getStorage(){
        return mStorage;
    }

}
