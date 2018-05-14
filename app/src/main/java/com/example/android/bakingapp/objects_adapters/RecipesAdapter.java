package com.example.android.bakingapp.objects_adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;

import java.util.List;

/**
 * Created by evi on 6. 5. 2018.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private List<Recipe> mRecipeNames;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // constructor
    public RecipesAdapter(Context context, List<Recipe> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mRecipeNames = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = mRecipeNames.get(position);
       String recipeName = recipe.getName();
        holder.recipeNameTV.setText(recipeName);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mRecipeNames.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipeNameTV;

        ViewHolder(View itemView) {
            super(itemView);
            recipeNameTV = itemView.findViewById(R.id.recipe_title_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    // convenience method for getting data at click position

    public Recipe getItem(int id) {

        return mRecipeNames.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}



