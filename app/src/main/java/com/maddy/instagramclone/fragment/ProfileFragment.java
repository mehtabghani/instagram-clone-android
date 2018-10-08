package com.maddy.instagramclone.fragment;

import android.accounts.Account;
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
import com.maddy.instagramclone.adapter.GridImageAdapter;
import com.maddy.instagramclone.helper.FireBaseHelper;
import com.maddy.instagramclone.interfaces.CompletionListener;
import com.maddy.instagramclone.manager.PersistanceManager;
import com.maddy.instagramclone.model.UserAccountInfo;
import com.maddy.instagramclone.model.UserSettings;
import com.maddy.instagramclone.util.UniversalImageLoader;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment{
    private static final String TAG = "ProfileFragment";


    private static final int NUM_GRID_COLUMNS = 3;


    private FireBaseHelper mFireBaseHelper;

    private TextView mTVTopProfileName, mTVDisplayName, mTVPosts, mTVFollowers, mTVFollowings, mTVDescription, mTVWebsite;
    private ProgressBar mProgressBar;
    private CircleImageView mProfilePhoto;
    private GridView mGridView;
    private Toolbar mToolbar;
    private ImageView mProfileMenu;

    private Context mContext;
    private PersistanceManager mPersistance;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mContext = getActivity();

        initWidgets(view);
        Log.d(TAG, "onCreateView: started");

        mProgressBar.setVisibility(View.VISIBLE);
        setupToolbar();
        initDataManager();
        tempGridSetup(view);
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
        setUpEditProfile(view);
    }

    private void setUpEditProfile(View view) {
        TextView editProfile  = (TextView) view.findViewById(R.id.tvEditProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "setUpEditProfile -> onClick: navigating to Edit profile fragment.");
                Intent intent = new Intent(getActivity(), AccountSettingsActivity.class);
                intent.putExtra(getString(R.string.calling_activity), getString(R.string.profile_activity));
                startActivity(intent);
            }
        });
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

    private void initDataManager() {
        mPersistance = PersistanceManager.getInstance().init(getActivity());
        mPersistance.getUserSettings(new CompletionListener() {
            @Override
            public void onSuccess(Object obj) {
                if( obj != null && obj.getClass() == UserSettings.class) {
                    updateUI((UserSettings)obj);
                }
            }

            @Override
            public void onFail(Error error) {
                Log.e(TAG, "onFail: Failed to get user settings data: ", error);
            }
        });
    }

    private void updateUI(UserSettings settings) {
        Log.d(TAG, "updateUI: updating screen UI.");
        if(settings == null)
            return;

        UserAccountInfo accountInfo = settings.getAccountInfo();

        try {
            mTVTopProfileName.setText(accountInfo.getUser_name());
            mTVDisplayName.setText(accountInfo.getDisplay_name());
            mTVDescription.setText(accountInfo.getDescription());
            mTVFollowers.setText(Long.toString(accountInfo.getFollowers()));
            mTVPosts.setText(Long.toString(accountInfo.getPosts()));
            mTVFollowings.setText(Long.toString(accountInfo.getFollowing()));
            mTVWebsite.setText(accountInfo.getWebsite());

            String photoURL = accountInfo.getProfile_photo();
            //setting profile photo
            if(photoURL == null)
                photoURL = "";

            UniversalImageLoader.setImage(photoURL, mProfilePhoto, null, "");
        }
        catch (NullPointerException ex) {
            Log.e(TAG, "updateUI: NullPointerException Occurred: " + ex.getMessage());
        }
        mProgressBar.setVisibility(View.GONE);
    }


    private void tempGridSetup(View view) {
        ArrayList<String> imgUrls = new ArrayList<>();

        for (int i=0; i<20; i++)
            imgUrls.add("cdn.animegame.me/uploads/update_gif/animegame.me14812575954185.jpg");

        setupGridView(imgUrls, view);

    }

    private void setupGridView(ArrayList<String> imgURLs, View view) {
        Log.d(TAG, "setupGridView: setting up grid view.");

        GridView gridView = (GridView) view.findViewById(R.id.profile_grid_view);

        int gridWidth = getResources().getDisplayMetrics().widthPixels; // getting screen width
        int imageWidth = gridWidth/NUM_GRID_COLUMNS;

        gridView.setColumnWidth(imageWidth);

        GridImageAdapter adapter =
                new GridImageAdapter(mContext, R.layout.layout_grid_image_view, "https://", imgURLs);
        gridView.setAdapter(adapter);

    }

}
