package tech.infofun.popularmovies.activity;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import tech.infofun.popularmovies.R;
import tech.infofun.popularmovies.fragment.DetailFragment;

/**
 * Created by tfbarbosa on 08/04/17.
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        if(savedInstanceState == null){
            DetailFragment f = new DetailFragment();
            f.setArguments(getIntent().getExtras());
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.Fragment_movie_detail,f,"DetailFragment");
            ft.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
    }
}
