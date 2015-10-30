package shn.study.jandan2.beans;

import java.util.Date;
import java.util.List;

public class Wuliaotu {

	private Author author;
	private String postID;
	private String postDate;
	private String text;
	private Vote vote;
	private String link;
	private List<String> images;
    private String html;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getPostID() {
		return postID;
	}

	public void setPostID(String postID) {
		this.postID = postID;
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

	public Vote getVote() {
		return vote;
	}

	public void setVote(Vote vote) {
		this.vote = vote;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Wuliaotu{" +
                "author=" + author +
                ", postID='" + postID + '\'' +
                ", postDate='" + postDate + '\'' +
                ", text='" + text + '\'' +
                ", vote=" + vote +
                ", link='" + link + '\'' +
                ", images=" + images +
                '}';
    }
}