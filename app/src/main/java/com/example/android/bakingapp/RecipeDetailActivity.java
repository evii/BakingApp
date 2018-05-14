package com.example.android.bakingapp;


import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.android.bakingapp.objects_adapters.Ingredient;
import com.example.android.bakingapp.objects_adapters.Step;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;


public class RecipeDetailActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        ArrayList<Ingredient> ingredients = getIntent().getParcelableArrayListExtra(MainActivity.INGREDIENTS_LIST);
        ArrayList<Step> steps = getIntent().getParcelableArrayListExtra(MainActivity.STEPS_LIST);


        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(MainActivity.INGREDIENTS_LIST, ingredients);
        bundle.putParcelableArrayList(MainActivity.STEPS_LIST, steps);


        StepsIngredientsFragment fragment = new StepsIngredientsFragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.recipe_detail_container,fragment).commit();

    }

}
