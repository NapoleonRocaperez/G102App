package com.example.g102app.providers;

import com.example.g102app.models.Post;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;

import java.util.Collection;

public class PostProviders {
    CollectionReference mCollection;

    public PostProviders() {
    }
    public Task<Void> save(Post post){
        return  mCollection.document().set(post);
    }
}
