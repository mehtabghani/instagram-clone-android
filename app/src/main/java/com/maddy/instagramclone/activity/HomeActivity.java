package com.maddy.instagramclone.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.maddy.instagramclone.R;
import com.maddy.instagramclone.adapter.SectionPagerAdapter;
import com.maddy.instagramclone.fragment.CameraFragment;
import com.maddy.instagramclone.fragment.HomeFragment;
import com.maddy.instagramclone.fragment.MessageFragment;
import com.maddy.instagramclone.helper.BottomNavigationViewHelper;
import com.maddy.instagramclone.helper.FireBaseHelper;
import com.maddy.instagramclone.helper.iFireBaseListener;
import com.maddy.instagramclone.util.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";
    private Context mContext = HomeActivity.this;
    private FireBaseHelper mFireBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: starting");

        initFireBase();
        initImageLoader();
        setupViewPager();
        super.setupBottomNavigationView();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: method called.");
        super.onStart();
        checkIfUserLoggedIn();
    }


    //**************************** FIREBASE ***********************

    private void initFireBase() {
        Log.d(TAG, "initFireBase: init FireBaseHelper");
        mFireBaseHelper = new FireBaseHelper(mContext);
    }

    private void checkIfUserLoggedIn() {

        if (mFireBaseHelper.checkIfUserLoggedIn()) {
            Log.d(TAG, "checkIfUserLoggedIn: firebase, user is already logged in");
            return;
        }

        Log.d(TAG, "checkIfUserLoggedIn: firebase, user is not logged in");
        showLoginScreen();

    }

    //*******************************************************************

    protected void signOut() {
        Log.d(TAG, "onSignOut: user logged out");
        showLoginScreen();
    }
    private void showLoginScreen() {
        Log.d(TAG, "showLoginScreen: show login screen");
        Intent intent = new Intent(mContext,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(HomeActivity.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }


    /**
     * Responsible for adding 3 tabs: Camera, Home and Message
     */

    private void setupViewPager() {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CameraFragment()); //index 0
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new MessageFragment());
        ViewPager viewPager = (ViewPager)findViewById(R.id.view_pager_container);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        //setting icon for TabLayout
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_instagram);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_arrow);

    }


//    private void setupBottomNavigationView() {
//        Log.d(TAG, "setupBottomNavigationView: setting up bottom navigation view");
//
//        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottom_nav_view_bar);
//        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
//        BottomNavigationViewHelper.enableNavigation(HomeActivity.this, bottomNavigationView);
//        Menu menu = bottomNavigationView.getMenu();
//        MenuItem menuItem = menu.getItem(BottomNavigationViewHelper.getSelectedMenuIndex());
//        menuItem.setChecked(true);
//    }
}
