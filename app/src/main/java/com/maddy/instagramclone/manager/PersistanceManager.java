package com.maddy.instagramclone.manager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.maddy.instagramclone.R;
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


    public void checkIfUserNameExists(final String username, final CompletionListener listener) {
        Log.d(TAG, "checkIfUserNameExists: checking if usrname: " + username + "already exists.");
        DatabaseReference ref = mFireBaseHelper.getDBReference();

        Query query = ref.child(mContext.getString(R.string.dbname_user))
                .orderByChild(mContext.getString(R.string.field_user_name))
                .equalTo(username);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) { //usrname not matched or exists.
                    //add the username
                    if(listener != null)
                        listener.onSuccess(new Boolean(false));
                }

                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    if(dataSnapshot.exists()) {
                        Log.d(TAG, "onDataChange: Match found, user name already exists.");
                        Toast.makeText(mContext, "User name already exists.", Toast.LENGTH_SHORT).show();

                        if(listener != null)
                            listener.onSuccess(new Boolean(true));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if(listener != null)
                    listener.onFail(null);
            }
        });
    }

    public void updateUserName(final String username, final CompletionListener listener) {

        Log.d(TAG, "updateUserName: updating user name");

        if(username == null)
            return;

        DatabaseReference ref = mFireBaseHelper.getDBReference();
        ref.child(mContext.getString(R.string.dbname_user))
                .child(mFireBaseHelper.getUserID())
                .child(mContext.getString(R.string.field_user_name))
                .setValue(username)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if(listener != null)
                            listener.onSuccess(null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Failed to save username on firebase.");

                        if(listener != null)
                            listener.onFail(null);
                    }
                });

        ref.child(mContext.getString(R.string.dbname_user_account_info))
                .child(mFireBaseHelper.getUserID())
                .child(mContext.getString(R.string.field_user_name))
                .setValue(username);
    }


    public void updateUserProfileInfo(final UserSettings settings, CompletionListener listener) {
        mFireBaseHelper.updateUserProfileInfo(settings, listener);
    }


    //setters

    public UserSettings getmUserSettings() {
        return mUserSettings;
    }

}
