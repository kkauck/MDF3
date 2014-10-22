//Kyle Kauck

package com.example.kyle.mapping;

import java.io.Serializable;

public class DataHelper implements Serializable {

    private String mName;
    private String mTime;
    private String mImage;
    private double mLat;
    private double mLong;

    public DataHelper(){

        mName = null;
        mTime = null;
        mImage = null;
        mLat = (double) 0;
        mLong = (double) 0;

    }

    public DataHelper (String _name, String _time, String _image, double _lat, double _long){

        mName = _name;
        mTime = _time;
        mImage = _image;
        mLat = _lat;
        mLong = _long;

    }

    public String getName(){

        return mName;

    }

    public String getTime(){

        return mTime;

    }

    public String getImage(){

        return mImage;

    }

    public double getLat(){

        return mLat;

    }

    public double getLong(){

        return mLong;

    }

}
