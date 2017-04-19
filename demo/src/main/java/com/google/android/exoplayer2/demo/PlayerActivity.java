package com.google.android.exoplayer2.demo;

import android.app.Activity;
import android.app.FragmentManager;
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
        sampleChooserFragment = new SampleChooserFragment().setCallback(pieceChosenCallback);
        getFragmentManager()
                .beginTransaction()
                .add(R.id.content, sampleChooserFragment, SampleChooserFragment.TAG)
                .commit();
    }

    private final SampleChooserFragment.PieceChosenCallback pieceChosenCallback = new SampleChooserFragment.PieceChosenCallback() {
        @Override
        public void pieceChosen(Intent intent) {
            // findViewById(R.id.chooser).setVisibility(View.GONE);
            playerFragment = new PlayerFragment();
            Bundle extras = intent.getExtras();
            extras.putString("ACTION", intent.getAction());
            extras.putString("DATA", intent.getData().toString());
            playerFragment.setArguments(extras);
            dummyFragment = new DummyFragment();
            currentOrientation = getResources().getConfiguration().orientation;

            // Add the fragment to the 'fragment_container' FrameLayout
            FragmentTransaction transaction = getFragmentManager()
                    .beginTransaction()
                    .addToBackStack("player")
                    .replace(R.id.content, playerFragment, PlayerFragment.TAG);
            if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                transaction = transaction.add(R.id.content, dummyFragment);
            }
            transaction.commit();
        }
    };

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();

        if(fm.getBackStackEntryCount() > 0){

            playerFragment.setShouldReleaseOnStop(false);
            PlayerFragment newFragment = new PlayerFragment();
            newFragment.initializePlayerFromInstance(playerFragment);
            this.playerFragment = newFragment;

            fm.popBackStack("player", FragmentManager.POP_BACK_STACK_INCLUSIVE);

            fm.beginTransaction()
                    .add(R.id.mini_player, this.playerFragment)
                    .commit();

            fm.executePendingTransactions();

            findViewById(R.id.mini_player).setVisibility(View.VISIBLE);

        } else{
            finish();
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getFragmentManager().findFragmentByTag(PlayerFragment.TAG) != null
                && newConfig.orientation != currentOrientation) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

                getFragmentManager().beginTransaction().remove(dummyFragment).commit();

            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){

                getFragmentManager().beginTransaction().add(R.id.content, dummyFragment).commit();

            }
            currentOrientation = newConfig.orientation;
        }
    }


}


