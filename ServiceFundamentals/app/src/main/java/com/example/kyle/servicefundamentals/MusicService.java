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
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import Fragments.UIFragment;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener {

    public static final int NOTIFY_LAUNCH = 0x020101;
    private Notification mNoteManager;
    public String mTitle;
    private static final String SAVED_POSITION = "MusicService.SAVED_POSITION";
    private static int NOTIFICATION_ID = 0x010101;
    MediaPlayer mMediaPlayer;
    boolean mResumed;
    boolean mReady;
    boolean mTesting = true;
    public boolean mStartAgain;
    int mPosition;
    BoundService mBinder;
    Uri mSongOne, mSongTwo, mSongThree, mSongFour, mSongFive;
    ArrayList <Uri> mSongList = new ArrayList<Uri>();
    public int mSong = 0;

    @Override
    public void onCreate() {

        super.onCreate();

        mReady = mResumed = false;
        mBinder = new BoundService();

        mSongOne = Uri.parse("android.resource://" + getPackageName() + "/raw/i_feel_fantastic");
        mSongTwo = Uri.parse("android.resource://" + getPackageName() + "/raw/chiron_beta_prime");
        mSongThree = Uri.parse("android.resource://" + getPackageName() + "/raw/creepy_doll");
        mSongFour = Uri.parse("android.resource://" + getPackageName() + "/raw/mr_fancy_pants");
        mSongFive = Uri.parse("android.resource://" + getPackageName() + "/raw/tom_cruise_crazy");

        mSongList.add(mSongOne);
        mSongList.add(mSongTwo);
        mSongList.add(mSongThree);
        mSongList.add(mSongFour);
        mSongList.add(mSongFive);

        Toast.makeText(this, "Music Service Created", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mTesting = UIFragment.mRestart;

        if (mMediaPlayer != null){

            Uri thisSong = mSongList.get(this.mSong);

            MediaMetadataRetriever info = new MediaMetadataRetriever();
            info.setDataSource(this, thisSong);
            String title = info.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

            UIFragment.mTitle.setText(title);
            mTitle = title;

        } else if (mMediaPlayer == null && mTesting == false) {

            onPlay();

        }

        if (mStartAgain){

            onPlay();

        }

        Toast.makeText(this, "Music Service Running", Toast.LENGTH_SHORT).show();

        return Service.START_NOT_STICKY;

    }

    @Override
    public void onDestroy() {

        if (mMediaPlayer != null) {

            UIFragment.mRestart = false;
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();

        }

        stopSelf();
        stopForeground(true);

    }

    public void onPlay(){

        if (mMediaPlayer == null){

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

        } else if (mMediaPlayer != null){

            mMediaPlayer.seekTo(mPosition);
            mMediaPlayer.start();

            Toast.makeText(this, "Music Service Still Running", Toast.LENGTH_SHORT).show();

        }

    }

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

    private void nextSong(){

        Uri readSong = mSongList.get(0);
        mSongList.add(readSong);

        mSongList.remove(0);
        mPosition = 0;
        onPlay();

    }

    public void onPause(){

        mResumed = false;

        if (mMediaPlayer != null && mMediaPlayer.isPlaying()){

            mMediaPlayer.pause();

            mPosition = mMediaPlayer.getCurrentPosition();

            Toast.makeText(this, "You Are Paused", Toast.LENGTH_SHORT).show();

        }

    }

    public class BoundService extends Binder {

        public MusicService getService(){

            return MusicService.this;

        }

    }

    @Override
    public IBinder onBind(Intent intent) {

        Toast.makeText(this, "You Are Bound", Toast.LENGTH_SHORT).show();
        return mBinder;

    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        mReady = true;

        Uri songTitle = mSongList.get(mSong);

        MediaMetadataRetriever info = new MediaMetadataRetriever();
        info.setDataSource(this, songTitle);
        String title = info.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

        UIFragment.mTitle.setText(title);
        mTitle = title;

        if (mp == mMediaPlayer){

            mMediaPlayer.seekTo(mPosition);
            mMediaPlayer.start();

        }

        Intent newIntent = new Intent(this, MainActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent intent = PendingIntent.getActivity(this, NOTIFY_LAUNCH, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(intent);
        builder.setTicker(title);
        builder.setOngoing(true);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        builder.setContentTitle("Currently Playing:");
        builder.setContentText(mTitle);
        builder.setAutoCancel(true);

        mNoteManager = builder.build();

        startForeground(NOTIFICATION_ID, mNoteManager);

    }

    public void skipSong() {

        //This increments the song int which is tied to my ArrayList and will increment to the correct index in the array
        mSong++;

        if (mSong <= mSongList.size() - 1){

            //Calls for my method that will correctly release the original media player and then create a new one for the current song that is playing.
            playSkippedSong();

        } else {

            mSong = mSong - 1;

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

}
