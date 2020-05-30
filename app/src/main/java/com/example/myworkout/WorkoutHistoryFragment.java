package com.example.myworkout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import androidx.core.app.Fragment;
import androidx.core.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myworkout.adapters.AdapterWorkoutHistory;

import java.util.ArrayList;

public class WorkoutHistoryFragment extends Fragment{

    private RecyclerView recyclerView;
    private AdapterWorkoutHistory adapterWorkoutDisplay;
    private RecyclerView.LayoutManager layoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_history, container, false);

        recyclerView = view.findViewById(R.id.workout_history_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapterWorkoutDisplay = new AdapterWorkoutHistory(getWorkoutFromDB());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterWorkoutDisplay);

        adapterWorkoutDisplay.setOnItemClickListener(new AdapterWorkoutHistory.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                WorkoutHistorySummaryFragment WHSF = new WorkoutHistorySummaryFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("WORKOUT_HISTORY_SUMMARY", adapterWorkoutDisplay.getWorkoutList().get(position));
                WHSF.setArguments(bundle);
                ft.replace(R.id.fragment_container, WHSF);
                ft.addToBackStack(null);
                ft.commit();

            }

            @Override
            public void onDeleteItem(final int position) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
                dialog.setTitle("DELETE WORKOUT HISTORY");
                dialog.setMessage("Would you like to delete this workout history?");
                dialog.setButton(dialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.setButton(dialog.BUTTON_POSITIVE, "DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WorkoutDatabaseHelper helper = new WorkoutDatabaseHelper(getActivity());
                        helper.deleteWorkoutHistoryfromDB(adapterWorkoutDisplay.getWorkoutList().get(position));
                        adapterWorkoutDisplay.getWorkoutList().remove(position);
                        adapterWorkoutDisplay.notifyDataSetChanged();

                    }
                });

                dialog.show();
            }
        });







        return view;

    }

    public ArrayList<Workout> getWorkoutFromDB(){

        ArrayList<Workout> workoutsDB = new ArrayList<>();


        WorkoutDatabaseHelper helper = new WorkoutDatabaseHelper(getActivity());

        Cursor workoutCursor = helper.getWorkoutHistoryNameFromDB();
        Cursor exerciseCursor = helper.getExerciseHistoryFromDB();
        Cursor setItemCursor = helper.getSetItemFromDB();

        try {

            //Loop through workout cursor creating workout objects
            while (workoutCursor.moveToNext()) {

                Workout workout = new Workout();
                workout.setName(workoutCursor.getString(1));
                workout.setId(workoutCursor.getInt(0));
                workout.setDateStamp(workoutCursor.getString(2));

                // Loop through exercise cursor creating an arraylist of exercises
                ArrayList<Exercise> workoutExercises = new ArrayList<>();

                while (exerciseCursor.moveToNext()) {
                    if (workoutCursor.getInt(0) == exerciseCursor.getInt(5)) {
                        Exercise exercise = new Exercise();

                        exercise.setName(exerciseCursor.getString(1));
                        exercise.setSets(exerciseCursor.getInt(3));

                        ArrayList<SetItem> setItems = new ArrayList<>();

                        while (setItemCursor.moveToNext()){

                            if (workoutCursor.getInt(0) == setItemCursor.getInt(3)){
                                SetItem setItem = new SetItem();
                                setItem.setReps(setItemCursor.getInt(1));
                                setItem.setWeight(Double.valueOf(setItemCursor.getString(2)));

                                setItems.add(setItem);
                            }
                        }

                        exercise.setSetInfo(setItems);

                        workoutExercises.add(exercise);
                    }
                }
                exerciseCursor.moveToFirst();

                //populate workout and return
                workout.setExerciseList(workoutExercises);

                workoutsDB.add(workout);
            }
        } finally {
            workoutCursor.close();
            exerciseCursor.close();
        }
        return workoutsDB;

    }

}
