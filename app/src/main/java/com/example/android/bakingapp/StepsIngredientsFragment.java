package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.objects_adapters.Ingredient;
import com.example.android.bakingapp.objects_adapters.Step;

import java.util.List;

/**
 * Created by evi on 6. 5. 2018.
 */

public class StepsIngredientsFragment extends Fragment {
    private List<Step> mSteps;
    private List<Ingredient> mIngredients;

    public StepsIngredientsFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_steps_ingredients, container,false);

        if (getArguments() != null) {
            mIngredients = getArguments().getParcelableArrayList(MainActivity.INGREDIENTS_LIST);
            mSteps = getArguments().getParcelableArrayList(MainActivity.STEPS_LIST);
                    }
        TextView tv = rootview.findViewById(R.id.ingredients_tv);
       String thirdStep = mSteps.get(2).getShortDescription();
       tv.setText(thirdStep);



        return rootview;
    }
}
