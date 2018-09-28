package com.maddy.instagramclone.helper;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.maddy.instagramclone.model.UserSettings;
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

    public FirebaseDatabase getDatabase() {
        return mDatabase;
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
        Log.d(TAG, "checkIfUserNameExists: checking if \"" + userName + "\" already exists.");

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


    /**
     * Create new User
     * @param email
     * @param username
     * @param desc
     * @param website
     * @param profilePhotoUrl
     */
    public void addNewUser(final String email, final String username, final String desc, final String website, final String profilePhotoUrl) {
        Log.d(TAG, "addNewUser: creating user data.");

        final User user= new User(mUserID, email, 1, StringManipulation.condenseUsername(username));

        if(user == null) {
            Log.d(TAG, "addNewUser: user object is null.");
        }

        mDBReference.child(mContext.getString(R.string.dbname_user))
                .child(mUserID)
                .setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        addUserAccountInfo(user.getUser_name(), desc, website, profilePhotoUrl);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Failed to add user data.");
                    }
                });

    }

    /**
     * Create new user account info in DB
     * @param username
     * @param desc
     * @param website
     * @param profilePhotoUrl
     */
    private void addUserAccountInfo(final String username, final String desc, final String website, final String profilePhotoUrl) {
        Log.d(TAG, "addUserAccountInfo: creating user account info.");

        UserAccountInfo userInfo = new UserAccountInfo(username, username, desc, profilePhotoUrl, website, 0,0, 0);
        mDBReference.child(mContext.getString(R.string.dbname_user_account_info))
                .child(mUserID)
                .setValue(userInfo)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Failed to add user account info data.");
                    }
                });
    }


    /**
     * Retrieving user account info from DB.
     * Collection name: user_account_info
     * @param dataSnapshot
     * @return
     */
    public UserSettings getUserSettings(DataSnapshot dataSnapshot) {
        Log.d(TAG, "getUserSettings: getting user account info from firebase.");

        User user = new User();
        UserAccountInfo accountInfo = new UserAccountInfo();

        final String dbNameUser = mContext.getString(R.string.dbname_user);
        final String dbNameUserAccount = mContext.getString(R.string.dbname_user_account_info);

        mUserID = mAuth.getCurrentUser().getUid();

        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            DataSnapshot child = ds.child(mUserID);
            try {
                if(ds.getKey().equals(dbNameUserAccount)) {
                    Log.d(TAG, "getUserSettings -> user_account_info: datasnapshot: " + ds);

                    accountInfo.setDisplay_name(child.getValue(UserAccountInfo.class).getDisplay_name());
                    accountInfo.setUser_name(child.getValue(UserAccountInfo.class).getUser_name());
                    accountInfo.setDescription(child.getValue(UserAccountInfo.class).getDescription());
                    accountInfo.setFollowers(child.getValue(UserAccountInfo.class).getFollowers());
                    accountInfo.setFollowing(child.getValue(UserAccountInfo.class).getFollowing());
                    accountInfo.setPosts(child.getValue(UserAccountInfo.class).getPosts());
                    accountInfo.setWebsite(child.getValue(UserAccountInfo.class).getWebsite());
                    accountInfo.setProfile_photo(child.getValue(UserAccountInfo.class).getProfile_photo());
                }
                else if(ds.getKey().equals(dbNameUser)) {
                    Log.d(TAG, "getUserSettings -> user: datasnapshot: " + ds);

                    user.setUser_id(child.getValue(User.class).getUser_id());
                    user.setEmail(child.getValue(User.class).getEmail());
                    user.setUser_name(child.getValue(User.class).getUser_name());
                    user.setPhone_number(child.getValue(User.class).getPhone_number());
                }
            }
            catch (NullPointerException ex) {
                Log.e(TAG, "getUserSettings: NullPointerException Occurred: " + ex.getMessage());
            }
        }

        return new UserSettings(user, accountInfo);
    }

}
