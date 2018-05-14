package com.example.android.bakingapp.objects_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by evi on 15. 5. 2018.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private List<Step> mSteps;

    public StepAdapter(List<Step> data) {
        mSteps = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.step_description_tv)
        TextView stepDescriptionTv;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public StepAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepAdapter.ViewHolder holder, final int position) {
        holder.stepDescriptionTv.setText(mSteps.get(position).getShortDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Item " + position + " is clicked.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }
}


