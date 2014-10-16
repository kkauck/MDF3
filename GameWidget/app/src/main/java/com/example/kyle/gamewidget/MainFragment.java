//Kyle Kauck

package com.example.kyle.gamewidget;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class MainFragment extends ListFragment {

    public static final String TAG = "Main Fragment";
    public final int DETAILCODE = 102;
    private gameDetails mDetails;
    private ArrayList<DataHelper> mGameDetailsArray = new ArrayList<DataHelper>();
    private DataHelper mGameDetails = new DataHelper();

    public static MainFragment newInstance(){

        return new MainFragment();

    }

    public interface gameDetails{

        public void returnNewArray(ArrayList<DataHelper> gameArray);

    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);

        if (activity instanceof gameDetails){

            mDetails = (gameDetails) activity;

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.main_fragment, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        final View view = getView();
        assert view != null;

        ((MainActivity) getActivity()).loadData();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Intent detailView = new Intent(getActivity(), DetailActivity.class);

        mGameDetails = ((MainActivity) getActivity()).getArray().get(position);

        detailView.putExtra(DetailActivity.EXTRA_ITEM, mGameDetails);

        startActivity(detailView);

    }

    public void updateList(){

        setListAdapter(null);
        setListAdapter(new CustomListAdapter(getActivity(), MainActivity.mGameDetails));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DETAILCODE){

            updateList();

            MainFragment frag = (MainFragment) getFragmentManager().findFragmentById(R.id.activity_main);

            mGameDetailsArray = (ArrayList<DataHelper>) data.getSerializableExtra("game_array");
            mDetails.returnNewArray(mGameDetailsArray);
            frag.setListAdapter(new CustomListAdapter(getActivity(), mGameDetailsArray));

        }

    }

}
