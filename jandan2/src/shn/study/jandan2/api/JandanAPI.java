package shn.study.jandan2.api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import shn.study.jandan2.beans.*;

import java.io.FileInputStream;
import java.util.ArrayList;
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

            Elements es = doc.select("#content > div.post.f.list-post");
            for(Element e : es) {
                Author author = new Author();
                NewsInfo newsInfo = new NewsInfo();

                try {
                    String iconLink = e.select("div.thumbs_b > a > img[src]").first().attr("src");
                    String newsLink = e.select("div.thumbs_b > a[href]").first().attr("href");

                    //String newsLink = e.select("div.indexs > h2 > a[href]").first().attr("href");
                    String newsTitle = e.select("div.indexs > h2 > a[href]").first().text();

                    String newsTag = e.select("div.indexs > div.time_s > a[href]").get(1).text();
                    String authorName = e.select("div.indexs > div.time_s > a").first().text();
                    String authorLink = e.select("div.indexs > div.time_s > a[href]").get(0).attr("href");

                    author.setName(authorName);
                    author.setLink(authorLink);

                    newsInfo.setIconLink(iconLink);
                    newsInfo.setLink(newsLink);
                    newsInfo.setTag(newsTag);
                    newsInfo.setTitle(newsTitle);
                }
                catch (NullPointerException npe){
                    npe.printStackTrace();
                    continue;
                }

                try {
                    e.select("div.thumbs_b").remove();
                    e.select("div.indexs > span.comment-link").remove();
                    e.select("div.indexs > div.time_s").remove();
                    e.select("div.indexs > h2").remove();
                    e.select("div.indexs > a").remove();
                    e.select("div.indexs > span").remove();
                }
                catch (NullPointerException npe){
                    npe.printStackTrace();
                }

                try {
                    String newsIntros = e.select("div.indexs").first().text();
                    newsInfo.setIntros(newsIntros);
                }
                catch (NullPointerException npe){
                    npe.printStackTrace();
                    continue;
                }

                newsInfo.setAuthor(author);
                newsInfoList.add(newsInfo);

                System.out.println(newsInfo);
                System.out.println(author);
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
                Author author = parseAuthor(e);
                Vote vote = parseVote(e);
                Wuliaotu wuliaotu = new Wuliaotu();

                try {
                    String postDate = e.select("div.author > small > a").first().text();
                    wuliaotu.setPostDate(postDate);
                }
                catch (NullPointerException e1){
                    //无关数据，忽略异常
                    e1.printStackTrace();
                }

                try {
                    String text = e.select("div.text > p").html();

                    // img的href中的图片链接
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
                }
                catch (NullPointerException e1){
                    continue;
                }

                wuliaotu.setAuthor(author);
                wuliaotu.setVote(vote);
                wuliaotuList.add(wuliaotu);

                System.out.println(wuliaotu);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return wuliaotuList;
    }

    public static NewsDetail getNewsDetail(String url){
        NewsDetail newsDetail = new NewsDetail();
        try {
//            HttpResponse response = HTTPClientHelper.getFromURL(url);
//
//            //Jsoup.parse()
//            InputStream is = response.getEntity().getContent();
//            Document doc = Jsoup.parse(is, "UTF-8", url);
//
            FileInputStream fis = new FileInputStream("/Users/shn7798/Documents/jandannews.html");
            Document doc = Jsoup.parse(fis, "UTF-8", url);

            Author author = parseAuthor(doc);

            doc.select("#content > div.post.f > div.time_s > a").remove();
            String title = doc.select("#content > div.post.f > h1 > a").text();
            String postDate = doc.select("#content > div.post.f > div.time_s").text();
            String tag = doc.select("#content > h3.title > a").text();
            String link = url;
            String text = doc.select("#content > div.post.f > p").html();

            newsDetail.setTitle(title);
            newsDetail.setAuthor(author);
            newsDetail.setTag(tag);
            newsDetail.setLink(link);
            newsDetail.setPostDate(postDate);
            newsDetail.setText(text);



            Elements comments = doc.select("#content > div > ol.commentlist > li[id~=comment-\\d+]");
            List<Comment> commentList = new ArrayList<>();
            for(Element ec : comments){
                Comment comment = parseNewsDetailComment(ec);
                commentList.add(comment);
            }
            newsDetail.setComments(commentList);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return newsDetail;
    }


    /* private methods */

//    private Author getAuthorFromNewsInfoHtml(String newsInfoHtml){
//        return new Author();
//    }

    private static Comment parseNewsDetailComment(Element e){
        Comment comment = new Comment();
        Author author = parseAuthor(e);
        Vote vote = parseVote(e);

        String index = e.select("div.text > span.righttext").text();
        e.select("div.text > span.righttext").remove();
        String text = e.select("div.text > p").html();
        String postDate = e.select("div.author > small > a").text();

        comment.setAuthor(author);
        comment.setVote(vote);
        comment.setIndex(index);
        comment.setText(text);
        comment.setPostDate(postDate);

        return comment;
    }

    public static String parseImageSrc(Element e){
        if("".equals(e.attr("org_src"))){
            // 小图片， 没有mask
            return e.attr("src");
        }else{
            // 大图片， src为mask图片地址
            return e.attr("org_src");
        }
    }

    public static Author parseAuthor(Element e){
        Author author = new Author();
        try {
            Element ae = e.select("div.author > strong").first();
            String name = ae.text();
            String hash = ae.attr("title");
            author.setName(name);
            author.setHash(hash);
        }
        catch (NullPointerException npe){
            npe.printStackTrace();
        }
        return author;
    }

    public static Vote parseVote(Element e){
        Vote vote = new Vote();
        try {
            String oo = e.select("span[id~=cos_support-\\d+").text();
            String xx = e.select("span[id~=cos_unsupport-\\d+").text();
            vote.setOo(oo);
            vote.setXx(xx);
        }
        catch (NullPointerException npe){
            npe.printStackTrace();
        }
        return vote;
    }

    public static void main(String[] args){
        List<NewsInfo> nsList = getNewsInfoList("http://jandan.net");
        //List<Wuliaotu> nsList = getWuliaotuList("http://jandan.net/pic");

    }
}
