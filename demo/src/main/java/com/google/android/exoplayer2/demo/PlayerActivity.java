package com.google.android.exoplayer2.demo;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

public class PlayerActivity extends Activity {

    private PlayerFragment playerFragment;
    private SampleChooserFragment sampleChooserFragment;
    private DummyFragment dummyFragment;
    private int currentOrientation;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity);
        sampleChooserFragment = new SampleChooserFragment();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.chooser, sampleChooserFragment)
                .commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        findViewById(R.id.chooser).setVisibility(View.GONE);
        playerFragment = new PlayerFragment();
        Bundle extras = intent.getExtras();
        extras.putString("ACTION", intent.getAction());
        extras.putString("DATA", intent.getData().toString());
        playerFragment.setArguments(extras);
        dummyFragment = new DummyFragment();
        currentOrientation = getResources().getConfiguration().orientation;

        // Add the fragment to the 'fragment_container' FrameLayout
        FragmentTransaction transaction = getFragmentManager().beginTransaction()
                .add(R.id.player, playerFragment);
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            transaction.add(R.id.player, dummyFragment);
        }
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        getFragmentManager().beginTransaction()
                .remove(playerFragment)
                .remove(dummyFragment)
                .commit();
        playerFragment = null;
        dummyFragment = null;
        findViewById(R.id.chooser).setVisibility(View.VISIBLE);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation != currentOrientation) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

                getFragmentManager().beginTransaction().remove(dummyFragment).commit();

            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){

                getFragmentManager().beginTransaction().add(R.id.player, dummyFragment).commit();

            }
            currentOrientation = newConfig.orientation;
        }
    }

}


