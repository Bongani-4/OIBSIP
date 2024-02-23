package com.example.todoapplication;

// TaskAdapter.java
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Random;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private int lastColor = -1;


    public  TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);

    }
    public static class TaskViewHolder extends RecyclerView.ViewHolder {


        TextView tasktype,checkImportant,textTaskName,Date;



        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textTaskName = itemView.findViewById(R.id.textTaskName);
            tasktype = itemView.findViewById(R.id.tasktype);
            checkImportant = itemView.findViewById(R.id.urgency);
            Date = itemView.findViewById(R.id.textDate);


        }
    }



    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.textTaskName.setText(task.getTaskName());
        holder.Date.setText(task.getDateTime());
        holder.checkImportant.setText(String.valueOf(task.isUrgent()));
        holder.tasktype.setText(task.isTaskType());


        // Set random background color
        int color = getRandomColor();
        holder.itemView.setBackgroundColor(color);
    }
    private int getRandomColor() {
        Random random = new Random();
        int color;

        // Generate random colors until a different color is obtained
        do {
            color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        } while (color == lastColor);

        // Update lastColor for the next iteration
        lastColor = color;

        return color;
    }





    @Override
    public int getItemCount() {
        return taskList.size();
    }
}


