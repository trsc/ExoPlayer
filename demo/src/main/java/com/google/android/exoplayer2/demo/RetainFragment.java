package com.google.android.exoplayer2.demo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.google.android.exoplayer2.util.Util;

public class RetainFragment<T> extends Fragment {
    public T data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Keeps this Fragment alive during configuration changes
        setRetainInstance(true);
    }

    // Find/Create in FragmentManager
    public static <T> RetainFragment<T> findOrCreate(FragmentManager fm, String tag) {
        RetainFragment<T> retainFragment = (RetainFragment<T>) fm.findFragmentByTag(tag);

        if(retainFragment == null){
            retainFragment = new RetainFragment<>();
            fm.beginTransaction()
                    .add(retainFragment, tag)
                    .commitAllowingStateLoss();
        }

        return retainFragment;
    }

    // Remove from FragmentManager
    public void remove(FragmentManager fm) {
        if(Util.SDK_INT >= 17 && !fm.isDestroyed()){
            fm.beginTransaction()
                    .remove(this)
                    .commitAllowingStateLoss();
            data = null;
        }
    }
}