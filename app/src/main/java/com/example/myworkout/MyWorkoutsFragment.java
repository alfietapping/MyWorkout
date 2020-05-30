package com.example.myworkout;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import androidx.core.app.Fragment;
import androidx.core.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myworkout.adapters.AdapterMyWorkouts;

import java.util.ArrayList;


public class MyWorkoutsFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterMyWorkouts adapter;
    private RecyclerView.LayoutManager layoutManager;
    TextView noData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_workouts, container, false);


        recyclerView = v.findViewById(R.id.recycler_view_my_workouts);
        noData = v.findViewById(R.id.tv_no_data);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new AdapterMyWorkouts(getWorkoutFomDB());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


       if (adapter.getmWorkoutList().size() == 0) {
            recyclerView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        } else {
           recyclerView.setVisibility(View.VISIBLE);
           noData.setVisibility(View.GONE);
       }


        adapter.setOnItemClickListener(new AdapterMyWorkouts.onItemClickListener() {
            @Override
            public void onItemClick(int position) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                WorkoutDisplayFragment edf = new WorkoutDisplayFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("WDFWORKOUT", adapter.getmWorkoutList().get(position));
                edf.setArguments(bundle);
                ft.replace(R.id.fragment_container, edf);
                ft.addToBackStack(null);
                ft.commit();
            }

            @Override
            public void onDeleteItemClick(final int position) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
                dialog.setTitle("DELETE WORKOUT");
                dialog.setMessage("Would you like to delete this workout?");
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
                        helper.deleteWorkoutfromDB(adapter.getmWorkoutList().get(position));
                        adapter.getmWorkoutList().remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                dialog.show();
            }
        });

       return v;
    }




    public ArrayList<Workout> getWorkoutFomDB() {

        ArrayList<Workout> workoutsDB = new ArrayList<>();


        WorkoutDatabaseHelper helper = new WorkoutDatabaseHelper(getActivity());

        Cursor workoutCursor = helper.getWorkoutNameFromDB();
        Cursor exerciseCursor = helper.getExerciseFromDB();

        try {

            //Loop through workout cursor creating workout objects
            while (workoutCursor.moveToNext()) {

                Workout workout = new Workout();
                workout.setName(workoutCursor.getString(1));
                workout.setId(workoutCursor.getInt(0));

                // Loop through exercise cursor creating an arraylist of exercises
                ArrayList<Exercise> workoutExercises = new ArrayList<>();
                while (exerciseCursor.moveToNext()) {
                    if (workoutCursor.getInt(0) == exerciseCursor.getInt(2)) {
                        Exercise exercises = new Exercise(exerciseCursor.getString(1));
                        workoutExercises.add(exercises);
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
