package com.maddy.instagramclone.util;

import android.os.Environment;

public class FilePath {

    //storage/emulated/0
    public static String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();

    public static String PICTURES = ROOT_DIR + "/Pictures";
    public static String CAMERA = ROOT_DIR + "/DCIM/camera";
}
