package com.example.clock.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;

import com.example.clock.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StopWatch extends Fragment {

    Chronometer chronometer;

    ImageButton ibtn_play, ibtn_stop;

    private boolean isResume;

    Handler handler;

    long tMilliSec, tStart, tBuff, tUpdate = 0L;

    int min, sec, milliSec;


    public StopWatch() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stop_watch, container, false);
        initUI(view);
        handler = new Handler();
        ibtn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibtn_play_onClick();
            }
        });

        ibtn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibtn_stop_onClick();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void ibtn_stop_onClick() {
        if (!isResume){
            ibtn_play.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
            tMilliSec = 0L;
            tStart = 0L;
            tBuff = 0L;
            tUpdate = 0L;
            sec = 0;
            min = 0;
            milliSec = 0;
            chronometer.setText("00:00,00");
        }
    }

    private void ibtn_play_onClick() {
        if (!isResume){
            tStart = SystemClock.uptimeMillis();
            handler.postDelayed(runnable, 0);
            chronometer.start();
            isResume = true;
            ibtn_stop.setVisibility(View.GONE);
            ibtn_play.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
        }
        else {
            tBuff += tMilliSec;
            handler.removeCallbacks(runnable);
            chronometer.stop();
            isResume = false;
            ibtn_stop.setVisibility(View.VISIBLE);
            ibtn_play.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
        }
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMilliSec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMilliSec;
            sec = (int) (tUpdate/1000);
            min = sec/60;
            sec = sec%60;
            milliSec = (int) (tUpdate%100);

            String tMin = String.format("%02d", min);
            String tSec = String.format("%02d", sec);
            String tMilliSec = String.format("%02d", milliSec);

            chronometer.setText(tMin+":"+tSec+","+tMilliSec);
            handler.postDelayed(this, 60);
        }
    };

    private void initUI(View view) {
        chronometer = view.findViewById(R.id.chronometer);
        ibtn_play = view.findViewById(R.id.ibtn_play);
        ibtn_stop = view.findViewById(R.id.ibtn_stop);
    }

}
