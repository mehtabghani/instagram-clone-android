package com.maddy.instagramclone.fragment;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.maddy.instagramclone.R;
import com.maddy.instagramclone.util.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {

    private static final String TAG = "EditProfileFragment";
    private CircleImageView mProfilePhoto;
    private ImageView mBackButtonImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started.");

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        mProfilePhoto = (CircleImageView) view.findViewById(R.id.edit_profile_photo);
        mBackButtonImage = (ImageView) view.findViewById(R.id.edit_profile_back_image);

        setProfilePhoto();
        setupBackButton();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started.");

    }

    private void setProfilePhoto() {
        Log.d(TAG, "setProfilePhoto: setting profile image");
        String imageUrl = "cdn2.techadvisor.co.uk/cmsdata/features/3614881/Android_thumb800.jpg";
        UniversalImageLoader.setImage(imageUrl, mProfilePhoto, null, "https://");
    }


    private void setupBackButton() {
        
        if(mBackButtonImage == null) {
            Log.d(TAG, "setupBackButton: back button image view is null");
            return;
        }

        mBackButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: Navigating back to Profile activity.");
               getActivity().onBackPressed();
            }
        });
    }
}
