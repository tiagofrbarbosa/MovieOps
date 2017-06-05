package tech.infofun.popularmovies.activity;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import tech.infofun.popularmovies.R;
import tech.infofun.popularmovies.fragment.ActivityFragment;
import tech.infofun.popularmovies.fragment.DetailFragment;

public class MainActivity extends DebugActivity {

    private static boolean dual;
    private static DetailFragment f;
    private static ActivityFragment a;
    private static final String fTag = "ActivityFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ScrollView s = (ScrollView) findViewById(R.id.scroll2);

        dual = s != null;

        if(dual){
            f = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.frag2);
        }

        if(savedInstanceState == null){
            ActivityFragment fa = new ActivityFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.Fragment_activity_main,fa,fTag);
            ft.commit();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        a = (ActivityFragment) getSupportFragmentManager().findFragmentByTag(fTag);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.pop_movies_show){
            a.refreshMenuMovies("popular");
        }

        if(id == R.id.top_movies_show){
            a.refreshMenuMovies("top");
        }

        if(id == R.id.action_settings) {
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }

        if(id == R.id.about){
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        if(id == R.id.fav_movies_show){
            Intent intent = new Intent(this, FavoriteActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public static boolean getDual(){
        return dual;
    }

    public static DetailFragment getDetailFragment(){
        return f;
    }

    public static ActivityFragment getActivityFragment(){
        return a;
    }
}
