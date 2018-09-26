package com.maddy.instagramclone.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.maddy.instagramclone.R;
import com.maddy.instagramclone.helper.FireBaseHelper;
import com.maddy.instagramclone.helper.iFireBaseListener;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    private Context mContext = LoginActivity.this;
    private FireBaseHelper mFireBaseHelper;

    //widgets
    private EditText mEmailField, mPasswordField;
    private RelativeLayout mProgressView;
    private Button mBtnLogin;
    private TextView mSignUpTextView;

    private String mEmail, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d(TAG, "onCreate: started.");
        initFireBase();
        setUpComponents();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setUpComponents() {
        Log.d(TAG, "setUpComponents: setting up widgets.");
        mProgressView   = (RelativeLayout) findViewById(R.id.relLayout_progress_view);
        mEmailField     = (EditText) findViewById(R.id.input_email);
        mPasswordField  = (EditText) findViewById(R.id.input_password);
        mBtnLogin       = (Button) findViewById(R.id.button_login);
        mSignUpTextView = (TextView) findViewById(R.id.link_signup);

        mProgressView.setVisibility(View.GONE);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogin(); 
            }
        });

        mSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignUpLinkTapped();
            }
        });
    }

    private void onLogin() {
        Log.d(TAG, "onLogin: Login button pressed");

        mEmail      = mEmailField.getText().toString();
        mPassword   = mPasswordField.getText().toString();

        if(!validateFields()) { //validation failed
            Log.d(TAG, "onLogin: Validation failed.");
            Toast.makeText(mContext, R.string.msg_validation_error, Toast.LENGTH_SHORT).show();
            return;
        }

        mProgressView.setVisibility(View.VISIBLE);
        signinViaFirebase();

    }

    private void onSignUpLinkTapped() {
        Log.d(TAG, "onSignUpLinkTapped: naivagate to Register activity. ");

        Intent intent = new Intent(mContext, RegisterActivity.class);
        startActivity(intent);
    }

    private boolean validateFields() {
        if(mEmail.isEmpty() || mPassword.isEmpty())
            return false;

        return true;
    }

    private void showHomeScreen() {
        Log.d(TAG, "showHomeScreen: Activity started.");
        Intent intent = new Intent(mContext, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    //**************************** FIREBASE ***********************


    private void initFireBase() {
        Log.d(TAG, "initFireBase: init FireBaseHelper");
        mFireBaseHelper = new FireBaseHelper(mContext);
    }

    private void signinViaFirebase() {
        Log.d(TAG, "signinViaFirebase: Firebase authentication started.");
        mFireBaseHelper.signIn(mEmail, mPassword, new iFireBaseListener() {
            @Override
            public void onCompletion(FirebaseUser currentUser) {
                mProgressView.setVisibility(View.GONE);
                showHomeScreen();
            }

            @Override
            public void onFailure() {
                mProgressView.setVisibility(View.GONE);
            }
        });
    }


    //*******************************************************************

}