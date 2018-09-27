package com.maddy.instagramclone.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.maddy.instagramclone.R;
import com.maddy.instagramclone.activity.AccountSettingsActivity;
import com.maddy.instagramclone.activity.ProfileActivity;
import com.maddy.instagramclone.helper.FireBaseHelper;
import com.maddy.instagramclone.model.UserAccountInfo;
import com.maddy.instagramclone.model.UserSettings;
import com.maddy.instagramclone.util.UniversalImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment{
    private static final String TAG = "ProfileFragment";

    private FireBaseHelper mFireBaseHelper;

    private TextView mTVTopProfileName, mTVDisplayName, mTVPosts, mTVFollowers, mTVFollowings, mTVDescription, mTVWebsite;
    private ProgressBar mProgressBar;
    private CircleImageView mProfilePhoto;
    private GridView mGridView;
    private Toolbar mToolbar;
    private ImageView mProfileMenu;
    private Context mContext;

    private UserSettings mSettings;


    public void setSettings(UserSettings settings) {
        this.mSettings = settings;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mContext = getActivity();

        initWidgets(view);
        Log.d(TAG, "onCreateView: started");

        mProgressBar.setVisibility(View.VISIBLE);
        initFireBase();
        setupToolbar();

        return view;
    }

    private void initWidgets (View view) {
        mTVDisplayName  = (TextView) view.findViewById(R.id.profile_tv_name);
        mTVTopProfileName = (TextView) view.findViewById(R.id.profile_name);
        mTVDescription  = (TextView) view.findViewById(R.id.profile_tv_detail);
        mTVWebsite      = (TextView) view.findViewById(R.id.profile_tv_link);
        mTVPosts        = (TextView) view.findViewById(R.id.tvPosts);
        mTVFollowers    = (TextView) view.findViewById(R.id.tvFollowers);
        mTVFollowings   = (TextView) view.findViewById(R.id.tvFollowing);
        mProfilePhoto   = (CircleImageView) view.findViewById(R.id.profile_photo);
        mProgressBar    = (ProgressBar) view.findViewById(R.id.profile_progress_bar);
        mGridView       = (GridView) view.findViewById(R.id.profile_grid_view);
        mToolbar        = (Toolbar) view.findViewById(R.id.profile_toolbar);
        mProfileMenu    = (ImageView) view.findViewById(R.id.profile_menu_image);
    }

    private void setupToolbar() {
        ((ProfileActivity)getActivity()).setSupportActionBar(mToolbar);

        mProfileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: navigating to account settings");
                Intent intent = new Intent(mContext, AccountSettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateUI() {
        Log.d(TAG, "updateUI: updating screen UI.");
        if(mSettings == null)
            return;

        UserAccountInfo accountInfo = mSettings.getAccountInfo();

        try {
            mTVTopProfileName.setText(accountInfo.getUser_name());
            mTVDisplayName.setText(accountInfo.getDisplay_name());
            mTVDescription.setText(accountInfo.getDescription());
            mTVFollowers.setText(Long.toString(accountInfo.getFollowers()));
            mTVPosts.setText(Long.toString(accountInfo.getPosts()));
            mTVFollowings.setText(Long.toString(accountInfo.getFollowing()));
            mTVWebsite.setText(accountInfo.getWebsite());


            //setting profile photo
            UniversalImageLoader.setImage(accountInfo.getProfile_photo(), mProfilePhoto, null, "");
        }
        catch (NullPointerException ex) {
            Log.e(TAG, "updateUI: NullPointerException Occurred: " + ex.getMessage());
        }

        mProgressBar.setVisibility(View.GONE);
    }


    //**************************** FIREBASE ***********************

    private void initFireBase() {
        Log.d(TAG, "initFireBase: init FireBaseHelper");
        mFireBaseHelper = new FireBaseHelper(mContext);

        DatabaseReference reference= mFireBaseHelper.getDBReference();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 1.retrieve user information from database
                final UserSettings settings = mFireBaseHelper.getUserSettings(dataSnapshot);

                setSettings(settings);
                updateUI();

                // 2. retrieve user images
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    //*******************************************************************
}
