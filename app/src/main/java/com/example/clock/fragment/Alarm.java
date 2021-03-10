package com.example.clock.fragment;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.clock.R;
import com.example.clock.fragment.alarm.AlarmReceiver;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class Alarm extends Fragment {

    TimePicker tp_picker;
    Button btn_set_time, btn_stop;
    TextView tv_set_time;

    Calendar calendar;
    ImageView img_bell;

    AlarmManager alarmManager;
    Intent intent;
    PendingIntent pendingIntent;

    Context context;


    public Alarm() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        initUI(view);

        btn_set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_set_time_onClick();
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_stop_onClick();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void btn_set_time_onClick() {
        int hour    = tp_picker.getCurrentHour();
        int minute  = tp_picker.getCurrentMinute();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        String set_time = "";
        if (hour < 10){
            if (minute < 10){
                set_time = "0"+hour+":0"+minute;
            }
            else {
                set_time = "0"+hour+":"+minute;
            }
        }
        else {
            if (minute < 10){
                set_time = hour+":0"+minute;
            }
            else {
                set_time = hour+":"+minute;
            }
        }
        intent.putExtra("status", "on");
        pendingIntent = PendingIntent.getBroadcast(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        tv_set_time.setText(set_time);
        visibleSetTime();
    }

    private void btn_stop_onClick() {
        alarmManager.cancel(pendingIntent);
        intent.putExtra("status", "off");
        context.sendBroadcast(intent);
        invisibleSetTime();
    }

    private void initUI(View view) {
        tp_picker       = view.findViewById(R.id.tp_picker);
        btn_set_time    = view.findViewById(R.id.btn_set_time);
        btn_stop        = view.findViewById(R.id.btn_stop);
        tv_set_time     = view.findViewById(R.id.tv_set_time);
        img_bell        = view.findViewById(R.id.img_bell);
        calendar = Calendar.getInstance();
        alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        intent = new Intent(context, AlarmReceiver.class);;

        invisibleSetTime();
    }

    private void invisibleSetTime(){
        tv_set_time.setVisibility(View.INVISIBLE);
        img_bell.setVisibility(View.INVISIBLE);
    }
    private void visibleSetTime(){
        tv_set_time.setVisibility(View.VISIBLE);
        img_bell.setVisibility(View.VISIBLE);
    }

}
