package com.google.android.exoplayer2.demo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.DebugTextViewHelper;

public class ExoPlayerService extends Service {

    public static final int NOTIFICATION_ID = 142;

    private final Binder binder = new ExoPlayerServiceBinder();

    private Handler mainHandler;
    private SimpleExoPlayer player;
    private EventLogger eventLogger;
    private DefaultTrackSelector trackSelector;
    private TrackSelectionHelper trackSelectionHelper;
    private DebugTextViewHelper debugViewHelper;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public Binder getBinder() {
        return binder;
    }

    public Handler getMainHandler() {
        return mainHandler;
    }

    public void setMainHandler(Handler mainHandler) {
        this.mainHandler = mainHandler;
    }

    public SimpleExoPlayer getPlayer() {
        return player;
    }

    public void setPlayer(SimpleExoPlayer player) {
        this.player = player;
    }

    public EventLogger getEventLogger() {
        return eventLogger;
    }

    public void setEventLogger(EventLogger eventLogger) {
        this.eventLogger = eventLogger;
    }

    public DefaultTrackSelector getTrackSelector() {
        return trackSelector;
    }

    public void setTrackSelector(DefaultTrackSelector trackSelector) {
        this.trackSelector = trackSelector;
    }

    public TrackSelectionHelper getTrackSelectionHelper() {
        return trackSelectionHelper;
    }

    public void setTrackSelectionHelper(TrackSelectionHelper trackSelectionHelper) {
        this.trackSelectionHelper = trackSelectionHelper;
    }

    public DebugTextViewHelper getDebugViewHelper() {
        return debugViewHelper;
    }

    public void setDebugViewHelper(DebugTextViewHelper debugViewHelper) {
        this.debugViewHelper = debugViewHelper;
    }

    public class ExoPlayerServiceBinder extends Binder {
        public ExoPlayerService getService() {
            return ExoPlayerService.this;
        }
    }
}