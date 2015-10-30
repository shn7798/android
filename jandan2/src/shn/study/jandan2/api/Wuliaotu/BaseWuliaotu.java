package shn.study.jandan2.api.Wuliaotu;

import org.jsoup.nodes.Element;
import shn.study.jandan2.api.Author.WuliaotuAuthor;
import shn.study.jandan2.api.Vote.WuliaotuVote;
import shn.study.jandan2.beans.Author;
import shn.study.jandan2.beans.Vote;
import shn.study.jandan2.beans.Wuliaotu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shn7798 on 15-10-30.
 */
public class BaseWuliaotu{
    public static Wuliaotu parse(Element e){
        Wuliaotu wuliaotu = new Wuliaotu();

        Author author = WuliaotuAuthor.parse(e);
        Vote vote = WuliaotuVote.parse(e);
        wuliaotu.setAuthor(author);
        wuliaotu.setVote(vote);

        try {
            String postDate = e.select("div.author > small > a").first().text();
            wuliaotu.setPostDate(postDate);
        }
        catch (NullPointerException npe){
            //无关数据，忽略异常
            npe.printStackTrace();
        }


        String text = e.select("div.text > p").text();
        String html = e.select("div.text > p").html();

        List<String> images = new ArrayList<String>();
        for(Element ie : e.select("div.text > p > img[src]")){
            images.add(parseImageSrc(ie));
        }
        wuliaotu.setImages(images);

        String link = e.select("div.text > span.righttext > a[href]").first().attr("href");
        String postId = e.select("div.text > span.righttext > a[href]").first().text();

        wuliaotu.setLink(link);
        wuliaotu.setPostID(postId);
        wuliaotu.setText(text);
        wuliaotu.setHtml(html);

        return wuliaotu;
    }


    private static String parseImageSrc(Element e){
        if("".equals(e.attr("org_src"))){
            // 小图片， 没有mask
            return e.attr("src");
        }else{
            // 大图片， src为mask图片地址
            return e.attr("org_src");
        }
    }

}
