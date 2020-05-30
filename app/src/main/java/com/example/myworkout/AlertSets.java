package com.example.myworkout;

import android.content.Context;
import android.os.Bundle;
import androidx.core.app.DialogFragment;
import androidx.core.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class AlertSets extends DialogFragment {

    private ImageButton setsRemove;
    private ImageButton setsAdd;
    private TextView setsNum;
    private Button cancel;
    private Button ok;


    public AlertSets.AlertDialogListener listener;

    private int currentSetsNum = 0;

    public interface AlertDialogListener {
        void sendInput(int sets);
    }
//
//    public void setListener(AlertDialogListener listener) {
//        this.listener = listener;
//    }
//
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (AlertDialogListener) getTargetFragment();
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

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.alert_sets, container, false);

        Bundle bundle = getArguments();
        final Exercise exercise = (Exercise) bundle.getSerializable("EXERCISE");

        setsRemove = view.findViewById(R.id.alert_sets_remove_button);
        setsAdd = view.findViewById(R.id.alert_sets_add_button);
        setsNum = view.findViewById(R.id.alert_sets_num_tv);
        cancel = view.findViewById(R.id.alert_sets_cancel_button);
        ok = view.findViewById(R.id.alert_sets_ok_button);

        setsRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSetsNum--;
                if (currentSetsNum < 0){
                    currentSetsNum = 0;
                }
                String current = String.valueOf(currentSetsNum);
                setsNum.setText(current);
            }
        });

        setsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSetsNum++;
                if (currentSetsNum > 99){
                    currentSetsNum = 99;
                }
                String current = String.valueOf(currentSetsNum);
                setsNum.setText(current);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int input = 0;
                try {
                    input = Integer.parseInt(setsNum.getText().toString());
                } catch(ClassCastException e){
                    e.printStackTrace();
                }
                exercise.setSets(input);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                AlertStartExercise ASF = new AlertStartExercise();
                Bundle bundle = new Bundle();
                bundle.putSerializable("EXERCISE", exercise);
                ASF.setArguments(bundle);
                ft.replace(R.id.fragment_container, ASF);
                getFragmentManager().popBackStack();
                ft.commit();
                getDialog().dismiss();
            }
        });

        return view;
    }
}
