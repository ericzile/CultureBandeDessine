package com.sydeem.girafe.culturebandedessine.feed.lib;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sydeem.girafe.culturebandedessine.ListeActivity.ViewHolder;
import com.sydeem.girafe.culturebandedessine.R;
import com.sydeem.girafe.culturebandedessine.feed.ImageLoader;

public class FeedAdapter extends BaseAdapter{

	private Entry[] entriesArray = null;
	private static LayoutInflater inflater = null;
	private ViewHolder holder = null;
	private ImageLoader imageLoader = null;
	private SharedPreferences sharedPref = null;


	public FeedAdapter(Context context, Entry[] entries) {
		inflater = LayoutInflater.from(context);
		this.entriesArray = entries;
		imageLoader = new ImageLoader(context);
		sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

	public int getCount() {
		return entriesArray.length;
	}

	public Entry getItem(int position) {

		return entriesArray[position];
	}


	public long getItemId(int position) {

		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row, null);
			holder = new ViewHolder();
			holder.titleView = (TextView) convertView.findViewById(R.id.titleView);
			holder.publisheddate = (TextView) convertView.findViewById(R.id.pubdate);
			holder.DescriptionView = (TextView) convertView.findViewById(R.id.DescriptionView);
            holder.creator = (TextView) convertView.findViewById(R.id.creator);
			holder.thumbnail = (ImageView)convertView.findViewById(R.id.thumbnail);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder)convertView.getTag();
		String title = entriesArray[position].getTitle();

		holder.titleView.setTextColor(sharedPref.getInt("titleColorPref", Color.parseColor("#222E7B")));
		holder.titleView.setText(title);
		String description = entriesArray[position].getContent();
		holder.DescriptionView.setTextColor(sharedPref.getInt("descriptionColorPref", Color.parseColor("#FFFFE1")));
		holder.DescriptionView.setText( Html.fromHtml(description) );
		if(holder.DescriptionView.getText().length() > 90){
			holder.DescriptionView.setText(holder.DescriptionView.getText().subSequence(0, 90)+"...");
		}
		String publishedDate = entriesArray[position].getPublishedDate();
        holder.publisheddate.setTextColor(sharedPref.getInt("publishedDateColorPref", Color.parseColor("#222E7B")));
        holder.publisheddate.setText(publishedDate);
        String author = entriesArray[position].getAuthor();
        holder.creator.setTextColor(sharedPref.getInt("creatorColorPref", Color.parseColor("#222E7B")));
        holder.creator.setText(author);
		String thumbnail = entriesArray[position].getMediaGroup();
		imageLoader.DisplayImage(thumbnail, holder.thumbnail);



        return convertView;
	}

}
