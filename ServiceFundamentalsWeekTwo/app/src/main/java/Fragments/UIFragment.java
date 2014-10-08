package Fragments;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.kyle.servicefundamentals.MusicService;
import com.example.kyle.servicefundamentals.R;

public class UIFragment extends Fragment implements View.OnClickListener, ServiceConnection {

    public final static String TAG = "UI Fragment";
    MusicService mMusic;
    boolean mServiceBound;
    public static TextView mTitle;
    public static boolean mRestart = true;
    public static boolean mSwitchStatus;
    public static SeekBar mDuration;
    public static Switch mShuffle;
    boolean mSwitchValue;

    public static UIFragment newInstance(){

        return new UIFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.media_ui, container, false);

        Intent intent = new Intent(getActivity(), MusicService.class);

        view.findViewById(R.id.stop).setOnClickListener(this);
        view.findViewById(R.id.previous).setOnClickListener(this);
        view.findViewById(R.id.pause).setOnClickListener(this);
        view.findViewById(R.id.forward).setOnClickListener(this);
        view.findViewById(R.id.play).setOnClickListener(this);
        mTitle = (TextView) view.findViewById(R.id.title);
        mDuration = (SeekBar) view.findViewById(R.id.songSeek);
        mShuffle = (Switch) view.findViewById(R.id.shuffle);

        if (!mServiceBound){

            getActivity().startService(intent);
            getActivity().bindService(intent, this, Context.BIND_AUTO_CREATE);

        }

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        mShuffle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){

                    if (mServiceBound) {

                        mSwitchValue = true;
                        mMusic.mShuffleSongs = mSwitchValue;

                    } else {

                        mSwitchValue = true;
                        mSwitchStatus = mSwitchValue;

                    }

                } else {

                    if (mServiceBound) {

                        mSwitchValue = false;
                        mMusic.mShuffleSongs = mSwitchValue;

                    } else {

                        mSwitchValue = false;
                        mSwitchStatus = mSwitchValue;

                    }

                }

            }
        });

    }


    @Override
    public void onClick(View v) {

        Intent intent = new Intent(getActivity(), MusicService.class);

        if (v.getId() == R.id.play){

            if (!mServiceBound){

                getActivity().startService(intent);
                getActivity().bindService(intent, this, Context.BIND_AUTO_CREATE);

            } else {

                if (mSwitchValue == true) {

                    mMusic.shuffle(mSwitchValue);

                } else {

                    mMusic.onPlay();

                }

            }


        } else if (v.getId() == R.id.stop){

            //mMusic.stopMusic();

            if (mServiceBound){

                getActivity().unbindService(this);
                mServiceBound = false;
                mMusic = null;

            }

            getActivity().stopService(intent);

        } else if (v.getId() == R.id.pause){

            if (mMusic != null){

                mMusic.onPause();

            }

        } else if (v.getId() == R.id.forward){

            if (mMusic != null){

                mMusic.skipSong();

            }

        } else if (v.getId() == R.id.previous){

            if (mMusic != null){

                mMusic.backwards();

            }

        }


    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

        MusicService.BoundService binder = (MusicService.BoundService)service;
        mMusic = binder.getService();
        mServiceBound = true;

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

        mMusic = null;
        mServiceBound = false;

    }

}
