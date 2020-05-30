package com.example.myworkout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import androidx.core.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myworkout.adapters.AdapterWorkoutSummary;

import java.util.ArrayList;

public class WorkoutHistorySummaryFragment extends Fragment {

    private TextView workoutNameTV;
    private TextView date;

    private String name;

    RecyclerView recyclerView;
    AdapterWorkoutSummary adapterWorkoutSummary;
    RecyclerView.LayoutManager layoutManager;



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_history_summary, container, false);

        Bundle bundle = getArguments();
        final Workout completedWorkout = (Workout) bundle.getSerializable("WORKOUT_HISTORY_SUMMARY");

        workoutNameTV = view.findViewById(R.id.workout_history_summary_workout_name);
        date = view.findViewById(R.id.workout_history_summary_date_stamp);

        name = completedWorkout.getName();

        ArrayList<Exercise> exercises = completedWorkout.getExerciseList();

        recyclerView = view.findViewById(R.id.recycler_workout_history_summary);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapterWorkoutSummary = new AdapterWorkoutSummary(exercises);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterWorkoutSummary);


        String currentDate;
        currentDate = completedWorkout.getDateStamp();

        workoutNameTV.setText(name);
        date.setText(currentDate);



        return view;
    }
}
