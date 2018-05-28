package com.example.android.bakingapp.retrofit_utils;

import com.example.android.bakingapp.objects_adapters.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by evi on 6. 5. 2018.
 */

public interface RetrofitInterface {

        @GET("baking.json")
        Call<List<Recipe>> getAllRecipes();
}
