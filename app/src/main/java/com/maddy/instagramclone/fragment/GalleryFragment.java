package com.maddy.instagramclone.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.maddy.instagramclone.R;
import com.maddy.instagramclone.util.FilePath;
import com.maddy.instagramclone.util.FileSearch;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private static final String TAG = "GalleryFragment";

    private GridView mGridView;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Spinner mSpinner;
    private TextView mTVNext;

    private ArrayList<String> mDirectories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        Log.d(TAG, "onCreateView: creating " + TAG);

        mDirectories = new ArrayList<>();

        mGridView       = (GridView) view.findViewById(R.id.gallery_grid_view);
        mImageView      = (ImageView) view.findViewById(R.id.gallery_image);
        mProgressBar    = (ProgressBar) view.findViewById(R.id.progress_bar);
        mSpinner        = (Spinner) view.findViewById(R.id.spinner_directory );
        mTVNext         = (TextView) view.findViewById(R.id.tv_next);


        mProgressBar.setVisibility(View.GONE);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: close button tapped. navigating back to Home screen.");
                getActivity().finish();
            }
        });

        mTVNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Navigating to final share screen."); 
            }
        });

        init();

        return view;
    }

    private void init() {
        //check for other folders in "/storage/emulated/0/pictures"
        ArrayList<String> dir = FileSearch.getDirectoryPaths(FilePath.PICTURES);
        if(dir != null) {
            mDirectories = dir;
        }

        mDirectories.add(FilePath.CAMERA);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemSelected: item selected " + mDirectories.get(i));
                
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
