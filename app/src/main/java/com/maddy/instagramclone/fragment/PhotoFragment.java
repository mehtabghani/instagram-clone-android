package com.maddy.instagramclone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ShareActionProvider;

import com.maddy.instagramclone.R;
import com.maddy.instagramclone.activity.ShareActivity;
import com.maddy.instagramclone.util.Permission;

public class PhotoFragment extends Fragment {

    private static final String TAG = "PhotoFragment";
    private static final int PHOTO_FRAGMENT_NUM = 1;
    private static final int CAMERA_REQUEST_CODE = 5;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        Log.d(TAG, "onCreateView: creating " + TAG);

        Button btnLaunchCamera = (Button) view.findViewById(R.id.button_launch_camera);
        btnLaunchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Launching camera.");

                if(((ShareActivity)getActivity()).getCurrentTabNumber() == PHOTO_FRAGMENT_NUM) {

                    if(Permission.checkPermissionsArray(Permission.PERMISSIONS_CAMERA, getContext())){

                        Log.d(TAG, "onClick: starting camera.");
                        /*
                        Starting camera and anticipating result in "onActivityResult"
                         */
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMERA_REQUEST_CODE);
                    }
                    else {
                        Intent intent = new Intent(getActivity(), ShareActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);


                    }

                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: method called.");

        if(requestCode == CAMERA_REQUEST_CODE){
            Log.d(TAG, "onActivityResult: Photo has been taken.");
            Log.d(TAG, "onActivityResult: attempting to navigate to share photo screen.");



        }
    }
}
