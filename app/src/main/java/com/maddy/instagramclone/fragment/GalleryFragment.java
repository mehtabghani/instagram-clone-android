package com.maddy.instagramclone.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.maddy.instagramclone.activity.ShareNextActivity;
import com.maddy.instagramclone.adapter.GridImageAdapter;
import com.maddy.instagramclone.util.FilePath;
import com.maddy.instagramclone.util.FileSearch;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private static final String TAG = "GalleryFragment";
    private static final int NUM_GRID_COLUMNS = 3;

    private final String urlType = "file://";

    private GridView mGridView;
    private ImageView mCloseImageView;
    private ImageView mDisplayImageView;
    private ProgressBar mProgressBar;
    private Spinner mSpinner;
    private TextView mTVNext;

    private ArrayList<String> mDirectories;
    private String mSelectedImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        Log.d(TAG, "onCreateView: creating " + TAG);

        mDirectories = new ArrayList<>();

        mGridView           = (GridView) view.findViewById(R.id.gallery_grid_view);
        mCloseImageView     = (ImageView) view.findViewById(R.id.image_view_close);
        mDisplayImageView   = (ImageView) view.findViewById(R.id.gallery_image);
        mProgressBar    = (ProgressBar) view.findViewById(R.id.progress_bar);
        mSpinner        = (Spinner) view.findViewById(R.id.spinner_directory );
        mTVNext         = (TextView) view.findViewById(R.id.tv_next);


        mProgressBar.setVisibility(View.GONE);

        mCloseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: close button tapped. navigating back to Home screen.");
                getActivity().finish();
            }
        });

        mTVNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Navigating to next share screen.");
                Intent intent = new Intent(getActivity(), ShareNextActivity.class);
                intent.putExtra(getString(R.string.share_image), mSelectedImage);
                startActivity(intent);
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

        final ArrayList<String> folders = new ArrayList<>();

        for(int i=0 ; i < mDirectories.size();i++) {
            String item = mDirectories.get(i);
            int index = item.lastIndexOf("/");
            String str = item.substring(index+1);
            folders.add(str);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, folders);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedFolder = mDirectories.get(i);
                Log.d(TAG, "onItemSelected: item selected " + selectedFolder);
                 setupGrid(selectedFolder);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void setupGrid(final String selectedDirectory){
        Log.d(TAG, "setupGrid: directory choosen: " + selectedDirectory);
        final ArrayList<String> imgURLs = FileSearch.getFilePaths(selectedDirectory);

        int gridWidth = getResources().getDisplayMetrics().widthPixels; // getting screen width
        int imageWidth = gridWidth/NUM_GRID_COLUMNS;
        mGridView.setColumnWidth(imageWidth);

        GridImageAdapter adapter =
                new GridImageAdapter(getContext(), R.layout.layout_grid_image_view, urlType, imgURLs);
        mGridView.setAdapter(adapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(!imgURLs.isEmpty())
                    showSelectedImage(imgURLs.get(position), mDisplayImageView, urlType);
            }
        });

        // Display frist image when fragment inflated.
        if(!imgURLs.isEmpty())
            showSelectedImage(imgURLs.get(0), mDisplayImageView, urlType);

    }


    private void showSelectedImage(String url, ImageView imageView, String append) {
        Log.d(TAG, "showSelectedImage: setting selected image url to Main display image view");

        mSelectedImage = url;
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(append + url, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                mProgressBar.setVisibility(View.VISIBLE);

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                mProgressBar.setVisibility(View.GONE);

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mProgressBar.setVisibility(View.GONE);

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                mProgressBar.setVisibility(View.GONE);

            }
        });
    }
}
