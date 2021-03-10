package com.example.clock.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.clock.fragment.Alarm;
import com.example.clock.fragment.InternationalTime;
import com.example.clock.fragment.StopWatch;
import com.example.clock.fragment.Timer;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new InternationalTime();
            case 1:
                return new Alarm();
            case 2:
                return new StopWatch();
            case 3:
                return new Timer();

            default:
                return new Timer();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
