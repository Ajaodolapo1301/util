package com.example.myutils.utilities;

import android.content.Context;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class BannerDownloader {

    public void fetchImage(Context c){
        String url = DataPersistence.getFirebasePrefs(c).getBanner();
        if (AppUtilities.isStringEmpty(url)){
            File file = DataPersistence.getBannerImage(c);
            if (file.exists()) {
                file.delete();
            }
            return;}
        File file = DataPersistence.getBannerPath(c);
        if (!file.exists())
            file.mkdirs();

        if (DataPersistence.isBannerChanged(c)||!DataPersistence.getBannerImage(c).exists()) {
        AppExecutors.getInstance().networkIO().execute( ()-> downloadFile(url,"image.png",file));
        }

    }

    private boolean downloadFile(String link, String fileName, File file) {
        boolean donec;

        try {
            URL url = new URL(link);
            InputStream inputStream = url.openStream();
              boolean c = file.mkdirs();
            File output = new File(file, fileName);
            DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(output));
            try {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) >= 0) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } finally {
                outputStream.close();
            }
            inputStream.close();
            donec = true;
        } catch (Exception e) {
            e.printStackTrace();
//            Log.d("error",e.getMessage());
            donec = false;
        }
        return donec;
    }
}
