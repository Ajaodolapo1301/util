package com.example.myutils.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.Timestamp;
import com.libramotors.libmot.data.model.FirebaseData;
import com.libramotors.libmot.data.model.Profile;

import java.io.File;
import java.util.Date;


public class DataPersistence {
    private static final String IS_LOGIN="is_login";
    private static final String PROFILE="profile";
    private static final String DUMMY_PROFILE="dummy_profile";

    private static final String FB_PREFS = "firebase-prefs";
    private static final String APP_PREFS = "app-prefs";
    private static final String TOKEN="token";
    private static final String FIRST_NAME="first_name";
    private static final String LAST_NAME="last_name";
    private static final String PHONE_NO="phone_no";
    private static final String GENDER="gender";
    private static final String EMAIL="email";
    private static final String NOKNAME="nokname";
    private static final String NOKPHONE="nokphone";
    private static final String REFERRAL="referralCode";
    private static final String APP_VERSION = "app_version";
    private static final String CALENDAE_MAX = "cal-max";
    private static final String USER_TYPE = "user-type";
    private static final String REFERRER = "referrer";
    private static final String BANNER = "banner";
    private static final String IS_FIRST_LAUNCH = "first-launch";
    private static final String LAUNCH_COUNT = "launch_count";


    private static final int MAX_LAUNCH_BEFORE_RATING = 10;




    public static void setLoginState(Context ctx){
        SharedPreferences.Editor editor = ctx.getSharedPreferences(IS_LOGIN, Context.MODE_PRIVATE).edit();
        editor.putBoolean(IS_LOGIN, true);
        editor.apply();
    }
    public static void logOut(Context ctx){
        SharedPreferences.Editor editor = ctx.getSharedPreferences(IS_LOGIN, Context.MODE_PRIVATE).edit();
        editor.remove(IS_LOGIN);
        editor.apply();
    }

    public static void setUserProfile(Context ctx,String firstName, String lastName, String phoneNo,String gender,String email,String nokName,String nokPhone,String referrer,  String referralCode, int userType){
        SharedPreferences.Editor editor = ctx.getSharedPreferences(PROFILE, Context.MODE_PRIVATE).edit();
        editor.putString(FIRST_NAME, firstName);
        editor.putString(LAST_NAME, lastName);
        editor.putString(PHONE_NO, phoneNo);
        editor.putString(GENDER, gender);
        editor.putString(EMAIL, email);
        editor.putString(NOKNAME, nokName);
        editor.putString(NOKPHONE, nokPhone);
        editor.putString(REFERRER, referrer);
        editor.putInt(USER_TYPE,userType);
        editor.putString(REFERRAL,referralCode);
        editor.apply();
    }
    public static boolean isLogged(Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences(IS_LOGIN, Context.MODE_PRIVATE);
        return prefs.getBoolean(IS_LOGIN, false);
    }


    public static boolean isFirstLaunch(Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        return prefs.getBoolean(IS_FIRST_LAUNCH, true);
    }


    public static void setIsFirstLaunch(Context ctx){
        SharedPreferences.Editor editor = ctx.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE).edit();
        editor.putBoolean(IS_FIRST_LAUNCH, false);
        editor.apply();
    }


    public static Profile getProfile(Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences(PROFILE, Context.MODE_PRIVATE);
        Profile profile=new Profile();
        profile.setFirstName(prefs.getString(FIRST_NAME, ""));
        profile.setLastName(prefs.getString(LAST_NAME, ""));
        profile.setPhoneNo(prefs.getString(PHONE_NO, ""));
        profile.setGender(prefs.getString(GENDER, ""));
        profile.setEmail(prefs.getString(EMAIL, ""));
        profile.setNokName(prefs.getString(NOKNAME, ""));
        profile.setNokPhone(prefs.getString(NOKPHONE, ""));
        profile.setReferral(prefs.getString(REFERRAL,""));
        profile.setReferrer(prefs.getString(REFERRER,""));
        profile.setUserType(prefs.getInt(USER_TYPE,3));
        return profile;
    }

    public static void updateFirebasePrefs(Context ctx, FirebaseData firebaseData){
        Log.e("time nysc from cloud",firebaseData.getNysc_date().toDate().getTime()+"");
        Log.e("time nysc from system",System.currentTimeMillis()+"");

        FirebaseData firebaseData1 = getFirebasePrefs(ctx);
        setBannerChanged(!firebaseData.getBanner().equalsIgnoreCase(firebaseData1.getBanner()),ctx);
        SharedPreferences.Editor editor = ctx.getSharedPreferences(FB_PREFS, Context.MODE_PRIVATE).edit();
        editor.putString(APP_VERSION, firebaseData.getApp_version());
        editor.putInt(CALENDAE_MAX, firebaseData.getCalendar_max());
        editor.putString(BANNER, firebaseData.getBanner());
        editor.putLong("nysc",firebaseData.getNysc_date().toDate().getTime());
        editor.apply();
    }



    public static FirebaseData getFirebasePrefs(Context ctx){

        SharedPreferences prefs = ctx.getSharedPreferences(FB_PREFS, Context.MODE_PRIVATE);
        FirebaseData firebaseData = new FirebaseData();
    firebaseData.setApp_version(prefs.getString(APP_VERSION,""));
    firebaseData.setCalendar_max(prefs.getInt(CALENDAE_MAX,7));
    firebaseData.setBanner(prefs.getString(BANNER,""));
    firebaseData.setNysc_date(new Timestamp(new Date(prefs.getLong("nysc",System.currentTimeMillis()))));
        Log.e("time nysc from pref",firebaseData.getNysc_date().toDate().getTime()+"");
        Log.e("time nysc from system",System.currentTimeMillis()+"");

        return firebaseData;}

    public static String getPassword(Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences(FB_PREFS, Context.MODE_PRIVATE);
//        return prefs.getString("password","Lme@onl1n3");
        return prefs.getString("password","Lme@onl1n3");
    }

    public static void setPassword(Context ctx, String s){
        SharedPreferences.Editor editor = ctx.getSharedPreferences(FB_PREFS, Context.MODE_PRIVATE).edit();
        editor.putString("password", s);
        editor.apply();
    }

    public static String getUsername(Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences(FB_PREFS, Context.MODE_PRIVATE);
    //    return prefs.getString("username","android@libmot.com");
        return prefs.getString("username","android@libmot.com");
    }

    public static void setUsername(Context ctx, String s){
        SharedPreferences.Editor editor = ctx.getSharedPreferences(FB_PREFS, Context.MODE_PRIVATE).edit();
        editor.putString("username", s);
        editor.apply();
    }


    public static String getAndroidPassword(){
        return "Lme@onl1n3";
    }

    public static String getAndroidUsername(){
        return "android@libmot.com";
    }



//    public static String getAndroidToken(Context ctx){
//        if (ctx!=null){
//        SharedPreferences prefs = ctx.getSharedPreferences(TOKEN, Context.MODE_PRIVATE);
//        return prefs.getString(TOKEN, "");
//    }return "";}
//
//
//    public static void setAndroidToken(Context ctx,String token){
//        SharedPreferences.Editor editor = ctx.getSharedPreferences(TOKEN, Context.MODE_PRIVATE).edit();
//        editor.putString(TOKEN,token );
//        editor.apply();
//    }
//    public static String getUserToken(Context ctx){
//        if (ctx!=null){
//        SharedPreferences prefs = ctx.getSharedPreferences(FB_PREFS, Context.MODE_PRIVATE);
//        return prefs.getString("token-user",null);
//    }
//        return "";
//    }
//
//    public static void setUserToken(Context ctx, String s){
//        SharedPreferences.Editor editor = ctx.getSharedPreferences(FB_PREFS, Context.MODE_PRIVATE).edit();
//        editor.putString("token-user", s);
//        editor.apply();
//    }


    public static String getAdminToken(Context ctx){
        if (ctx!=null){
        SharedPreferences prefs = ctx.getSharedPreferences(FB_PREFS, Context.MODE_PRIVATE);
        return prefs.getString("token-admin",null);
        }
        return "";
    }

    public static void setAdminToken(Context ctx, String s){
        SharedPreferences.Editor editor = ctx.getSharedPreferences(FB_PREFS, Context.MODE_PRIVATE).edit();
        editor.putString("token-admin", s);
        editor.apply();
    }


    public static void setHasBooked(Context ctx){
        SharedPreferences.Editor editor = ctx.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE).edit();
        editor.putBoolean("first-booking", true);
        editor.apply();
    }

    public static boolean hasBooked(Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        return prefs.getBoolean("first-booking",false);
    }

    public static int getUserType(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(PROFILE, Context.MODE_PRIVATE);
        return prefs.getInt(USER_TYPE, 3);
    }


//    public static String getReferralCode(Context ctx){
//        if (ctx!=null){
//            SharedPreferences prefs = ctx.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
//            return prefs.getString("referral-code",null);
//        }
//        return "";
//    }
//
//    public static void saveReferralCode(Context ctx, String s){
//        SharedPreferences.Editor editor = ctx.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE).edit();
//        editor.putString("referral-code", s);
//        editor.apply();
//    }
//

    private static String getRoot(Context context){
            return context.getApplicationContext().getFilesDir().getAbsolutePath();
    }

    public static File getBannerPath(Context context){
      return new File(getRoot(context)+"/banner");
    }

    public static File getBannerImage(Context context){
        return new File(getBannerPath(context)+"/image.png");
    }


    private static void setBannerChanged(boolean b, Context ctx) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(FB_PREFS, Context.MODE_PRIVATE).edit();
        editor.putBoolean("banner-changed", b);
        editor.apply();
    }

    public static boolean isBannerChanged(Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences(FB_PREFS, Context.MODE_PRIVATE);
        return prefs.getBoolean("banner-changed", true);
    }


    private static final String IS_MAIN_ACTIVITY_ACTIVE = "isMainActivityActive";
    public static void mainActivityDestroyed(boolean b, Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE).edit();
        editor.putBoolean(IS_MAIN_ACTIVITY_ACTIVE, b);
        editor.apply();
    }


    public static boolean isMainActivityDestroyed(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        return prefs.getBoolean(IS_MAIN_ACTIVITY_ACTIVE, true);
    }

    public static int getLaunchCount(Context context){
        SharedPreferences prefs = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        return prefs.getInt(LAUNCH_COUNT, 0);
    }

    public static void appLaunched(Context context){
        int currentCount = getLaunchCount(context);
        if (currentCount >= MAX_LAUNCH_BEFORE_RATING){
            currentCount = 0;

        }else {
            currentCount++;
        }


        SharedPreferences.Editor editor = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE).edit();
        editor.putInt(LAUNCH_COUNT, currentCount);
        editor.apply();
    }


    public static void createDummyProfile(Context ctx,String firstName, String lastName, String phoneNo,String gender,String email,String nokName,String nokPhone){
        SharedPreferences.Editor editor = ctx.getSharedPreferences(DUMMY_PROFILE, Context.MODE_PRIVATE).edit();
        editor.putString(FIRST_NAME, firstName);
        editor.putString(LAST_NAME, lastName);
        editor.putString(PHONE_NO, phoneNo);
        editor.putString(GENDER, gender);
        editor.putString(EMAIL, email);
        editor.putString(NOKNAME, nokName);
        editor.putString(NOKPHONE, nokPhone);
        editor.apply();
    }

    public static Profile getDummyProfile(Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences(DUMMY_PROFILE, Context.MODE_PRIVATE);
        Profile profile=new Profile();
        profile.setFirstName(prefs.getString(FIRST_NAME, ""));
        profile.setLastName(prefs.getString(LAST_NAME, ""));
        profile.setPhoneNo(prefs.getString(PHONE_NO, ""));
        profile.setGender(prefs.getString(GENDER, ""));
        profile.setEmail(prefs.getString(EMAIL, ""));
        profile.setNokName(prefs.getString(NOKNAME, ""));
        profile.setNokPhone(prefs.getString(NOKPHONE, ""));
        return profile;
    }


    private static final String STATUS_PROFILE = "status-profile";

    public static void setSignUpHelperStatus(SignUpHelperStatus status, Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(DUMMY_PROFILE, Context.MODE_PRIVATE).edit();
        editor.putString(STATUS_PROFILE, status.name());
        editor.apply();
    }


    public static SignUpHelperStatus getSignUpHelperStatus(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(DUMMY_PROFILE, Context.MODE_PRIVATE);
         String name = prefs.getString(STATUS_PROFILE, SignUpHelperStatus.NEVER_SHOWN.name());
         return SignUpHelperStatus.valueOf(name);
    }


public enum SignUpHelperStatus{
        LATER, NEVER, USED, NEVER_SHOWN
    }

}
