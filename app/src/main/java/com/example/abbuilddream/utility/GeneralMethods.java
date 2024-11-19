package com.example.abbuilddream.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.abbuilddream.R;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

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

    public static String getCurrentDate()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

    public static String getGreetingMessage() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= 5 && hour < 12) {
            return "Good Morning";
        } else if (hour >= 12 && hour < 17) {
            return "Good Afternoon";
        } else if (hour >= 17) {
            return "Good Evening";
        }else
        {
            return "";
        }
    }

    public static String generateRandomOrderId(Context context) {
        SessionManager sessionManager = new SessionManager(context);
        if(sessionManager.getIsOrderDone()) {
            String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 12); // Shorten the UUID
            Log.w("jarvis", "UUID: " + uuid);
            return "ORDER-" + uuid.toUpperCase();
        }
        return "";
    }



    public static void showNoInternetDialog(Context context) {
        // Create an alert dialog
        if(!isInternetAvailable(context)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("No Internet Connection");
            builder.setMessage("It seems you are offline. Please check your internet connection and try again.");

            // Add an "OK" button
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            // Make the dialog non-cancelable
            builder.setCancelable(false);
            // Show the dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
            return capabilities != null &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        }
        return false;
    }


    public static int generateRandomOrderId() {
        // Generate a random integer within a range
        return ThreadLocalRandom.current().nextInt(1000, 9999);
    }
}
