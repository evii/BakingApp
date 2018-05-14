package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.objects_adapters.Ingredient;
import com.example.android.bakingapp.objects_adapters.RecipesAdapter;
import com.example.android.bakingapp.objects_adapters.Step;
import com.example.android.bakingapp.objects_adapters.StepAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by evi on 6. 5. 2018.
 */

public class StepsIngredientsFragment extends Fragment implements StepAdapter.StepItemClickListener {
    private List<Step> mSteps;
    private List<Ingredient> mIngredients;
    @BindView(R.id.ingredients_tv) TextView ingredientsTv;
    private RecyclerView mRecyclerView;
    private StepAdapter mStepAdapter;
    public final static String STEP_DETAIL_LIST = "STEP_DETAIL_LIST";

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
        mRecyclerView = rootview.findViewById(R.id.steps_rv);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mStepAdapter = new StepAdapter(mSteps);
        mStepAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mStepAdapter);

        return rootview;
    }


    // implements itemclick for Steps
    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(view.getContext(), "Item " + position + " is clicked.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(view.getContext(), StepDetailActivity.class);
        intent.putParcelableArrayListExtra(STEP_DETAIL_LIST, (ArrayList) mSteps);
        startActivity(intent);
    }

    // helper method for displaying Ingredients
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


