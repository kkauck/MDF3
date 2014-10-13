package com.example.kyle.gamewidget;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFrag extends Fragment {

    public static final String TAG = "Detail Fragment";
    DataHelper mGamesHelper;

    public static DetailFrag newInstance(){

        return new DetailFrag();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.detail_fragment, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        final View view = getView();
        assert view != null;

        mGamesHelper = ((DetailActivity) getActivity()).setValue();

        TextView title = (TextView) view.findViewById(R.id.detailTitle);
        TextView platform = (TextView) view.findViewById(R.id.detailPlatform);
        TextView genre = (TextView) view.findViewById(R.id.detailGenre);

        title.setText(mGamesHelper.getTitle());
        platform.setText("Platform: " + mGamesHelper.getPlatform());
        genre.setText("Genre: " + mGamesHelper.getGenre());

    }
}
