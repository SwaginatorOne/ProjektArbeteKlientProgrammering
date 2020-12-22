package com.example.Tajming;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileManagerActivity extends AppCompatActivity
{

    TextView profile_username;
    TextView profile_phone_number;
    TextView profile_email;
    TextView profile_full_name;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    String userID;
    Button button_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_manager);

        profile_username = findViewById(R.id.textField_username_profile_manager);
        profile_phone_number = findViewById(R.id.textField_phone_number_profile_manager);
        profile_email = findViewById(R.id.textField_email_profile_manager);
        profile_full_name = findViewById(R.id.textField_full_name_profile_manager);
        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        button_logout = findViewById(R.id.button_logout_profile_manager);

        userID = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fireStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>()
        {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error)
            {
                profile_full_name.setText(documentSnapshot.getString("fullName"));
                profile_email.setText(documentSnapshot.getString("email"));
                profile_username.setText(documentSnapshot.getString("username"));
                profile_phone_number.setText(documentSnapshot.getString("phoneNumber"));
            }
        });

        button_logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileManagerActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}