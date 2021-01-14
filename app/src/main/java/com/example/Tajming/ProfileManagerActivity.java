package com.example.Tajming;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Tajming.main.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class ProfileManagerActivity extends AppCompatActivity
{

    TextView profile_username;
    TextView profile_phone_number;
    TextView profile_email;
    TextView profile_full_name;
    String userID;
    Button button_logout;
    Button button_edit_profile;
    Button button_save_profile;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    FirebaseUser firebaseUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_manager);

        profile_username = findViewById(R.id.editText_username_profile_manager);
        profile_phone_number = findViewById(R.id.editText_phone_number_profile_manager);
        profile_email = findViewById(R.id.editText_email_profile_manager);
        profile_full_name = findViewById(R.id.editText_full_name_profile_manager);
        button_logout = findViewById(R.id.button_logout_profile_manager);
        button_edit_profile = findViewById(R.id.button_edit_profile);
        button_save_profile = findViewById(R.id.button_edit_profile_save);

        profile_username.setFocusable(false);
        profile_phone_number.setFocusable(false);
        profile_email.setFocusable(false);
        profile_full_name.setFocusable(false);
        button_save_profile.setVisibility(View.GONE);

        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        firebaseUserID = firebaseAuth.getCurrentUser();
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

        button_edit_profile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                button_edit_profile.setVisibility(View.GONE);
                button_logout.setVisibility(View.INVISIBLE);
                button_save_profile.setVisibility(View.VISIBLE);
                profile_username.setFocusableInTouchMode(true);
                profile_phone_number.setFocusableInTouchMode(true);
                profile_email.setFocusableInTouchMode(true);
                profile_full_name.setFocusableInTouchMode(true);
            }
        });

        button_save_profile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                button_edit_profile.setVisibility(View.VISIBLE);
                button_logout.setVisibility(View.VISIBLE);
                button_save_profile.setVisibility(View.GONE);
                profile_username.setFocusable(false);
                profile_phone_number.setFocusable(false);
                profile_email.setFocusable(false);
                profile_full_name.setFocusable(false);

                if(profile_full_name.getText().toString().isEmpty()
                        || profile_email.getText().toString().isEmpty()
                        || profile_phone_number.getText().toString().isEmpty()
                        || profile_username.getText().toString().isEmpty())
                {
                    Toast.makeText(ProfileManagerActivity.this, "One or more fields are empty", Toast.LENGTH_LONG).show();
                    return;
                }
                String fullName = profile_full_name.getText().toString();
                String email = profile_email.getText().toString();
                String phoneNumber = profile_phone_number.getText().toString();
                String username = profile_username.getText().toString();

                firebaseUserID.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        DocumentReference documentReferenceEdited = fireStore.collection("users").document(firebaseUserID.getUid());
                        Map<String, Object> edited = new HashMap<>();
                        edited.put("email", email);
                        edited.put("fullName", fullName);
                        edited.put("phoneNumber", phoneNumber);
                        edited.put("username", username);
                        documentReferenceEdited.update(edited).addOnSuccessListener(new OnSuccessListener<Void>()
                        {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                Toast.makeText(ProfileManagerActivity.this, "Profile updated successfully", Toast.LENGTH_LONG).show();
                            }
                        });

                        Toast.makeText(ProfileManagerActivity.this, "Email updated successfully", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(ProfileManagerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}