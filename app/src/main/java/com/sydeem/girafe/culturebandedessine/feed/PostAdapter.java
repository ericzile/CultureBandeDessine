package com.sydeem.girafe.culturebandedessine.feed;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sydeem.girafe.culturebandedessine.ListeActivity.ViewHolder;
import com.sydeem.girafe.culturebandedessine.R;

public class PostAdapter extends BaseAdapter {

	private List<Post> posts = null;
	private static LayoutInflater inflater = null;
	ViewHolder holder = null;
	ImageLoader imageLoader;
	
	
	public PostAdapter(Context context, List<Post> posts) {
		inflater = LayoutInflater.from(context);
		this.posts = posts;
		imageLoader = new ImageLoader(context);
    }



	public int getCount() {
		// TODO Auto-generated method stub
		return posts.size();
	}

	public Post getItem(int position) {
		// TODO Auto-generated method stub
		return posts.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
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

		holder.titleView.setText(posts.get(position).getTitle());
		holder.DescriptionView.setText( Html.fromHtml(posts.get(position).getDescription()) );
		if(holder.DescriptionView.getText().length() > 90){
			holder.DescriptionView.setText(holder.DescriptionView.getText().subSequence(0, 90)+"...");
		}
		holder.publisheddate.setText(posts.get(position).getPublishedDate());
        holder.creator.setText(posts.get(position).getAuthor());
		imageLoader.DisplayImage((posts.get(position).getThumbnail()), holder.thumbnail);

        return convertView;
	}
	
	
}
