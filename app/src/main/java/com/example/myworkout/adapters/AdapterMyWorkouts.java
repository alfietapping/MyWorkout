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

public class AdapterMyWorkouts extends RecyclerView.Adapter<AdapterMyWorkouts.AdapterViewHolder> {
    private ArrayList<Workout> mWorkoutList;
    private onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int position);
        void onDeleteItemClick(int position);
    }

    public ArrayList<Workout> getmWorkoutList() {
        return mWorkoutList;
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }

    public static class AdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageButton deleteButton;

        public AdapterViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            textView = itemView.findViewById(R.id.my_workout_tv);
            deleteButton = itemView.findViewById(R.id.my_workout_del_button);


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
                            listener.onDeleteItemClick(position);
                            
                        }
                    }
                }
            });
        }
    }

    public AdapterMyWorkouts(ArrayList<Workout> workoutList){
        mWorkoutList = workoutList;
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_my_workouts, viewGroup, false);
        AdapterViewHolder avh  = new AdapterViewHolder(v, mListener);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder adapterViewHolder, int position) {
        Workout myWorkoutItem = mWorkoutList.get(position);
        adapterViewHolder.textView.setText(myWorkoutItem.getName());


    }

    @Override
    public int getItemCount() {
        return mWorkoutList.size();
    }
}
