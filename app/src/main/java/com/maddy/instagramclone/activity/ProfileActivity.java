package com.maddy.instagramclone.activity;

import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.maddy.instagramclone.R;
import com.maddy.instagramclone.helper.BottomNavigationViewHelper;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    private Context mContext = ProfileActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //setup
        setupToolbar();
        setupBottomNavigationView();


        ProgressBar  progressBar = (ProgressBar) findViewById(R.id.profile_progress_bar);

        progressBar.setVisibility(View.GONE);

    }


    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);

        ImageView imageView = (ImageView) findViewById(R.id.profile_menu_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: navigating to account settings");
                Intent intent = new Intent(mContext, AccountSettingsActivity.class);
                startActivity(intent);
            }
        });
    }


    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up bottom navigation view");

        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottom_nav_view_bar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(ProfileActivity.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(BottomNavigationViewHelper.getSelectedMenuIndex());
        menuItem.setChecked(true);

    }


}
