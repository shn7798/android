package shn.study.jandan2.api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import shn.study.jandan2.api.NewsDetail.JandanNewsDetail;
import shn.study.jandan2.api.NewsInfo.JandanNewsInfo;
import shn.study.jandan2.api.Wuliaotu.JandanWuliaotu;
import shn.study.jandan2.beans.*;
import shn.study.jandan2.utils.HttpHelper;

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
            String html = HttpHelper.fetchJandanHtml(url);
            Document doc = Jsoup.parse(html, url);

            Elements es = doc.select("div.post");
            for(Element e : es) {
                try {
                    NewsInfo newsInfo = JandanNewsInfo.parse(e);
                    newsInfoList.add(newsInfo);
                }
                catch (NullPointerException npe){
                    npe.printStackTrace();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return newsInfoList;
    }

    public static NewsDetail getNewsDetail(String url){
        try {
//            HttpResponse response = HTTPClientHelper.getFromURL(url);
//
//            //Jsoup.parse()
//            InputStream is = response.getEntity().getContent();
//            Document doc = Jsoup.parse(is, "UTF-8", url);
//
            FileInputStream fis = new FileInputStream("test/newsDetail.html");
            Document doc = Jsoup.parse(fis, "UTF-8", url);
            NewsDetail newsDetail = JandanNewsDetail.parse(doc.body());
            return newsDetail;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
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
            FileInputStream fis = new FileInputStream("test/wuliaotu.html");
            Document doc = Jsoup.parse(fis, "UTF-8", url);

            Element list = doc.select("ol.commentlist").first();
            Elements es = list.select("li[id~=comment-\\d+]");
            for (Element e : es) {
                try {
                    Wuliaotu wuliaotu = JandanWuliaotu.parse(e);
                    wuliaotuList.add(wuliaotu);
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
        List<NewsInfo> newsInfoList = getNewsInfoList("http://jandan.net");
        for(NewsInfo newsInfo : newsInfoList){
            System.out.println(newsInfo);
        }
//        List<Wuliaotu> WuliaotuList = getWuliaotuList("http://jandan.net/pic/page-7690#comments");
//        for(Wuliaotu wuliaotu : WuliaotuList){
//            System.out.println(wuliaotu);
//        }
//        NewsDetail newsDetail = getNewsDetail("http://jandan.net/2015/10/30/metal-spiders.html");
//        System.out.println(newsDetail);

    }
}
