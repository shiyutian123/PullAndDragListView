package ex.listview.shitou.com.pullanddraglistview.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

/**
 * Created by shitou on 15/6/8.
 */
public class ImageUtil {

    /**
     *
     * @param imagePath
     * @return
     */
    public static Bitmap getBitmap(String imagePath){
        if(!(imagePath.length()>5)){
            return null;
        }
        File cache_file = new File(new File(Environment.getExternalStorageDirectory(), "xxxx"),"cachebitmap");
        cache_file = new File(cache_file,getMD5(imagePath));

        if(cache_file.exists()){
              return BitmapFactory.decodeFile(getBitmapCache(imagePath));
        }else{
            try {
                URL url = new URL(imagePath);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                if (conn.getResponseCode() == 200) {
                    InputStream inStream = conn.getInputStream();
                    File file = new File(new File(Environment.getExternalStorageDirectory(), "xxxx"),"cachebitmap");
                    if(!file.exists())file.mkdirs();
                   file = new File(file,getMD5(imagePath));
                    FileOutputStream out = new FileOutputStream(file);
                    byte buff[] = new byte[1024];
                    int len = 0;
                    while((len = inStream.read(buff))!=-1){
                        out.write(buff,0,len);
                    }
                    out.close();
                    inStream.close();
                    return BitmapFactory.decodeFile(getBitmapCache(imagePath));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取缓存
     * @param url
     * @return
     */
    public static String getBitmapCache(String url){
        File file = new File(new File(Environment.getExternalStorageDirectory(), "xxxx"),"cachebitmap");
        file = new File(file,getMD5(url));
        if(file.exists()){
            return file.getAbsolutePath();
        }
        return null;

    }

    //加密为MD5
    public static String getMD5(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(content.getBytes());
            return getHashString(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String getHashString(MessageDigest digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest.digest()) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        return builder.toString().toLowerCase();
    }
}
