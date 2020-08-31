package com.example.myutils.utilities;


import android.content.Context;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.libramotors.libmot.data.model.FeedObject;
import com.libramotors.libmot.data.model.FirebaseData;

import java.util.ArrayList;
import java.util.List;

public class FirebaseImplementation {

    private static OnActionCompleteListener mOnActionCompleteListener;
    private Context context;
    FirebaseData firebaseData = new FirebaseData("",0,"","","",null);
    private static FirebaseImplementation instance;
    private DocumentReference ref;
    private FirebaseImplementation(Context context){
        this.context = context;
        init();
    }

    public static void initialize(Context cxt){
        if (instance == null){
            instance  = new FirebaseImplementation(cxt);
        }
  }


    public static void initialize(Context cxt, OnActionCompleteListener onActionCompleteListener){
        mOnActionCompleteListener = onActionCompleteListener;
        if (instance == null){
            instance  = new FirebaseImplementation(cxt);
        }
    }

    private void init(){
        ref = FirebaseFirestore.getInstance().document("libmot/preference");
        ref.get().addOnSuccessListener(this::saveData);

        ref.addSnapshotListener((documentSnapshot, e) -> {
           if (e ==  null)
            saveData(documentSnapshot);
        });
    }

    private  static List<FeedObject> feedObjects  = new ArrayList<>();
    public static void fetchFeeds(OnFetchCompleteListener onFetchCompleteListener){
        CollectionReference ref = FirebaseFirestore.getInstance().collection("/feeds");
        ref.get().addOnCompleteListener(task -> {
            if (task.getResult()!=null)
                feedObjects = task.getResult().toObjects(FeedObject.class);
                onFetchCompleteListener.onFinish(feedObjects);
        });
    }


public interface OnActionCompleteListener{
        void onFinish();
}

public interface OnFetchCompleteListener{
        void onFinish(List<FeedObject> feedObjects);
    }


private void saveData(DocumentSnapshot documentSnapshot){
    if (documentSnapshot.exists()){
        firebaseData = documentSnapshot.toObject(FirebaseData.class);
        if (firebaseData!=null){
            DataPersistence.updateFirebasePrefs(context, firebaseData);
//            Log.e("max cal", firebaseData.getCalendar_max()+"");
//            Log.e("app version",firebaseData.getApp_version());
//            Log.e("banner url",firebaseData.getBanner());

                new BannerDownloader().fetchImage(context);

            if (mOnActionCompleteListener != null){
                mOnActionCompleteListener.onFinish();
            }
        }}
}
}
