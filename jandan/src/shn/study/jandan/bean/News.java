package shn.study.jandan.bean;

import android.graphics.Bitmap;

/**
 * Created by shn7798 on 14-9-7.
 */
public class News extends Base {
    private String title;
    private String body;
    private NewsComment newsComment;
    private Integer commentCount;
    private NewsAuthor newsAuthor;
    private String author;
    private String catlog;
    private String postTime;
    private String quote;
    private String tag;
    private String link;
    private String picURL;
    private Bitmap picObj;

    public Bitmap getPicObj() {
        return picObj;
    }

    public void setPicObj(Bitmap picObj) {
        this.picObj = picObj;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }



    public News parse(String newsData){
        //TODO
        return new News();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public NewsComment getNewsComment() {
        return newsComment;
    }

    public void setNewsComment(NewsComment newsComment) {
        this.newsComment = newsComment;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public NewsAuthor getNewsAuthor() {
        return newsAuthor;
    }

    public void setNewsAuthor(NewsAuthor newsAuthor) {
        this.newsAuthor = newsAuthor;
    }

    public String getCatlog() {
        return catlog;
    }

    public void setCatlog(String catlog) {
        this.catlog = catlog;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}
