package com.maddy.instagramclone.interfaces;

import com.google.firebase.auth.FirebaseUser;

public interface iFireBaseListener {
   public void onCompletion(FirebaseUser currentUser);
   public void onFailure();
}
