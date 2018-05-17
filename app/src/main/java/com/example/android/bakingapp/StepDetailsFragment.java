package com.example.android.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

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
    private SimpleExoPlayer mPlayer;
    private long mPlaybackPosition;
    private static final String POSITION_KEY = "POSITION_KEY";

    @BindView(R.id.step_detail_tv)
    TextView stepDetailTv;

    @BindView(R.id.button_previous)
    ImageView stepPreviousButton;

    @BindView(R.id.button_next)
    ImageView stepNextButton;

    @BindView(R.id.playerView)
    SimpleExoPlayerView playerView;


    public StepDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, rootView);

        // getting Step details + step position from StepsIngredientsFragment
        if (getArguments() != null) {
            mStepList = getArguments().getParcelableArrayList(StepsIngredientsFragment.STEP_DETAIL_LIST);
            mStepPosition = getArguments().getInt(StepsIngredientsFragment.POSITION_KEY);
        }

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
        Timber.v("krok?" + mDetailedDescription);

        if (savedInstanceState != null) {
            mPlaybackPosition = savedInstanceState.getLong(POSITION_KEY);
        }

        Uri videoUri = Uri.parse(mStep.getVideoURL());
        setUpVideoPlayer(videoUri);

        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPlaybackPosition = mPlayer.getCurrentPosition();
        if(outState!=null) {
            outState.putLong(POSITION_KEY, mPlaybackPosition);

        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState!=null) {
            mPlaybackPosition = savedInstanceState.getLong(POSITION_KEY);
            mPlayer.seekTo(mPlaybackPosition);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    //helper method for Previous button
    private void getPreviousStep() {
        mStepPosition = mStepPosition - 1;
        if (mStepPosition < 0) {
            Toast.makeText(getContext(), "There is no previous step", Toast.LENGTH_SHORT).show();
        } else {
            mDetailedDescription = mStepList.get(mStepPosition).getDescription();
            stepDetailTv.setText(mDetailedDescription);
        }
    }

    //helper method for Next button
    private void getNextStep() {
        mStepPosition = mStepPosition + 1;
        if (mStepPosition > mStepList.size() - 1) {
            Toast.makeText(getContext(), "There is no next step", Toast.LENGTH_SHORT).show();
        } else {
            mDetailedDescription = mStepList.get(mStepPosition).getDescription();
            stepDetailTv.setText(mDetailedDescription);
        }
    }

    // helper method to initialiaze Exoplayer

    private void setUpVideoPlayer(Uri uri) {
        if (mPlayer == null) {
            Handler mainHandler = new Handler();
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            mPlayer =
                    ExoPlayerFactory.newSimpleInstance(getActivity().getApplicationContext(), trackSelector);

            playerView.setPlayer(mPlayer);


           // Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this.getActivity(),
                    Util.getUserAgent(this.getActivity(), "BakingApp"));
            // This is the MediaSource representing the media to be played.
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(uri);
            // Prepare the player with the source.
            mPlayer.prepare(videoSource);
            mPlayer.setPlayWhenReady(true);

        }
    }

    // helper method for releasing player
    private void releasePlayer() {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }
}

