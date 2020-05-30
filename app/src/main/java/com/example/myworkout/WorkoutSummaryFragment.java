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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.widget.Button;
import android.widget.Toast;

import com.example.myworkout.adapters.AdapterWorkoutSummary;

public class WorkoutSummaryFragment extends Fragment {

    private TextView workoutNameTV;
    private TextView date;
    private Button storeButton;

    private String name;
    private String currentDate;

    WorkoutDatabaseHelper databaseHelper;

    RecyclerView recyclerView;
    AdapterWorkoutSummary adapterWorkoutSummary;
    RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_workout_summary, container, false);

        Bundle bundle = getArguments();
        final Workout completedWorkout = (Workout) bundle.getSerializable("WORKOUT_SUMMARY");

        storeButton = v.findViewById(R.id.storeButton);

        ArrayList<Exercise> exercises = completedWorkout.getExerciseList();

        databaseHelper = new WorkoutDatabaseHelper(getActivity());

        recyclerView = v.findViewById(R.id.workout_summary_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapterWorkoutSummary = new AdapterWorkoutSummary(exercises);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterWorkoutSummary);

        workoutNameTV = v.findViewById(R.id.workout_summary_tv);
        date = v.findViewById(R.id.workout_summary_date_stamp);

        name = completedWorkout.getName();

        Calendar calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        workoutNameTV.setText(name);
        date.setText(currentDate);





        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addWorkoutHistoryToDB(completedWorkout);
                Toast.makeText(getActivity(), "Workout added to 'Workout History'", Toast.LENGTH_LONG).show();
            }

        });

        return v;
    }

    public void addWorkoutHistoryToDB(Workout workout){

        int workoutHistoryID = databaseHelper.addWorkoutHistoryName(name, currentDate);

        for (Exercise e:workout.getExerciseList()){

            String name = e.getName();
            int sets = e.getSets();

            for (SetItem s : e.getSetInfo()){
                String weight = String.valueOf(s.getWeight()); //converting weight (stored as double) to string for SQLITE saving
                int reps = s.getReps();

                databaseHelper.addSetItem(reps, weight, workoutHistoryID);

            }
            databaseHelper.addExerciseHistory(name, sets, workoutHistoryID);

        }
    }


}
