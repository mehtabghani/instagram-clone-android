package com.maddy.instagramclone.activity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.opengl.Visibility;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.maddy.instagramclone.R;
import com.maddy.instagramclone.adapter.GridImageAdapter;
import com.maddy.instagramclone.fragment.ProfileFragment;
import com.maddy.instagramclone.helper.BottomNavigationViewHelper;
import com.maddy.instagramclone.util.UniversalImageLoader;

import java.util.ArrayList;

public class ProfileActivity extends BaseActivity {

    private static final String TAG = "ProfileActivity";
    private Context mContext = ProfileActivity.this;
    private ImageView mProfilePhoto;
    private static final int NUM_GRID_COLUMNS = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
        //setup
//        setupToolbar();
        super.setupBottomNavigationView();
//        setupActivityWidget();
//        setProfilePhoto();
//        tempGridSetup();
    }
    
    
    private void init(){
        Log.d(TAG, "init: inflating " + getString(R.string.profile_fragment));

        ProfileFragment fragment = new ProfileFragment();

        FragmentTransaction transaction = ProfileActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(getString(R.string.profile_fragment));
        transaction.commit();

    }

//    private void setupToolbar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
//        setSupportActionBar(toolbar);
//
//        ImageView imageView = (ImageView) findViewById(R.id.profile_menu_image);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "onClick: navigating to account settings");
//                Intent intent = new Intent(mContext, AccountSettingsActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//
////    private void setupBottomNavigationView() {
////        Log.d(TAG, "setupBottomNavigationView: setting up bottom navigation view");
////
////        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottom_nav_view_bar);
////        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
////        BottomNavigationViewHelper.enableNavigation(ProfileActivity.this, bottomNavigationView);
////        Menu menu = bottomNavigationView.getMenu();
////        MenuItem menuItem = menu.getItem(BottomNavigationViewHelper.getSelectedMenuIndex());
////        menuItem.setChecked(true);
////
////    }
//
//    private void setupActivityWidget() {
//        ProgressBar  progressBar = (ProgressBar) findViewById(R.id.profile_progress_bar);
//        progressBar.setVisibility(View.GONE);
//
//        mProfilePhoto = (ImageView) findViewById(R.id.profile_photo);
//
//    }
//
//    private void setProfilePhoto() {
//        Log.d(TAG, "setProfilePhoto: setting profile image");
//        String imageUrl = "cdn2.techadvisor.co.uk/cmsdata/features/3614881/Android_thumb800.jpg";
//        UniversalImageLoader.setImage(imageUrl, mProfilePhoto, null, "https://");
//
//    }
//
//    private void tempGridSetup() {
//        ArrayList<String> imgUrls = new ArrayList<>();
//
//        for (int i=0; i<20; i++)
//            imgUrls.add("cdn.animegame.me/uploads/update_gif/animegame.me14812575954185.jpg");
//
//        setupGridView(imgUrls);
//
//    }
//
//    private void setupGridView(ArrayList<String> imgURLs) {
//        GridView gridView = (GridView) findViewById(R.id.profile_grid_view);
//
//        int gridWidth = getResources().getDisplayMetrics().widthPixels; // getting screen width
//        int imageWidth = gridWidth/NUM_GRID_COLUMNS;
//
//        gridView.setColumnWidth(imageWidth);
//
//        GridImageAdapter adapter =
//                new GridImageAdapter(mContext, R.layout.layout_grid_image_view, "https://", imgURLs);
//        gridView.setAdapter(adapter);
//
//    }

}
