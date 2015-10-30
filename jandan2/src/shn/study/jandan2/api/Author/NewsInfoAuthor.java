package shn.study.jandan2.api.Author;

import org.jsoup.nodes.Element;
import shn.study.jandan2.beans.Author;

/**
 * Created by shn7798 on 15-10-30.
 */
public class NewsInfoAuthor extends BaseAuthor {
    public static Author parse(Element e) throws NullPointerException{
        Author author = new Author();
        String name = e.select("div.time_s > a").first().text();
        String link = e.select("div.time_s > a[href]").first().attr("href");

        author.setName(name);
        author.setLink(link);
        return author;
    }
}
