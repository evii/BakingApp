package com.example.android.bakingapp;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        StepsIngredientsFragment fragment = new StepsIngredientsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.recipe_detail_container,fragment).commit();

    }
    // to be deleted - just for layout check purposes
    public void openDetail (View view){
        Intent i = new Intent(this, StepDetailActivity.class);
        startActivity(i);
    }
}
