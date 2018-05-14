package com.example.android.bakingapp.objects_adapters;

import com.google.gson.annotations.SerializedName;

/**
 * Created by evi on 14. 5. 2018.
 */

public class Ingredient {

    @SerializedName("quantity")
    private double mQuantity;

    @SerializedName("measure")
    private String mMeasure;

    @SerializedName("ingredient")
    private String mIngredient;

    public Ingredient(double quantity, String measure, String ingredient) {
        mQuantity = quantity;
        mMeasure = measure;
        mIngredient = ingredient;
    }

    public double getQuantity() {
        return mQuantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public String getIngredient() {
        return mIngredient;
    }


}
