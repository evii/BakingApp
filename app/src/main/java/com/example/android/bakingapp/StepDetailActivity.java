package com.example.android.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.objects_adapters.Ingredient;
import com.example.android.bakingapp.objects_adapters.Step;

import java.util.ArrayList;

import timber.log.Timber;

public class StepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);


        ArrayList<Step> stepsList = getIntent().getExtras().getParcelableArrayList(StepsIngredientsFragment.STEP_DETAIL_LIST);
        int stepPosition = getIntent().getExtras().getInt(StepsIngredientsFragment.POSITION_KEY);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(StepsIngredientsFragment.STEP_DETAIL_LIST,stepsList);
        bundle.putInt(StepsIngredientsFragment.POSITION_KEY, stepPosition);

        StepDetailsFragment stepFragment = new StepDetailsFragment();
        stepFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.step_description_container,stepFragment).commit();
    }
}
