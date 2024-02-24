package com.example.todoapplication;

// TaskAdapter.java
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.List;
import android.content.Context;
import android.view.View;
import android.content.res.Resources;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private int lastColor = -1;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int position);

    }

    public void  setOnItemClickListener(OnItemClickListener clickListener) {
         listener = clickListener;
    }

    public  TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view,listener);



    }
    public static class TaskViewHolder extends RecyclerView.ViewHolder  {



        TextView tasktype,checkImportant,textTaskName,Date;



        public TaskViewHolder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            textTaskName = itemView.findViewById(R.id.textTaskName);
            tasktype = itemView.findViewById(R.id.tasktype);
            checkImportant = itemView.findViewById(R.id.urgency);
            Date = itemView.findViewById(R.id.textDate);

          //  itemView.setOnClickListener(this);



        }


    }

    public interface OnTaskClickListener {
        void onTaskClick(int position);
    }






    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);


        holder.textTaskName.setTextColor(Color.BLACK);
        holder.Date.setTextColor(Color.BLACK);
        holder.checkImportant.setTextColor(Color.BLACK);
        holder.tasktype.setTextColor(Color.BLACK);

        holder.textTaskName.setText(task.getTaskName());
        holder.Date.setText(task.getDateTime());
        holder.checkImportant.setText(task.isUrgent() ? "Urgent" : "Not Urgent");
        holder.tasktype.setText(task.isTaskType());


        // Set background color
        int color;
        if (task.isUrgent()) {
            color = Color.parseColor("#800000");

        } else {
            color = getRandomColor();
        }
        holder.itemView.setBackgroundColor(color);


        // Set click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(holder.getAdapterPosition());
                }
            }
        });
    }

    private boolean isTaskInPast(Task task) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            Date taskDate = dateFormat.parse(task.getDateTime());
            if (taskDate != null) {
                // Get the current date and time
                Calendar currentDateTime = Calendar.getInstance();
                // Compare the task's date and time with the current date and time
                return taskDate.before(currentDateTime.getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
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

        return taskList.size();
    }
    public void removeTask() {
        Iterator<Task> iterator = taskList.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (isTaskInPast(task)) {
                iterator.remove();
            }
        }
        notifyDataSetChanged();
    }

}



