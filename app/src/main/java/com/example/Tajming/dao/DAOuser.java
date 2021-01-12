package com.example.Tajming.dao;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.Tajming.java.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.Executor;

public class DAOuser {

    private static DAOuser instance = null;

    private DAOuser()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
    }

    public static DAOuser getInstance()
    {
        if (instance == null) {
            instance = new DAOuser();
        }
        return instance;
    }

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    String userID;

    public boolean createUser(User user, String password){
        final boolean[] isCreated = new boolean[1];
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    userID = firebaseAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = db.collection("users").document(userID);
                    documentReference.set(user.getHashMap()).addOnSuccessListener(new OnSuccessListener<Void>()
                    {
                        @Override
                        public void onSuccess(Void aVoid)
                        {
                            Log.d("TAG", "onSuccess: user profile is created for " + userID );
                        }
                    });
                    isCreated[0] = true;
                }
                else {
                    isCreated[0] = false;
                }
            }
        });
        return isCreated[0];
    }

    public User getUser(){
        User user = new User();
        DocumentReference documentReference = db.collection("users").document(userID);
        documentReference.addSnapshotListener((Executor) this, new EventListener<DocumentSnapshot>()
        {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error)
            {
                user.setFullName(documentSnapshot.getString("fullName"));
                user.setEmail(documentSnapshot.getString("email"));
                user.setUsername(documentSnapshot.getString("username"));
                user.setPhoneNumber(documentSnapshot.getString("phoneNumber"));
            }
        });
        return user;
    }

    public void editUserInDatabase(User user){
        DocumentReference documentReference = db.collection("user").document(userID);
        documentReference.update(user.getHashMap());
    }

    public String getUserID(){
        return userID;
    }
}
