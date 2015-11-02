package shn.study.jandan2.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;

/**
 * Created by shn7798 on 15-11-1.
 */
public class HttpHelper {

    public static HttpGet getJandanRequest(String url){
        HttpGet request = new HttpGet(url);
        //request.addHeader("Pragma","no-cache");
        //request.addHeader("Accept-Encoding","gzip, deflate, sdch");
        request.addHeader("Accept-Language","zh-CN,zh;q=0.8");
        //request.addHeader("Upgrade-Insecure-Requests","1");
        //request.addHeader("Cache-Control","no-cache");
        //request.addHeader("Connection","keep-alive");
        request.addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36");
        request.addHeader("Cookie","1415497472=38");

        return request;
    }

    public static Bitmap fetchJandanImage(String url){
        Bitmap image = null;
        HttpGet request = getJandanRequest(url);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                InputStream is = response.getEntity().getContent();
                image = BitmapFactory.decodeStream(is);
                is.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return image;
    }

    public static String fetchJandanHtml(String url){
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet request = getJandanRequest(url);

            HttpResponse response = httpClient.execute(request);
            if(response.getStatusLine().getStatusCode() == 200){
                InputStream is = response.getEntity().getContent();
                String html = HTTPClientHelper.InputStreamTOString(is, "UTF-8");
                is.close();
                return html;
            } else {
                Log.d("errpage",EntityUtils.toString(response.getEntity()));

                throw new Exception("HTTP status == "+ response.getStatusLine().getStatusCode());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
