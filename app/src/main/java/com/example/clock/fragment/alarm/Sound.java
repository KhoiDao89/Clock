package com.example.clock.fragment.alarm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.clock.R;

public class Sound extends Service {

    MediaPlayer mediaPlayer;
    int id;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.tricky);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String status = intent.getExtras().getString("status");
        if (status.equals("on")){
            id = 1;
        }
        else if (status.equals("off")){
            id = 0;
        }
        if (id == 1){
            mediaPlayer.start();
            id = 0;
        }
        else if (id == 0){
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        return START_NOT_STICKY;
    }
}
