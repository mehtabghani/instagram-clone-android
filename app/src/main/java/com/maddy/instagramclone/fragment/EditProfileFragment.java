package com.maddy.instagramclone.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.maddy.instagramclone.helper.FireBaseHelper;
import com.maddy.instagramclone.interfaces.CompletionListener;
import com.maddy.instagramclone.R;
import com.maddy.instagramclone.manager.PersistanceManager;
import com.maddy.instagramclone.model.User;
import com.maddy.instagramclone.model.UserAccountInfo;
import com.maddy.instagramclone.model.UserSettings;
import com.maddy.instagramclone.util.StringManipulation;
import com.maddy.instagramclone.util.UniversalImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {

    private static final String TAG = "EditProfileFragment";


    //Edit profile widgets
    private TextView mTVTopProfileName, mTVChangePhoto;
    private EditText mTVDisplayName, mTVUserName, mTVDescription, mTVWebsite, mTVEmail, mTVPhoneNumber;
    private CircleImageView mProfilePhoto;

    private Context mContext;
    private PersistanceManager mPersistance;
    private UserSettings mUserSettings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started.");

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        mContext = getActivity();


        initWidgets(view);
        setupBackButton(view);
        setupSavekButton(view);
        initDataManager();

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started.");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initWidgets (View view) {
        mTVTopProfileName   = (TextView) view.findViewById(R.id.profile_name);
        mTVDisplayName  = (EditText) view.findViewById(R.id.display_name);
        mTVUserName     = (EditText) view.findViewById(R.id.username);
        mTVDescription  = (EditText) view.findViewById(R.id.description);
        mTVWebsite      = (EditText) view.findViewById(R.id.website);
        mTVEmail        = (EditText) view.findViewById(R.id.email);
        mTVPhoneNumber  = (EditText) view.findViewById(R.id.phoneNumber);
        mTVChangePhoto  = (TextView) view.findViewById(R.id.tv_change_profile_photo);
        mProfilePhoto   = (CircleImageView) view.findViewById(R.id.edit_profile_photo);


        Log.d(TAG, "initWidgets: widgets have been initialised.");
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

        mUserSettings = settings;

        if(settings == null)
            return;

        User user = settings.getUser();
        UserAccountInfo accountInfo = settings.getAccountInfo();

        try {
            mTVTopProfileName.setText(accountInfo.getUser_name());
            mTVDisplayName.setText(accountInfo.getDisplay_name());
            mTVDescription.setText(accountInfo.getDescription());
            mTVWebsite.setText(accountInfo.getWebsite());
            mTVUserName.setText(accountInfo.getUser_name());
            mTVEmail.setText(user.getEmail());
            mTVPhoneNumber.setText(String.valueOf(user.getPhone_number()));

            String photoURL = accountInfo.getProfile_photo();
            //setting profile photo
            if(photoURL == null)
                photoURL = "";

            UniversalImageLoader.setImage(photoURL, mProfilePhoto, null, "");
        }
        catch (NullPointerException ex) {
            Log.e(TAG, "updateUI: NullPointerException Occurred: " + ex.getMessage());
        }
    }


    private void setupSavekButton(View view) {
        ImageView mSaveButtonImage = (ImageView) view.findViewById(R.id.edit_profile_save_image);

        if(mSaveButtonImage == null) {
            Log.d(TAG, "setupSavekButton: save button image view is null");
            return;
        }

        mSaveButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: saving profile data...");
                saveProfileInfo();
            }
        });
    }

    private void setupBackButton(View view) {
        ImageView mBackButtonImage = (ImageView) view.findViewById(R.id.edit_profile_back_image);

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


    private void saveProfileInfo() {

        Log.d(TAG, "saveProfileInfo: method called.");

        UserAccountInfo userInfo = new UserAccountInfo();
        User user = new User();

        userInfo.setUser_name(mTVUserName.getText().toString());
        userInfo.setDisplay_name(mTVDisplayName.getText().toString());
        userInfo.setDescription(mTVDescription.getText().toString());
        userInfo.setWebsite(mTVWebsite.getText().toString());
        user.setEmail(mTVEmail.getText().toString());
        user.setPhone_number(Long.parseLong(mTVPhoneNumber.getText().toString()));
        user.setUser_name(mTVUserName.getText().toString());

        //Fields that dont need to change
        user.setUser_id(mUserSettings.getUser().getUser_id());
        userInfo.setProfile_photo(mUserSettings.getAccountInfo().getProfile_photo());
        userInfo.setPosts(mUserSettings.getAccountInfo().getPosts());
        userInfo.setFollowing(mUserSettings.getAccountInfo().getFollowing());
        userInfo.setFollowers(mUserSettings.getAccountInfo().getFollowers());


        try {
            User tUser = mUserSettings.getUser();
            Log.d(TAG, "onDataChange: getting user object");
            if (!tUser.getUser_name().equals(userInfo.getUser_name())) {
                checkIfUserNameExists(new UserSettings(user, userInfo));
            } else {
                saveOtherUserInfo(new UserSettings(user, userInfo));
            }
        }catch (Exception ex) {
            Log.e(TAG, "saveProfileInfo: exception occurred", ex);
        }

    }

    private void checkIfUserNameExists(final UserSettings settings) {

        Log.d(TAG, "checkIfUserNameExists: checking if user name exists.");

        mPersistance.checkIfUserNameExists(settings.getAccountInfo().getUser_name(), new CompletionListener() {
            @Override
            public void onSuccess(Object obj) {
                if(obj.getClass().equals(Boolean.class)) {
                    Boolean isExist = ((Boolean) obj).booleanValue();

                    Log.d(TAG, "checkIfUserNameExists->onSuccess: user name exists: " + isExist.toString());

                    if(!isExist) {
                        updateUserName(settings.getAccountInfo().getUser_name());
                        saveOtherUserInfo(settings);
                    }


                }
            }

            @Override
            public void onFail(Error error) {
                Log.d(TAG, "checkIfUserNameExists->onFail: failed.");
            }
        });

    }

    private void saveOtherUserInfo(final UserSettings settings) {
        Log.d(TAG, "saveOtherUserInfo: going to save user profile.");
        mPersistance.updateUserProfileInfo(settings, new CompletionListener() {
            @Override
            public void onSuccess(Object o) {
                Log.d(TAG, "onSuccess: user profile updated.");
            }

            @Override
            public void onFail(Error error) {
                Log.e(TAG, "onFail: Failed to update user profile, Error:" + error.getMessage());
            }
        });
    }


    private void updateUserName(final String username) {
        Log.d(TAG, "updateUserName: updating user name");
        mPersistance.updateUserName(username, new CompletionListener() {
            @Override
            public void onSuccess(Object o) {
                Log.d(TAG, "onSuccess: username updated on firebase DB");
            }

            @Override
            public void onFail(Error error) {

            }
        });
    }


}
