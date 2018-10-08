package com.maddy.instagramclone.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.maddy.instagramclone.R;
import com.maddy.instagramclone.adapter.SectionPagerAdapter;
import com.maddy.instagramclone.fragment.CameraFragment;
import com.maddy.instagramclone.fragment.GalleryFragment;
import com.maddy.instagramclone.fragment.HomeFragment;
import com.maddy.instagramclone.fragment.MessageFragment;
import com.maddy.instagramclone.fragment.PhotoFragment;
import com.maddy.instagramclone.helper.BottomNavigationViewHelper;
import com.maddy.instagramclone.util.Permission;

import java.security.Permissions;

public class ShareActivity extends BaseActivity {

    private static final String TAG = "ShareActivity";
    private Context mContext = ShareActivity.this;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        if(Permission.checkPermissionsArray(Permission.PERMISSIONS, mContext)){
            setupViewPager();
        }else{
            Permission.verifyPermissions(Permission.PERMISSIONS, mContext);
        }

    }


    /**
     * Responsible for adding 3 tabs: Camera, Home and Message
     */

    private void setupViewPager() {
        Log.d(TAG, "setupViewPager: setting up pagerview and tabs.");
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GalleryFragment()); //index 0
        adapter.addFragment(new PhotoFragment());
        mViewPager = (ViewPager)findViewById(R.id.view_pager_container);
        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_bottom);
        tabLayout.setupWithViewPager(mViewPager);

        //setting title for TabLayout
        tabLayout.getTabAt(0).setText(getString(R.string.gallery));
        tabLayout.getTabAt(1).setText(getString(R.string.photo));

    }

}
