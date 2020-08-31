package com.example.myutils.utilities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.libramotors.libmot.R;

public class AppRater implements View.OnClickListener{
    private final static String APP_TITLE = "Libmot Mobile";// App Name
    private final static String APP_PNAME = "com.libramotors.libmot";// Package Name

    private View.OnClickListener listener;

    private final static int DAYS_UNTIL_PROMPT = 3;//Min number of days
    private final static int LAUNCHES_UNTIL_PROMPT = 3;//Min number of launches

    public static void app_launched(Context mContext) {
        boolean launch = false;
        SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
        if (prefs.getBoolean("dontshowagain", false)) { return ; }

        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }

        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch +
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showRateDialog(mContext, editor);
            }
        }

        editor.apply();
    }

    private static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {
        final Dialog dialog = new Dialog(mContext);
        dialog.setTitle("Rate " + APP_TITLE);

        LinearLayout ll = new LinearLayout(mContext);
        ll.setOrientation(LinearLayout.VERTICAL);

        TextView tv = new TextView(mContext);
        tv.setText("If you enjoy using " + APP_TITLE + ", please take a moment to rate it. Thanks for your support!");
        tv.setWidth(240);
        tv.setPadding(4, 0, 4, 10);
        ll.addView(tv);

        Button b1 = new Button(mContext);
        b1.setText("Rate " + APP_TITLE);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
                dialog.dismiss();
            }
        });
        ll.addView(b1);

        Button b2 = new Button(mContext);
        b2.setText("Remind me later");
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ll.addView(b2);

        Button b3 = new Button(mContext);
        b3.setText("No, thanks");
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
                dialog.dismiss();
            }
        });
        ll.addView(b3);

        dialog.setContentView(ll);
        dialog.show();
    }

public static boolean shouldLaunchApp(Context context){
        return (DataPersistence.getLaunchCount(context)==0);
}


private static AlertDialog alertDialog;
public void showDialog(Context context,View.OnClickListener listener){
    this.listener = listener;
    DataPersistence.appLaunched(context);
    LayoutInflater inflater = LayoutInflater.from(context);
    View rateDialogView = inflater.inflate(R.layout.dialog_rating, null);
    TextView textView = rateDialogView.findViewById(R.id.textView);
    Button rate = rateDialogView.findViewById(R.id.rateUs);
    rate.setOnClickListener(this);
    RatingBar ratingBar = rateDialogView.findViewById(R.id.star);
    ratingBar.setOnClickListener(this);
    ratingBar.setEnabled(false);
    if (DataPersistence.isLogged(context)){
        String name = DataPersistence.getProfile(context).getFirstName();
        textView.setText(new StringBuilder("Hello "+name+"!"));
    }else {
        textView.setText(new StringBuilder("Hello there!"));
    }
    rateDialogView.setOnClickListener(this);
    Button b = rateDialogView.findViewById(R.id.rate_later);
    b.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            alertDialog.dismiss();
            if (listener!=null){
                listener.onClick(v);
            }
        }
    });

//    RatingBar ratingBar = rateDialogView.findViewById(R.id.star);
//    ratingBar.setOnClickListener(v ->
//    { context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
//        alertDialog.dismiss();
//        if (listener!=null){
//            listener.onClick(v);
//        }});

    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setView(rateDialogView);
    builder.setCancelable(false);
    alertDialog = builder.create();
//    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    alertDialog.show();

}

    @Override
    public void onClick(View v) {
        // Redirect to PlayStore
        alertDialog.dismiss();
        if (listener!=null){
            listener.onClick(v);
        }
        v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
    }
}