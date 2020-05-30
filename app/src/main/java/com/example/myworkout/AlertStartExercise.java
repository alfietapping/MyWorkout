package com.example.myworkout;

import android.content.Context;
import android.os.Bundle;
import androidx.core.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myworkout.adapters.AdapterStartExercise;

import java.util.ArrayList;

public class AlertStartExercise extends DialogFragment implements AdapterStartExercise.onItemCheckListener {

    private RecyclerView recyclerView;
    private AdapterStartExercise adapterStartExercise;
    private RecyclerView.LayoutManager layoutManager;

    private TextView exerciseName;
    private Button finishButton;
    final ArrayList<Integer> checkedItems = new ArrayList<>();
    final ArrayList<SetItem> setItems = new ArrayList<>();

    private sendExercise listener;

    @Override
    public void onCheck(int position) {
        checkedItems.add(position);
    }

    public interface sendExercise {
        void exerciseComplete(Exercise exercise);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (sendExercise) getTargetFragment();
        } catch(ClassCastException e){
            Log.e("TAG", "Class cast exception");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow()
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = layoutInflater.inflate(R.layout.alert_start_exercise, container, false);

        exerciseName = view.findViewById(R.id.start_exercise_text_view);
        finishButton = view.findViewById(R.id.start_exercise_finish_button);

        Bundle bundle = getArguments();
        final Exercise passedExercise = (Exercise) bundle.getSerializable("EXERCISE");

        String name = passedExercise.getName();
        int numberOfViews = passedExercise.getSets();

        final SetItem setItem = new SetItem();

        for (int i = 0; i < numberOfViews; i++) {
            setItems.add(setItem);
        }

        for (int i = 0; i <setItems.size(); i++){
            SetItem set = new SetItem();
            int oldVal = i-1;
            int newVal = oldVal+1;
            set.setId(newVal);
            setItems.set(i, set);
        }

        exerciseName.setText(name);

        recyclerView = view.findViewById(R.id.recycler_start_exercise);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapterStartExercise = new AdapterStartExercise(setItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterStartExercise);

        for (int i = 0; i < setItems.size(); i++){
            adapterStartExercise.getWeightCounter().add(0.0);
            adapterStartExercise.getRepsCounter().add(0);
        }


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapterStartExercise.getSetCompleteCounter().size() == setItems.size()) {
                    passedExercise.setSetInfo(setItems);
                    passedExercise.setFinished(true);
                    listener.exerciseComplete(passedExercise);
                    getDialog().dismiss();
                } else {
                    Toast.makeText(getActivity(), "You still have sets to do!", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }


}
