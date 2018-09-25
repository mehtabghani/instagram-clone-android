package com.maddy.instagramclone.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    //firebase
    private FirebaseAuth mAuth;

    /**
     * Setup firebase auth object.
     */
    protected void setupFireBase() {

        Log.d(TAG, "setupFireBase: setting up firebase auth .");

        //firebase
        mAuth = FirebaseAuth.getInstance();
    }


    protected FirebaseAuth getFireBaseAuth() {
        return mAuth;
    }

    protected void signOutFireBaseAuth() {
        Log.d(TAG, "signoutFireBaseAuth: logging out user from birebase.");
        mAuth.signOut();
        onSignOut();
    }

    protected void onSignOut(){
        Log.d(TAG, "onSignOut: super method called");
    }

 }
