package com.example.android.bakingapp;


import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.android.bakingapp.objects_adapters.Ingredient;
import com.example.android.bakingapp.objects_adapters.Step;
import com.example.android.bakingapp.utils.UpdateIngredientsService;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;


public class RecipeDetailActivity extends AppCompatActivity {

    static ArrayList<Ingredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        ingredients = getIntent().getParcelableArrayListExtra(MainActivity.INGREDIENTS_LIST);
        ArrayList<Step> steps = getIntent().getParcelableArrayListExtra(MainActivity.STEPS_LIST);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(MainActivity.INGREDIENTS_LIST, ingredients);
        bundle.putParcelableArrayList(MainActivity.STEPS_LIST, steps);

        //test for whether the fragment was already created
        boolean addNewFragment = true;
        if (savedInstanceState != null) {
            addNewFragment = false;
        }

        if (addNewFragment) {
            StepsIngredientsFragment fragment = new StepsIngredientsFragment();
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.recipe_detail_container,fragment).commit();

        } else {
            Timber.d("Use existing StepIngredientsFragment.");
        }
        startActionUpdateIngredients(this);


    }

    public static void startActionUpdateIngredients(Context context) {
        Intent intent = new Intent(context, UpdateIngredientsService.class);
        intent.putParcelableArrayListExtra(MainActivity.INGREDIENTS_LIST, ingredients);
        intent.setAction(UpdateIngredientsService.ACTION_UPDATE_INGREDIENTS);
        context.startService(intent);
    }

}
