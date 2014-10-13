//Kyle Kauck

package com.example.kyle.widget.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.example.kyle.widget.MainActivity;

public class CollectionService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new CollectionViewFactory(getApplicationContext(), MainActivity.mGameDetails);

    }

}
