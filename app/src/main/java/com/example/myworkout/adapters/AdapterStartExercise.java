package com.example.myworkout.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.myworkout.R;
import com.example.myworkout.SetItem;

import java.util.ArrayList;

public class AdapterStartExercise extends RecyclerView.Adapter<AdapterStartExercise.AdapterViewHolder> {

    private ArrayList<SetItem> setItems;
    private ArrayList<Integer> setCompleteCounter = new ArrayList<>();
    private ArrayList<Double> weightCounter = new ArrayList<>();
    private ArrayList<Integer> repsCounter = new ArrayList<>();
    private onItemCheckListener mListener;

    public interface onItemCheckListener {
        void onCheck(int integer);
    }



    public AdapterStartExercise(ArrayList<SetItem> setItems) {
        this.setItems = setItems;
        setHasStableIds(true);
    }

    public ArrayList<Integer> getSetCompleteCounter() {
        return setCompleteCounter;
    }

    public ArrayList<Double> getWeightCounter() {
        return weightCounter;
    }

    public ArrayList<Integer> getRepsCounter() {
        return repsCounter;
    }

    public static class AdapterViewHolder extends RecyclerView.ViewHolder {


        public TextView set;
        public TextView weightSetItem;
        public TextView repsSetItem;
        public TextView weightNum;
        public TextView repsNum;
        public ImageButton smallWeightRemove;
        public ImageButton weightRemove;
        public ImageButton weightAdd;
        public ImageButton smallWeightAdd;
        public ImageButton repsRemove;
        public ImageButton repsAdd;
        public Button okButton;
        ImageView arrow;


        ConstraintLayout expandableLayout;
        ConstraintLayout setConstraintLayout;

        public AdapterViewHolder(@NonNull final View itemView, final onItemCheckListener listener) {
            super(itemView);
            set = itemView.findViewById(R.id.set_item_tv);
            weightSetItem = itemView.findViewById(R.id.set_item_weight);
            repsSetItem = itemView.findViewById(R.id.set_item_reps);

            weightNum = itemView.findViewById(R.id.expand_weight_tv);
            repsNum = itemView.findViewById(R.id.expand_reps_tv);
            smallWeightRemove = itemView.findViewById(R.id.expand_small_weight_remove);
            smallWeightAdd = itemView.findViewById(R.id.expand_small_weight_add);
            weightRemove = itemView.findViewById(R.id.expand_weight_remove);
            weightAdd = itemView.findViewById(R.id.expand_weight_add);
            repsRemove = itemView.findViewById(R.id.expand_reps_remove);
            repsAdd = itemView.findViewById(R.id.expand_reps_add);
            okButton = itemView.findViewById(R.id.expand_menu_ok_button);
            arrow = itemView.findViewById(R.id.set_item_arrow_down);

            setConstraintLayout = itemView.findViewById(R.id.setConstraintLayout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            expandableLayout.setVisibility(View.GONE);

        }
    }


    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_set_item, viewGroup, false);
        AdapterViewHolder avh = new AdapterViewHolder(v, mListener);
        return avh;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterViewHolder adapterViewHolder, final int position) {
            final SetItem setItem = setItems.get(position);
            int setPosition = position+1;
            adapterViewHolder.set.setText("Set " + setPosition);



            setItem.setExpanded(false);

            adapterViewHolder.setConstraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!setItem.getChecked()&&!setItem.isExpanded()){
                        setItem.setExpanded(true);
                        adapterViewHolder.arrow.setImageResource(R.drawable.ic_keyboard_arrow_down_24px);
                        adapterViewHolder.expandableLayout.setVisibility(View.VISIBLE);
                    } else {
                        setItem.setExpanded(false);
                        adapterViewHolder.arrow.setImageResource(R.drawable.ic_keyboard_arrow_left_24px);
                        adapterViewHolder.expandableLayout.setVisibility(View.GONE);
                    }
                }
            });

        adapterViewHolder.smallWeightRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double count = weightCounter.get(position);
                double newCount = count - 1.25;
                if (newCount < 0) {
                    newCount = 0;
                }
                String value = String.valueOf(newCount);
                adapterViewHolder.weightNum.setText(value);
                weightCounter.set(position, newCount);
            }
        });

        adapterViewHolder.smallWeightAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double count = weightCounter.get(position);
                double newCount = count + 1.25;
                if (newCount > 99) {
                    newCount = 99;
                }
                String value = String.valueOf(newCount);
                adapterViewHolder.weightNum.setText(value);
                weightCounter.set(position, newCount);
            }
        });

        adapterViewHolder.weightRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double count = weightCounter.get(position);
                double newCount = count - 10;
                if (newCount < 0) {
                    newCount = 0;
                }
                String value = String.valueOf(newCount);
                adapterViewHolder.weightNum.setText(value);
                weightCounter.set(position, newCount);
            }
        });

        adapterViewHolder.weightAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double count = weightCounter.get(position);
                double newCount = count + 10;
                if (newCount > 99) {
                    newCount = 99;
                }
                String value = String.valueOf(newCount);
                adapterViewHolder.weightNum.setText(value);
                weightCounter.set(position, newCount);
            }
        });

        adapterViewHolder.repsRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = repsCounter.get(position);
                count--;
                if (count < 0) {
                    count = 0;
                }
                String value = String.valueOf(count);
                adapterViewHolder.repsNum.setText(value);
                repsCounter.set(position, count);
            }
        });

        adapterViewHolder.repsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = repsCounter.get(position);
                count++;
                if (count > 99) {
                    count = 99;
                }
                String value = String.valueOf(count);
                adapterViewHolder.repsNum.setText(value);
                repsCounter.set(position, count);
            }
        });

        adapterViewHolder.okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (adapterViewHolder.weightNum.getText().toString().equals("0") ||
                    adapterViewHolder.repsNum.getText().toString().equals("0")){
                    Toast.makeText(v.getContext(), "Please complete both weight & reps", Toast.LENGTH_LONG).show();
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(v.getContext()).create();
                    dialog.setTitle("SET COMPLETE");
                    dialog.setMessage("Would you like to add these stats at your set?");
                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "ADD", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setCompleteCounter.add(1);
                            SetItem newSetItem = new SetItem();
                            newSetItem.setChecked(true);
                            double weight = weightCounter.get(position);
                            int reps = repsCounter.get(position);
                            newSetItem.setWeight(weight);
                            newSetItem.setReps(reps);

                            if (weight % 1 == 0) {
                                int intWeight = (int) weight;
                                String newWeight = Integer.toString(intWeight);
                                adapterViewHolder.weightSetItem.setText(newWeight + " KG");
                            } else {
                                String strWeight = Double.toString(weight);
                                adapterViewHolder.weightSetItem.setText(strWeight + " KG");
                            }

                            String strReps = Integer.toString(reps);
                            adapterViewHolder.repsSetItem.setText(strReps + " REPS");
                            setItems.set(position, newSetItem);
                            adapterViewHolder.expandableLayout.setVisibility(View.GONE);
                            adapterViewHolder.setConstraintLayout.setBackgroundColor(Color.parseColor("#03dac6"));
                            adapterViewHolder.setConstraintLayout.setBackgroundResource(R.drawable.custom_border_set_complete);
                            adapterViewHolder.arrow.setVisibility(View.INVISIBLE);
                            notifyDataSetChanged();
                        }

                    });

                    dialog.show();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return setItems.size();
    }
}
