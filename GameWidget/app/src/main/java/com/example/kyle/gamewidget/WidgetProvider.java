//Kyle Kauck

package com.example.kyle.gamewidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {

    public static final String ACTION_VIEW_DETAILS = "com.example.kyle.ACTION_VIEW_DETAILS";
    public static final String ACTION_VIEW_CREATE = "com.example.kyle.ACTION_VIEW_CREATE";
    public static final String EXTRA_ITEM = "com.example.kyle.WidgetProvider.EXTRA_ITEM";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i < appWidgetIds.length; i++){

            int widgetID = appWidgetIds[i];

            appWidgetManager.notifyAppWidgetViewDataChanged(widgetID, R.id.games_list);

            Intent intent = new Intent(context, WidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);

            //This code sets the pending intent for a list item, as well as sets information if the list is currently empty
            RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            widgetView.setRemoteAdapter(R.id.games_list, intent);
            widgetView.setEmptyView(R.id.games_list, R.id.empty);

            Intent detailIntent = new Intent(ACTION_VIEW_DETAILS);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            widgetView.setPendingIntentTemplate(R.id.games_list, pendingIntent);

            //Sets the pending intent for the Create Button
            Intent createIntent = new Intent(ACTION_VIEW_CREATE);
            PendingIntent createPIntent = PendingIntent.getBroadcast(context, 0, createIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            widgetView.setOnClickPendingIntent(R.id.widgetCreate, createPIntent);

            appWidgetManager.updateAppWidget(widgetID, widgetView);

        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ACTION_VIEW_DETAILS)){

            DataHelper game = (DataHelper) intent.getSerializableExtra(EXTRA_ITEM);

            //If game is not null the information will be passed in and when the user clicks a game the detail activity is started with the correct information
            if (game != null){

                Intent details = new Intent(context, DetailActivity.class);
                details.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                details.putExtra(DetailActivity.EXTRA_ITEM, game);
                context.startActivity(details);

            }

            //When the create button is pressed an intent is created and started to launch the create activity
        } else if (intent.getAction().equals(ACTION_VIEW_CREATE)){

            Intent create = new Intent(context, CreateActivity.class);
            create.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(create);

        }

        super.onReceive(context, intent);

    }
}
