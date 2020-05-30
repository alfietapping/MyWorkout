package com.example.myworkout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import com.example.myworkout.AlertStartExercise.sendExercise
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(), sendExercise {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val navigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        navigationView.setOnNavigationItemSelectedListener(navListener)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MyWorkoutsFragment()).commit()
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.nav_workout_builder -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, WorkoutBuilderFragment()).commit()
            R.id.nav_my_workouts -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MyWorkoutsFragment()).commit()
            R.id.nav_workout_videos -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, WorkoutVideosFragment()).commit()
            R.id.nav_workout_history -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, WorkoutHistoryFragment()).commit()
        }
        true
    }

    override fun exerciseComplete(exercise: Exercise) {}
}
