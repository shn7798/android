package shn.study.jandan.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import org.apache.http.HttpResponse;

import java.io.IOException;

/**
 * Created by shn7798 on 14-9-10.
 */
public class ImageHelper {
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

