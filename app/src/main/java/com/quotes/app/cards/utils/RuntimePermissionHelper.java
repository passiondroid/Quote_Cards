package com.quotes.app.cards.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akhan on 1/11/2016.
 */
public class RuntimePermissionHelper {

    private static RuntimePermissionHelper runtimePermissionHelper = null;
    public static final int PERMISSION_REQUEST_CODE = 1;
    //private Context mContext;
    private Activity activity;
    private ArrayList<String> requiredPermissions = new ArrayList<String>();
    private ArrayList<String> ungrantedPermissions = new ArrayList<String>();

    public static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;

    public static synchronized RuntimePermissionHelper getInstance(Activity activity){
        if(runtimePermissionHelper == null) {
            runtimePermissionHelper = new RuntimePermissionHelper(activity);
        }
        return runtimePermissionHelper;
    }

    private RuntimePermissionHelper(Activity activity)  {
        //this.mContext = context;
        this.activity = activity;
        initPermissions();
    }

    private void initPermissions() {
        requiredPermissions.add(PERMISSION_READ_EXTERNAL_STORAGE);
        requiredPermissions.add(PERMISSION_WRITE_EXTERNAL_STORAGE);
        requiredPermissions.add(PERMISSION_CAMERA);
    }

    private void initMCPermission(String permission) {
        try {
            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
            requiredPermissions.add(permission);
        } catch (IllegalArgumentException ex) {
            Log.d("IllegalArgumentExcep:" , ex.getMessage());
        }
    }

    public void requestPermissionsIfDenied(){
         ungrantedPermissions = getUnGrantedPermissionsList();
        if(canShowPermissionRationaleDialog()){
            showMessageOKCancel("You need to allow access to Mobi Control permissions",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            askPermissions();
                        }
                    });
            return;
        }
        askPermissions();
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public boolean canShowPermissionRationaleDialog() {
        boolean shouldShowRationale = false;
        for(String permission: ungrantedPermissions) {
            boolean shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
            if(shouldShow) {
                shouldShowRationale = true;
                //mcPreferenceManager.setBooleanPreference(permission, shouldShow);
            }
        }
        return shouldShowRationale;
    }

    private void askPermissions() {
        ActivityCompat.requestPermissions(activity, ungrantedPermissions.toArray(new String[ungrantedPermissions.size()]), PERMISSION_REQUEST_CODE);
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    public boolean isAllPermissionAvailable() {
        boolean isAllPermissionAvailable = true;
        initPermissions();
        for(String permission : requiredPermissions){
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED){
                isAllPermissionAvailable = false;
                break;
            }
        }
        return isAllPermissionAvailable;
    }

    public ArrayList<String> getUnGrantedPermissionsList() {
        ArrayList<String> list = new ArrayList<String>();
        for(String permission: requiredPermissions) {
            int result = ActivityCompat.checkSelfPermission(activity, permission);
            if(result != PackageManager.PERMISSION_GRANTED) {
                list.add(permission);
            }
        }
        return list;
    }
}