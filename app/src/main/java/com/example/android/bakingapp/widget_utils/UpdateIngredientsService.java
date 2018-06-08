package com.example.android.bakingapp.widget_utils;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.android.bakingapp.R;

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
