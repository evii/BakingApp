package com.example.android.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        VideoFragment videoFragment = new VideoFragment();
        StepDetailsFragment stepFragment = new StepDetailsFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.video_container,videoFragment).commit();
        fragmentManager.beginTransaction().add(R.id.step_description_container,stepFragment).commit();
    }
}
