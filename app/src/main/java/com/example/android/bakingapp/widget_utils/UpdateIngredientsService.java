package com.example.android.bakingapp.widget_utils;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.android.bakingapp.MainActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.StepsIngredientsFragment;
import com.example.android.bakingapp.objects_adapters.Ingredient;
import com.example.android.bakingapp.widget_utils.BakingAppWidgetProvider;

import java.util.List;

/**
 * Created by evi on 21. 5. 2018.
 */

public class UpdateIngredientsService extends IntentService {

    public static final String ACTION_UPDATE_INGREDIENTS = "com.example.android.bakingapp.utils.action.update_ingredients";


    public UpdateIngredientsService() {
        super("UpdateIngredientsService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_INGREDIENTS.equals(action)) {
                handleActionUpdateIngredients();
            }
        }
    }

    private void handleActionUpdateIngredients() {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_listview);

    }

}