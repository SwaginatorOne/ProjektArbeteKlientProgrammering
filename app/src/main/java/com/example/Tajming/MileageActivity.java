package com.example.Tajming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Tajming.java.Milage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MileageActivity extends AppCompatActivity
{
    SimpleDateFormat dayFormat = new SimpleDateFormat("E, dd MMM", Locale.ENGLISH);
    Calendar calendar = Calendar.getInstance();
    Date date = new Date();
    TextView textView_current_day_mileage;
    EditText startLocation;
    EditText endLocation;
    EditText regNum;
    EditText kilometer;
    Button registerButton;

    String start;
    String end;
    String reg;
    String km;
    String userID;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mileage);
        textView_current_day_mileage = findViewById(R.id.textView_current_day_mileage);
        String current_day = dayFormat.format(date);
        textView_current_day_mileage.setText(current_day);

        startLocation = findViewById(R.id.editText_start_address_mileage);
        endLocation = findViewById(R.id.editText_stop_address_mileage);
        regNum = findViewById(R.id.editText_car_registration_number);
        kilometer = findViewById(R.id.editText_distance);

        registerButton = findViewById(R.id.button_mileage_save);

        userID = firebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startLocation.getText().toString().isEmpty()
                        || endLocation.getText().toString().isEmpty()
                        || regNum.getText().toString().isEmpty()
                        || kilometer.getText().toString().isEmpty())
                {
                    Toast.makeText(MileageActivity.this, "One or more fields are empty", Toast.LENGTH_LONG).show();
                    return;
                }if (!isInteger(kilometer.getText().toString())){
                    Toast.makeText(MileageActivity.this, "Distance need to be a numeric value", Toast.LENGTH_LONG).show();
                    return;
                }
                    start = startLocation.getText().toString();
                    end = endLocation.getText().toString();
                    reg = regNum.getText().toString();
                    km = kilometer.getText().toString();
                    Milage milage = new Milage(userID, date, start, end, reg, Integer.parseInt(km));
                    addMilageToDb(milage);
            }
        });

    }
    public boolean isInteger(String string) {
        try {
            int x = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public void addMilageToDb(Milage milage){
        Map<String, Object> mil = new HashMap<String, Object>();
        mil.put("date", milage.getDate());
        mil.put("start_location", milage.getStartLocation());
        mil.put("end_location", milage.getEndLocation());
        mil.put("reg_number", milage.getRegNumber());
        mil.put("kilometer", milage.getKilometer());
        mil.put("user", userID);

        db.collection("milages")
                .add(mil)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG","DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }
}