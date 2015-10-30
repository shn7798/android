package shn.study.jandan2.api.Author;

import org.jsoup.nodes.Element;
import shn.study.jandan2.beans.Author;

/**
 * Created by shn7798 on 15-10-30.
 */
public class BaseAuthor{
    public static Author parse(Element e) throws NullPointerException{
        Author author = new Author();
        Element ae = e.select("div.author > strong").first();
        String name = ae.text();
        String hash = ae.attr("title");
        author.setName(name);
        author.setHash(hash);
        return author;
    }
}


