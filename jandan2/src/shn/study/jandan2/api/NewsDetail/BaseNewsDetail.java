package shn.study.jandan2.api.NewsDetail;

import org.jsoup.nodes.Element;
import shn.study.jandan2.api.Author.BaseAuthor;
import shn.study.jandan2.api.Author.NewsDetailAuthor;
import shn.study.jandan2.beans.Author;
import shn.study.jandan2.beans.NewsDetail;
import shn.study.jandan2.beans.NewsInfo;

/**
 * Created by shn7798 on 15-10-30.
 */
public class BaseNewsDetail {

    public static NewsDetail parse(Element e){
        NewsDetail newsDetail = new NewsDetail();

        String title = e.select("#content > div.post.f > h1 > a").text();
        String postDate = e.select("#content > div.post.f > div.time_s").text();
        String tag = e.select("#content > h3.title > a").text();
        //String link = url;
        String text = e.select("#content > div.post.f > p").text();
        String html = e.select("#content > div.post.f > p").html();

        Author author = NewsDetailAuthor.parse(e);
        newsDetail.setAuthor(author);

        newsDetail.setTitle(title);
        newsDetail.setTag(tag);
        //newsDetail.setLink(link);
        newsDetail.setPostDate(postDate);
        newsDetail.setText(text);
        newsDetail.setText(html);

        return newsDetail;
    }
}
