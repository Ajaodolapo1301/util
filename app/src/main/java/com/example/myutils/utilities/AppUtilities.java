package com.example.myutils.utilities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
//import com.libramotors.libmot.R;

import java.util.Calendar;
import java.util.Objects;

public class AppUtilities {

//      public static final String BASE_URL = "http://192.168.137.1:5001/";
      public static final String BASE_URL = "http://client.libmot.com/";
//      public static final String BASE_URL = "https://libmotapidev.azurewebsites.net/";
//    public static final String BASE_URL = "https://libmotapidevrs.azurewebsites.net/";
//    libmotapidev.azurewebsites.net
//
    public static boolean isHTTPOkCode(String s){
        if (s == null)
            return false;
        else return s.equalsIgnoreCase("200");

    }


    public static String getValidString(String s){
        if (s == null)
            return "";
        else if(s.length()<3)
            return "";
        else if (s.trim().equalsIgnoreCase("N/A"))
            return "";
        else return s;

    }

    public static boolean isNetworkAvailable(@NonNull Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = Objects.requireNonNull(connectivityManager)
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public static boolean isStringEmpty(String s){
        if (s == null){
            return true;
        }else if (s.length()<1){
            return true;
        }
        return false;
    }


    public static void hideKeyboard(Activity activity) {
        try{
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = activity.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }catch(Exception e){

        }

    }

    public static final int ERROR_SNACK_BAR = 1;
    public static final int WARNING_SNACK_BAR = 2;
    public static final int SUCCESSFUL_SNACK_BAR = 3;
    public static final int NEUTRAL_SNACK_BAR = 4;
    public static final int TRANSPARENT_SNACK_BAR = 5;


    public static void showSnackBar(View view, String text, int duration, int type){
        Snackbar snackbar = Snackbar.make(view,text,duration);
        switch (type){
            case ERROR_SNACK_BAR: {
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.red));
                break;
            }
            case WARNING_SNACK_BAR:{
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.yellow));
            break;}

            case SUCCESSFUL_SNACK_BAR:{
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.green));
                break;}

            case NEUTRAL_SNACK_BAR:{
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.yellowGray));
                break;
            }
            case TRANSPARENT_SNACK_BAR:{
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.Transparent150));
            break;}

        }
        snackbar.show();
    }

public interface SnackbarAction{

        void perform();
}
    public static void showSnackBar(View view, String text, int duration, int type, String action, SnackbarAction listener){
        Snackbar snackbar = Snackbar.make(view,text,duration);
        switch (type){
            case ERROR_SNACK_BAR: {
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.red));
                break;
            }
            case WARNING_SNACK_BAR:{
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.yellow));
                break;}

            case SUCCESSFUL_SNACK_BAR:{
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.green));
                break;}

            case TRANSPARENT_SNACK_BAR:{
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.Transparent75));
                break;
            }
        }
        snackbar.setAction(action, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
                listener.perform();
            }
        });

        snackbar.show();
    }

    public static boolean checkSmsPermissions(Context context) {
            if (Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP_MR1)
                if (context.checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                    return false;
        }
        return true;
    }


    public static void requstPermission(Activity activity, int requestCode) {
            if (Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP_MR1)
                if (activity.checkSelfPermission(Manifest.permission.READ_SMS) == PackageManager.PERMISSION_DENIED)
                activity.requestPermissions(new String[]{Manifest.permission.READ_SMS}, requestCode);

    }

    private static void enableUserInteraction(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private static void disableUserInteraction(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
//    puclic static Profile getEmptyProfile(){
//        return             DataPersistence.setUserProfile(this,"","","","","","","","","",0)
//
//    }


    public static float dpToPx(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static float pxToDp(Context context, float px) {
        if (context == null) {
            return -1;
        }
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static void startCountDown(TextView v, long till,ActionInterface actionInterface){
//        SimpleDateFormat sdf = new SimpleDateFormat("");
        final CountDownTimer timer = new CountDownTimer(Long.MAX_VALUE,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long rest = till - System.currentTimeMillis();
                Log.e("time left ", rest+"");
                Calendar now = Calendar.getInstance();
                now.setTimeInMillis(rest);
                int days = now.get(Calendar.DAY_OF_YEAR);
                int minute = now.get(Calendar.MINUTE);
                int hour = now.get(Calendar.HOUR_OF_DAY);
                int seconds = now.get(Calendar.SECOND);
                String countDownText;
                if(rest<1){
                    actionInterface.perform();
                    countDownText = "Book now";
//                    timer.cancel();
                }else if(days>1){
                    countDownText = days+" days to go";
                }else if(hour>1){
                    countDownText = hour+" hours to go";
                }else if(seconds>60){
                    countDownText = minute+" minutes to go";
                }else if(seconds>0){
                    countDownText = seconds+" seconds left, get ready";
                }else{
                    //actionInterface.perform();
                    countDownText = "Book now";

                }
//                int days = now.get(Calendar.DAY_OF_WEEK_IN_MONTH);
                v.setText(countDownText);

            }

            @Override
            public void onFinish() {

            }
        };

        timer.start();
    }


    public interface ActionInterface{
        void perform();
    }

}
