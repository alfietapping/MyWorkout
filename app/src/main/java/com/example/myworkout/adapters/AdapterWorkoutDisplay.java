package com.example.myworkout.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import androidx.constraintlayout.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myworkout.Exercise;
import com.example.myworkout.R;

import java.util.ArrayList;

public class AdapterWorkoutDisplay extends RecyclerView.Adapter<AdapterWorkoutDisplay.AdapterViewHolder> {


    private ArrayList<Exercise> mExerciseList;
    private onItemClickListener mListener;
    private ArrayList<Integer> setnums = new ArrayList<>();





    public interface onItemClickListener {
        void onStartClick(int position);
    }


    public AdapterWorkoutDisplay(ArrayList<Exercise> mExerciseList) {
        this.mExerciseList = mExerciseList;

    }

    public ArrayList<Integer> getSetnums() {
        return setnums;
    }

    public ArrayList<Exercise> getmExerciseList() {
        return mExerciseList;
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        mListener = listener;
    }

    public static class AdapterViewHolder extends RecyclerView.ViewHolder {

        public TextView workoutDisplayTV;
        public TextView setsNumberTV;
        public TextView setTV;
        public TextView exerciseCompleteTV;
        public ImageButton removeButton;
        public ImageButton addButton;
        public Button startExercise;
        public ImageView arrow;

        public ConstraintLayout constraintLayout2;
        public ConstraintLayout constraintLayout;


        public AdapterViewHolder(@NonNull View itemView, final onItemClickListener mListener) {
            super(itemView);
            workoutDisplayTV = itemView.findViewById(R.id.workout_display_exercise_name);
            setsNumberTV = itemView.findViewById(R.id.card_view_number_TV);
            setTV = itemView.findViewById(R.id.card_view_set_tv);
            removeButton = itemView.findViewById(R.id.card_view_remove_button);
            addButton = itemView.findViewById(R.id.card_view_add_button);
            startExercise = itemView.findViewById(R.id.workout_display_start_exercise_button);
            exerciseCompleteTV = itemView.findViewById(R.id.exercise_complete_textview);
            arrow = itemView.findViewById(R.id.workout_display_arrow);

            constraintLayout2 = itemView.findViewById(R.id.workout_display_constraint);
            constraintLayout = itemView.findViewById(R.id.expandable_constraint_layout_workout_display);
            constraintLayout.setVisibility(View.GONE);

            startExercise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onStartClick(position);
                        }
                    }
                }
            });


        }

    }


    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_workout_display, viewGroup, false);
        final AdapterViewHolder avh = new AdapterViewHolder(v, mListener);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterViewHolder adapterViewHolder, final int position) {
        final Exercise exerciseItem = mExerciseList.get(position);
        adapterViewHolder.workoutDisplayTV.setText(exerciseItem.getName());

        exerciseItem.setExpanded(false);

        adapterViewHolder.constraintLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!exerciseItem.getFinished() && !exerciseItem.isExpanded()){
                    exerciseItem.setExpanded(true);
                    adapterViewHolder.arrow.setImageResource(R.drawable.ic_keyboard_arrow_down_24px);
                    adapterViewHolder.constraintLayout.setVisibility(View.VISIBLE);
                } else {
                    exerciseItem.setExpanded(false);
                    adapterViewHolder.arrow.setImageResource(R.drawable.ic_keyboard_arrow_left_24px);
                    adapterViewHolder.constraintLayout.setVisibility(View.GONE);
                }
            }
        });

        adapterViewHolder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = setnums.get(position);
                count--;
                if (count < 0) {
                    count = 0;
                }
                String value = String.valueOf(count);
                adapterViewHolder.setsNumberTV.setText(value);
                setnums.set(position, count);
            }
        });

        adapterViewHolder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = setnums.get(position);
                count++;
                if (count > 99) {
                    count = 99;
                }
                String value = String.valueOf(count);
                adapterViewHolder.setsNumberTV.setText(value);
                setnums.set(position, count);
            }
        });


        if (exerciseItem.getFinished()) {
            adapterViewHolder.startExercise.setEnabled(false);
            adapterViewHolder.removeButton.setVisibility(View.INVISIBLE);
            adapterViewHolder.addButton.setVisibility(View.INVISIBLE);
            adapterViewHolder.setsNumberTV.setVisibility(View.INVISIBLE);
            adapterViewHolder.setTV.setVisibility(View.INVISIBLE);
            adapterViewHolder.itemView.setBackgroundResource(R.drawable.custom_border_set_complete);
            adapterViewHolder.constraintLayout2.setBackgroundColor(Color.parseColor("#03dac6"));
            adapterViewHolder.arrow.setVisibility(View.GONE);
            adapterViewHolder.startExercise.setWidth(100);
            adapterViewHolder.exerciseCompleteTV.setText("COMPLETE");
            adapterViewHolder.startExercise.setBackgroundColor(Color.TRANSPARENT);
            adapterViewHolder.startExercise.setTextColor(Color.parseColor("#191919"));
            adapterViewHolder.constraintLayout.setVisibility(View.GONE);
        } else {
            adapterViewHolder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return mExerciseList.size();
    }


}
