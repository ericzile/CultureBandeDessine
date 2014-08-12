package com.sydeem.girafe.culturebandedessine.feed;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class RSSHandler extends DefaultHandler {
	
	// ===========================================================
    // Fields
    // ===========================================================
    private boolean in_channel = false;
    private boolean in_item = false;
    private boolean in_title = false;
    private boolean in_published = false;
    private boolean in_description = false;
    private boolean in_author = false;;
    
    private List<Post> posts = null;
    private Post currentPost = null;
    
    private StringBuffer buffer = null;
   
    private ParsedExampleDataSet myParsedExampleDataSet = new ParsedExampleDataSet();

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public ParsedExampleDataSet getParsedData() {
            return this.myParsedExampleDataSet;
    }

    // ===========================================================
    // Methods
    // ===========================================================
    @Override
    public void startDocument() throws SAXException {
            this.myParsedExampleDataSet = new ParsedExampleDataSet();
    }

    @Override
    public void endDocument() throws SAXException {
            // Nothing to do
    	StringBuffer b = new StringBuffer();
    	for (Post post : posts) {
    		b.append(post.getThumbnail());
		}
    	myParsedExampleDataSet.setExtractedString(b.toString());
    }

    /** Gets be called on opening tags like:
     * <tag>
     * Can provide attribute(s), when xml was like:
     * <tag attribute="attributeValue">*/
    @Override
    public void startElement(String namespaceURI, String localName,
                    String qName, Attributes atts) throws SAXException {
    	buffer = new StringBuffer();
    	if (localName.equalsIgnoreCase("channel")) {
    		posts = new ArrayList<Post>();     
    		currentPost = new Post();
    		this.in_channel = true;
    	}else if (localName.equalsIgnoreCase("item")) {
    		
            this.in_item = true;
        }else if (localName.equalsIgnoreCase("title")) {
                this.in_title = true;                      
        }else if (localName.equalsIgnoreCase("publishedDate")) {
                this.in_published = true;
        }else if (localName.equalsIgnoreCase("description")) {
            this.in_description = true;
        }else if (localName.equalsIgnoreCase("author")) {
            this.in_author = true;
        }else if (localName.equalsIgnoreCase("thumbnail")) {
        	Log.i("Info","Url");
        	String attrValue = atts.getValue("url");
        	currentPost.setThumbnail(attrValue);
        }
    	
    }
   
    /** Gets be called on closing tags like:
     * </tag> */
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
                    throws SAXException {
    	if (localName.equalsIgnoreCase("channel")) {
    		this.in_channel = false;
    	}else if (localName.equalsIgnoreCase("item")) {
    		posts.add(currentPost);
    		currentPost = new Post();
            this.in_item = false;                
        }else if (localName.equalsIgnoreCase("title")) {
    		currentPost.setTitle(buffer.toString());
    		buffer = null;
            this.in_title = false;                      
        }else if (localName.equalsIgnoreCase("publishedDate")) {
    		currentPost.setPublishedDate(buffer.toString());
    		buffer = null;
            this.in_published = false;
        }else if (localName.equalsIgnoreCase("description")) {
    		currentPost.setDescription(buffer.toString());
    		buffer = null;
            this.in_description = false;
        }else if (localName.equalsIgnoreCase("author")) {
    		currentPost.setAuthor(buffer.toString());
    		buffer = null;
            this.in_author = false;
        }
    }
   
    /** Gets be called on the following structure:
     * <tag>characters</tag> */
    @Override
	public void characters(char ch[], int start, int length){
	    	if(buffer != null)
	    		buffer.append(new String(ch, start, length));
	    	myParsedExampleDataSet.setExtractedString(new String(ch, start, length));
	}
	
	public List<Post> getPostList(){
		return this.posts;
	}

}

