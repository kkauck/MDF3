package com.example.kyle.gamewidget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {

    public static final long CONSTANT_ID = 0x01010101L;
    private Context mContext;
    private ArrayList<DataHelper> mGameDetails;

    public CustomListAdapter (Context _context, ArrayList<DataHelper> _gameArray){

        mContext = _context;
        mGameDetails = _gameArray;

    }

    @Override
    public int getCount() {

        return mGameDetails.size();

    }

    @Override
    public Object getItem(int position) {

        return mGameDetails.get(position);

    }

    @Override
    public long getItemId(int position) {

        return CONSTANT_ID + position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){

            convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_list, parent, false);

        }

        DataHelper helper = (DataHelper) getItem(position);

        TextView gameName = (TextView) convertView.findViewById(R.id.gameTitle);
        TextView gamePlatform = (TextView) convertView.findViewById(R.id.gamePlatform);

        gameName.setText(helper.getTitle());
        gamePlatform.setText(helper.getPlatform());

        return convertView;

    }
}
