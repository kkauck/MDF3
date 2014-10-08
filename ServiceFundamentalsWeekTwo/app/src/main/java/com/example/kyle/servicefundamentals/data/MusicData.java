package com.example.kyle.servicefundamentals.data;

import android.net.Uri;

public class MusicData {

    private Uri mSongTitle;
    private int mSongArt;

    public MusicData(){

        mSongTitle = null;
        mSongArt = 0;

    }

    public MusicData (Uri _title, int _art){

        mSongTitle = _title;
        mSongArt = _art;

    }

    public Uri getSongTitle(){

        return mSongTitle;

    }

    public int getSongArt(){

        return mSongArt;

    }

}
