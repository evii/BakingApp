package com.example.android.bakingapp.objects_adapters;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by evi on 5. 5. 2018.
 */

public class Recipe {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("servings")
    private int mServings;

    @SerializedName("ingredients")
    private List<Ingredient> mIngredients = null;

    @SerializedName("steps")
    private List<Step> mSteps = null;


    public Recipe (int id, String name, int servings, List<Ingredient> ingredients, List<Step> steps) {
        mId = id;
        mName = name;
        mServings = servings;
        mIngredients = ingredients;
        mSteps = steps;
    }

    public int getId() {
        return mId;
    }

    public String getName(){
        return mName;
    }

    public int getServings(){
        return mServings;
    }

    public List<Ingredient> getIngredients(){
        return mIngredients;
    }

    public List<Step> getSteps(){
        return mSteps;
    }

    /**public static class RecipeResult {

        @SerializedName("")
        private List<Recipe> mRecipes;

        public List<Recipe> getAllRecipes() {
            return mRecipes;
        }
    }
*/

}
