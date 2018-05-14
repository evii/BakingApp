package com.example.android.bakingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.android.bakingapp.objects_adapters.Ingredient;
import com.example.android.bakingapp.objects_adapters.Recipe;
import com.example.android.bakingapp.objects_adapters.RecipesAdapter;
import com.example.android.bakingapp.objects_adapters.Step;
import com.example.android.bakingapp.utils.RetrofitClient;
import com.example.android.bakingapp.utils.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.ItemClickListener {

    private ProgressDialog progressDialog;
    // @BindView(R.id.resultTV) TextView result;
    private RecipesAdapter adapter;
    private List<Recipe> mRecipeList;
    public static final String INGREDIENTS_LIST = "INGREDIENTS_LIST";
    public static final String STEPS_LIST = "STEPS_LIST";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //     ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());

        //progress dialog for loading data
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.show();

        //reading recipe data from the url - Retrofit
        RetrofitInterface retrofitInterface = RetrofitClient.getRetrofitInstance().create(RetrofitInterface.class);
        Call<List<Recipe>> call = retrofitInterface.getAllRecipes();
        call.enqueue(new Callback<List<Recipe>>() {

            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                progressDialog.dismiss();
                generateRecipeGrid(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Method to generate List of data using RecyclerView with custom adapter
    private void generateRecipeGrid(List<Recipe> recipeList) {
        mRecipeList = recipeList;
        String name;
        String names = "";
        for (int i = 0; i < mRecipeList.size(); i++) {
            Recipe recipe = mRecipeList.get(i);
            name = recipe.getName();
            names = names + ", " + name;
        }


        // measure the screen width to define if tablet or phone:
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = Math.round(displayMetrics.widthPixels / displayMetrics.density);
        int numberOfColumns;
        if (width < 600) {
            numberOfColumns = 1;
        } else {
            numberOfColumns = 3;
        }

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recipes_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new RecipesAdapter(this, mRecipeList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {

        ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>) mRecipeList.get(position).getIngredients();
        ArrayList<Step> steps = (ArrayList<Step>) mRecipeList.get(position).getSteps();

        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putParcelableArrayListExtra(INGREDIENTS_LIST, ingredients);

        intent.putParcelableArrayListExtra(STEPS_LIST, steps);
        startActivity(intent);
    }

}


