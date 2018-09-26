package com.maddy.instagramclone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.internal.FirebaseAppHelper;
import com.maddy.instagramclone.R;
import com.maddy.instagramclone.activity.HomeActivity;
import com.maddy.instagramclone.activity.LoginActivity;
import com.maddy.instagramclone.helper.FireBaseHelper;
import com.maddy.instagramclone.helper.iFireBaseListener;

public class SignOutFragment extends Fragment {

    private static final String TAG = "SignOutFragment";
    private FireBaseHelper mFireBaseHelper;
    private RelativeLayout mProgressView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_sign_out, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setup();

    }

    private void setup() {
        mFireBaseHelper = new FireBaseHelper(getActivity());
        mProgressView = (RelativeLayout) getView().findViewById(R.id.relLayout_progress_view);
        mProgressView.setVisibility(View.GONE);

        Button btnSignout = (Button) getView().findViewById(R.id.button_signout);
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressView.setVisibility(View.VISIBLE);

                
                mFireBaseHelper.signOutFireBaseAuth(new iFireBaseListener() {
                    @Override
                    public void onCompletion(FirebaseUser currentUser) {
                        Log.d(TAG, "onCompletion: Sign out button pressed.");
                        mProgressView.setVisibility(View.GONE);
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        getActivity().startActivity(intent);
                    }

                    @Override
                    public void onFailure() {
                        mProgressView.setVisibility(View.GONE);
                    }
                });
                
             
            }
        });
    }
}
