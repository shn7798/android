package shn.study.jandan2.api.NewsInfo;

import org.jsoup.nodes.Element;
import shn.study.jandan2.api.Author.NewsInfoAuthor;
import shn.study.jandan2.beans.Author;
import shn.study.jandan2.beans.NewsInfo;

/**
 * Created by shn7798 on 15-10-30.
 */
public class BaseNewsInfo{
    public static shn.study.jandan2.beans.NewsInfo parse(Element e){
        NewsInfo newsInfo = new NewsInfo();

        Element iconE = e.select("div.thumbs_b > a > img").first();
        String iconLink = iconE.attr("src");
        if(iconLink.isEmpty()){
            // jquery lazyload
            iconLink = iconE.attr("data-original");
        }
        String newsLink = e.select("div.thumbs_b > a[href]").first().attr("href");
        //String newsLink = e.select("div.indexs > h2 > a[href]").first().attr("href");
        String newsTitle = e.select("div.indexs > h2 > a[href]").first().text();
        String newsTag = e.select("div.indexs > div.time_s > a[href]").get(1).text();
        String newsIntros = e.select("div.indexs").first().text();

        Author author = NewsInfoAuthor.parse(e);
        newsInfo.setAuthor(author);

        newsInfo.setIconLink(iconLink);
        newsInfo.setLink(newsLink);
        newsInfo.setTag(newsTag);
        newsInfo.setTitle(newsTitle);
        newsInfo.setIntros(newsIntros);

        return newsInfo;
    }
}
