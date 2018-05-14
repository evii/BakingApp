package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bakingapp.objects_adapters.Ingredient;
import com.example.android.bakingapp.objects_adapters.Step;
import com.example.android.bakingapp.objects_adapters.StepAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by evi on 6. 5. 2018.
 */

public class StepsIngredientsFragment extends Fragment {
    private List<Step> mSteps;
    private List<Ingredient> mIngredients;
    @BindView(R.id.ingredients_tv) TextView ingredientsTv;
    private RecyclerView mRecyclerView;
    private StepAdapter mStepAdapter;

    public StepsIngredientsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_steps_ingredients, container, false);

        // getting Ingredients and Steps from MainActivity
        if (getArguments() != null) {
            mIngredients = getArguments().getParcelableArrayList(MainActivity.INGREDIENTS_LIST);
            mSteps = getArguments().getParcelableArrayList(MainActivity.STEPS_LIST);
        }
        ButterKnife.bind(this, rootview);

        // Displaying Ingredients
        String finalIngredients = putIngredientsInString(mIngredients);
        ingredientsTv.setText(finalIngredients);

        // Displaying Steps
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.steps_rv);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
/**
        List data = new ArrayList<Step>();
        for (int i = 0; i < data.size(); i++)
        {
            data.add(
                    new DataNote
                            (
                                    DataNoteImformation.id[i],
                                    DataNoteImformation.textArray[i],
                                    DataNoteImformation.dateArray[i]
                            ));
        }
*/
        mStepAdapter = new StepAdapter(mSteps);
        mRecyclerView.setAdapter(mStepAdapter);





        return rootview;
    }



    private String putIngredientsInString(List<Ingredient> ingredients) {
        String ingredientsFinalString = getResources().getString(R.string.ingredients) + "\n";
        for (int i = 0; i < ingredients.size(); i++) {
            double quantity = ingredients.get(i).getQuantity();
            String measure = ingredients.get(i).getMeasure();
            String ingredient = ingredients.get(i).getIngredient();

            ingredientsFinalString = ingredientsFinalString + quantity + " " + measure + " " + ingredient + "\n";
        }

        return ingredientsFinalString;
    }


}
