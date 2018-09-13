package com.maddy.instagramclone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.maddy.instagramclone.R;
import com.maddy.instagramclone.helper.BottomNavigationViewHelper;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //setup
        setupToolbar();
        setupBottomNavigationView();
    }


    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Log.d(TAG, "onMenuItemClick: clicked menu item: " + menuItem);


                switch (menuItem.getItemId()) {
                    case R.id.profile_menu:
                        Log.d(TAG, "onMenuItemClick: Navigation to Profile preferences");


                    default:
                        break;
                }

                return false;
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


    /**
     * This override method helps to show option menu
     * @param menu
     * @return
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
}
