package com.example.android.bakingapp.objects_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.bakingapp.R;

import java.util.List;

/**
 * Created by evi on 21. 5. 2018.
 */

public class IngredientAdapter extends ArrayAdapter<Ingredient> {

    private List<Ingredient> mIngredients;
    Context mContext;

    public IngredientAdapter(List<Ingredient> ingredients, Context context) {
        super(context, R.layout.ingredient_item, ingredients);
        mIngredients = ingredients;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.ingredient_item, parent, false);

        Ingredient ingredient = mIngredients.get(position);

        TextView quantityTv = listItem.findViewById(R.id.quantity_tv);
        quantityTv.setText(String.valueOf(ingredient.getQuantity()));

        TextView measureTv = listItem.findViewById(R.id.measure_tv);
        measureTv.setText(ingredient.getMeasure());

        TextView ingredientTv = listItem.findViewById(R.id.ingredient_tv);
        ingredientTv.setText(ingredient.getIngredient());

        return listItem;
    }
}


