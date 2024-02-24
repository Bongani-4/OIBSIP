package com.example.todoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class addNOtes extends AppCompatActivity {

    private Button save;
    private EditText heading;
    private MultiAutoCompleteTextView notes;
    private TextView  cancel;
    private DatabaseReference tasksRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);


        cancel=findViewById(R.id.cancelN);
        heading =findViewById(R.id.HeadingNotes);
        notes =findViewById(R.id.notes);
        save=findViewById(R.id.saveN);

        // Initialize Firebase Database reference
        tasksRef = FirebaseDatabase.getInstance().getReference("notes");



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(addNOtes.this,MainActivity.class));

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveNotesToFirebase();

            }
        });




    }

    private void saveNotesToFirebase() {


        String NotesHeading= heading.getText().toString();
        String note =  notes.getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Create a map to store task data
            Map<String, Object> noteData = new HashMap<>();
            noteData.put("NotesHeading", NotesHeading);
            noteData.put("note", note);

            // Generate a unique key for the task
            String noteId = tasksRef.child(user.getUid()).push().getKey();
            // Save the task data to Firebase Realtime Database under the user's UID
            if (noteId!= null) {
                tasksRef.child(user.getUid()).child(noteId).setValue(noteData);
                Toast.makeText(addNOtes.this, "Notes saved ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(addNOtes.this, "Failed to save node.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(addNOtes.this,"User Unknown for nodes,try log out  and log in again",Toast.LENGTH_LONG).show();
        }




        }
}