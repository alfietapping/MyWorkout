package com.example.myworkout;

import android.support.annotation.NonNull;

import androidx.core.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

//public class MainActivity extends AppCompatActivity implements AlertStartExercise.sendExercise {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        BottomNavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setOnNavigationItemSelectedListener(navListener);
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyWorkoutsFragment()).commit();
//
//    }
//
//    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//                case R.id.nav_workout_builder:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WorkoutBuilderFragment()).commit();
//                    break;
//                case R.id.nav_my_workouts:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyWorkoutsFragment()).commit();
//                    break;
//
//                case R.id.nav_workout_history:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WorkoutHistoryFragment()).commit();
//            }
//
//            return true;
//        }
//    };
//
//    @Override
//    public void exerciseComplete(Exercise exercise) {
//
//    }
//}
