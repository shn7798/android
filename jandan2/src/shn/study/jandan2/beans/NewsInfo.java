package shn.study.jandan2.beans;

public class NewsInfo {

	private String title;
	private Author author;
	private String tag;
	private String link;
	private String intros;
	private String iconLink;

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

	public String getIntros() {
		return intros;
	}

	public void setIntros(String intros) {
		this.intros = intros;
	}

	public String getIconLink() {
		return iconLink;
	}

	public void setIconLink(String iconLink) {
		this.iconLink = iconLink;
	}

	@Override
	public String toString() {
		return "NewsInfo{" +
				"title='" + title + '\'' +
				", author=" + author +
				", tag='" + tag + '\'' +
				", link='" + link + '\'' +
				", intros='" + intros + '\'' +
				", iconLink='" + iconLink + '\'' +
				'}';
	}
}