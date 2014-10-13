//Kyle Kauck

package com.example.kyle.gamewidget;

import java.io.Serializable;

public class DataHelper implements Serializable {

    private String mGameTitle;
    private String mGamePlatform;
    private String mGameGenre;

    public DataHelper(){

        mGameTitle = "";
        mGamePlatform = "";
        mGameGenre = "";

    }

    public DataHelper (String _name, String _platform, String _genre){

        mGameTitle = _name;
        mGamePlatform = _platform;
        mGameGenre = _genre;

    }

    public String getTitle(){

        return mGameTitle;

    }

    public String getPlatform(){

        return mGamePlatform;

    }

    public String getGenre(){

        return mGameGenre;

    }

}

