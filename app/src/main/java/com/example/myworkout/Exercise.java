package com.example.myworkout;

import java.io.Serializable;
import java.util.ArrayList;

public class Exercise implements Serializable {

    private String name;
    private int reps;
    private int sets;
    private int weight;
    private boolean finished;
    private boolean expanded;
    private ArrayList<SetItem> setInfo = new ArrayList<>();

    public Exercise(String name) {
        this.name = name;
    }

    public Exercise(){

    }

    public ArrayList<SetItem> getSetInfo() {
        return setInfo;
    }

    public void setSetInfo(ArrayList<SetItem> setInfo) {
        this.setInfo = setInfo;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReps() {
        return reps;
    }

    public int getSets() {
        return sets;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }
}


