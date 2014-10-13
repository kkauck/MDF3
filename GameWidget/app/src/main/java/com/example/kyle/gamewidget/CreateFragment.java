package com.example.kyle.gamewidget;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CreateFragment extends Fragment {

    public static final String TAG = "Create Fragment";
    private gameDetails mGameDetail;
    private TextView mTitle;
    private TextView mPlatform;
    private TextView mGenre;
    private TextView mDetails;

    public static CreateFragment newInstance(){

        return new CreateFragment();

    }

    public interface gameDetails{

        public void details(String _title, String _platform, String _genre);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.create_frag, container, false);

    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);

        if (activity instanceof gameDetails){

            mGameDetail = (gameDetails) activity;

        } else {

            throw new IllegalArgumentException("Must Implement This!");

        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        final View view = getView();
        assert view != null;

        mTitle = (TextView) view.findViewById(R.id.createGameTitle);
        mPlatform = (TextView) view.findViewById(R.id.createGamePlatform);
        mGenre = (TextView) view.findViewById(R.id.createGameGenre);

        Button createButton = (Button) view.findViewById(R.id.createGame);
        createButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                InputMethodManager hideKeyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                hideKeyboard.hideSoftInputFromWindow(mTitle.getWindowToken(), 0);
                hideKeyboard.hideSoftInputFromWindow(mPlatform.getWindowToken(), 0);
                hideKeyboard.hideSoftInputFromWindow(mGenre.getWindowToken(), 0);

                Toast.makeText(getActivity(), "You have successfully added a game!", Toast.LENGTH_SHORT).show();

                String name = mTitle.getText().toString();
                String platform = mPlatform.getText().toString();
                String genre = mGenre.getText().toString();

                mGameDetail.details(name, platform, genre);

                mTitle.setText("");
                mPlatform.setText("");
                mGenre.setText("");

            }

        });

    }

}
