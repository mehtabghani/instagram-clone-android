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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.maddy.instagramclone.R;
import com.maddy.instagramclone.helper.FireBaseHelper;
import com.maddy.instagramclone.helper.iFireBaseListener;
import com.maddy.instagramclone.model.User;
import com.maddy.instagramclone.util.StringManipulation;

public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";

    private Context mContext = RegisterActivity.this;
    private FireBaseHelper mFireBaseHelper;

    //widgets
    private EditText mEmailField, mFullNameField, mPasswordField;
    private RelativeLayout mProgressView;
    private Button mBtnRegister;

    private String mEmail, mFullName, mPassword;
    private String append = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Log.d(TAG, "onCreate: started.");
        initFireBase();
        setUpComponents();
    }

    private void setUpComponents() {
        Log.d(TAG, "setUpComponents: setting up widgets.");
        mProgressView   = (RelativeLayout) findViewById(R.id.relLayout_progress_view);
        mEmailField     = (EditText) findViewById(R.id.input_email);
        mFullNameField  = (EditText) findViewById(R.id.input_fullname);
        mPasswordField  = (EditText) findViewById(R.id.input_password);
        mBtnRegister    = (Button) findViewById(R.id.button_register);

        mProgressView.setVisibility(View.GONE);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegister();
            }
        });
    }

    private void onRegister() {
        Log.d(TAG, "onRegister: Register button pressed.");

        mEmail      = mEmailField.getText().toString();
        mFullName   = mFullNameField.getText().toString();
        mPassword   = mPasswordField.getText().toString();

        if(!validateFields()) { //validation failed
            Log.d(TAG, "onRegister: Validation failed.");
            Toast.makeText(mContext, R.string.msg_validation_error, Toast.LENGTH_SHORT).show();
            return;
        }

        mProgressView.setVisibility(View.VISIBLE);
        registerNewUser();

    }

    private boolean validateFields() {
        if(mEmail.isEmpty() || mFullName.isEmpty() || mPassword.isEmpty())
            return false;

        return true;
    }

    private void showHomeScreen() {
        Log.d(TAG, "showHomeScreen: Activity started.");
        Intent intent = new Intent(mContext, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void OnSucessOfUserRegistartion() {

        Log.d(TAG, "OnSucessOfUserRegistartion: User registered successfully. now setting up other user info.");
       final DatabaseReference reference = mFireBaseHelper.getDatabase().getReference("user");

       reference.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

               //1st CHECK: Make sure user name is not already in use
              if(mFireBaseHelper.checkIfUserNameExists(mFullName, dataSnapshot)){
                    append = reference.push().getKey().substring(3, 7);//generating random key from firebase
              }
              mFullName = mFullName + append;

               Log.d(TAG, "OnSucessOfUserRegistartion -> onDataChange: New UserName will be: \"" + mFullName+ "\"");

               //add new user to database
               mFireBaseHelper.addNewUser(mEmail, mFullName,"", "", "");

               showHomeScreen();
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {
               Log.d(TAG, "onCancelled: Failed to add user info.");
           }
       });

    }


    //**************************** FIREBASE ***********************

    private void initFireBase() {
        Log.d(TAG, "initFireBase: init FireBaseHelper");
        mFireBaseHelper = new FireBaseHelper(mContext);
    }

    private void registerNewUser() {

        mFireBaseHelper.registerNewUser(mEmail, mPassword, new iFireBaseListener() {
            @Override
            public void onCompletion(FirebaseUser currentUser) {
                mProgressView.setVisibility(View.GONE);
                OnSucessOfUserRegistartion();
            }

            @Override
            public void onFailure() {
                mProgressView.setVisibility(View.GONE);
            }
        });
    }


    //*******************************************************************


}
