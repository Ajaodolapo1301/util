package com.example.myutils.utilities;

import android.content.Context;

import com.libramotors.libmot.data.Repository;
import com.libramotors.libmot.data.model.RefreshTokenRequest;
import com.libramotors.libmot.data.model.TokenObject;
import com.libramotors.libmot.data.model.TokenRequest;
import com.libramotors.libmot.data.model.TokenResponse;
import com.libramotors.libmot.data.network.NetworkDataSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

public class TokenManager {

    private Context mContext;
    private static final HashMap<String, TokenData> STORE = new HashMap<>();


    private TokenManager instance;

    public static final String USER_TOKEN = "user";
    public static final String ANDROID_TOKEN = "android";
    private static final long TOLERANCE = 100000;
    private static final int MAX_TRIAL = 2;
    private static int recursionControl = 0;





    public TokenManager(Context context)
    {  recursionControl = 0;
        mContext = context;
    }

    public  TokenManager getInstance(Context context) {
        if (instance == null)
            instance = new TokenManager(context);
        recursionControl = 0;
        return instance;
    }


    public String getToken(String name){
        TokenData tokenData = STORE.get(name);
        if (tokenData!=null){
            long now = System.currentTimeMillis()-3000000;
            if ((now+TOLERANCE) >= tokenData.expiryDate){
                if (recursionControl<MAX_TRIAL){
                    recursionControl++;
                    refreshToken(tokenData);
                    return getToken(tokenData.name);
                }else
                return null;
            }else {
                STORE.remove(name);
                STORE.put(name, tokenData);
                return tokenData.tokenObject.getToken();
            }
        }
        if (name.equalsIgnoreCase(ANDROID_TOKEN)){
            // login again
            TokenData tokenData1 = loginNow(ANDROID_TOKEN, DataPersistence.getAndroidUsername(), DataPersistence.getAndroidPassword());
            if (tokenData1!=null)
            manageToken(tokenData1.tokenObject,ANDROID_TOKEN,tokenData1.username, tokenData1.password);
            return getToken(ANDROID_TOKEN);

        }else if (name.equalsIgnoreCase(USER_TOKEN)){

            TokenData tokenData1 = loginNow(USER_TOKEN, DataPersistence.getUsername(mContext), DataPersistence.getPassword(mContext));
            if (tokenData1!=null)
            manageToken(tokenData1.tokenObject,USER_TOKEN,tokenData1.username, tokenData1.password);
            return getToken(USER_TOKEN);

        }
        return null;
    }








    public void manageToken(TokenObject tokenObject, String name, String username, String password){
            TokenData tokenData = new TokenData();
            tokenData.tokenObject = tokenObject;
            tokenData.name = name;
            tokenData.username = username;
            tokenData.password = password;
            tokenData.expiryDate = getExpiryDate(tokenObject.getExpires());
            STORE.remove(name);
            STORE.put(name,tokenData);
        }

    private long getExpiryDate(String time) {
        time  = time.substring(0,19);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",
                Locale.getDefault());
        long expTime ;
        try {
           expTime  = simpleDateFormat1.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            expTime = System.currentTimeMillis()+(10000*60);
        }
        return expTime;
    }


    private class TokenData{

        private TokenObject tokenObject;
        private String name;
        private long expiryDate;
        private String username;
        private String password;

        }


    public boolean isValid(String name){
        TokenData tokenData = STORE.get(name);
        if (tokenData!=null){
            long now = System.currentTimeMillis();

            return (tokenData.expiryDate+TOLERANCE<tokenData.expiryDate);

            //2019-12-10T11:58:15.2093283+01:00
        }
        return false;
    }

    private boolean refreshToken(TokenData tokenData){
        TokenResponse response = provideRepository(mContext).refreshToken(new RefreshTokenRequest(tokenData.tokenObject.getToken(), tokenData.tokenObject.getRefreshToken()));
        if (response!=null){
            if (AppUtilities.isHTTPOkCode(response.getCode())){
                tokenData.tokenObject = response.getObject();
//                STORE.remove(tokenData.name);
//                STORE.put(tokenData.name, tokenData);
                manageToken(response.getObject(),tokenData.name,tokenData.username,tokenData.password);
                return true;
            }else if (loginNow(tokenData.name,tokenData.username,tokenData.password) != null){
                return true;
            }
        }


        return false;
    }


//     private long getValidity(TokenObject tokenObject){
//        long now = System.currentTimeMillis()+5000;
//         long future = Timestamp.valueOf(tokenObject.getExpires()).getTime();
//         return future-now;
//     }

    private static Repository provideRepository(Context context) {
        AppExecutors executors = AppExecutors.getInstance();
        NetworkDataSource networkDataSource =
                NetworkDataSource.getInstance(executors);
        return Repository.getInstance(networkDataSource, executors, context);
    }


    private TokenData loginNow(String tag,String username, String password){
        TokenData data = new TokenData();
        TokenResponse response = provideRepository(mContext).getTokenObject(new TokenRequest(username, password));
        if (response != null){
            if (AppUtilities.isHTTPOkCode(response.getCode())){
                data.tokenObject = response.getObject();
                manageToken(response.getObject(),tag,username,password);
                return data;
            }

        }
            return null ;
    }

}
