package shn.study.jandan2.beans;


import java.util.Date;

public class Comment {

	private Author author;
	private String postDate;
	private String text;
	private Vote vote;
	private String index;
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

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

    @Override
    public String toString() {
        return "Comment{" +
                "author=" + author +
                ", postDate='" + postDate + '\'' +
                ", text='" + text + '\'' +
                ", vote=" + vote +
                ", index='" + index + '\'' +
                ", html='" + html + '\'' +
                '}';
    }
}