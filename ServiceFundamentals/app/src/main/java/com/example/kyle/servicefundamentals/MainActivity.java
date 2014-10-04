package com.example.kyle.servicefundamentals;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;

import Fragments.UIFragment;


public class MainActivity extends Activity {

    public static final int NOTIFICATION = 0x010101;
    public static final int NOTIFY_LAUNCH = 0x020101;
    public static String title;
    private NotificationManager mNoteManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){

            UIFragment frag = UIFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.main_container, frag, UIFragment.TAG).commit();

        }

        /*Intent newIntent = new Intent(this, MainActivity.class);

        PendingIntent intent = PendingIntent.getActivity(this, NOTIFY_LAUNCH, newIntent, 0);

        mNoteManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(intent);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        builder.setContentTitle("Custom Music");
        builder.setContentText(title);
        builder.setAutoCancel(true);
        mNoteManager.notify(NOTIFICATION, builder.build());*/

    }

    /*public static void setTitle (String _title){

        title = _title;

    }

    public void createNotification(){

        Intent newIntent = new Intent(this, MainActivity.class);

        PendingIntent intent = PendingIntent.getActivity(this, NOTIFY_LAUNCH, newIntent, 0);

        mNoteManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(intent);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        builder.setContentTitle("Custom Music");
        builder.setContentText(title);
        builder.setAutoCancel(true);
        mNoteManager.notify(NOTIFICATION, builder.build());

    }*/

}
