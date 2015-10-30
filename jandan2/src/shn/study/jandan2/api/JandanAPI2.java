package shn.study.jandan2.api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import shn.study.jandan2.api.NewsInfo.JandanNewsInfo;
import shn.study.jandan2.api.Wuliaotu.JandanWuliaotu;
import shn.study.jandan2.beans.*;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shn7798 on 15-10-29.
 */
public class JandanAPI2 {

    public static List<NewsInfo> getNewsInfoList(String url){
        List<NewsInfo> newsInfoList = new ArrayList<NewsInfo>();
        try{
//            HttpResponse response = HTTPClientHelper.getFromURL(url);
//
//            //Jsoup.parse()
//            InputStream is = response.getEntity().getContent();
//            Document doc = Jsoup.parse(is, "UTF-8", url);
//
            FileInputStream fis = new FileInputStream("/Users/shn7798/Documents/jandan.html");
            Document doc = Jsoup.parse(fis, "UTF-8", url);

            Elements es = doc.select("#content > div.post.f.list-post");
            for(Element e : es) {
                try {
                    NewsInfo newsInfo = JandanNewsInfo.parse(e);
                    newsInfoList.add(newsInfo);
                    System.out.println(newsInfo);
                }
                catch (NullPointerException npe){
                    npe.printStackTrace();
                }
                //break;
            }
            fis.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return newsInfoList;
    }

    public static List<Wuliaotu> getWuliaotuList(String url){
        List<Wuliaotu> wuliaotuList = new ArrayList<Wuliaotu>();
        try {
//            HttpResponse response = HTTPClientHelper.getFromURL(url);
//
//            //Jsoup.parse()
//            InputStream is = response.getEntity().getContent();
//            Document doc = Jsoup.parse(is, "UTF-8", url);
//
            FileInputStream fis = new FileInputStream("/Users/shn7798/Documents/jandanpic.html");
            Document doc = Jsoup.parse(fis, "UTF-8", url);

            Element list = doc.select("ol.commentlist").first();
            Elements es = list.select("li[id~=comment-\\d+]");
            for (Element e : es) {
                try {
                    Wuliaotu wuliaotu = JandanWuliaotu.parse(e);
                    wuliaotuList.add(wuliaotu);
                    System.out.println(wuliaotu);
                }
                catch (NullPointerException npe){
                    npe.printStackTrace();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return wuliaotuList;
    }

    public static void main(String[] args){
        //List<NewsInfo> nsList = getNewsInfoList("http://jandan.net");
        List<Wuliaotu> nsList = getWuliaotuList("http://jandan.net/pic");

    }
}