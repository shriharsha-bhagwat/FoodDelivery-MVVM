package com.kopa.me.driver.core.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionUtils {

    private Activity currentActivity;
    private OnPermissionResultListener onPermissionResultListener;

    private ArrayList<String> permissionList = new ArrayList<>();
    private ArrayList<String> listPermissionsNeeded = new ArrayList<>();

    private String dialogContent = "";
    private int reqCode;

    public PermissionUtils(Context context, OnPermissionResultListener callback) {
        this.currentActivity = (Activity) context;
        onPermissionResultListener = callback;

    }

    /*
     * check permission granted or not
     * */
    public void checkPermissionGranted(ArrayList<String> permissions, String dialogContent,
                                       int requestCode) {
        this.permissionList = permissions;
        this.dialogContent = dialogContent;
        this.reqCode = requestCode;

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkWithoutRequestPermissions(permissions, requestCode)) {
                onPermissionResultListener.permissionGranted(requestCode);
            } else {
                onPermissionResultListener.permissionDenied(requestCode);
            }
        } else {
            onPermissionResultListener.permissionGranted(requestCode);
        }
    }

    /**
     * Check the API Level & Permission
     */

    public void checkPermission(ArrayList<String> permissions, String dialogContent,
                                int requestCode) {
        this.permissionList = permissions;
        this.dialogContent = dialogContent;
        this.reqCode = requestCode;

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkAndRequestPermissions(permissions, requestCode)) {
                onPermissionResultListener.permissionGranted(requestCode);
            }
        } else {
            onPermissionResultListener.permissionGranted(requestCode);
        }
    }


    private boolean checkWithoutRequestPermissions(ArrayList<String> permissions, int requestCode) {

        if (!permissions.isEmpty()) {
            listPermissionsNeeded = new ArrayList<>();

            for (int i = 0; i < permissions.size(); i++) {
                int hasPermission = ContextCompat.checkSelfPermission(currentActivity, permissions.get(i));

                if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(permissions.get(i));
                }
            }
            return listPermissionsNeeded.isEmpty();
        }

        return true;
    }


    /**
     * Check and request the Permissions
     */

    private boolean checkAndRequestPermissions(ArrayList<String> permissions, int requestCode) {

        if (!permissions.isEmpty()) {
            listPermissionsNeeded = new ArrayList<>();

            for (int i = 0; i < permissions.size(); i++) {
                int hasPermission = ContextCompat
                        .checkSelfPermission(currentActivity, permissions.get(i));

                if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(permissions.get(i));
                }
            }

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(currentActivity,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                        requestCode);
                return false;
            }
        }

        return true;
    }

    /**
     *
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        reqCode = requestCode;
        if (grantResults.length > 0) {
            Map<String, Integer> perms = new HashMap<>();

            for (int i = 0; i < permissions.length; i++) {
                perms.put(permissions[i], grantResults[i]);
            }

            final ArrayList<String> pendingPermissions = new ArrayList<>();

            for (int i = 0; i < listPermissionsNeeded.size(); i++) {
                if (perms.get(listPermissionsNeeded.get(i)) != null
                        && PackageManager.PERMISSION_GRANTED != perms.get(listPermissionsNeeded.get(i))) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(currentActivity,
                            listPermissionsNeeded.get(i))) {
                        pendingPermissions.add(listPermissionsNeeded.get(i));
                    } else {
                        Log.i("PermissionUtils", "Go to settings and enable permissions");
                        onPermissionResultListener.neverAskAgain(reqCode);
                        return;
                    }
                }
            }
            if (!pendingPermissions.isEmpty()) {
                checkForPendingPermissions(pendingPermissions);
            } else {
                Log.i("PermissionUtils", "All permissions granted");
                onPermissionResultListener.permissionGranted(reqCode);

            }

        }
    }

    private void checkForPendingPermissions(ArrayList<String> pendingPermissions) {
        showMessageOKCancel(dialogContent,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:

                                checkPermission(permissionList, dialogContent, reqCode);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                Log.i("PermissionUtils", "Permissions not fully given");

                                if (permissionList.size() == pendingPermissions.size()) {
                                    onPermissionResultListener.permissionDenied(reqCode);
                                } else {
                                    onPermissionResultListener
                                            .partialPermissionGranted(reqCode, pendingPermissions);
                                }
                                break;
                            default:
                                break;
                        }


                    }
                });

    }


    /**
     * Explain why the app needs permissions
     */
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(currentActivity)
                .setMessage(message)
                .setPositiveButton("Ok", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    public static boolean checkAndRequestPermissions(Context context) {

        int permissionCamera = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        int permissionwifi = ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_WIFI_STATE);
        int phonestateCamera = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_PHONE_STATE);
        int writeStoragePermission = ContextCompat
                .checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readStoragePermission = ContextCompat
                .checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int recordAudioPermission = ContextCompat
                .checkSelfPermission(context, Manifest.permission.RECORD_AUDIO);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (readStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (writeStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (phonestateCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (permissionwifi != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_WIFI_STATE);
        }
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (recordAudioPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) context,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
            return false;
        }
        return true;
    }

    public interface OnPermissionResultListener {

        void permissionGranted(int requestCode);

        void partialPermissionGranted(int requestCode, List<String> grantedPermissions);

        void permissionDenied(int requestCode);

        void neverAskAgain(int requestCode);
    }
}