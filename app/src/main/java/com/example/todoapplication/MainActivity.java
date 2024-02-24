package com.example.todoapplication;

import static com.example.todoapplication.ui.login.LoginActivity.saveLoginStatus;
import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.core.models.Size;
import androidx.recyclerview.widget.DividerItemDecoration;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;


public class MainActivity extends AppCompatActivity {


    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private View mainContainer;
    private DatabaseReference tasksRef;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private    Party party;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Initialize RecyclerView and layout manager
        binding.recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.recyclerViewTasks.getContext(), DividerItemDecoration.VERTICAL);
        binding.recyclerViewTasks.addItemDecoration(dividerItemDecoration);


        // Initialize taskList and taskAdapter

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);

        // Set the adapter to the RecyclerView
        binding.recyclerViewTasks.setAdapter(taskAdapter);
        taskAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            public void onItemClick(int position) {



            }
        });



        // Fetch tasks from Firebase
        fetchTasksFromFirebase();



        setSupportActionBar(binding.toolbar);

        binding.addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Addtasks.class));
            }
        });
        binding.textViewAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this,addNOtes.class));
            }
        });
        binding.textViewViewNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ViewNotes.class));

            }
        });
        EmitterConfig emitterConfig = new Emitter(5L, TimeUnit.SECONDS).perSecond(50);
        party =
                new PartyFactory(emitterConfig)
                        .angle(270)
                        .spread(90)
                        .setSpeedBetween(1f, 5f)
                        .timeToLive(2000L)
                        .shapes(new Shape.Rectangle(0.2f))
                        .sizes(new Size(12, 5f, 0.2f))
                        .position(0.0, 0.0, 1.0, 0.0)
                        .build();
       // binding.konfettiView.setOnClickListener(view -> binding.konfettiView.start(party));

        taskAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            public void onItemClick(int position) {


                Task task = taskList.get(position);
                showProgressDialog(task, position);
            }
        });







    }


    private void showProgressDialog(final Task task, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Task Progress");
        builder.setMessage("Progress with this task: " + task.getTaskName());

        builder.setPositiveButton("Complete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // action on completion,
                showFireworks();
                removeTaskFromDatabase(task);
                //taskAdapter.notifyItemRemoved(position);
            }
        });

        builder.setNegativeButton("Remove Task", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                taskList.remove(position);
                taskAdapter.notifyItemRemoved(position);
                 removeTaskFromDatabase(task);
            }
        });

        builder.show();
    }


    private void showFireworks() {
        binding.konfettiView.start(party);


        Toast.makeText(this, "Well done!", Toast.LENGTH_SHORT).show();
    }

    private void removeTaskFromDatabase(Task task) {
        // Check if the user is authenticated
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Get the unique task identifier (taskId)
            String taskId = task.getTaskId();

            // Get a reference to the user's tasks in the database
            DatabaseReference userTasksRef = tasksRef.child(user.getUid());

            // Remove the task from the database
            userTasksRef.child(taskId).removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Task removed successfully
                            Toast.makeText(MainActivity.this, "Task removed successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Error occurred while removing the task
                            Toast.makeText(MainActivity.this, "Failed to remove task", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // User is not authenticated
            Toast.makeText(MainActivity.this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }





    private void fetchTasksFromFirebase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        tasksRef = FirebaseDatabase.getInstance().getReference("tasks");

        if (user != null) {
            tasksRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Task> fetchedTasks = new ArrayList<>();

                    // Check if the user has tasks
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                            Task task = taskSnapshot.getValue(Task.class);
                            if (task != null) {
                                task.setTaskId(taskSnapshot.getKey());
                                fetchedTasks.add(task);

                            }
                        }

                        // Sort tasks by date
                        Collections.sort(fetchedTasks, new Comparator< Task>() {
                            @Override
                            public int compare(Task task1, Task task2) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                                try {
                                    Date date1 = sdf.parse(task1.getDateTime());
                                    Date date2 = sdf.parse(task2.getDateTime());
                                    return date1.compareTo(date2);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    return 0;
                                }
                            }
                        });

                        // Update the RecyclerView with the sorted tasks
                        if (!fetchedTasks.isEmpty()) {
                            displayTasks(fetchedTasks);

                            taskAdapter.removeTask();
                        } else {
                            Toast.makeText(MainActivity.this, "Fetched tasks are empty", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "User has no tasks", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, "Failed to fetch tasks.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "User not found, log out and log in again", Toast.LENGTH_SHORT).show();
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

        binding.recyclerViewTasks.scrollToPosition(0);
    }



    public void logoutUser(View view) {
        saveLoginStatus(getApplicationContext(), false);


        FirebaseAuth.getInstance().signOut();


        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}
