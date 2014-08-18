package com.sydeem.girafe.culturebandedessine;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ResultatActivity extends ActionBarActivity {
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_resultat);
        //get rating bar object
        RatingBar bar=(RatingBar)findViewById(R.id.ratingBar1);
        bar.setNumStars(5);
        bar.setStepSize(0.5f);
        //get text view
        TextView t=(TextView)findViewById(R.id.textResult);
        //get score
        Bundle b = getIntent().getExtras();
        int score= b.getInt("score");
        //display score
        bar.setRating(score);
        switch (score)
        {
            case 0: t.setText("Un seul mot à dire, Je suis deçu de votre niveau");
                message="Score: "+score+ "\n Un seul mot à dire, Je suis deçu de votre niveau"+"\n #cultureBD #SydeemGirafe #gdg";
                break;
            case 1: t.setText("Un seul mot à dire, Cultivez vous");
                message="Score: "+score+ "\n Un seul mot à dire, Cultivez vous"+"\n #cultureBD #SydeemGirafe #gdg";
                break;
            case 2: t.setText("Oouuupsss ! La chance sera de vôtre côté au prochain essai!");
                message="Score: "+score+ "\n Oouuupsss ! La chance sera de vôtre côté au prochain essai!"+"\n #cultureBD #SydeemGirafe #gdg";
                break;
            case 3: t.setText("Travail passable, vous pouvez mieux faire");
                message="Score: "+score+ "\n Travail passable, vous pouvez mieux faire "+"\n #cultureBD #SydeemGirafe #gdg";
                break;
            case 4:t.setText("Hmmmmmmm.. Vous avez été tous proche de  faire un sans faute");
                message="Score: "+score+ "\n Hmmmmmmm.. Vous avez été tous proche de  faire un sans faute"+"\n #cultureBD #SydeemGirafe #gdg";
                break;
            case 5:t.setText("Greaaaaattt, Vous êtes trop fort");
                message="Score: "+score+ "\n Greaaaaattt, Vous êtes trop fort"+"\n #cultureBD #SydeemGirafe #gdg";
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.resultat, menu);

       //okzojrgjeg
        // ShareActionProvider
      /*  MenuItem itemProvider = menu.findItem(R.id.action_share);
        ShareActionProvider mShareActionProvider = (ShareActionProvider) itemProvider.getActionProvider();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Message");*/
       // mShareActionProvider.setShareIntent(intent);
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
        if (id == R.id.action_share){
            OnShare();
        }
        return super.onOptionsItemSelected(item);
    }
    private void OnShare(){
        //partage social
        Intent intent=new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        // Add data to the intent, the receiving app will decide what to do with it.
        intent.putExtra(Intent.EXTRA_SUBJECT, "Simulation");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(intent, "Partager"));
    }
}
