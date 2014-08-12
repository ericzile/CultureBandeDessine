package com.sydeem.girafe.culturebandedessine.feed.lib;

public class Feed {
	
	private String feedUrl = null;
	private String title = null;
	private String link = null;
	private String description = null;
	private String author = null;
    private String publishedDate = null;
	private Entry[] entries = null;
	
	public String getFeedUrl() {
		return feedUrl;
	}
	public void setFeedUrl(String feedUrl) {
		this.feedUrl = feedUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
    public String getPublishedDate() {
        return publishedDate;
    }
    public  void setPublishedDate (String publishedDate) {
        this.publishedDate = publishedDate;
    }
	public Entry[] getEntries() {
		return entries;
	}
	public void setEntries(Entry[] entries) {
		this.entries = entries;
	}
	

}
