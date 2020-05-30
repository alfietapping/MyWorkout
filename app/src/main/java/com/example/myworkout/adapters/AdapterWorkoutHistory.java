package com.example.myworkout.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myworkout.R;
import com.example.myworkout.Workout;

import java.util.ArrayList;

public class AdapterWorkoutHistory extends RecyclerView.Adapter<AdapterWorkoutHistory.AdapterViewHolder> {

    private onItemClickListener listener;
    private ArrayList<Workout> workoutList;


    public interface onItemClickListener{
        void onItemClick(int position);
        void onDeleteItem(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public AdapterWorkoutHistory(ArrayList<Workout> mWorkoutList) {
        this.workoutList = mWorkoutList;
    }

    public ArrayList<Workout> getWorkoutList(){
        return workoutList;
    }

    public static class AdapterViewHolder extends RecyclerView.ViewHolder{

        private TextView workoutName;
        private TextView dateStamp;
        private ImageButton deleteButton;

        public AdapterViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            workoutName = itemView.findViewById(R.id.workout_history_workout_name);
            dateStamp = itemView.findViewById(R.id.workout_history_date);
            deleteButton = itemView.findViewById(R.id.workout_history_del_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            listener.onItemClick(position);

                        }
                    }
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            listener.onDeleteItem(position);
                        }
                    }
                }
            });


        }
    }





    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_workout_history, viewGroup, false);
        AdapterViewHolder avh = new AdapterViewHolder(v, listener);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder adapterViewHolder, int position) {
        Workout workoutItem = workoutList.get(position);
        adapterViewHolder.workoutName.setText(workoutItem.getName());
        adapterViewHolder.dateStamp.setText(workoutItem.getDateStamp());
    }



    @Override
    public int getItemCount() {
        return workoutList.size();
    }
}
