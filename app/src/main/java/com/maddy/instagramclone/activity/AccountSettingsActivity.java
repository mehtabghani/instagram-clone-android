package com.maddy.instagramclone.activity;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.maddy.instagramclone.R;
import com.maddy.instagramclone.adapter.SectionStatePagerAdapter;
import com.maddy.instagramclone.fragment.EditProfileFragment;
import com.maddy.instagramclone.fragment.SignOutFragment;

import java.util.ArrayList;
import java.util.List;

public class AccountSettingsActivity extends BaseActivity {

    private static final String TAG = "AccountSettingsActivity";
    private Context mContext = AccountSettingsActivity.this;
    private SectionStatePagerAdapter mStatePagerAdapter;
    private ViewPager mViewPager;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        Log.d(TAG, "onCreate: activity created");


        mViewPager = (ViewPager) findViewById(R.id.view_pager_container);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.setting_rel_layout1);

        setupFragment();
        setupBackButton();
        setUpSettingsList();
        super.setupBottomNavigationView();
    }

    private void setupFragment() {
        mStatePagerAdapter = new SectionStatePagerAdapter(getSupportFragmentManager());
        mStatePagerAdapter.addFragment(new EditProfileFragment(), getString(R.string.edit_profile_fragment));
        mStatePagerAdapter.addFragment(new SignOutFragment(), getString(R.string.sign_out_fragment));
    }

    private void setViewPager(int fragmentNumber) {
        mRelativeLayout.setVisibility(View.GONE);
        Log.d(TAG, "setViewPager: navigatin to fragment number: " + fragmentNumber);
        mViewPager.setAdapter(mStatePagerAdapter);
        mViewPager.setCurrentItem(fragmentNumber);
    }

    private void setupBackButton() {

        ImageView imageView = (ImageView) findViewById(R.id.setting_back_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: Navigating back to Profile activity.");
                finish();
            }
        });
    }

    private void setUpSettingsList () {

        List<String> options = new ArrayList<>();
        options.add(getString(R.string.edit_profile_fragment));
        options.add(getString(R.string.sign_out_fragment));

        ListView listView = (ListView) findViewById(R.id.account_setting_recycler_view);

        ArrayAdapter adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, options);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Log.d(TAG, "onItemClick: navigatin to fragment position: " + pos);
                setViewPager(pos);
            }
        });
    }


    private void setupFragments() {

    }
}
