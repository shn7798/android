package shn.study.jandan.bean;

/**
 * Created by shn7798 on 14-9-7.
 */
public class News extends Base {
    private String title;
    private String body;
    private NewsComment newsComment;
    private Integer commentCount;
    private NewsAuthor newsAuthor;
    private String catlog;
    private String postTime;
    private String quote;

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
