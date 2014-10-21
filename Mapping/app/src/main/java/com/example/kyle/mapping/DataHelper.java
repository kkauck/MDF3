//Kyle Kauck

package com.example.kyle.mapping;

import android.net.Uri;

import java.io.Serializable;

public class DataHelper implements Serializable {

    private String mName;
    private String mTime;
    private Uri mImage;

    public DataHelper(){

        mName = null;
        mTime = null;
        mImage = null;

    }

    public DataHelper (String _name, String _time, Uri _image){

        mName = _name;
        mTime = _time;
        mImage = _image;

    }

    public String getName(){

        return mName;

    }

    public String getTime(){

        return mTime;

    }

    public Uri getImage(){

        return mImage;

    }

}
