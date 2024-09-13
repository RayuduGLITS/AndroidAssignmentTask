package com.rayudu.androidassignmenttask.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.snackbar.Snackbar;
import com.rayudu.androidassignmenttask.R;

public class Constants {

    public static String SERVER_BASE_URL = "https://reqres.in/api/";// production



    public static void custom_snackbar(Context context, View v, String str, boolean isError){
        Snackbar snack = Snackbar.make(context,v, str, Snackbar.LENGTH_LONG);
        if(isError) {
            snack.setTextColor(context.getResources().getColor(R.color.red));
        }else {
            snack.setTextColor(context.getResources().getColor(R.color.black));
        }
        snack.setActionTextColor(context.getResources().getColor(R.color.black));
        snack.setAction("CLOSE", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snack.dismiss();
            }
        });
        View view = snack.getView();
        view.setBackgroundColor(context.getResources().getColor(R.color.white_gray));
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snack.show();

    }
}
