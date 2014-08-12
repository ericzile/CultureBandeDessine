package com.sydeem.girafe.culturebandedessine.feed.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class FeedService extends Service {

	private Intent intent = null;
	private String address = null;
	public static Feed feedResult = null;
	
	@Override
	public IBinder onBind(Intent intent) {
		this.intent = intent;
		if(this.intent != null && this.intent.getExtras() != null){
			this.address = this.intent.getExtras().getString("feedUrl");
		}
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(this.address != null)
			getFeedFromUrl(this.address);
		return START_NOT_STICKY;
		
	}
		
	public static void getFeedFromUrl(final String address){
		new Thread(new Runnable() {
			
			public void run() {
				URL url = null;
				try {
					url = new URL("https://ajax.googleapis.com/ajax/services/feed/load?" +"v=1.0&q="+address+"&hl=fr&num=-1&userip=192.168.194.101");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			URLConnection connection = null;
			try {
				connection = url.openConnection();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connection.addRequestProperty("Referer", "http://www.smsamour.net/feed/");
			
			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Log.i("Test", "Arrive ici");
			try {
				while((line = reader.readLine()) != null) {
				builder.append(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				JSONObject json = new JSONObject(builder.toString());
				Log.i("Feed", "Start parsing JSON");
				Feed myfeed = ReadFeed(json);
				feedResult = myfeed;
				if(myfeed == null){
					Log.i("Feed", "is null");
				}else{
					Log.i("Feed", "not null");
				}
				Log.i("Feed", "End parsing JSON");
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			}
		}).start();
	}
	
	public static Feed ReadFeed(JSONObject json){
		try {
			JSONObject responseData = json.getJSONObject("responseData");
			JSONObject feed = responseData.getJSONObject("feed");
			Feed rssFeed = new Feed();
			rssFeed.setFeedUrl(feed.getString("feedUrl"));
			rssFeed.setTitle(feed.getString("title"));
			rssFeed.setLink(feed.getString("link"));
			rssFeed.setAuthor(feed.getString("author"));
            rssFeed.setPublishedDate(feed.getString("publishedDate"));
			rssFeed.setDescription(feed.getString("description"));
			JSONArray items = feed.getJSONArray("entries");
			Entry[] entries = new Entry[items.length()];
			for(int i = 0; i < items.length(); i++){
				JSONObject item = items.getJSONObject(i);
				entries[i] = new Entry();
				entries[i].setTitle(item.getString("title"));
				entries[i].setLink(item.getString("link"));
				entries[i].setAuthor(item.getString("author"));
				entries[i].setContent(item.getString("content"));
				entries[i].setContentSnippet(item.getString("contentSnippet"));
				entries[i].setPublishedDate(item.getString("publishedDate"));
				JSONArray categories = item.getJSONArray("categories");
				Category[] Categories = new Category[categories.length()];
				for(int j = 0; j < categories.length(); j++){
					Categories[j] = new Category();
					Categories[j].setName(categories.getString(j));
				}
			}
			rssFeed.setEntries(entries);
			return rssFeed;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
