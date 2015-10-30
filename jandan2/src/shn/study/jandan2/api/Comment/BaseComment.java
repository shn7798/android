package shn.study.jandan2.api.Comment;

import org.jsoup.nodes.Element;
import shn.study.jandan2.api.Author.BaseAuthor;
import shn.study.jandan2.api.Vote.BaseVote;
import shn.study.jandan2.beans.Author;
import shn.study.jandan2.beans.Comment;
import shn.study.jandan2.beans.Vote;

/**
 * Created by shn7798 on 15-10-30.
 */
public class BaseComment{
    public static Comment parse(Element e){
        Comment comment = new Comment();

        Author author = BaseAuthor.parse(e);
        Vote vote = BaseVote.parse(e);

        String index = e.select("div.text > span.righttext").text();
        e.select("div.text > span.righttext").remove();
        String text = e.select("div.text > p").text();
        String html = e.select("div.text > p").html();
        String postDate = e.select("div.author > small > a").text();

        comment.setAuthor(author);
        comment.setVote(vote);
        comment.setIndex(index);
        comment.setText(text);
        comment.setHtml(html);
        comment.setPostDate(postDate);
        return comment;
    }

}
