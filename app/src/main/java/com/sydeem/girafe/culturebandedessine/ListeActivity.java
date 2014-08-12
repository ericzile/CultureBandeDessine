package com.sydeem.girafe.culturebandedessine;

import android.app.ActivityManager;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sydeem.girafe.culturebandedessine.feed.Post;
import com.sydeem.girafe.culturebandedessine.feed.lib.Feed;
import com.sydeem.girafe.culturebandedessine.feed.lib.FeedAdapter;
import com.sydeem.girafe.culturebandedessine.feed.lib.FeedService;

import java.util.ArrayList;
import java.util.List;


public class ListeActivity extends ListActivity {

    Spinner spinner1, spinner2;
    private Feed QUIZ_FEED = null;
    private List<Post> postItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);

        spinner1 = (Spinner)findViewById(R.id.tag_spinner);
        spinner2 = (Spinner)findViewById(R.id.filtre_spinner);

        spinner1.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.tags_array)));
        spinner1.setSelection(0);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                load(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        FeedService.getFeedFromUrl("http://www.smsamour.net/feed/");
        postItems = new ArrayList<Post>();
        QUIZ_FEED = FeedService.feedResult;
        ListView list=(ListView)findViewById(R.id.list);
        FeedAdapter adapter = new FeedAdapter(this, QUIZ_FEED.getEntries());
        list.setAdapter(adapter);

    }

    private boolean isMyServiceRunning(String servicePackage) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (servicePackage.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.liste, menu);
            return true;
        }


        public void load(int position) {
            switch (position) {
                case 0:
                    spinner2.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.difficulte_array)));
                    break;
                case 1:
                    spinner2.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.public_array)));
                    break;
                case 2:
                    spinner2.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.origine)));
                    break;

                case 3:
                    spinner2.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.duree_array)));
                    break;

                default:
                    break;
            }


        }

        public static class ViewHolder {
            public TextView titleView;
            public TextView publisheddate;
            public TextView creator;
            public TextView DescriptionView;
            public ImageView thumbnail;
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            switch (id) {
                case R.id.action_settings:
                    return true;
                case R.id.action_next: {
                    Intent i = new Intent(getApplicationContext(), PubliciteActivity.class);
                    startActivity(i);
                }
            }
            return super.onOptionsItemSelected(item);
        }
}
