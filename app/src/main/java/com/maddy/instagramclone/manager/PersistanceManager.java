package com.maddy.instagramclone.manager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.maddy.instagramclone.interfaces.CompletionListener;
import com.maddy.instagramclone.helper.FireBaseHelper;
import com.maddy.instagramclone.model.UserSettings;

public class PersistanceManager {

    private static final String TAG = "PersistanceManager";

    private static PersistanceManager instance;
    private FireBaseHelper mFireBaseHelper;
    private Context mContext;

    private UserSettings mUserSettings;

    public static PersistanceManager getInstance() {
        if(instance == null) {
            instance = new PersistanceManager();
        }

        return instance;
    }

    public PersistanceManager() {
        Log.d(TAG, "PersistanceManager: initialised");
    }

    public PersistanceManager init(Context context) {
        mContext = context;
        if(mFireBaseHelper == null && context != null) {
            mFireBaseHelper = new FireBaseHelper(context);
        }
        else {
            mFireBaseHelper.setContext(context);
        }

        return getInstance();
    }

    public void getUserSettings(final CompletionListener listener) {

        Log.d(TAG, "getUserSettings: getting user settings.");

        DatabaseReference reference= mFireBaseHelper.getDBReference();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 1.retrieve user information from database
                mUserSettings = mFireBaseHelper.getUserSettings(dataSnapshot);

                if (listener != null) {
                    listener.onSuccess(mUserSettings);
                }

                // 2. retrieve user images
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if (listener != null) {
                    listener.onFail(null);
                }
            }
        });

    }


    //setters

    public UserSettings getmUserSettings() {
        return mUserSettings;
    }

}
