package com.sydeem.girafe.culturebandedessine.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

import com.sydeem.girafe.culturebandedessine.ListeActivity;
import com.sydeem.girafe.culturebandedessine.R;
import com.sydeem.girafe.culturebandedessine.feed.ParsedExampleDataSet;
import com.sydeem.girafe.culturebandedessine.feed.Post;
import com.sydeem.girafe.culturebandedessine.feed.PostAdapter;
import com.sydeem.girafe.culturebandedessine.feed.RSSHandler;

public class NewsService extends Service implements OnSharedPreferenceChangeListener {
	
	private Timer timer = null;
	public static int ID_NOTIFICATION = 1;
	private Intent notificationIntent = null;
	private Notification notification = null;
	private NotificationManager notificationManager = null;
	
	public static List<Post> Posts = null;
	private List<String> Titles = null;
	private List<String> Urls = null;
	private List<String> Descriptions = null;
	private List<String> Thumbnails = null;
	private Intent intent = null;
	private MediaPlayer mMediaPlayer;
	private long updateTimePref = 86400000;
	
	private static int i = 0; 

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		this.intent = intent;
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		timer = new Timer(); 
	}
	
	@Override
	public int onStartCommand(final Intent intent, int flags, int startId) {
		
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					public void run() {
					
						Posts = GetFeeds();
						
						if(intent!=null && intent.getExtras()!=null){
							if(intent.getExtras().containsKey("ShowNotification")){
								if(intent.getExtras().getBoolean("ShowNotification")){
									if(checkConnectionState())
										ShowNotification(Posts.size());
									else
										stopSelf();
								}
							}
							intent.putExtra("ShowNotification", true);							
						}
					}
				}).start();
				
			}
		}, 0, updateTimePref);
	 
	    return START_NOT_STICKY; 
	}
	
	private void ShowNotification(int number){
		Log.i("Call", "News Call " + String.valueOf(i));
		// Traitement
		CharSequence tickerText = "Titre de la notification";
		// Dat� de maintenant
		long when = System.currentTimeMillis();
		// La notification est cr��e
		notification = new Notification(R.drawable.rss_80, tickerText,
		when);
		notification.number = number;
		notification.flags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONGOING_EVENT | Notification.FLAG_SHOW_LIGHTS;
		// Intent qui lancera vers l'activit� MainActivity
		notificationIntent = new Intent(NewsService.this, ListeActivity.class);
		notificationIntent.putExtra("FeedsReady", true);
		notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent contentIntent =
		PendingIntent.getActivity(NewsService.this, 0, notificationIntent,
		0);
		notification.setLatestEventInfo(NewsService.this, "Titre", "Texte", 
		contentIntent);

		notification.ledARGB = Color.GREEN;
		notification.ledOnMS = 500;
		notification.ledOffMS = 5000;
		
		AudioManager audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
		switch(audioManager.getRingerMode()){
		
			case AudioManager.RINGER_MODE_VIBRATE:
				notification.vibrate = new long[] {0,1000,600,1000,600,1000};
			break;
			
			case AudioManager.RINGER_MODE_NORMAL:
				notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				break;
		}

		
		
		notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(ID_NOTIFICATION, notification);
	}
	
 	private List<Post> GetFeeds(){
		Posts = new ArrayList<Post>();
        Titles = new ArrayList<String>();
        Urls = new ArrayList<String>();
        Descriptions = new ArrayList<String>();
        //PublishedDate = new ArrayList<String>();
        //Author = new ArrayList<String>();
        Thumbnails = new ArrayList<String>();
        
        /* Create a new TextView to display the parsingresult later. */
        final TextView tv = new TextView(this);
        try {
        	new Thread(new Runnable() {
				
				public void run() {
					// TODO Auto-generated method stub
					
					/* Create a URL we want to load some xml-data from. */
	                URL url = null;
					try {
						url = new URL("http://www.rfi.fr/taxonomy/term/3463/all/feed");
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                
	                /* Get a SAXParser from the SAXPArserFactory. */
	                SAXParserFactory spf = SAXParserFactory.newInstance();
	                SAXParser sp = null;;
					try {
						sp = spf.newSAXParser();
					} catch (ParserConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	                /* Get the XMLReader of the SAXParser we created. */
	                XMLReader xr = null;
					try {
						xr = sp.getXMLReader();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                /* Create a new ContentHandler and apply it to the XML-Reader*/
	                RSSHandler myExampleHandler = new RSSHandler();
	                xr.setContentHandler(myExampleHandler);
	                
	                /* Parse the xml-data from our URL. */
	                try {
						xr.parse(new InputSource(url.openStream()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                
	                /* Parsing has finished. */
	                /* Our ExampleHandler now provides the parsed data to us. */                
	                ParsedExampleDataSet parsedExampleDataSet =
	                                                                myExampleHandler.getParsedData();

	                Posts = myExampleHandler.getPostList();
					
				}
			}).start();
        	
                
                
        } catch (Exception e) {
                /* Display any Error to the GUI. */
                tv.setText("Error: " + e.getMessage());
                Log.e("NewsService", "WeatherQueryError", e);
        }
        
        return Posts;
	}
 	
 	private boolean checkConnectionState(){
    	final ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
    	NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
    	          
    	if (info != null)          
    	{                                  
    		for (int i = 0; i <info.length; i++)                 
    		{                          
    			if (info[i].getState() == NetworkInfo.State.CONNECTED)                                                             				
    				return true;                                
    		}               
    	}              	
    	
    	return false;
    }
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.timer.cancel();
		stopForeground(true);
		notificationManager.cancel(ID_NOTIFICATION);
	}

    //Get an alarm sound. Try for an alarm. If none set, try notification,
    //Otherwise, ringtone.
	private Uri getAlarmUri() {
	    Uri alert = RingtoneManager
	            .getDefaultUri(RingtoneManager.TYPE_ALARM);
	    if (alert == null) {
	        alert = RingtoneManager
	                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	        if (alert == null) {
	            alert = RingtoneManager
	                    .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
	        }
	    }
	    return alert;
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		this.updateTimePref = sharedPreferences.getLong("updateNewsTimePref", 86400000);
	}




}
