package shn.study.jandan2.api;

import org.apache.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import shn.study.jandan2.beans.Author;
import shn.study.jandan2.beans.NewsDetail;
import shn.study.jandan2.beans.NewsInfo;
import shn.study.jandan2.utils.HTTPClientHelper;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by shn7798 on 15-10-29.
 */
public class JandanAPI {

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

            Iterator<Element> esi = doc.select("#content > div.post.f.list-post").iterator();
            for(Element e = esi.next(); esi.hasNext();) {
                //System.out.println("-----------------------");
                //System.out.print(e.html());

                Author author = new Author();
                NewsInfo newsInfo = new NewsInfo();

                String iconLink = e.select("div.thumbs_b > a > img[src]").first().attr("src");
                String newsLink = e.select("div.thumbs_b > a[href]").first().attr("href");
                String authorName = e.select("div.time_s > a").first().text();
                String authorLink = e.select("div.time_s > a[href]").get(0).attr("href");
                String newsTag = e.select("div.time_s > a[href]").get(1).text();
                //String newsLink = e.select("div.indexs > h2 > a[href]").first().attr("href");
                String newsTitle = e.select("div.indexs > h2 > a[href]").first().text();
                String newsIntros = e.select("div.indexs").first().text();

                author.setName(authorName);
                author.setLink(authorLink);
                author.setHash("");

                newsInfo.setAuthor(author);
                newsInfo.setIconLink(iconLink);
                newsInfo.setIntros(newsIntros);
                newsInfo.setLink(newsLink);
                newsInfo.setTag(newsTag);
                newsInfo.setTitle(newsTitle);

                newsInfoList.add(newsInfo);

                System.out.print(newsInfo);
                break;
            }
            fis.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return newsInfoList;
    }

    public NewsDetail getNewsDetail(String url){
        NewsDetail newsDetail = new NewsDetail();
        return newsDetail;
    }


    /* private methods */

//    private Author getAuthorFromNewsInfoHtml(String newsInfoHtml){
//        return new Author();
//    }


    public static void main(String[] args){
        List<NewsInfo> nsList = getNewsInfoList("http://jandan.net");
    }
}
