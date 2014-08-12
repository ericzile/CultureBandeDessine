package com.sydeem.girafe.culturebandedessine.feed.lib;

public class Entry {
	private String mediaGroup = null;
	private String title = null; //Use
	private String link = null;
	private String content = null;
	private String contentSnippet = null;
	private String publishedDate = null; //Use
	private String author = null;
	private Category[] catogories = null;
	
	
	public String getMediaGroup() {
		return mediaGroup;
	}
	public void setMediaGroup(String mediaGroup) {
		this.mediaGroup = mediaGroup;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentSnippet() {
		return contentSnippet;
	}
	public void setContentSnippet(String contentSnippet) {
		this.contentSnippet = contentSnippet;
	}
	public String getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
	public Category[] getCatogories() {
		return catogories;
	}
	public void setCatogories(Category[] catogories) {
		this.catogories = catogories;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
}
