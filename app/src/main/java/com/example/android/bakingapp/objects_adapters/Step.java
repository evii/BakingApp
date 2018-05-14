package com.example.android.bakingapp.objects_adapters;

import com.google.gson.annotations.SerializedName;

/**
 * Created by evi on 7. 5. 2018.
 */

public class Step {

    @SerializedName("id")
    private int mIdStep;

    @SerializedName("shortDescription")
    private String mShortDescription;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("videoURL")
    private String mVideoURL;

    @SerializedName("thumbnailURL")
    private String mThumbnailURL;

    public Step(int idStep, String shortDescription, String description, String videoURL, String thumbnailURL) {
        mIdStep = idStep;
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoURL = videoURL;
        mThumbnailURL = thumbnailURL;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getVideoURL() {
        return mVideoURL;
    }

}
