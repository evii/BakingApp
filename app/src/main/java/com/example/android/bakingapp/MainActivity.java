package com.example.android.bakingapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.utils.RecipesAdapter;
import com.example.android.bakingapp.utils.RetrofitClient;
import com.example.android.bakingapp.utils.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.ItemClickListener{

    ProgressDialog progressDialog;
   // @BindView(R.id.resultTV) TextView result;
    RecipesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

   //     ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());

        //progress dialog for loading data
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("");
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
        String name;
        String names = "";
        for (int i = 0; i < recipeList.size(); i++) {
            Recipe recipe = recipeList.get(i);
            name = recipe.getName();
            names = names+ ", " + name;
        }
        Timber.d(String.valueOf(recipeList.size()));
        Timber.d(names);



        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recipes_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecipesAdapter(this, recipeList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    }


