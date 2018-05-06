package com.example.android.bakingapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by evi on 5. 5. 2018.
 */

public class Recipe {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("servings")
    private Integer mServings;

    public Recipe (int id, String name, int servings) {
        mId = id;
        mName = name;
        mServings = servings;
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

    /**public static class RecipeResult {

        @SerializedName("")
        private List<Recipe> mRecipes;

        public List<Recipe> getAllRecipes() {
            return mRecipes;
        }
    }
*/

}
