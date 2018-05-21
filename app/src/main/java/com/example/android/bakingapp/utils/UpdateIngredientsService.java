package com.example.android.bakingapp.utils;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.android.bakingapp.MainActivity;
import com.example.android.bakingapp.StepsIngredientsFragment;
import com.example.android.bakingapp.objects_adapters.Ingredient;

import java.util.List;

/**
 * Created by evi on 21. 5. 2018.
 */

public class UpdateIngredientsService extends IntentService {

    public static final String ACTION_UPDATE_INGREDIENTS = "com.example.android.bakingapp.utils.action.update_ingredients";
    private List<Ingredient> mIngredients;

    public UpdateIngredientsService() {
        super("UpdateIngredientsService");
    }



    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            mIngredients = intent.getParcelableArrayListExtra(MainActivity.INGREDIENTS_LIST);
            final String action = intent.getAction();
            if (ACTION_UPDATE_INGREDIENTS.equals(action)) {
                handleActionUpdateIngredients();
            }
        }

    }

    private void handleActionUpdateIngredients() {


        String widgetText = StepsIngredientsFragment.putIngredientsInString(mIngredients);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidgetProvider.class));
        //Now update all widgets
        BakingAppWidgetProvider.updateAppWidgets(this, appWidgetManager, widgetText, appWidgetIds);


    }



}