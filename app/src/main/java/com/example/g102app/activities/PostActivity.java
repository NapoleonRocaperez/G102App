package com.example.g102app.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g102app.R;
import com.example.g102app.models.Post;
import com.example.g102app.providers.AuthProviders;
import com.example.g102app.providers.PostProviders;
import com.example.g102app.utils.FileUtil;
import com.example.g102app.utils.ImageProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import dmax.dialog.SpotsDialog;

public class PostActivity extends AppCompatActivity {

    ImageView mImageViewPost1;
    ImageView mImageViewPost2;
    File mImageFile;
    File mImageFile2;
    private final  int Gallery_REQUEST_CODE=1;
    private final  int Gallery_REQUEST_CODE_2=2;
    Button mButtonPost;
    ImageProvider mImageProvider;
    TextInputEditText mTextInputTitle;
    TextInputEditText mTextInputDescription;
    ImageView mImageViewPC;
    ImageView mImageViewPS5;
    ImageView mImageViewXBOX;
    ImageView mImageViewNitendo;
    TextView mTextViewCategory;
    String mCategory="";
    PostProviders mPostProvider;
    String mTitle="";
    String mDescription="";
    AuthProviders mAuthProviders;
    AlertDialog mDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mButtonPost=findViewById(R.id.btnPost);
        mImageViewPost1=findViewById(R.id.imageViewPost1);
        mImageViewPost2=findViewById(R.id.imageViewPost2);

        mTextInputTitle = findViewById(R.id.textImputVideoGame);
        mTextInputDescription = findViewById(R.id.textImputDescription);
        mImageViewPC = findViewById(R.id.imageViewPC);
        mImageViewPS5 = findViewById(R.id.imageViewPS5);
        mImageViewXBOX = findViewById(R.id.imageViewXbox);
        mImageViewNitendo = findViewById(R.id.imageViewNintendo);
        mTextViewCategory = findViewById(R.id.textViewCategory);


        mImageProvider=new ImageProvider();
        mPostProvider=new PostProviders();
        mAuthProviders=new AuthProviders();

        mDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un momento...")
                .setCancelable(false).build();

        mButtonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPost();

            }
        });
        mImageViewPost1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(Gallery_REQUEST_CODE);
            }
        });

        mImageViewPost2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(Gallery_REQUEST_CODE_2);
            }
        });

        mImageViewPC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory="PC";
                mTextViewCategory.setText(mCategory);
            }
        });

        mImageViewPS5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory="PS5";
                mTextViewCategory.setText(mCategory);
            }
        });

        mImageViewXBOX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory="XBOX";
                mTextViewCategory.setText(mCategory);

            }
        });

        mImageViewNitendo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory="NINTENDO";
                mTextViewCategory.setText(mCategory);
            }
        });
    }



    private void clickPost() {
        mTitle=mTextInputTitle.getText().toString();
        mDescription=mTextInputDescription.getText().toString();
        if(!mTitle.isEmpty() && !mDescription.isEmpty() && !mCategory.isEmpty()){
            if(mImageFile !=null){
                saveImage();
            }else {
                Toast.makeText(this, "seleccione una imagen", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Completa los campos para publicar", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImage() {
        mDialog.show();
       mImageProvider.save(PostActivity.this,mImageFile).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
               if(task.isSuccessful()){
                   mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                       @Override
                       public void onSuccess(Uri uri) {
                          final String url= uri.toString();

                           mImageProvider.save(PostActivity.this,mImageFile2).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                               @Override
                               public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> taskImage2) {
                                   if (taskImage2.isSuccessful()){
                                       mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                           @Override
                                           public void onSuccess(Uri uri2) {
                                               String url2=uri2.toString();
                                               Post post=new Post();
                                               post.setImage1(url);
                                               post.setImage2(url2);
                                               post.setTitle(mTitle);
                                               post.setDescription(mDescription);
                                               post.setCategoria(mCategory);
                                               post.setIdUser(mAuthProviders.getUid());

                                               mPostProvider.save(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<Void> tasksave) {
                                                       mDialog.dismiss();
                                                       if(tasksave.isSuccessful()){
                                                           clearForm();
                                                           Toast.makeText(PostActivity.this, "La informacion se almaceno correctamente", Toast.LENGTH_SHORT).show();
                                                       }else {
                                                           Toast.makeText(PostActivity.this, "No se pudo almacenar la informacion", Toast.LENGTH_SHORT).show();
                                                       }
                                                   }
                                               });

                                           }

                                       });
                                   }else {
                                       mDialog.dismiss();
                                       Toast.makeText(PostActivity.this,"Error al almacenar la imagen 2",Toast.LENGTH_LONG).show();
                                   }

                               }
                           });

                       }
                   });
                   Toast.makeText(PostActivity.this, "la imagen se almaceno correctamente", Toast.LENGTH_SHORT).show();
               }else {
                   mDialog.dismiss();
                   Toast.makeText(PostActivity.this,"Error al almacenar la imagen",Toast.LENGTH_LONG).show();
               }
           }
       });
    }

    private void clearForm() {
        mTextInputTitle.setText("");
        mTextInputDescription.setText("");
        mTextViewCategory.setText("CATEGORIAS");
        mImageViewPost1.setImageResource(R.drawable.ic_subir_foto);
        mImageViewPost2.setImageResource(R.drawable.ic_subir_foto);
        mTitle = "";
        mDescription = "";
        mCategory = "";
        mImageFile = null;
        mImageFile2 = null;
    }

    private void openGallery(int requestCode) {
        Intent galleryIntent =new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,requestCode); /*un numero entero que nos va ejecutar un */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /* VALIDACION DE IMAGEN CON GALERIA */
        if (requestCode == Gallery_REQUEST_CODE && resultCode == RESULT_OK) {
            try {

                mImageFile = FileUtil.from(this, data.getData());
                mImageViewPost1.setImageBitmap(BitmapFactory.decodeFile(mImageFile.getAbsolutePath()));
            } catch(Exception e) {
                Log.d("ERROR", "Se produjo un error " + e.getMessage());
                Toast.makeText(this, "Se produjo un error " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        if (requestCode == Gallery_REQUEST_CODE_2 && resultCode == RESULT_OK) {
            try {

                mImageFile2 = FileUtil.from(this, data.getData());
                mImageViewPost2.setImageBitmap(BitmapFactory.decodeFile(mImageFile2.getAbsolutePath()));
            } catch(Exception e) {
                Log.d("ERROR", "Se produjo un error " + e.getMessage());
                Toast.makeText(this, "Se produjo un error " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

  }
}