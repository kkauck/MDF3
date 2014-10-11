//Kyle Kauck

package com.example.kyle.widget.Fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kyle.widget.Data_and_Adapters.CustomListAdapter;
import com.example.kyle.widget.Data_and_Adapters.DataHelper;
import com.example.kyle.widget.DetailView;
import com.example.kyle.widget.MainActivity;
import com.example.kyle.widget.R;

import java.util.ArrayList;

public class MainFragment extends ListFragment {

    public static final String TAG = "Main Fragment";
    public final int DETAILCODE = 102;
    private gameDetails mDetails;
    private ArrayList<DataHelper> mLoadedArray = new ArrayList<DataHelper>();
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

        Toast.makeText(getActivity(), "This is working now my friend", Toast.LENGTH_SHORT).show();

        ((MainActivity) getActivity()).loadData();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Intent detailView = new Intent(getActivity(), DetailView.class);

        int arrayPosition = position;
        mLoadedArray = ((MainActivity) getActivity()).getArray();
        mGameDetails = ((MainActivity) getActivity()).getArray().get(position);

        detailView.putExtra("game_details", mGameDetails);
        detailView.putExtra("game_array", mLoadedArray);
        detailView.putExtra("position", arrayPosition);

        startActivityForResult(detailView, DETAILCODE);

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

            MainFragment frag = (MainFragment) getFragmentManager().findFragmentById(R.id.main_activity);

            mGameDetailsArray = (ArrayList<DataHelper>) data.getSerializableExtra("game_array");
            mDetails.returnNewArray(mGameDetailsArray);
            frag.setListAdapter(new CustomListAdapter(getActivity(), mGameDetailsArray));

        }

    }
}
