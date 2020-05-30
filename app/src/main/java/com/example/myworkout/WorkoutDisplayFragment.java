package com.example.myworkout;


import android.os.Bundle;
import androidx.core.app.Fragment;
import androidx.core.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myworkout.adapters.AdapterWorkoutDisplay;

import java.util.ArrayList;


public class WorkoutDisplayFragment extends Fragment implements AlertStartExercise.sendExercise {

    private RecyclerView recyclerView;
    private AdapterWorkoutDisplay adapterWorkoutDisplay;
    private RecyclerView.LayoutManager layoutManager;

    TextView workoutName;
    Button finishButton;
    private ArrayList<Exercise> exercises;
    private ArrayList<Integer> counter = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_workout_display, container, false);

        Bundle bundle = getArguments();
        final Workout passedWorkout = (Workout) bundle.getSerializable("WDFWORKOUT");

        workoutName = v.findViewById(R.id.workout_display_ex_workout_name);
        finishButton = v.findViewById(R.id.workout_display_finish_button);

        exercises = passedWorkout.getExerciseList();
        final String passedWorkoutName = passedWorkout.getName();
        workoutName.setText(passedWorkoutName);

        recyclerView = v.findViewById(R.id.recycler_workout_display);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapterWorkoutDisplay = new AdapterWorkoutDisplay(exercises);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterWorkoutDisplay);

        for (int i = 0; i < exercises.size(); i++) {
            adapterWorkoutDisplay.getSetnums().add(0);
        }


        adapterWorkoutDisplay.setOnItemClickListener(new AdapterWorkoutDisplay.onItemClickListener() {
            @Override
            public void onStartClick(int position) {

                int totalSets = adapterWorkoutDisplay.getSetnums().get(position);

                if (totalSets != 0) {
                    AlertStartExercise ASE = new AlertStartExercise();
                    Exercise exercise = new Exercise();
                    exercise.setName(adapterWorkoutDisplay.getmExerciseList().get(position).getName());
                    exercise.setSets(totalSets);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("EXERCISE", exercise);
                    ASE.setArguments(bundle);
                    ASE.setTargetFragment(WorkoutDisplayFragment.this, 1);
                    ASE.show(getFragmentManager(), "AlertStartExercise");
                } else {
                    Toast.makeText(getActivity(), "Sets need to be added", Toast.LENGTH_LONG).show();
                }
            }

        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter.size() == exercises.size()) {

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    WorkoutSummaryFragment WSF = new WorkoutSummaryFragment();
                    Bundle bundle = new Bundle();
                    Workout completedWorkout = new Workout(workoutName.getText().toString(), exercises);
                    bundle.putSerializable("WORKOUT_SUMMARY", completedWorkout);
                    WSF.setArguments(bundle);
                    ft.replace(R.id.fragment_container, WSF);
                    ft.commit();
                }
            }
        });

        return v;

    }

    @Override
    public void exerciseComplete(Exercise exercise) {
        counter.add(1);

        for (Exercise e : exercises) {
            if (e.getName().equals(exercise.getName())) {
                exercises.set(exercises.indexOf(e), exercise);
                adapterWorkoutDisplay.notifyDataSetChanged();
            }
        }
    }
}
