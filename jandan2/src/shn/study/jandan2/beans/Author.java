package shn.study.jandan2.beans;

public class Author {

	private String name;
	private String hash;
	private String link;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", hash='" + hash + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}