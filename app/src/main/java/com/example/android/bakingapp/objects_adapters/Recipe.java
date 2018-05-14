package com.example.android.bakingapp.objects_adapters;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by evi on 5. 5. 2018.
 */

public class Recipe implements Parcelable{

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

    // Parcelable implementation
    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    protected Recipe(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mServings = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeInt(mServings);
        parcel.writeList(mIngredients);
        parcel.writeList(mSteps);
    }

}
