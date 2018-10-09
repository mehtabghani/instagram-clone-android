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
import android.view.View;
import android.widget.TextView;

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
    private TextView mTVPermissionError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        mTVPermissionError = (TextView) findViewById(R.id.tv_permission_error);
        mTVPermissionError.setVisibility(View.VISIBLE);

        if(Permission.checkPermissionsArray(Permission.PERMISSIONS, mContext)){
            mTVPermissionError.setVisibility(View.GONE);
            setupViewPager();
        }else{
            Permission.verifyPermissions(Permission.PERMISSIONS, mContext);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        Log.d(TAG, "onRequestPermissionsResult: got permission results");
        switch (requestCode) {
            case Permission.VERIFY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mTVPermissionError.setVisibility(View.GONE);
                    setupViewPager();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d(TAG, "onRequestPermissionsResult: permission is not granted");
                    mTVPermissionError.setVisibility(View.VISIBLE);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    /**
     * Responsible for adding 2 tabs: Gallery and Photo
     */

    private void setupViewPager() {
        Log.d(TAG, "setupViewPager: setting up pagerview and tabs.");
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GalleryFragment()); //index 0
        adapter.addFragment(new PhotoFragment());
        mViewPager = (ViewPager) findViewById(R.id.view_pager_container);
        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_bottom);
        tabLayout.setupWithViewPager(mViewPager);

        //setting title for TabLayout
        tabLayout.getTabAt(0).setText(getString(R.string.gallery));
        tabLayout.getTabAt(1).setText(getString(R.string.photo));

    }

    /**
     * Return current tab number
     * 0- Gallery
     * 1- Photo
     * @return
     */
    public int getCurrentTabNumber() {
        return mViewPager.getCurrentItem();
    }

}
