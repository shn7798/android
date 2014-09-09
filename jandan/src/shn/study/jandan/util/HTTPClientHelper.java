package shn.study.jandan.util;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HTTPClientHelper {
    final static int BUFFER_SIZE = 4096;

    /**
     * 设置请求的HTTP头部
     * @param headers
     * @param httpRequest
     */
    private static void setRequestHeaders(Map<String,String> headers, HttpRequestBase httpRequest){
        if(headers != null && headers.size() > 0){
            Iterator iter = headers.entrySet().iterator();
            while(iter.hasNext()){
                Map.Entry<String,String> entry = (Map.Entry<String,String>)iter.next();
                httpRequest.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 设置POST请求的表单
     * @param form
     * @param httpPost
     */
    private static void setPostRequestForm(List form, HttpPost httpPost){
        if(form != null && form.size() > 0){
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(form, HTTP.UTF_8));
            } catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取HTTPClient实例
     * @return
     */
    public static HttpClient getHttpClient(){
        return new DefaultHttpClient();
    }

    public static HttpResponse getFromURL(String URL){
        return getFromURL(URL,null);
    }

    /**
     * 从URL获取response
     * @param URL
     * @param headers
     * @return response
     */
    public static HttpResponse getFromURL(String URL, Map<String,String> headers){
        if(URL == null)
            return null;
        HttpResponse response;

        HttpClient httpClient = getHttpClient();
        HttpGet httpGet = new HttpGet(URL);

        setRequestHeaders(headers, httpGet);

        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e){
            response = null;
            e.printStackTrace();
        }
        return response;
    }

    public static HttpResponse postFromURL(String URL, Map<String,String> headers){
        return postFromURL(URL,headers,null);
    }
    public static HttpResponse postFromURL(String URL, List form){
        return postFromURL(URL,null,form);
    }
    public static HttpResponse postFromURL(String URL){
        return postFromURL(URL,null,null);
    }

    /**
     * 从URL获取response
     * @param URL
     * @param headers
     * @param form
     * @return response
     */
    public static HttpResponse postFromURL(String URL, Map<String,String> headers, List form){
        if(URL == null)
            return null;
        HttpResponse response;

        HttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(URL);

        setRequestHeaders(headers, httpPost);
        setPostRequestForm(form,httpPost);

        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e){
            response = null;
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 转换流到String
     * @param in
     * @param charSet
     * @return
     * @throws Exception
     */

    public static String InputStreamTOString(InputStream in, String charSet) throws Exception{

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return new String(outStream.toByteArray(),charSet);
    }

    public static String InputStreamTOString(InputStream in) throws Exception {
        return InputStreamTOString(in,HTTP.UTF_8);
    }

}
