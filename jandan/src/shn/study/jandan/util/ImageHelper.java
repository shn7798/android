package shn.study.jandan.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;

/**
 * Created by shn7798 on 14-9-10.
 */
public class ImageHelper {
    public static void threadGetImageFromURL(final Object obj, final String imageURL, final Handler handler, final int msgSign){
        new Thread(){
            @Override
            public void run() {
                Bitmap img;
                try {
                    img = getImageFromURL(imageURL);
                } catch (ClientProtocolException e){
                    e.printStackTrace();
                    return;
                } catch (IOException e){
                    e.printStackTrace();
                    return;
                }
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

    public static Bitmap getImageFromURL(String imageURL)
            throws IOException, ClientProtocolException {

        HttpResponse response = HTTPClientHelper.getFromURL(imageURL);
        return BitmapFactory.decodeStream(response.getEntity().getContent());
    }

    public static Bitmap getImageFromPath(String imagePath){
        return BitmapFactory.decodeFile(imagePath);
    }

    public static Boolean saveImageToPath(String savePath){
        return false;
    }


}

