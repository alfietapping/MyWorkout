package com.example.myworkout;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.core.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myworkout.adapters.AdapterCurrentWorkout;

import java.util.ArrayList;


public class WorkoutBuilderFragment extends Fragment{

    private RecyclerView recyclerView;
    private AdapterCurrentWorkout adapterCurrentWorkout;
    private RecyclerView.LayoutManager layoutManager;

    ImageButton workoutNameButton;
    ImageButton addExerciseButton;
    ImageButton buildWorkoutButton;
    TextView workoutNameTV;

    WorkoutDatabaseHelper databaseHelper;

    ArrayList<Exercise> current = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_workout_builder, container, false);

        databaseHelper = new WorkoutDatabaseHelper(getActivity());

        workoutNameButton = v.findViewById(R.id.workout_name_button);
        addExerciseButton = v.findViewById(R.id.add_exercise_button);
        buildWorkoutButton = v.findViewById(R.id.build_workout_button);
        workoutNameTV = v.findViewById(R.id.workout_name_tv);

        recyclerView = v.findViewById(R.id.current_workout_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapterCurrentWorkout = new AdapterCurrentWorkout(current);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterCurrentWorkout);

        if (workoutNameTV.getText().toString().equals("")) {
            workoutNameButton.setVisibility(View.VISIBLE);
            addExerciseButton.setVisibility(View.INVISIBLE);
            buildWorkoutButton.setVisibility(View.INVISIBLE);
        }

        workoutNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(getActivity());

                AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
                dialog.setTitle("WORKOUT NAME");
                dialog.setView(editText);
                editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                dialog.setButton(dialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.setButton(dialog.BUTTON_POSITIVE, "ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editText.getText().toString();
                        Workout workoutName = new Workout();
                        workoutName.setName(name);
                        workoutNameTV.setText(name);

                        addExerciseButton.setVisibility(View.VISIBLE);
                        buildWorkoutButton.setVisibility(View.VISIBLE);
                        workoutNameButton.setVisibility(View.INVISIBLE);
                    }
                });

                dialog.show();
            }
        });


        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText editText = new EditText(getActivity());

                AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
                dialog.setTitle("EXERCISE NAME");
                dialog.setView(editText);
                editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                dialog.setButton(dialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.setButton(dialog.BUTTON_POSITIVE, "ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editText.getText().toString();
                        Exercise exercise = new Exercise();
                        exercise.setName(name);
                        current.add(exercise);
                    }
                });

                dialog.show();
            }
        });


        buildWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = workoutNameTV.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter workout name", Toast.LENGTH_LONG).show();
                } else if (current.isEmpty()) {
                    Toast.makeText(getActivity(), "Please add an exercise using the + button", Toast.LENGTH_LONG).show();
                } else {

                    Workout workout = new Workout(name, current);

                    addWorkoutToDB(workout);
                    Toast.makeText(getActivity(), "Workout added", Toast.LENGTH_LONG).show();
                    workoutNameTV.setText("");
                    current.clear();

                    workoutNameButton.setVisibility(View.VISIBLE);
                    addExerciseButton.setVisibility(View.INVISIBLE);
                    buildWorkoutButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        adapterCurrentWorkout.setOnItemClickListener(new AdapterCurrentWorkout.onItemClickListener() {
            @Override
            public void onDeleteItemClick(final int position) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
                dialog.setTitle("DELETE WORKOUT");
                dialog.setMessage("Would you like to delete this exercise?");
                dialog.setButton(dialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.setButton(dialog.BUTTON_POSITIVE, "DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapterCurrentWorkout.getmExerciseList().remove(position);
                        adapterCurrentWorkout.notifyDataSetChanged();
                    }
                });

            }
        });

        return v;
    }

    private void addWorkoutToDB(Workout workout) {

        int workoutId = databaseHelper.addWorkoutName(workout.getName());

        for (Exercise exercise : workout.getExerciseList()) {
            databaseHelper.addExercise(exercise.getName(), workoutId);
        }
    }

}
