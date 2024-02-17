package com.example.todoapplication;

// TaskAdapter.java
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todoapplication.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;

    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.textTaskName.setText(task.getTaskName());
        holder.textTime.setText(task.getTime());

        holder.checkImportant.setChecked(task.isImportant());
        holder.checkWork.setChecked(task.isWork());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView textTaskName;
        TextView textTime;
        CheckBox checkImportant;
        CheckBox checkWork;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textTaskName = itemView.findViewById(R.id.textTaskName);
            textTime = itemView.findViewById(R.id.textTime);
            checkImportant = itemView.findViewById(R.id.checkImportant);
            checkWork = itemView.findViewById(R.id.checkWork);
        }
    }
}
