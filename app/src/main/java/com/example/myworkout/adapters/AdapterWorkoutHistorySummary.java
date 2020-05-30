package com.example.myworkout.adapters;

import android.support.annotation.NonNull;
import androidx.constraintlayout.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myworkout.Exercise;
import com.example.myworkout.R;
import com.example.myworkout.SetItem;

import java.util.ArrayList;

public class AdapterWorkoutHistorySummary extends RecyclerView.Adapter<AdapterWorkoutHistorySummary.AdapterViewHolder> {


    private ArrayList<Exercise> exercises = new ArrayList<>();

    public AdapterWorkoutHistorySummary(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }



    public static class AdapterViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout expandedLayout;
        private ConstraintLayout viewSetsLayout;
        private TextView exerciseNameTV;
        private TextView setTV;
        private TextView weightTV;
        private TextView repsTV;
        private ImageView arrow;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            expandedLayout = itemView.findViewById(R.id.workout_history_summary_constraint_layout);
            viewSetsLayout = itemView.findViewById(R.id.workout_history_summary_view_sets_layout);
            exerciseNameTV = itemView.findViewById(R.id.workout_history_summary_exercise_name_tv);
            setTV = itemView.findViewById(R.id.workout_history_summary_sets_holder);
            weightTV = itemView.findViewById(R.id.workout_history_summary_weight_holder);
            repsTV = itemView.findViewById(R.id.workout_history_summary_reps_holder);
            arrow = itemView.findViewById(R.id.card_view_workout_history_summary_arrow);

            expandedLayout.setVisibility(View.GONE);
        }
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_workout_history_summary, viewGroup, false);
        AdapterViewHolder avh = new AdapterViewHolder(v);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterViewHolder viewHolder, int position) {
        final Exercise exerciseItem = exercises.get(position);
        viewHolder.exerciseNameTV.setText(exerciseItem.getName());

        exerciseItem.setExpanded(false);

        viewHolder.viewSetsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!exerciseItem.isExpanded()) {
                    exerciseItem.setExpanded(true);
                    viewHolder.expandedLayout.setVisibility(View.VISIBLE);
                    viewHolder.arrow.setImageResource(R.drawable.ic_keyboard_arrow_down_24px);

                } else {
                    exerciseItem.setExpanded(false);
                    viewHolder.expandedLayout.setVisibility(View.GONE);
                    viewHolder.arrow.setImageResource(R.drawable.ic_keyboard_arrow_left_24px);
                }
            }
        });


        int tempSets = exerciseItem.getSets();

        ArrayList<Integer> sets = new ArrayList<>();

        StringBuilder setStringBuilder = new StringBuilder();

        for (int i = 0; i < tempSets; i++) {
            int temp = i + 1;
            sets.add(temp);
            setStringBuilder.append(sets.get(i));
            setStringBuilder.append("\n");
            viewHolder.setTV.setText(setStringBuilder.toString());
        }


        ArrayList<SetItem> setItems = exerciseItem.getSetInfo();

        ArrayList<Double> weight = new ArrayList<>();
        ArrayList<Integer> reps = new ArrayList<>();

        for (SetItem s : setItems) {
            double tempWeight = s.getWeight();
            int tempRep = s.getReps();


            weight.add(tempWeight);

            StringBuilder weightStringBuilder = new StringBuilder();

            for (Double d : weight) {
                weightStringBuilder.append(d);
                weightStringBuilder.append("\n");
                viewHolder.weightTV.setText(weightStringBuilder.toString());
            }


            reps.add(tempRep);

            StringBuilder repsStringBuilder = new StringBuilder();

            for (int i = 0; i < reps.size(); i++) {
                repsStringBuilder.append(reps.get(i));
                repsStringBuilder.append("\n");
                viewHolder.repsTV.setText(repsStringBuilder.toString());
            }
        }
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }


}
