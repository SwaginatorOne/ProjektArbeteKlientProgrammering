package com.example.officespace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity
{
    private static final String TAG="TAG";
    EditText username_register;
    EditText fullName_register;
    EditText password_register;
    EditText email_register;
    EditText phoneNumber_register;
    Button buttonRegister_register;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBarRegister;
    FirebaseFirestore fireStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username_register = findViewById(R.id.editText_username_register);
        fullName_register = findViewById(R.id.editText_fullName_register);
        password_register = findViewById(R.id.editText_Password_register);
        email_register = findViewById(R.id.editText_email_register);
        phoneNumber_register = findViewById(R.id.editText_phoneNumber_register);
        buttonRegister_register = findViewById(R.id.button_register);

        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        progressBarRegister = findViewById(R.id.progressBar_register);

        progressBarRegister.setVisibility(View.INVISIBLE);

        if(firebaseAuth.getCurrentUser() != null)
        {
            Toast.makeText(this, "User already logged in", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }


        buttonRegister_register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email = email_register.getText().toString().trim();
                String password = password_register.getText().toString().trim();
                String username = username_register.getText().toString();
                String fullName = fullName_register.getText().toString();
                String phoneNumber = phoneNumber_register.getText().toString();

                if(TextUtils.isEmpty(email))
                {
                    email_register.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password))
                {
                    password_register.setError("Password is required");
                    return;
                }

                if(password.length() < 6)
                {
                    password_register.setError("Password must be 6 characters or longer");
                    return;
                }

                progressBarRegister.setVisibility(View.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(SignUpActivity.this, "User created", Toast.LENGTH_LONG).show();
                            userID = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fireStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("username",username);
                            user.put("email", email);
                            user.put("phoneNumber",phoneNumber);
                            user.put("fullName", fullName);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                                public void onSuccess(Void aVoid)
                                {
                                    Log.d(TAG, "onSuccess: user profile is created for " + userID );
                                }
                            });
                            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(SignUpActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBarRegister.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}
