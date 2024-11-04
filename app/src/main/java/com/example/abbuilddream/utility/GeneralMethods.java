package com.example.abbuilddream.utility;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;

import com.example.abbuilddream.R;

public class GeneralMethods {





    public static void hideSystemUI(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    public static void showSystemUI(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }


   public static void showSpinner(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
    }

    public static void hideBottomNav(Activity activity) {
        activity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
    }
    public static void showBottomNav(Activity activity) {
        activity.findViewById(R.id.bottomNavigationView).setVisibility(View.VISIBLE);
        showSystemUI(activity);
    }

}
