package com.example.todoapplication;

// TaskAdapter.java
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.List;

public class Notesadapter extends RecyclerView.Adapter<Notesadapter.noteViewHolder> {

    private List<Notes> notelist;
    private int lastColor = -1;


    public  Notesadapter(List<Notes> notelist) {
        this.notelist = notelist;
    }

    @NonNull
    @Override
    public noteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new noteViewHolder(view);

    }
    public static class noteViewHolder extends RecyclerView.ViewHolder {


        TextView Heaading,noteN;



        public noteViewHolder(@NonNull View itemView) {
            super(itemView);
            Heaading = itemView.findViewById(R.id.HeadingNo);
            noteN = itemView.findViewById(R.id.note);



        }
    }


    @Override
    public void onBindViewHolder(@NonNull noteViewHolder holder, int position) {
        Notes note = notelist.get(position);



        holder.Heaading.setTextColor(Color.BLACK);
        holder.noteN.setTextColor(Color.BLACK);


        holder.Heaading.setText(note.getHeading());
        holder.noteN.setText(note.getNote());








        // Set background color

           int  color = getRandomColor();

        holder.itemView.setBackgroundColor(color);
    }



    private int getRandomColor() {
        // Define  set of colors
        int[] predefinedColors = {Color.rgb(96, 104, 115),   // Pewter
                Color.rgb(70, 89, 101),    // Steel
                Color.rgb(65, 67, 77),     // Iron
                Color.rgb(53, 56, 60)};    // Seal

        Random random = new Random();
        int color;

        // Generate random colors until a different color is obtained
        do {
            color = predefinedColors[random.nextInt(predefinedColors.length)];
        } while (color == lastColor);

        // Update lastColor for the next iteration
        lastColor = color;

        return color;
    }










    @Override
    public int getItemCount() {

        return notelist.size();
    }

}



