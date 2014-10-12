//Kyle Kauck

package com.example.kyle.widget.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.kyle.widget.Data_and_Adapters.DataHelper;
import com.example.kyle.widget.R;

import java.util.ArrayList;

public class CollectionViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final int ID_CONSTANT = 0x010101;
    private ArrayList<DataHelper> mGames;
    private Context mContext;

    public CollectionViewFactory(Context _context, ArrayList<DataHelper> _games){

        mContext = _context;
        mGames = _games;

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

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

        DataHelper helper = mGames.get(position);

        RemoteViews itemView = new RemoteViews(mContext.getPackageName(), R.layout.widget_layout);
        itemView.setTextViewText(R.id.widgetTitle, helper.getTitle());
        itemView.setTextViewText(R.id.widgetPlatform, helper.getPlatform());
        itemView.setTextViewText(R.id.widgetGenre, helper.getGenre());

        Intent intent = new Intent();
        intent.putExtra(CollectionProvider.EXTRA_ITEM, intent);
        itemView.setOnClickFillInIntent(R.id.game_item, intent);

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
