package com.example.android.bakingapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.bakingapp.objects_adapters.Ingredient;
import com.example.android.bakingapp.objects_adapters.Step;
import com.example.android.bakingapp.widget_utils.UpdateIngredientsService;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import timber.log.Timber;


public class RecipeDetailActivity extends AppCompatActivity {

    static ArrayList<Ingredient> ingredients;
    public static String recipeName;
    private boolean isTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        ingredients = getIntent().getParcelableArrayListExtra(MainActivity.INGREDIENTS_LIST);
        ArrayList<Step> steps = getIntent().getParcelableArrayListExtra(MainActivity.STEPS_LIST);
        recipeName = getIntent().getStringExtra(MainActivity.RECIPE_NAME);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(MainActivity.INGREDIENTS_LIST, ingredients);
        bundle.putParcelableArrayList(MainActivity.STEPS_LIST, steps);


        // test if its twopane + inflate twopane mode
        if (findViewById(R.id.video_step_ll) != null) {
            isTwoPane = true;
            Timber.d("twopane " + isTwoPane);

           boolean addNewRecipeDetailFragment = true;
            if (savedInstanceState != null) {
                addNewRecipeDetailFragment = false;
            }

            if (addNewRecipeDetailFragment) {
                StepsIngredientsFragment fragment = new StepsIngredientsFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().add(R.id.recipe_detail_container, fragment).commit();

            } else {
                Timber.d("Use existing StepIngredientsFragment.");
            }

            //test for whether the fragment was already created
            boolean addNewStepsFragment = true;
            if (savedInstanceState != null) {
                addNewStepsFragment = false;
            }

            Bundle stepBundle = new Bundle();
            stepBundle.putParcelableArrayList(StepsIngredientsFragment.STEP_DETAIL_LIST, steps);
            Timber.d("twopane" + steps.size());
            stepBundle.putInt(StepsIngredientsFragment.POSITION_KEY, 0);

            //test for whether the fragment was already created
            boolean addNewFragment = true;
            if (savedInstanceState != null) {
                addNewFragment = false;
            }

            if (addNewFragment) {
                StepDetailsFragment stepFragment = new StepDetailsFragment();
                stepFragment.setArguments(stepBundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.step_description_container, stepFragment).commit();

            } else {
                Timber.d("Use existing StepDetailsFragment.");
            }
            if (addNewStepsFragment) {
                StepDetailsFragment stepFragment;
                stepFragment = new StepDetailsFragment();

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.step_description_container, stepFragment).commit();

            } else {
                Timber.d("Use existing StepDetailsFragment.");
            }


        } else {
            isTwoPane = false;


           /* Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(MainActivity.INGREDIENTS_LIST, ingredients);
            bundle.putParcelableArrayList(MainActivity.STEPS_LIST, steps);*/

            //test for whether the fragment was already created
            boolean addNewFragment = true;
            if (savedInstanceState != null) {
                addNewFragment = false;
            }

            if (addNewFragment) {
                StepsIngredientsFragment fragment = new StepsIngredientsFragment();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.recipe_detail_container, fragment).commit();

            } else {
                Timber.d("Use existing StepIngredientsFragment.");
            }
        }

        //saving Ingredients into SharedPref
        Gson gson = new Gson();
        String json = gson.toJson(ingredients);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("SharedPrefForWidget", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("IngredientsForWidget", json);
        editor.putString("RecipeName", recipeName);
        editor.commit();

        startActionUpdateIngredients(this);

    }

    public static void startActionUpdateIngredients(Context context) {
        Intent intent = new Intent(context, UpdateIngredientsService.class);
        intent.setAction(UpdateIngredientsService.ACTION_UPDATE_INGREDIENTS);
        context.startService(intent);

    }

}
