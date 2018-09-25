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

public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";

    private Context mContext = RegisterActivity.this;

    //widgets
    private EditText mEmailField, mFullNameField, mPasswordField;
    private RelativeLayout mProgressView;
    private Button mBtnRegister;

    private String mEmail, mFullName, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Log.d(TAG, "onCreate: started.");
        super.setupFireBase();
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

    //**************************** FIREBASE ***********************

    private void registerNewUser() {
        final FirebaseAuth auth = super.getFireBaseAuth();

        auth.createUserWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(mContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser currentUser){
        mProgressView.setVisibility(View.GONE);

        if (currentUser == null) {
            return;
        }

        showHomeScreen(); //finish activity and show home screen
    }



    //*************************************************************


}
