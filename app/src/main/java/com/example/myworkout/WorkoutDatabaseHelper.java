package com.example.myworkout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WorkoutDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "workout";
    public static final int DB_VERSION = 1;

    public static final String WORKOUT_TABLE_NAME = "WORKOUT";
    public static final String WORKOUT_ID = "WORKOUT_ID";
    public static final String WORKOUT_NAME = "WORKOUT_NAME";

    public static final String EXERCISE_TABLE_NAME = "EXERCISE";
    public static final String EXERCISE_ID = "EX_ID";
    public static final String EXERCISE_NAME = "EXERCISE_NAME";


    public static final String WORKOUT_HISTORY_TABLE_NAME = "WORKOUT_HISTORY";
    public static final String WORKOUT_HISTORY_ID = "WORKOUT_ID";
    public static final String WORKOUT_HISTORY_NAME = "WORKOUT_NAME";
    public static final String WORKOUT_HISTORY_DATE_STAMP = "WORKOUT_HISTORY_STAMP";

    public static final String EXERCISE_HISTORY_TABLE_NAME = "EXERCISE_HISTORY";
    public static final String EXERCISE_HISTORY_ID = "EX_ID";
    public static final String EXERCISE_HISTORY_NAME = "EXERCISE_NAME";
    public static final String REPS_HISTORY = "REPS";
    public static final String SETS_HISTORY = "SETS";
    public static final String WEIGHT_HISTORY = "WEIGHT";

    public static final String SET_ITEM_TABLE_NAME = "SET_ITEM";
    public static final String SET_ITEM_REPS = "SET_ITEM_REPS";
    public static final String SET_ITEM_WEIGHT = "SET_ITEM_WEIGHT";





    WorkoutDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createWorkoutTable = "CREATE TABLE " + WORKOUT_TABLE_NAME + " (WORKOUT_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORKOUT_NAME +" TEXT)";

        String createExerciseTable = "CREATE TABLE " + EXERCISE_TABLE_NAME + " (EXERCISE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EXERCISE_NAME +" TEXT,"+
                WORKOUT_ID +" INTEGER)";

        String createWorkoutHistoryTable = "CREATE TABLE " + WORKOUT_HISTORY_TABLE_NAME + " (WORKOUT_HISTORY_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WORKOUT_HISTORY_NAME +" TEXT,"+
                WORKOUT_HISTORY_DATE_STAMP +" TEXT)";

        String createExerciseHistoryTable = "CREATE TABLE " + EXERCISE_HISTORY_TABLE_NAME + " (EXERCISE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EXERCISE_HISTORY_NAME +" TEXT,"+
                REPS_HISTORY +" INTEGER,"+
                SETS_HISTORY +" INTEGER,"+
                WEIGHT_HISTORY +" TEXT,"+
                WORKOUT_HISTORY_ID +" INTEGER)";

        String createSetItemTable = "CREATE TABLE " + SET_ITEM_TABLE_NAME + " (SET_ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SET_ITEM_REPS +" INTEGER,"+
                SET_ITEM_WEIGHT +" TEXT,"+
                WORKOUT_HISTORY_ID +" INTEGER)";


        db.execSQL(createWorkoutTable);
        db.execSQL(createExerciseTable);
        db.execSQL(createWorkoutHistoryTable);
        db.execSQL(createExerciseHistoryTable);
        db.execSQL(createSetItemTable);


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addSetItem(int reps, String weight, int workoutID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues setItemValues = new ContentValues();
        setItemValues.put(SET_ITEM_REPS, reps);
        setItemValues.put(SET_ITEM_WEIGHT, weight);
        setItemValues.put(WORKOUT_HISTORY_ID, workoutID);

        long result = db.insert(SET_ITEM_TABLE_NAME, null, setItemValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public int addWorkoutName(String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues workoutValues = new ContentValues();
        workoutValues.put(WORKOUT_NAME, data);

        long result = db.insert(WORKOUT_TABLE_NAME, null, workoutValues);

       return (int) result;

    }

    public boolean addExercise(String name, int workoutID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues exerciseValues = new ContentValues();
        exerciseValues.put(EXERCISE_NAME, name);
        exerciseValues.put(WORKOUT_ID, workoutID);

        long result = db.insert(EXERCISE_TABLE_NAME, null, exerciseValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }

    }
    public int addWorkoutHistoryName(String data, String dateStamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues workoutHistoryValues = new ContentValues();
        workoutHistoryValues.put(WORKOUT_HISTORY_NAME, data);
        workoutHistoryValues.put(WORKOUT_HISTORY_DATE_STAMP, dateStamp);

        long result = db.insert(WORKOUT_HISTORY_TABLE_NAME, null, workoutHistoryValues);



        return (int) result;

    }

    public boolean addExerciseHistory(String name, int sets, int workoutID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues exerciseHistoryValues = new ContentValues();
        exerciseHistoryValues.put(EXERCISE_HISTORY_NAME, name);
        exerciseHistoryValues.put(SETS_HISTORY, sets);
        exerciseHistoryValues.put(WORKOUT_HISTORY_ID, workoutID);

        long result = db.insert(EXERCISE_HISTORY_TABLE_NAME, null, exerciseHistoryValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }

    }


    public void deleteWorkoutfromDB(Workout workout){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(WORKOUT_TABLE_NAME, WORKOUT_ID + "=?", new String[]{String.valueOf(workout.getId())});
        db.delete(EXERCISE_TABLE_NAME, WORKOUT_ID + "=?", new String[]{String.valueOf(workout.getId())});
    }

    public void deleteWorkoutHistoryfromDB(Workout workout){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(WORKOUT_HISTORY_TABLE_NAME, WORKOUT_HISTORY_ID + "=?", new String[]{String.valueOf(workout.getId())});
        db.delete(EXERCISE_HISTORY_TABLE_NAME, WORKOUT_HISTORY_ID + "=?", new String[]{String.valueOf(workout.getId())});
        db.delete(SET_ITEM_TABLE_NAME, WORKOUT_HISTORY_ID + "=?", new String[]{String.valueOf(workout.getId())});
    }

    public Cursor getSetItemFromDB(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + SET_ITEM_TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getWorkoutNameFromDB(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + WORKOUT_TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getWorkoutHistoryNameFromDB(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + WORKOUT_HISTORY_TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getExerciseFromDB(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + EXERCISE_TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getExerciseHistoryFromDB(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + EXERCISE_HISTORY_TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}




