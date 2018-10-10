package com.maddy.instagramclone.util;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class FileSearch {

    private static final String TAG = "FileSearch";

    /**
     *  Search a direcotry and return all the directories paths contained inside
     * @param directory
     * @return
     */
    public static ArrayList<String> getDirectoryPaths(String directory) {

        ArrayList<String> pathArray = new ArrayList<>();
        File file = new File(directory);
        File[] filesList = file.listFiles();

        for(int i=0; i < filesList.length; i++){
            if(filesList[i].isDirectory())
                pathArray.add(filesList[i].getAbsolutePath());
        }

        return pathArray;
    }

    /**
     * Search all the directory and return file paths
     * @param directory
     * @return
     */
    public static ArrayList<String> getFilePaths(String directory) {
        ArrayList<String> pathArray = new ArrayList<>();

        try {
            File file = new File(directory);
            File[] filesList = file.listFiles();

            for(int i=0; i < filesList.length; i++){
                if(filesList[i].isFile())
                    pathArray.add(filesList[i].getAbsolutePath());
            }
        } catch (NullPointerException ex) {
            Log.e(TAG, "getFilePaths: Exception occurred: ", ex);
        }

        return pathArray;
    }
}
