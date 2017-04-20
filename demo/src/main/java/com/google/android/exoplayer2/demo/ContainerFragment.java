package com.google.android.exoplayer2.demo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public abstract class ContainerFragment<T> extends Fragment
{
    private RetainFragment<T> retainFragment;

    // Used as a unique tag (as long as not using multiple ContainerFragments with same type)
    private String tag = getClass().getCanonicalName();

    // Keeps track if this Fragment is being destroy by System or User
    protected boolean destroyedBySystem;

    public ContainerFragment() {}

    // Convenience method to get data.
    public T getData(){ return retainFragment.data; }

    // Convenience method to set data.
    public void setData(T data){ retainFragment.data = data; }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Find or Create a RetainFragment to hold the component
        retainFragment = RetainFragment.findOrCreate(getFragManager(), tag);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reset this variable
        destroyedBySystem = false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        destroyedBySystem = true;
    }

    @Override
    public void onDestroy() {
        if(destroyedBySystem) onDestroyBySystem(); else onDestroyByUser();
        super.onDestroy();
    }

    // Activity destroyed By User. Perform cleanup of retain fragment.
    public void onDestroyByUser(){
        retainFragment.remove(getFragManager());
        retainFragment.data = null;
        retainFragment = null;
    }

    // Activity destroyed by System. Subclasses can override this if needed.
    public void onDestroyBySystem(){}

    public FragmentManager getFragManager(){
        return getActivity().getFragmentManager();
    }
}