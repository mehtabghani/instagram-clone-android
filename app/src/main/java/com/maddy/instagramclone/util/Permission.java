package com.maddy.instagramclone.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.maddy.instagramclone.activity.ShareActivity;

public class Permission {

    private static final String TAG = "Permission";
    private static final int VERIFY_PERMISSIONS_REQUEST = 1;


    public static final String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Check an array of permissions
     * @param permissions
     * @return
     */
    public static boolean checkPermissionsArray(final String[] permissions,final Context context){
        Log.d(TAG, "checkPermissionsArray: checking permissions array.");

        for(int i = 0; i< permissions.length; i++){
            String check = permissions[i];
            if(!checkPermissions(check, context)){
                return false;
            }
        }
        return true;
    }

    /**
     * Check a single permission is it has been verified
     * @param permission
     * @return
     */
    public static boolean checkPermissions(String permission, final Context context){
        Log.d(TAG, "checkPermissions: checking permission: " + permission);

        int permissionRequest = ActivityCompat.checkSelfPermission(context, permission);

        if(permissionRequest != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "checkPermissions: \n Permission was not granted for: " + permission);
            return false;
        }
        else{
            Log.d(TAG, "checkPermissions: \n Permission was granted for: " + permission);
            return true;
        }
    }


    /**
     * verifiy all the permissions passed to the array
     * @param permissions
     */
    public static void verifyPermissions(String[] permissions, Context context){
        Log.d(TAG, "verifyPermissions: verifying permissions.");

        ActivityCompat.requestPermissions(
                (Activity) context,
                permissions,
                VERIFY_PERMISSIONS_REQUEST
        );
    }


}
