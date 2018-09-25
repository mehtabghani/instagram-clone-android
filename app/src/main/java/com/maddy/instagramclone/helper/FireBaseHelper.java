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

public class FireBaseHelper {


    private static final String TAG = "FireBaseHelper";

    //firebase
    private FirebaseAuth mAuth;


    public FireBaseHelper() {
        Log.d(TAG, "FireBaseHelper: setting up fireBaseHelper");

        //firebase
        mAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getFireBaseAuth() {
        return mAuth;
    }

    public void signOutFireBaseAuth(iFireBaseListener listener) {
        Log.d(TAG, "signoutFireBaseAuth: logging out user from birebase.");
        mAuth.signOut();

        if(listener!= null)
            listener.onCompletion(null);
    }

    public boolean checkIfUserLoggedIn() {
        Log.d(TAG, "checkIfUserLoggedIn: called.");

        if(mAuth != null && mAuth.getCurrentUser() == null)
            return false;

        return true;
    }


    public void registerNewUser(final String email, final String password, final Context context,final iFireBaseListener listener) {
        Log.d(TAG, "registerNewUser: Registering new user on Firebase");

        if(mAuth == null) {
            Log.d(TAG, "registerNewUser: Can't register new user, init failed.");
            if (listener != null)
                listener.onFailure();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (listener != null)
                                listener.onCompletion(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            if (listener != null)
                                listener.onFailure();
                        }
                    }
                });
    }

    public void signIn(final String email, final String password, final Context context,final iFireBaseListener listener) {
        Log.d(TAG, "signIn: Firebase authentication started.");

        if(mAuth == null) {
            Log.d(TAG, "signIn: Can't login new user, init failed.");
            if (listener != null)
                listener.onFailure();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
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
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            if (listener != null)
                                listener.onFailure();                        }

                    }
                });
    }

}
