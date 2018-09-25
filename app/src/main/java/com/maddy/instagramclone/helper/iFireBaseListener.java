package com.maddy.instagramclone.helper;

import com.google.firebase.auth.FirebaseUser;

public interface iFireBaseListener {
   public void onCompletion(FirebaseUser currentUser);
   public void onFailure();
}
