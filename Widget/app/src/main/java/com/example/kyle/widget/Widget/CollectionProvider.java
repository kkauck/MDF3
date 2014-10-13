//Kyle Kauck

package com.example.kyle.widget.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.kyle.widget.Data_and_Adapters.DataHelper;
import com.example.kyle.widget.DetailView;
import com.example.kyle.widget.R;

public class CollectionProvider extends AppWidgetProvider {

    public static final String ACTION_VIEW = "com.example.kyle.ACTION_VIEW";
    public static final String EXTRA_ITEM = "com.example.kyle.CollectionProvider.EXTRA_ITEM";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i < appWidgetIds.length; i++){

            int widgetID = appWidgetIds[i];

            Intent intent = new Intent(context, CollectionService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);

            RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            widgetView.setRemoteAdapter(R.id.games_list, intent);
            widgetView.setEmptyView(R.id.games_list, R.id.empty);

            Intent detailIntent = new Intent(ACTION_VIEW);
            PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            widgetView.setPendingIntentTemplate(R.id.games_list, pIntent);

            appWidgetManager.updateAppWidget(widgetID, widgetView);

        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ACTION_VIEW)){

            DataHelper helper = (DataHelper) intent.getSerializableExtra("game_detail");

            if (helper != null){

                Intent details = new Intent(context, DetailView.class);
                details.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                details.putExtra("game_detail", helper);
                context.startActivity(details);

            }

        }

        super.onReceive(context, intent);
    }
}
