package shn.study.jandan.api;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import shn.study.jandan.bean.News;
import shn.study.jandan.util.HTTPClientHelper;
import shn.study.jandan.util.StringHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shn7798 on 14-9-9.
 */
public class NewsAPI {
    public static final String TAG = "NewsAPI";
    public static final int API_SUCEESS = 1;

    public static final String SITE_URL = "http://jandan.net";

    /**
     *  URL生成
     *  http://jandan.net/2014/09/09
     */
    public static String getDayNewsURL(int day){
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000 * day);
        return SITE_URL + "/" + df.format(date);
    }


    /**
     * News 提取
     */
    private static final String PREVIEW_NEWS_BODY_START = "<!-- BEGIN content -->";
    private static final String PREVIEW_NEWS_BODY_END = "<!-- END content -->";

    public static String getPreviewNewsBody(String URL){
        try {
            HttpResponse response = HTTPClientHelper.getFromURL(URL);
            HttpEntity entity = response.getEntity();
            String content = HTTPClientHelper.InputStreamTOString(entity.getContent());
            return StringHelper.getSubString(content, PREVIEW_NEWS_BODY_START, PREVIEW_NEWS_BODY_END, null);
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    private static final String PREVIEW_NEWS_ITEM_START = "<div class=\"post f\">";
    private static final String PREVIEW_NEWS_ITEM_END = "<span class=\"break\"></span>";

    public static List<News> getPreviewNewsItems(String body){
        StringHelper.StringCursor cursor = new StringHelper.StringCursor(0);
        int posStart;
        int posEnd;
        List<News> items = new ArrayList<News>();

        String itemBody;
        while(!(itemBody = StringHelper.getSubString(body,PREVIEW_NEWS_ITEM_START,PREVIEW_NEWS_ITEM_END,cursor)).equals("")){
            items.add(getPreviewNews(itemBody));
        }
        return items;
    }

    private static final String PREVIEW_NEWS_ITEM_LINK_START = "<a href=\"";
    private static final String PREVIEW_NEWS_ITEM_LINK_END = "\"";

    /**
     * <div class="thumbs"><a href="http://jandan.net/2014/09/09/pregnancy-myths.html" target="_blank"><img src="http://tankr.net/s/square/8288.jpg" /></a></div>^M
     *<div class="indexs">^M
     *<span class="comment-link">15</span><h2><a href="http://jandan.net/2014/09/09/pregnancy-myths.html" >关于怀孕的种种迷思：>剖腹产会影响亲子关系？</a></h2>^M
     *<div class="time_s"><a href="http://jandan.net/author/loog">王大发财</a> / <a href="http://jandan.net/tag/%e5%81%a5%e5%ba%b7" rel="tag">健康</a></div>^M
     *说到怀孕，很多人都自称这方面的专家，他们知道什么食物能助产，他们可以光看你肚子隆起的形状就判定婴儿的性别。
     *身为科学记者的Linda Geddes列出了五种最常... </div>
     *
     *
     */
    private static News getPreviewNews(String newsBody){
        StringHelper.StringCursor cursor = new StringHelper.StringCursor(0);
        News news = new News();

        Log.d(TAG,"newBody: "+newsBody);
        String link = StringHelper.getSubString(newsBody,"<a href=\"","\"",cursor);
        Log.d(TAG,"link: "+link);
        String picURL = StringHelper.getSubString(newsBody, "<img src=\"","\"", cursor);
        Log.d(TAG,"picURL: "+picURL);
        String count = StringHelper.getSubString(newsBody, "<span class=\"comment-link\">", "</span>", cursor);
        Log.d(TAG,"count: "+count);
        String title = StringHelper.getSubString(newsBody, "\" >", "</a>", cursor);
        Log.d(TAG,"title: "+title);
        cursor.value += 29;  // skip  [<div class="time_s"><a href="]
        String author = StringHelper.getSubString(newsBody,"\">","</a>",cursor);
        Log.d(TAG,"author: "+author);
        String tag = StringHelper.getSubString(newsBody,"tag\">","</a>",cursor);
        Log.d(TAG,"tag: "+tag);
        String body = StringHelper.getSubString(newsBody,"</div>","</div>",cursor);
        Log.d(TAG,"body: "+body);

        news.setLink(link.trim());
        news.setCommentCount(Integer.valueOf(count.trim()));
        news.setTitle(title.trim());
        news.setAuthor(author.trim());
        news.setTag(tag.trim());
        news.setBody(body.trim());
        news.setPicURL(picURL.trim());

        return news;
    }
}