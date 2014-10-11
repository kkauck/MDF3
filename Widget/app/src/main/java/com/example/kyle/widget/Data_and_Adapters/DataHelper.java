//Kyle Kauck

package com.example.kyle.widget.Data_and_Adapters;

import java.io.Serializable;

public class DataHelper implements Serializable {

    private String mGameTitle;
    private String mGamePlatform;
    private String mGameGenre;
    private String mGameDetails;

    public DataHelper(){

        mGameTitle = "";
        mGamePlatform = "";
        mGameGenre = "";
        mGameDetails = "";

    }

    public DataHelper (String _name, String _platform, String _genre, String _details){

        mGameTitle = _name;
        mGamePlatform = _platform;
        mGameGenre = _genre;
        mGameDetails = _details;

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

    public String getDetails(){

        return mGameDetails;

    }

}
