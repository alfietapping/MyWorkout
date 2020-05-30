package com.example.myworkout;

import java.io.Serializable;
import java.util.ArrayList;

public class Workout implements Serializable {

    private String name;
    private String dateStamp;
    private int id;
    private ArrayList<Exercise> exerciseList;
    private ArrayList<Integer> exerciseCounter;

    public Workout(){

    }

    public Workout(String name, ArrayList<Exercise> exercisesist) {
        this.name = name;
        this.exerciseList = exercisesist;
    }

    public ArrayList<Integer> getExerciseCounter() {
        return exerciseCounter;
    }

    public void setExerciseCounter(ArrayList<Integer> exerciseCounter) {
        this.exerciseCounter = exerciseCounter;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExerciseList(ArrayList<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Exercise> getExerciseList() {
        return exerciseList;
    }


}





