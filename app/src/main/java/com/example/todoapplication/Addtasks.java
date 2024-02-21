package com.example.todoapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.Button;

public class Addtasks extends AppCompatActivity {
Button btnschedule,btncancel ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        btnschedule = findViewById(R.id.Set);
        btncancel = findViewById(R.id.cancel);

        btnschedule.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.grey));
        btncancel.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.grey));




    }
}