package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.objects_adapters.Step;

import java.util.List;

/**
 * Created by evi on 7. 5. 2018.
 */

public class StepDetailsFragment extends Fragment {
    private Step mStep;

    public StepDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);

        // getting Step details from StepsIngredientsFragment
        if (getArguments() != null) {
            mStep = getArguments().getParcelable(StepsIngredientsFragment.STEP_DETAIL_LIST);
        }
        return rootView;
    }
}
