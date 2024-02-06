package com.example.parkingapp.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.parkingapp.R;

public class LoadingCircle {
    private AlertDialog loadingDialog;

    private Context context;

    private Activity activity;

    public LoadingCircle(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void showLoadingDialog() {
        if (loadingDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.LoadingDialog);
            LayoutInflater inflater = activity.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.loading_dialog, null);
            builder.setView(dialogView);
            builder.setCancelable(false);
            loadingDialog = builder.create();
        }

        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
