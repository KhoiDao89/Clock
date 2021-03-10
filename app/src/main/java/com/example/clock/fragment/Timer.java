package com.example.clock.fragment;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.clock.MainActivity;
import com.example.clock.R;

import ticker.views.com.ticker.widgets.circular.timer.callbacks.CircularViewCallback;
import ticker.views.com.ticker.widgets.circular.timer.view.CircularView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Timer extends Fragment {

    CircularView circularViewWithTimer;

    Button btn_destroy, btn_start;

    CircularView.OptionsBuilder builderWithTimer;

    NumberPicker np_minute, np_second;

    TextView tv_minute, tv_second;

    int minute, second;

    Context context;


    public Timer() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        initUI(view);

        np_minute.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minute = newVal;
            }
        });

        np_second.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                second = newVal;
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_start_onClick();
            }
        });
        btn_destroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_destroy_onClick();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private int minutesToSeconds(int minutes, int seconds){
        return minutes*60+seconds;
    }

    private void setTimePicker(){
        np_minute.setMaxValue(59);
        np_minute.setMinValue(0);
        np_minute.setValue(0);

        np_second.setMaxValue(59);
        np_second.setMinValue(0);
        np_second.setValue(5);

        minute = np_minute.getValue();
        second = np_second.getValue();
    }

    private void invisibleNumberPicker(){
        np_minute.setVisibility(View.INVISIBLE);
        np_second.setVisibility(View.INVISIBLE);
        tv_minute.setVisibility(View.INVISIBLE);
        tv_second.setVisibility(View.INVISIBLE);
        circularViewWithTimer.setVisibility(View.VISIBLE);
    }

    private void visibleNumberPicker(){
        np_minute.setVisibility(View.VISIBLE);
        np_second.setVisibility(View.VISIBLE);
        tv_minute.setVisibility(View.VISIBLE);
        tv_second.setVisibility(View.VISIBLE);
        circularViewWithTimer.setVisibility(View.INVISIBLE);
    }

    private void buildTimer(int time_seconds){
        builderWithTimer = new CircularView.OptionsBuilder()
                .shouldDisplayText(true)
                .setCounterInSeconds(time_seconds)
                .setCircularViewCallback(new CircularViewCallback() {
                    @Override
                    public void onTimerFinish() {
                        btn_start.setBackgroundResource(R.drawable.greendark_circle_button);
                        btn_start.setText(getResources().getString(R.string.start));
                        btn_start.setTextColor(getResources().getColor(R.color.colorGreenLight));
                        btn_destroy.setTextColor(getResources().getColor(R.color.colorBlack));
                        builderWithTimer = null;
                        notification();
                        visibleNumberPicker();
                    }
                    @Override
                    public void onTimerCancelled() {
                        btn_start.setBackgroundResource(R.drawable.greendark_circle_button);
                        btn_start.setText(getResources().getString(R.string.start));
                        btn_start.setTextColor(getResources().getColor(R.color.colorGreenLight));
                    }
                });

        circularViewWithTimer.setOptions(builderWithTimer);
    }

    private void notification(){
        String message = "Time up";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_access_alarms)
                .setContentTitle(getResources().getString(R.string.app_name)).setContentText(message)
                .setAutoCancel(true).setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

        Intent intent = new Intent(context.getApplicationContext(), Timer.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    private void btn_start_onClick(){
        if (btn_start.getText().toString().equals(getResources().getString(R.string.start))){
            if (minutesToSeconds(minute, second) == 0){
                // do nothing
            }
            else {
                invisibleNumberPicker();
                buildTimer(minutesToSeconds(minute, second));
                circularViewWithTimer.startTimer();
                btn_start.setBackgroundResource(R.drawable.yellow_circle_button);
                btn_start.setText(getResources().getString(R.string.stop));
                btn_start.setTextColor(getResources().getColor(R.color.colorYellowLight));
                btn_destroy.setTextColor(getResources().getColor(R.color.colorWhite));
            }
        }
        else if (btn_start.getText().toString().equals(getResources().getString(R.string.stop))){
            circularViewWithTimer.pauseTimer();
            btn_start.setBackgroundResource(R.drawable.greendark_circle_button);
            btn_start.setText(getResources().getString(R.string.resume));
            btn_start.setTextColor(getResources().getColor(R.color.colorGreenLight));
        }
        else if (btn_start.getText().toString().equals(getResources().getString(R.string.resume))){
            circularViewWithTimer.resumeTimer();
            btn_start.setBackgroundResource(R.drawable.yellow_circle_button);
            btn_start.setText(getResources().getString(R.string.stop));
            btn_start.setTextColor(getResources().getColor(R.color.colorYellowLight));
        }
    }

    private void btn_destroy_onClick(){
        if (btn_start.getText().toString().equals(getResources().getString(R.string.start))){
            // do nothing
        }
        else {
            circularViewWithTimer.stopTimer();
            visibleNumberPicker();
            btn_destroy.setTextColor(getResources().getColor(R.color.colorBlack));
            circularViewWithTimer.setOptions(builderWithTimer);
        }
    }

    private void initUI(View view) {
        circularViewWithTimer   = view.findViewById(R.id.circular_view);
        btn_start               = view.findViewById(R.id.btn_start);
        btn_destroy             = view.findViewById(R.id.btn_destroy);
        np_minute               = view.findViewById(R.id.np_minute);
        np_second               = view.findViewById(R.id.np_second);
        tv_minute               = view.findViewById(R.id.tv_minute);
        tv_second               = view.findViewById(R.id.tv_second);
        setTimePicker();
    }

}
