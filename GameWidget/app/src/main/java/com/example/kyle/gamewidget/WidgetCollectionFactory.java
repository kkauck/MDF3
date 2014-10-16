//Kyle Kauck

package com.example.kyle.gamewidget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

public class WidgetCollectionFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final int ID_CONSTANT = 0x0101010;

    private ArrayList<DataHelper> mGames;
    private Context mContext;

    public WidgetCollectionFactory(Context context){

        mContext = context;
        mGames = new ArrayList<DataHelper>();

    }

    @Override
    public void onCreate() {


    }

    @Override
    public void onDataSetChanged() {

        mGames = MainActivity.mGameDetails;

    }

    @Override
    public void onDestroy() {


    }

    @Override
    public int getCount() {

        return mGames.size();

    }

    @Override
    public RemoteViews getViewAt(int position) {

        DataHelper game = mGames.get(position);

        //This code will work with the actual listview of the widget for displaying the game information
        RemoteViews itemView = new RemoteViews(mContext.getPackageName(), R.layout.game_item);
        itemView.setTextViewText(R.id.widgetTitle, game.getTitle());
        itemView.setTextViewText(R.id.widgetPlatform, game.getPlatform());
        itemView.setTextViewText(R.id.widgetGenre, game.getGenre());

        //This will setup the intent for when a game is actually clicked in the list
        Intent intent = new Intent();
        intent.putExtra(WidgetProvider.EXTRA_ITEM, game);
        itemView.setOnClickFillInIntent(R.id.game_item, intent);

        //This sets up the intent for the click button
        Intent createIntent = new Intent();
        itemView.setOnClickFillInIntent(R.id.widgetCreate, createIntent);

        return itemView;

    }

    @Override
    public RemoteViews getLoadingView() {

        return null;

    }

    @Override
    public int getViewTypeCount() {

        return 1;

    }

    @Override
    public long getItemId(int position) {

        return ID_CONSTANT + position;

    }

    @Override
    public boolean hasStableIds() {

        return true;

    }
}
