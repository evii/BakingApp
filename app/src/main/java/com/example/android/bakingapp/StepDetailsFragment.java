package com.example.android.bakingapp;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
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

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
    private String mVideoUrl;
    private Uri mVideoUri;
    private boolean mPlayVideoWhenReady;
    private static final String POSITION_KEY = "POSITION_KEY";
    private static final String URI_KEY = "URI_KEY";
    private static final String DESCRIPTION_KEY = "DESCRIPTION_KEY";
    private static final String PLAY_WHE_READY_KEY = "PLAY_WHE_READY_KEY";

    @BindView(R.id.step_detail_tv)
    TextView stepDetailTv;

    @BindView(R.id.button_previous)
    ImageView stepPreviousButton;

    @BindView(R.id.button_next)
    ImageView stepNextButton;

    @BindView(R.id.playerView)
    SimpleExoPlayerView playerView;

    @BindView(R.id.no_video_image)
    ImageView noVideoImageView;

    public StepDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, rootView);

        //hiding app bar in landscape mode
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE &&
                !RecipeDetailActivity.isTwoPane) {
            if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            }
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        }

        // getting Step details + step position from StepsIngredientsFragment
        if (getArguments() != null) {
            mStepList = getArguments().getParcelableArrayList(StepsIngredientsFragment.STEP_DETAIL_LIST);
            mStepPosition = getArguments().getInt(StepsIngredientsFragment.POSITION_KEY);
            mStep = mStepList.get(mStepPosition);
            mDetailedDescription = mStep.getDescription();
            stepDetailTv.setText(mDetailedDescription);

            //get video URL + check for validity
            mVideoUrl = mStep.getVideoURL();
            if (URLUtil.isValidUrl(mVideoUrl)) {
                mVideoUri = Uri.parse(mStep.getVideoURL());
            } else {
                Timber.v("Video does not have valid URL");
            }
        }

        // Populate the View with the detailed step description
        if (savedInstanceState != null) {
            mStepPosition = savedInstanceState.getInt(DESCRIPTION_KEY);
            mDetailedDescription = mStepList.get(mStepPosition).getDescription();
        }

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
        if (RecipeDetailActivity.isTwoPane) {
            stepNextButton.setVisibility(GONE);
            stepPreviousButton.setVisibility(GONE);
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mVideoUri == null) {
            playerView.setVisibility(View.INVISIBLE);
            noVideoImageView.setVisibility(View.VISIBLE);
        } else if (URLUtil.isValidUrl(mVideoUri.toString())) {
            setUpVideoPlayer(mVideoUri);
        } else {
            mPlayer.stop();
            playerView.setVisibility(View.INVISIBLE);
            noVideoImageView.setVisibility(View.VISIBLE);
        }

        if (savedInstanceState != null) {
            if (mPlayer != null) {
                mPlaybackPosition = savedInstanceState.getLong(POSITION_KEY);
                mVideoUri = Uri.parse(savedInstanceState.getString(URI_KEY));
                mPlayVideoWhenReady = savedInstanceState.getBoolean(PLAY_WHE_READY_KEY);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPlayer != null) {
            mPlaybackPosition = mPlayer.getCurrentPosition();
            mPlayVideoWhenReady = mPlayer.getPlayWhenReady();
        }
        if (outState != null) {
            if (mPlayer != null) {
                outState.putString(URI_KEY, mVideoUri.toString());
                outState.putLong(POSITION_KEY, mPlaybackPosition);
                outState.putBoolean(PLAY_WHE_READY_KEY, mPlayVideoWhenReady);
            }
            outState.putInt(DESCRIPTION_KEY, mStepPosition);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            if (mPlayer != null) {
                mVideoUri = Uri.parse(savedInstanceState.getString(URI_KEY));
            }

            if (mVideoUri == null) {
                playerView.setVisibility(View.INVISIBLE);
                noVideoImageView.setVisibility(View.VISIBLE);
            } else if (URLUtil.isValidUrl(mVideoUri.toString())) {
                playerView.setVisibility(View.VISIBLE);
                noVideoImageView.setVisibility(View.INVISIBLE);
                goToVideo(mVideoUri);
            } else {
                mPlayer.stop();
                playerView.setVisibility(View.INVISIBLE);
                noVideoImageView.setVisibility(View.VISIBLE);
            }
            if (mPlayer != null) {
                mPlaybackPosition = savedInstanceState.getLong(POSITION_KEY);
                mPlayer.seekTo(mPlaybackPosition);
                mPlayVideoWhenReady = savedInstanceState.getBoolean(PLAY_WHE_READY_KEY);
                mPlayer.setPlayWhenReady(mPlayVideoWhenReady);
            }
            // save description state
            mStepPosition = savedInstanceState.getInt(DESCRIPTION_KEY);
            stepDetailTv.setText(mStepList.get(mStepPosition).getDescription());
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
            mStepPosition = 0;
        } else {
            mDetailedDescription = mStepList.get(mStepPosition).getDescription();
            stepDetailTv.setText(mDetailedDescription);

            mVideoUri = Uri.parse(mStepList.get(mStepPosition).getVideoURL());
            if (mVideoUri == null) {
                playerView.setVisibility(View.INVISIBLE);
                noVideoImageView.setVisibility(View.VISIBLE);
            } else if (URLUtil.isValidUrl(mVideoUri.toString())) {
                playerView.setVisibility(View.VISIBLE);
                noVideoImageView.setVisibility(View.INVISIBLE);
                goToVideo(mVideoUri);
            } else {
                mPlayer.stop();
                playerView.setVisibility(View.INVISIBLE);
                noVideoImageView.setVisibility(View.VISIBLE);
            }
        }
    }

    //helper method for Next button
    private void getNextStep() {
        mStepPosition = mStepPosition + 1;
        if (mStepPosition > mStepList.size() - 1) {
            Toast.makeText(getContext(), "There is no next step", Toast.LENGTH_SHORT).show();
            mStepPosition = mStepList.size() - 1;
        } else {
            mDetailedDescription = mStepList.get(mStepPosition).getDescription();
            stepDetailTv.setText(mDetailedDescription);
            mVideoUri = Uri.parse(mStepList.get(mStepPosition).getVideoURL());
            if (mVideoUri == null) {
                playerView.setVisibility(View.INVISIBLE);
                noVideoImageView.setVisibility(View.VISIBLE);
            } else if (URLUtil.isValidUrl(mVideoUri.toString())) {
                playerView.setVisibility(View.VISIBLE);
                noVideoImageView.setVisibility(View.INVISIBLE);
                goToVideo(mVideoUri);
            } else {
                mPlayer.stop();
                playerView.setVisibility(View.INVISIBLE);
                noVideoImageView.setVisibility(View.VISIBLE);
            }
        }
    }

    // helper method to initialize Exoplayer
    private void setUpVideoPlayer(Uri uri) {
        if (mPlayer == null) {
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

    // helper method to go to next/previous video
    private void goToVideo(Uri uri) {
        mPlayer.stop();
        if (uri == null) {
            playerView.setVisibility(View.INVISIBLE);
            noVideoImageView.setVisibility(View.VISIBLE);
        } else if (URLUtil.isValidUrl(uri.toString())) {
            playerView.setVisibility(View.VISIBLE);
            noVideoImageView.setVisibility(View.INVISIBLE);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this.getActivity(),
                    Util.getUserAgent(this.getActivity(), "BakingApp"));
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(uri);
            mPlayer.prepare(videoSource);
            mPlayer.setPlayWhenReady(true);

        } else {
            mPlayer.stop();
            playerView.setVisibility(View.INVISIBLE);
            noVideoImageView.setVisibility(VISIBLE);
        }
    }

    // helper method for releasing player
    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    // helper method for getting steps
    public void setSteps(ArrayList<Step> steps) {
        mStepList = steps;
    }

    // helper method for getting steps
    public void setStepPosition(int position) {
        mStepPosition = position;
    }
}

