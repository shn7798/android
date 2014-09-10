package shn.study.jandan.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import org.apache.http.HttpResponse;

import java.io.IOException;

/**
 * Created by shn7798 on 14-9-10.
 */
public class ImageHelper {
    public static void threadGetImageFromURL(final Object obj, final String imageURL, final Handler handler, final int msgSign){
        new Thread(){
            @Override
            public void run() {
                Bitmap img = getImageFromURL(imageURL);
                Bundle data = new Bundle();
                data.putParcelable("image",img);

                Message msg = new Message();
                msg.what = msgSign;
                msg.obj = obj;
                msg.setData(data);

                handler.sendMessage(msg);
            }
        }.start();
    }

    public static Bitmap getImageFromURL(String imageURL){
        Bitmap bitmap;
        try {
            HttpResponse response = HTTPClientHelper.getFromURL(imageURL);
            bitmap = BitmapFactory.decodeStream(response.getEntity().getContent());
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    public static Bitmap getImageFromPath(String imagePath){
        return BitmapFactory.decodeFile(imagePath);
    }

    public static Boolean saveImageToPath(String savePath){
        return false;
    }


}

