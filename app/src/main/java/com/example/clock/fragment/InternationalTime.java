package com.example.clock.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clock.R;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class InternationalTime extends Fragment {

    TextView tvTime;

    Context context;

    public InternationalTime() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_international_time, container, false);
        tvTime = view.findViewById(R.id.tv_time);
        tvTime.setText(getInstance());

        final Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    while (!isInterrupted()){
                        Thread.sleep(1000);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvTime.setText(getInstance());
                            }
                        });
                    }
                }
                catch (Exception e){

                }

            }
        };
        thread.start();
        // Inflate the layout for this fragment
        return view;
    }


    private String getInstance(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm");
        String dateTime = dateformat.format(calendar.getTime());
        return dateTime;
    }

}
