package com.example.Tajming.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Tajming.R;
import com.example.Tajming.java.WorkShift;
import com.example.Tajming.java.WorkShiftManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;


public class MonthFragment extends Fragment
{
    private FirebaseFirestore db;
    private CollectionReference ref;
    private FirebaseAuth firebaseAuth;

    private String monthTime;
    private String breakTime;

    private String userID;
    private TextView totalTimeMonth;
    private TextView totalBreakMonth;
    private HorizontalCalendar horizontalCalendar;
    WorkShiftManager workShiftManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_month, container, false);

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -2);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 2);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        ref  = db.collection("work_shifts");
        userID = firebaseAuth.getCurrentUser().getUid();
        totalTimeMonth = rootView.findViewById(R.id.textField_hours_worked_report_show_month);
        totalBreakMonth = rootView.findViewById(R.id.textField_break_taken_report_show_month);
        workShiftManager = new WorkShiftManager();
        List<WorkShift> ws = new ArrayList<WorkShift>();

        db.collection("work_shifts")
               .whereEqualTo("user", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshots : task.getResult()) {
                                Log.d("TAG", documentSnapshots.getId() + " => " + documentSnapshots.getData());
                                String user = documentSnapshots.getString("user");
                                String date = documentSnapshots.get("date").toString();
                                String start = documentSnapshots.getString("start_time");
                                String end = documentSnapshots.getString("end_time");
                                String breakTime = documentSnapshots.getString("break");

                                WorkShift workShift = new WorkShift(user, date,
                                        start, end, breakTime);
                                ws.add(workShift);
                                workShiftManager  = new WorkShiftManager(ws);

                            }
                            LocalDate date = LocalDate.now();
                            monthTime = workShiftManager.getTotalTimeMonth(date.getMonthValue());
                            breakTime = workShiftManager.getTotalBreakTimeMonth(date.getMonthValue());
                            totalTimeMonth.setText(monthTime);
                            totalBreakMonth.setText(breakTime);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });


        horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.calendarViewMonth)
                .range(startDate,endDate)
                .datesNumberOnScreen(3)
                .mode(HorizontalCalendar.Mode.MONTHS)
                .configure()
                .formatMiddleText("MMM")
                .formatBottomText("yyyy")
                .showTopText(false)
                .showBottomText(true)
                .end()
                .build();



        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSelected(Calendar date, int position)
            {
                List<WorkShift> list = new ArrayList<WorkShift>();
                db.collection("work_shifts")
                        .whereEqualTo("user", userID)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot documentSnapshots : task.getResult()) {
                                        Log.d("TAG", documentSnapshots.getId() + " => " + documentSnapshots.getData());
                                        String user = documentSnapshots.getString("user");
                                        String date = documentSnapshots.get("date").toString();
                                        String start = documentSnapshots.getString("start_time");
                                        String end = documentSnapshots.getString("end_time");
                                        String breakTime = documentSnapshots.getString("break");

                                        WorkShift workShift = new WorkShift(user, date,
                                                start, end, breakTime);
                                        list.add(workShift);
                                        workShiftManager  = new WorkShiftManager(list);

                                    }
                                    int monthValue = date.get(Calendar.MONTH) +1;
                                    monthTime = workShiftManager.getTotalTimeMonth(monthValue);
                                    breakTime = workShiftManager.getTotalBreakTimeMonth(monthValue);
                                    totalTimeMonth.setText(monthTime);
                                    totalBreakMonth.setText(breakTime);
                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }

        });
        return rootView;
    }
}