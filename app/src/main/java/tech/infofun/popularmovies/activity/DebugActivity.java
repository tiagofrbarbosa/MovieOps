package tech.infofun.popularmovies.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by tfbarbosa on 13/05/17.
 */
public class DebugActivity extends AppCompatActivity {

    protected static final String TAG = "DebugActivity";


    @Override
    protected void onCreate(Bundle icicle){
        super.onCreate(icicle);

        Log.i(TAG, getClassName() + ".onCreate() chamado." );
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, getClassName() + ".onStart() chamado");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i(TAG, getClassName() + ".onRestart() chamado");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG, getClassName() + ".onResume() chamado");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, getClassName() + ".onPause() chamado");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i(TAG, getClassName() + ".onSavedInstanceState() chamado");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(TAG,getClassName() + ".onStop() chamado");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, getClassName() + ".onDestroy() chamado");
    }

    private String getClassName(){
        String s = getClass().getName();
        return s.substring(s.lastIndexOf("."));
    }
}
