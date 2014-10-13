package com.example.kyle.gamewidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {

    public static final String ACTION_VIEW_DETAILS = "com.example.kyle.ACTION_VIEW_DETAILS";
    public static final String EXTRA_ITEM = "com.example.kyle.WidgetProvider.EXTRA_ITEM";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i < appWidgetIds.length; i++){

            int widgetID = appWidgetIds[i];

            Intent intent = new Intent(context, WidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);

            RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            widgetView.setRemoteAdapter(R.id.games_list, intent);
            widgetView.setEmptyView(R.id.games_list, R.id.empty);

            Intent detailIntent = new Intent(ACTION_VIEW_DETAILS);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            widgetView.setPendingIntentTemplate(R.id.games_list, pendingIntent);

            appWidgetManager.updateAppWidget(widgetID, widgetView);

        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ACTION_VIEW_DETAILS)){

            DataHelper game = (DataHelper) intent.getSerializableExtra(EXTRA_ITEM);

            if (game != null){

                Intent details = new Intent(context, DetailActivity.class);
                details.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                details.putExtra(DetailActivity.EXTRA_ITEM, game);
                context.startActivity(details);

            }

        }

        super.onReceive(context, intent);

    }
}
