package com.maddy.instagramclone.helper;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maddy.instagramclone.R;
import com.maddy.instagramclone.model.User;
import com.maddy.instagramclone.model.UserAccountInfo;
import com.maddy.instagramclone.util.StringManipulation;

public class FireBaseHelper {


    private static final String TAG = "FireBaseHelper";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDBReference;
    private String mUserID;
    private Context mContext;



    public FireBaseHelper(Context context) {
        Log.d(TAG, "FireBaseHelper: setting up fireBaseHelper");

        //firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mDBReference = mDatabase.getReference();
        mContext = context;
    }

    public FirebaseAuth getFireBaseAuth() {
        return mAuth;
    }

    public DatabaseReference getDBReference() {
        return mDBReference;
    }

    /**
     * Logging out user via Firebase Auth
     * @param listener
     */
    public void signOutFireBaseAuth(iFireBaseListener listener) {
        Log.d(TAG, "signoutFireBaseAuth: logging out user from birebase.");
        mAuth.signOut();

        if(listener!= null)
            listener.onCompletion(null);
    }

    /**
     * Register new user
     * @param email
     * @param password
     * @param listener
     */
    public void registerNewUser(final String email, final String password, final iFireBaseListener listener) {
        Log.d(TAG, "registerNewUser: Registering new user on Firebase");

        if(mAuth == null) {
            Log.d(TAG, "registerNewUser: Can't register new user, init failed.");
            if (listener != null)
                listener.onFailure();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                             mUserID = user.getUid();
                            if (listener != null)
                                listener.onCompletion(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(mContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            if (listener != null)
                                listener.onFailure();
                        }
                    }
                });
    }

    /**
     * Logging in user via Firebase Auth
     * @param email
     * @param password
     * @param listener
     */
    public void signIn(final String email, final String password, final iFireBaseListener listener) {
        Log.d(TAG, "signIn: Firebase authentication started.");

        if(mAuth == null) {
            Log.d(TAG, "signIn: Can't login new user, init failed.");
            if (listener != null)
                listener.onFailure();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (listener != null)
                                listener.onCompletion(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(mContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            if (listener != null)
                                listener.onFailure();                        }

                    }
                });
    }

    public boolean checkIfUserLoggedIn() {
        Log.d(TAG, "checkIfUserLoggedIn: called.");

        if(mAuth != null && mAuth.getCurrentUser() == null)
            return false;

        return true;
    }


    public boolean checkIfUserNameExists(String userName, DataSnapshot dataSnapshot) {
        Log.d(TAG, "checkIfUserNameExists: checking if " + userName + " already exists.");

        User user = new User();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Log.d(TAG, "onDataChange: Datasnapshot:\n" + ds);

            user.setUser_name(ds.getValue(User.class).getUser_name());
            Log.d(TAG, "onDataChange: userName: " + user.getUser_name());


            if(StringManipulation.expandUsername(user.getUser_name()).equals(userName)) {
                Log.d(TAG, "checkIfUserNameExists: FOUND A MATCH: "+ user.getUser_name());
                return true;
            }
        }

        return false;
    }


    public void addNewUser(String email, String username, String desc, String website, String profilePhotoUrl) {

        User user= new User(mUserID, email, 1, StringManipulation.condenseUsername(username));
        mDBReference.child(mContext.getString(R.string.dbname_user))
                .child(mUserID)
                .setValue(user);

        UserAccountInfo userInfo = new UserAccountInfo(username, username, desc, profilePhotoUrl, website, 0,0, 0);
        mDBReference.child(mContext.getString(R.string.dbname_user_account_info))
                .child(mUserID)
                .setValue(userInfo);
    }

}
