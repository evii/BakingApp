package com.example.android.bakingapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.example.android.bakingapp.objects_adapters.Ingredient;
import com.example.android.bakingapp.objects_adapters.Step;

import java.util.ArrayList;

import timber.log.Timber;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class StepDetailActivity extends AppCompatActivity {
    StepDetailsFragment stepFragment;
    int orientation;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        orientation = getResources().getConfiguration().orientation;


        ArrayList<Step> stepsList = getIntent().getExtras().getParcelableArrayList(StepsIngredientsFragment.STEP_DETAIL_LIST);
        int stepPosition = getIntent().getExtras().getInt(StepsIngredientsFragment.POSITION_KEY);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(StepsIngredientsFragment.STEP_DETAIL_LIST, stepsList);
        bundle.putInt(StepsIngredientsFragment.POSITION_KEY, stepPosition);

        //test for whether the fragment was already created
        boolean addNewFragment = true;
        if (savedInstanceState != null) {
            addNewFragment = false;
        }

        if (addNewFragment) {
            stepFragment = new StepDetailsFragment();
            stepFragment.setArguments(bundle);
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.step_description_container, stepFragment, "StepDetailFragment").commit();

        } else {
            Timber.d("Use existing StepDetailsFragment.");
           /* if(orientation == ORIENTATION_LANDSCAPE){
                Timber.d("Orientationissue-LANDSCAPE");
                fragmentManager = getSupportFragmentManager();
                stepFragment = (StepDetailsFragment) fragmentManager.findFragmentByTag("StepDetailFragment");

                if (fragmentManager.findFragmentByTag("StepDetailFragment") == null) {
                    //not found
                    Timber.d("Orientationissue-fragment not found");

                }
                else{
                    Timber.d("Orientationissue-fragment found");
                   fragmentManager.beginTransaction().replace(R.id.step_description_container, stepFragment).commit();


                }
            }*/
        }
    }
}
