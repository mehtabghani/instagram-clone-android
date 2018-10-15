package com.maddy.instagramclone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.maddy.instagramclone.R;

public class ShareNextActivity extends BaseActivity {

    private static final String TAG = "ShareNextActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_next);

        Log.d(TAG, "onCreate: activity created.");
        Log.d(TAG, "onCreate: got selected image path " + getIntent().getStringExtra(getString(R.string.share_image)) );
    }
}
