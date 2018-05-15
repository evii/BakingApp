package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.objects_adapters.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by evi on 7. 5. 2018.
 */

public class StepDetailsFragment extends Fragment {

    private Step mStep;
    private int mStepPosition;
    private ArrayList<Step> mStepList;
    private String mDetailedDescription;

    @BindView(R.id.step_detail_tv)
    TextView stepDetailTv;

    @BindView(R.id.button_previous)
    ImageView stepPreviousButton;

    @BindView(R.id.button_next)
    ImageView stepNextButton;

    public StepDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, rootView);

        // getting Step details + position from StepsIngredientsFragment
        if (getArguments() != null) {
            mStepList = getArguments().getParcelableArrayList(StepsIngredientsFragment.STEP_DETAIL_LIST);
            mStepPosition = getArguments().getInt(StepsIngredientsFragment.POSITION_KEY);
        }

        Timber.v("pocetkroku" + mStepList.size());
        // Populate the View with the detailed step description
        mStep = mStepList.get(mStepPosition);
        mDetailedDescription = mStep.getDescription();
        stepDetailTv.setText(mDetailedDescription);

        // set functionality for Previous button - to get previous Step
        stepPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPreviousStep();

            }
        });

        // set functionality for Next button - to get next Step
        stepNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNextStep();

            }
        });


        return rootView;
    }

    private void getPreviousStep() {
        mStepPosition = mStepPosition - 1;
        if (mStepPosition < 0) {
            Toast.makeText(getContext(), "There is no previous step", Toast.LENGTH_SHORT).show();
        } else {
            mDetailedDescription = mStepList.get(mStepPosition).getDescription();
            stepDetailTv.setText(mDetailedDescription);
        }
    }

    private void getNextStep() {
        mStepPosition = mStepPosition + 1;
        if (mStepPosition > mStepList.size()-1) {
            Toast.makeText(getContext(), "There is no next step", Toast.LENGTH_SHORT).show();
        } else {
            mDetailedDescription = mStepList.get(mStepPosition).getDescription();
            stepDetailTv.setText(mDetailedDescription);
        }
    }

}

