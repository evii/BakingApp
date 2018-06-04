package com.example.android.bakingapp.widget_utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import com.example.android.bakingapp.MainActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.objects_adapters.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import timber.log.Timber;

/**
 * Created by evi on 22. 5. 2018.
 */

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Context mContext = null;
    List<Ingredient> mIngredientsList = new ArrayList<>();




    public ListRemoteViewsFactory(Context appContext, Intent intent) {
        mContext = appContext;
    }


    @Override
    public void onCreate() {


    }

    @Override
    public void onDataSetChanged() {
        //getting list of ingredients from shared preferences
        SharedPreferences pref = mContext.getSharedPreferences("SharedPrefForWidget", 0);
        String ingredientsString = pref.getString("IngredientsForWidget", "");
       Timber.d(ingredientsString);

        Gson gson = new Gson();
        //List<Ingredient> ingredientsFromShared = new ArrayList<>();
        Type type = new TypeToken<List<Ingredient>>() {
        }.getType();
        mIngredientsList = gson.fromJson(ingredientsString, type);

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredientsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                R.layout.ingredient_item);

        view.setTextViewText(R.id.quantity_tv, String.valueOf(mIngredientsList.get(position).getQuantity()));
        view.setTextViewText(R.id.measure_tv, mIngredientsList.get(position).getMeasure());
        view.setTextViewText(R.id.ingredient_tv, mIngredientsList.get(position).getIngredient());

        Intent fillInIntent1 = new Intent();
        Intent fillInIntent2 = new Intent();
        Intent fillInIntent3 = new Intent();

        view. setOnClickFillInIntent(R.id.ingredient_tv, fillInIntent1);
        view. setOnClickFillInIntent(R.id.measure_tv, fillInIntent2);
        view. setOnClickFillInIntent(R.id.quantity_tv, fillInIntent3);
        return view;


    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
