package com.sydeem.girafe.culturebandedessine;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.sydeem.girafe.culturebandedessine.app.AppController;

import java.util.ArrayList;


public class QuestionActivity extends ActionBarActivity {

    private TextView genreTextView = null;
    private TextView titleTextView = null;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        genreTextView = (TextView)findViewById(R.id.genre);
        titleTextView = (TextView)findViewById(R.id.titre_quiz);
        NetworkImageView thumbNail = (NetworkImageView)findViewById(R.id.barner);
        int position;

        if (getIntent().getExtras() != null){
            if(getIntent().getExtras().containsKey("title"))
                titleTextView.setText(getIntent().getExtras().getString("title"));

           /* if(getIntent().getExtras().containsKey("releaseYear"))
                genreTextView.setText(String.valueOf(getIntent().getExtras().getString("releaseYear")));*/
            ArrayList<String> test = getIntent().getExtras().getStringArrayList("genre");
            // genre
            String genreStr = "";
            for (String str : test) {
                genreStr += str + ", ";
            }
            genreStr = genreStr.length() > 0 ? genreStr.substring(0,
                    genreStr.length() - 2) : genreStr;

                genreTextView.setText(genreStr);
            if (imageLoader == null)
                imageLoader = AppController.getInstance().getImageLoader();
                if(getIntent().getExtras().containsKey("image"))
                    thumbNail.setImageUrl(getIntent().getExtras().getString("image"),imageLoader);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void next_quizClick (View view){
        Intent i = new Intent(getApplicationContext(), PubliciteAutreActivity.class);
        startActivity(i);
    }
}
