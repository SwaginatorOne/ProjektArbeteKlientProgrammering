package com.example.Tajming.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Tajming.HomeActivity;
import com.example.Tajming.R;
import com.example.Tajming.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
{
    EditText email_login;
    EditText password_login;
    Button button_login;
    TextView forgot_password;
    TextView new_account;
    ProgressBar progressBar_login;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email_login = findViewById(R.id.editText_email_login_main);
        password_login = findViewById(R.id.editText_Password_main);
        button_login = findViewById(R.id.button_login_main);
        forgot_password = findViewById(R.id.textView_forgot_password_main);
        new_account = findViewById(R.id.textView_sign_up_main);
        progressBar_login = findViewById(R.id.progressBar_main);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar_login.setVisibility(View.INVISIBLE);

        forgot_password.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText passwordReset = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset password");
                passwordResetDialog.setMessage("Enter your email to receive reset password link");
                passwordResetDialog.setView(passwordReset);

                passwordResetDialog.setPositiveButton("Send link", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String mail = passwordReset.getText().toString();
                        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>()
                        {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                Toast.makeText(MainActivity.this, "Reset password link has been sent", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(MainActivity.this, "Error, reset link was not sent " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //Do nothing, only closes the dialog.
                    }
                });
                passwordResetDialog.create().show();
            }
        });

        new_account.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        button_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email = email_login.getText().toString().trim();
                String password = password_login.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    email_login.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password))
                {
                    password_login.setError("Password is required");
                    return;
                }

                if(password.length() < 6)
                {
                    password_login.setError("Password must be 6 characters or longer");
                    return;
                }
                progressBar_login.setVisibility(View.VISIBLE);

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar_login.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser!=null)
        {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            //User is not logged in continue with login routine.
        }
    }
}