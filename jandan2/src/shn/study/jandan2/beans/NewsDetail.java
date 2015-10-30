package shn.study.jandan2.beans;

import java.util.Date;
import java.util.List;

public class NewsDetail {

	private String title;
	private Author author;
	private String tag;
	private String link;
	private String postDate;
	private String text;
	private String via;
	private List<Comment> comments;
    private String html;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPostDate() {
		return postDate;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "NewsDetail{" +
				"title='" + title + '\'' +
				", author=" + author +
				", tag='" + tag + '\'' +
				", link='" + link + '\'' +
				", postDate='" + postDate + '\'' +
				", text='" + text + '\'' +
				", via='" + via + '\'' +
				", comments=" + comments +
				'}';
	}
}