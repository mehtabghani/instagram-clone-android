package com.maddy.instagramclone.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.maddy.instagramclone.R;
import com.maddy.instagramclone.util.SqaureImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class  GridImageAdapter extends ArrayAdapter<String> {

    private static final String TAG = "GridImageAdapter";

    private Context mContext;
    private LayoutInflater mInflater;
    private int layoutResource;
    private String mAppend;
    private ArrayList<String> imgURLs;

    public GridImageAdapter(Context mContext,  int layoutResource, String mAppend, ArrayList<String> imgURLs) {
        super(mContext, layoutResource, imgURLs);
        this.mContext = mContext;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutResource = layoutResource;
        this.mAppend = mAppend;
        this.imgURLs = imgURLs;
    }

    private static class ViewHolder {
        SqaureImageView image;
        ProgressBar progressBar;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        /*
         View holder build pattern similat to Recycler view.
         */
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.image = (SqaureImageView)convertView.findViewById(R.id.grid_image_view);
            viewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.grid_progress_bar);

            convertView.setTag(viewHolder);
        }
        else {
           viewHolder = (ViewHolder) convertView.getTag();
        }

        String imgURL = getItem(position);

        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.displayImage(mAppend + imgURL, viewHolder.image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if(viewHolder.progressBar != null) {
                    viewHolder.progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                Log.d(TAG, "onLoadingFailed: Failed to load image: " + imageUri);
                if(viewHolder.progressBar != null) {
                    viewHolder.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if(viewHolder.progressBar != null) {
                    viewHolder.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if(viewHolder.progressBar != null) {
                    viewHolder.progressBar .setVisibility(View.GONE);
                }
            }
        });

        return convertView;
    }
}