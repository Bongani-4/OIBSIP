package com.example.todoapplication;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;

import java.util.List;



public class ViewNotes extends AppCompatActivity {


    private Notesadapter notesAdapter;
    private List<Notes> NoteList;
    private View mainContainer;

    private DatabaseReference tasksRef;

    private AppBarConfiguration appBarConfiguration;
  private  RecyclerView recyclerViewNotes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);



        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);



        // Initialize RecyclerView and layout manager
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));



        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewNotes.getContext(), DividerItemDecoration.VERTICAL);
        recyclerViewNotes.addItemDecoration(dividerItemDecoration);


        // Initialize taskList and taskAdapter

        NoteList = new ArrayList<>();
      notesAdapter = new Notesadapter(NoteList);

        // Set the adapter to the RecyclerView
        recyclerViewNotes.setAdapter(notesAdapter);

        // Fetch tasks from Firebase
        fetchNotesFromFirebase();











    }




    private void fetchNotesFromFirebase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        tasksRef = FirebaseDatabase.getInstance().getReference("notes");

        if (user != null) {
            tasksRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Notes> fetchedNotes = new ArrayList<>();

                    // Check if the user has notes
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                            Notes note = noteSnapshot.getValue(Notes.class);
                            if (note != null) {
                                fetchedNotes.add(note);
                            }
                        }

                        // Update the RecyclerView with the fetched notes
                        if (!fetchedNotes.isEmpty()) {
                            displayNotes(fetchedNotes);
                        } else {
                            Toast.makeText(ViewNotes.this, "Fetched notes are empty", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ViewNotes.this, "User has no notes", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ViewNotes.this, "Failed to fetch notes.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(ViewNotes.this, "User not found(notes), log out and log in again", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayNotes(List<Notes> notes) {
        // Update the NoteList with the new notes
        NoteList.clear();
        NoteList.addAll(notes);

        // Notify the adapter that the data has changed
        notesAdapter.notifyDataSetChanged();
        recyclerViewNotes.scrollToPosition(0);
    }









}
