package com.example.todoapplication;

import static com.example.todoapplication.ui.login.LoginActivity.saveLoginStatus;
import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todoapplication.TaskAdapter;
import com.example.todoapplication.Task;
import com.example.todoapplication.databinding.ActivityMainBinding;
import com.example.todoapplication.ui.login.LoginActivity;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private RecyclerView    recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private View mainContainer;
    private DatabaseReference tasksRef;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchTasksFromFirebase();



        recyclerView = findViewById(R.id.recyclerViewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Addtasks.class));
            }
        });

    }


    private void addTask(String taskName, String time, boolean isImportant, boolean isWork) {
        Task task = new Task(taskName, time, isImportant, isWork);
        taskList.add(task);
        taskAdapter.notifyDataSetChanged();
    }


    private void fetchTasksFromFirebase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        tasksRef = FirebaseDatabase.getInstance().getReference("tasks");
        assert user != null;
        Toast.makeText(MainActivity.this, "Authenticated User: " + user.getUid() + " - " + user.getEmail(), Toast.LENGTH_SHORT).show();


        if (user != null) {
            tasksRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Task> fetchedTasks = new ArrayList<>();

                    for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                        // Additional loop for nested task IDs
                        for (DataSnapshot nestedTaskSnapshot : taskSnapshot.getChildren()) {
                            Task task = nestedTaskSnapshot.getValue(Task.class);
                            if (task != null) {
                                fetchedTasks.add(task);
                            }
                        }
                    }

                    // Update the RecyclerView with the fetched tasks
                    displayTasks(fetchedTasks);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, "Failed to fetch tasks.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {

            Toast.makeText(MainActivity.this, "User not found ,log out and log in again", Toast.LENGTH_SHORT).show();


        }
    }
    private void displayTasks(List<Task> tasks) {
        // Update the taskList with the new tasks
        taskList.clear();
        taskList.addAll(tasks);

        // Notify the adapter that the data has changed

        int newPosition = taskList.size() - 1;
        taskAdapter.notifyItemInserted(newPosition);

        //taskAdapter.notifyDataSetChanged();

        recyclerView.scrollToPosition(0);
    }



    public void logoutUser(View view) {
        saveLoginStatus(getApplicationContext(), false);


        FirebaseAuth.getInstance().signOut();


        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}
