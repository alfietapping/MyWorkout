package com.example.myworkout.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myworkout.Exercise;
import com.example.myworkout.R;

import java.util.ArrayList;


    public class AdapterCurrentWorkout extends RecyclerView.Adapter<AdapterCurrentWorkout.AdapterViewHolder> {
        private ArrayList<Exercise> mExerciseList;
        private onItemClickListener mListener;




        public interface onItemClickListener{
            void onDeleteItemClick(int position);
        }

        public ArrayList<Exercise> getmExerciseList() {
            return mExerciseList;
        }

        public void setOnItemClickListener(onItemClickListener listener){
            mListener = listener;
        }

        public static class AdapterViewHolder extends RecyclerView.ViewHolder {
            public TextView exerciseName;
            public ImageButton deleteButton;

            public AdapterViewHolder(View itemView, final onItemClickListener listener) {
                super(itemView);
                exerciseName = itemView.findViewById(R.id.current_workout_exercise_name_tv);
                deleteButton = itemView.findViewById(R.id.current_workout_del_button);

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

        public AdapterCurrentWorkout(ArrayList<Exercise> exerciseList){
            mExerciseList = exerciseList;
        }


        @NonNull
        @Override
        public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_current_workout, viewGroup, false);
            AdapterViewHolder avh = new AdapterViewHolder(v, mListener);
            return avh;
        }

        @Override
        public void onBindViewHolder(@NonNull AdapterViewHolder adapterViewHolder, int position) {
            Exercise exercise = mExerciseList.get(position);
            adapterViewHolder.exerciseName.setText(exercise.getName());
        }

        @Override
        public int getItemCount() {
            return mExerciseList.size();
        }




    }


