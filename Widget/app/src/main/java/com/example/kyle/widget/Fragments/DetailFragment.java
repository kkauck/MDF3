//Kyle Kauck

package com.example.kyle.widget.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kyle.widget.Data_and_Adapters.DataHelper;
import com.example.kyle.widget.DetailView;
import com.example.kyle.widget.R;

public class DetailFragment extends Fragment {

    public static final String TAG = "Detail Fragment";
    DataHelper mGameDetails;

    public static DetailFragment newInstance(){

        return new DetailFragment();

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

        mGameDetails = ((DetailView) getActivity()).setValue();

        TextView title = (TextView) view.findViewById(R.id.detailTitle);
        TextView platform = (TextView) view.findViewById(R.id.detailPlatform);
        TextView genre = (TextView) view.findViewById(R.id.detailGenre);
        TextView details = (TextView) view.findViewById(R.id.detailDetails);

        title.setText(mGameDetails.getTitle());
        platform.setText("Platform: " + mGameDetails.getPlatform());
        genre.setText("Genre: " + mGameDetails.getGenre());
        details.setText("Details: " + mGameDetails.getDetails());

    }
}
