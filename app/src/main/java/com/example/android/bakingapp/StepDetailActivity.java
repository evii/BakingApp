package com.example.android.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.objects_adapters.Ingredient;
import com.example.android.bakingapp.objects_adapters.Step;

import java.util.ArrayList;

public class StepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        Step step = getIntent().getExtras().getParcelable(StepsIngredientsFragment.STEP_DETAIL_LIST);

        Bundle bundle = new Bundle();
        bundle.putParcelable(StepsIngredientsFragment.STEP_DETAIL_LIST, step);

        StepDetailsFragment stepFragment = new StepDetailsFragment();
        stepFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.step_description_container,stepFragment).commit();
    }
}
