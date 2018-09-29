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

import com.maddy.instagramclone.interfaces.CompletionListener;
import com.maddy.instagramclone.R;
import com.maddy.instagramclone.manager.PersistanceManager;
import com.maddy.instagramclone.model.User;
import com.maddy.instagramclone.model.UserAccountInfo;
import com.maddy.instagramclone.model.UserSettings;
import com.maddy.instagramclone.util.UniversalImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {

    private static final String TAG = "EditProfileFragment";


    //Edit profile widgets
    private TextView mTVTopProfileName, mTVChangePhoto;
    private EditText mTVDisplayName, mTVUserName, mTVDescription, mTVWebsite, mTVEmail, mTVPhoneNumber;
    private CircleImageView mProfilePhoto;
    private ImageView mBackButtonImage;

    private Context mContext;
    private PersistanceManager mPersistance;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started.");

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        mContext = getActivity();


        initWidgets(view);
        setupBackButton();
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

        mBackButtonImage = (ImageView) view.findViewById(R.id.edit_profile_back_image);
        mTVTopProfileName = (TextView) view.findViewById(R.id.profile_name);
        mTVDisplayName  = (EditText) view.findViewById(R.id.display_name);
        mTVUserName     = (EditText) view.findViewById(R.id.username);
        mTVDescription  = (EditText) view.findViewById(R.id.description);
        mTVWebsite      = (EditText) view.findViewById(R.id.website);
        mTVEmail        = (EditText) view.findViewById(R.id.email);
        mTVPhoneNumber  = (EditText) view.findViewById(R.id.phoneNumber);
        mTVChangePhoto  = (TextView) view.findViewById(R.id.tv_change_profile_photo);
        mProfilePhoto   = (CircleImageView) view.findViewById(R.id.edit_profile_photo);

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


            //setting profile photo
            UniversalImageLoader.setImage(accountInfo.getProfile_photo(), mProfilePhoto, null, "");
        }
        catch (NullPointerException ex) {
            Log.e(TAG, "updateUI: NullPointerException Occurred: " + ex.getMessage());
        }
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

    private void editPrfoileSettings() {
       // mTVUserName.va

    }

}
