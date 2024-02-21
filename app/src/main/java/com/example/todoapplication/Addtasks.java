package com.example.todoapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.content.IntentFilter;
import com.example.todoapplication.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Addtasks extends AppCompatActivity {

    private EditText taskNameEditText;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private RadioGroup radioGroupOptions;
    private CheckBox urgentCheckBox,setalert;
    private Button btnSave, btnCancel;

    private DatabaseReference tasksRef;
    private static final String ALERT_BROADCAST = "com.example.todoapplication.ALERT_BROADCAST";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Initialize Firebase Database reference
        tasksRef = FirebaseDatabase.getInstance().getReference("tasks");

        // Initialize UI elements
        taskNameEditText = findViewById(R.id.editTaskName);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        radioGroupOptions = findViewById(R.id.radioGroupOptions);
        urgentCheckBox = findViewById(R.id.urgent);
        btnSave = findViewById(R.id.Set);
        btnCancel = findViewById(R.id.cancel);
        setalert = findViewById(R.id.setALERT);


        btnSave.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.grey));
        btnCancel.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.grey));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTaskToFirebase();
                if(setalert.isChecked())
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        setAlert();
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Addtasks.this, MainActivity.class));
            }
        });
    }

    private void saveTaskToFirebase() {
        // Retrieve user inputs
        String taskName = taskNameEditText.getText().toString();
        int year = datePicker.getYear();
        int month = datePicker.getMonth() + 1;
        int day = datePicker.getDayOfMonth();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        String dateTime = year + "-" + month + "-" + day + " " + hour + ":" + minute;

        RadioButton selectedRadioButton = findViewById(radioGroupOptions.getCheckedRadioButtonId());
        String taskType = selectedRadioButton.getText().toString();

        boolean isUrgent = urgentCheckBox.isChecked();

        // Get the currently logged-in user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Create a map to store task data
            Map<String, Object> taskData = new HashMap<>();
            taskData.put("taskName", taskName);
            taskData.put("dateTime", dateTime);
            taskData.put("taskType", taskType);
            taskData.put("urgent", isUrgent);

            // Generate a unique key for the task
            String taskId = tasksRef.child(user.getUid()).push().getKey();

            // Save the task data to Firebase Realtime Database under the user's UID
            if (taskId != null) {
                tasksRef.child(user.getUid()).child(taskId).setValue(taskData);
                Toast.makeText(Addtasks.this, "Task saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Addtasks.this, "Failed to save task.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Addtasks.this,"User Unknown!,try log out  and log in again",Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void setAlert() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        // Retrieving user inputs
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        // Calculating the time 10 minutes before the selected time
        long alertTime = calculateAlertTime(year, month, day, hour, minute);

        // an intent for the alert broadcast
        Intent alertIntent = new Intent(ALERT_BROADCAST);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alertIntent, PendingIntent.FLAG_IMMUTABLE);

        // Setting the alarm
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, pendingIntent);
        }

        // Registering the broadcast receiver to handle the alert
        registerReceiver(new AlertReceiver(), new IntentFilter(ALERT_BROADCAST), null, null, Context.RECEIVER_NOT_EXPORTED);
    }

    private long calculateAlertTime(int year, int month, int day, int hour, int minute) {
        // time in milliseconds
        long eventTime = System.currentTimeMillis() + ((long) minute * 60 * 1000) + ((long) hour * 60 * 60 * 1000) +
                ((long) day * 24 * 60 * 60 * 1000) + ((long) month * 30 * 24 * 60 * 60 * 1000) + ((long) year * 365 * 24 * 60 * 60 * 1000);

        // -10 minutes for the alert time
        long alertTime = eventTime - (10 * 60 * 1000);

        return alertTime;
    }




}
