//Kyle Kauck

package com.example.kyle.servicefundamentals;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import Fragments.UIFragment;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener {

    public String mTitle;
    MediaPlayer mMediaPlayer;
    BoundService mBinder;
    Uri mSongOne, mSongTwo, mSongThree, mSongFour, mSongFive;
    ArrayList <Uri> mSongList = new ArrayList<Uri>();

    //Boolean Variables
    boolean mResumed;
    boolean mReady;
    boolean mTesting = true;
    boolean mShuffleSongs = false;
    public boolean mStartAgain;

    //Handler for seek bar updates
    private Handler mHandler = new Handler();

    //Number Variables
    private double songStart = 0;
    private double songStop = 0;
    private Random mShuffleInt;
    public int mSong = 0;
    int mPosition;
    public static final int NOTIFY_LAUNCH = 0x020101;


    @Override
    public void onCreate() {

        super.onCreate();

        mReady = mResumed = false;
        mBinder = new BoundService();
        mShuffleInt = new Random();

        //Sets all my song URIs
        mSongOne = Uri.parse("android.resource://" + getPackageName() + "/raw/i_feel_fantastic");
        mSongTwo = Uri.parse("android.resource://" + getPackageName() + "/raw/chiron_beta_prime");
        mSongThree = Uri.parse("android.resource://" + getPackageName() + "/raw/creepy_doll");
        mSongFour = Uri.parse("android.resource://" + getPackageName() + "/raw/mr_fancy_pants");
        mSongFive = Uri.parse("android.resource://" + getPackageName() + "/raw/tom_cruise_crazy");

        //Adds each song into an array for playing
        mSongList.add(mSongOne);
        mSongList.add(mSongTwo);
        mSongList.add(mSongThree);
        mSongList.add(mSongFour);
        mSongList.add(mSongFive);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mTesting = UIFragment.mRestart;

        if (mMediaPlayer != null){

            //This will set me song title for the UI
            Uri thisSong = mSongList.get(this.mSong);

            MediaMetadataRetriever info = new MediaMetadataRetriever();
            info.setDataSource(this, thisSong);
            String title = info.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

            songStop = mMediaPlayer.getDuration();

            UIFragment.mDuration.setMax((int) songStop);

            mHandler.postDelayed(songUpdate, 100);

            UIFragment.mTitle.setText(title);
            mTitle = title;

        } else if (mMediaPlayer == null && !mTesting) {

            onPlay();

        }

        if (mStartAgain){

            onPlay();

        }

        return Service.START_NOT_STICKY;

    }

    @Override
    public void onDestroy() {

        if (mMediaPlayer != null) {

            //This will save a boolean that is used for determining if music was paused or needs to start playing again, as well as stops the media player and then stops the service from running
            UIFragment.mRestart = false;
            UIFragment.mSwitchStatus = mShuffleSongs;
            mHandler.removeCallbacks(songUpdate);
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();

        }

        stopSelf();
        stopForeground(true);

    }

    public void onPlay(){

        if (mShuffleSongs) {

            int nextSong = mSong;

            while (nextSong == mSong) {

                nextSong = mShuffleInt.nextInt(mSongList.size());

            }

            mSong = nextSong;

        }

            if (mMediaPlayer == null){

            //Creates a new media player
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            try {

                //Prepares the media player by getting the correct song from the array playlist
                Uri thisSong = mSongList.get(this.mSong);
                mMediaPlayer.setDataSource(this, thisSong);
                mMediaPlayer.setOnPreparedListener(this);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {

                        //Calls two methods to first release the media player and then start with a new song
                        release();
                        nextSong();

                    }
                });

            } catch (IOException e){

                e.printStackTrace();

                mMediaPlayer.release();
                mMediaPlayer = null;

            }

        } else if (mMediaPlayer != null){

            //If music was paused then the media player will seek to the saved position
            mMediaPlayer.seekTo(mPosition);
            mMediaPlayer.start();

        }

    }

    //This method is called when the user skips to a new song, the original media player is released and a new one is created for the new song
    public void playSkippedSong(){

        mMediaPlayer.release();

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {

            Uri thisSong = mSongList.get(this.mSong);
            mMediaPlayer.setDataSource(this, thisSong);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {

                    release();
                    nextSong();

                }
            });

        } catch (IOException e){

            e.printStackTrace();

            mMediaPlayer.release();
            mMediaPlayer = null;

        }

    }

    //This is called when a song is done and the next one needs to begin playing
    private void release(){

        if (mMediaPlayer == null){

            return;

        }

        if (mMediaPlayer.isPlaying()){

            mMediaPlayer.stop();

        }

        mMediaPlayer.release();
        mMediaPlayer = null;

    }

    //This will be called after the release so that the user is able to continue listening to music
    private void nextSong(){

        //Before being removed from the list the current song is saved again to the list at the back so that it can be looped back through when all five songs are done playing.
        Uri readSong = mSongList.get(0);
        mSongList.add(readSong);

        mSongList.remove(0);
        mPosition = 0;
        onPlay();

    }

    public void onPause(){

        mResumed = false;

        //This will allow the user to pause the current song, saving the songs position to easily resume playing
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()){

            mMediaPlayer.pause();

            mPosition = mMediaPlayer.getCurrentPosition();

        }

    }

    //Used to bind my services
    public class BoundService extends Binder {

        public MusicService getService(){

            return MusicService.this;

        }

    }

    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;

    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        mReady = true;

        Uri songTitle = mSongList.get(mSong);

        MediaMetadataRetriever info = new MediaMetadataRetriever();
        info.setDataSource(this, songTitle);
        String title = info.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

        songStart = mp.getCurrentPosition();
        songStop = mp.getDuration();

        mHandler.postDelayed(songUpdate, 100);

        String musicTime = (String.format("%d Minutes, %d Seconds",
                TimeUnit.MILLISECONDS.toMinutes((long) songStop),
                TimeUnit.MILLISECONDS.toSeconds((long) songStop) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) songStop)))
        );

        Toast.makeText(this, "Current Song Time Is: " + musicTime, Toast.LENGTH_SHORT).show();

        UIFragment.mDuration.setMax((int) songStop);

        UIFragment.mTitle.setText(title);
        mTitle = title;

        if (mp == mMediaPlayer){

            mMediaPlayer.seekTo(mPosition);
            mMediaPlayer.start();

        }

        notificationGen();

    }

    private Runnable songUpdate = new Runnable() {

        @Override
        public void run() {

            songStart = mMediaPlayer.getCurrentPosition();
            UIFragment.mDuration.setProgress((int) songStart);
            mHandler.postDelayed(this, 100);

        }

    };

    //Called to create a notification when the the user plays music and anytime a new song begins to play
    public void notificationGen(){

        int NOTIFICATION_ID = 0x010101;
        Notification mNoteManager;

        Intent newIntent = new Intent(this, MainActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent intent = PendingIntent.getActivity(this, NOTIFY_LAUNCH, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(intent);
        builder.setTicker(mTitle);
        builder.setOngoing(true);
        builder.setSmallIcon(R.drawable.ic_stat_av_play_over_video);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_stat_av_play_over_video));
        builder.setContentTitle("Currently Playing:");
        builder.setContentText(mTitle);
        builder.setAutoCancel(true);

        mNoteManager = builder.build();

        startForeground(NOTIFICATION_ID, mNoteManager);

    }

    public void skipSong() {

        //This increments the song int which is tied to my ArrayList and will increment to the correct index in the array
        if (mShuffleSongs){

            int nextSong = mSong;

            while (nextSong ==  mSong){

                nextSong = mShuffleInt.nextInt(mSongList.size());

            }

            mSong = nextSong;

        } else {

            mSong++;

            if (mSong <= mSongList.size() - 1) {

                //Calls for my method that will correctly release the original media player and then create a new one for the current song that is playing.
                playSkippedSong();

            } else {

                mSong = mSong - 1;

            }

        }

    }

    public void backwards(){

        mSong--;

        if (mSong >= 0){

            playSkippedSong();

        } else if (mSong < 0) {

            mSong = 0;

        }

    }

    public void shuffle(boolean _shuffle){

        //if (mShuffleSongs) mShuffleSongs = false;
        //else mShuffleSongs = true;

        mShuffleSongs = _shuffle;

        if (mShuffleSongs){

            onPlay();

        }

    }

}
