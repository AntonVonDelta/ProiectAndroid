package com.example.proiectandroid.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proiectandroid.R;
import com.example.proiectandroid.Services.LocationsService;
import com.github.niqdev.mjpeg.DisplayMode;
import com.github.niqdev.mjpeg.Mjpeg;
import com.github.niqdev.mjpeg.MjpegView;

public class VideoTutorialFragment extends Fragment {
    private View view;

    public VideoTutorialFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate view
        view = inflater.inflate(R.layout.fragment_video, container, false);

        int TIMEOUT = 30; //seconds
        MjpegView mjpegView=view.findViewById(R.id.video);

        Mjpeg.newInstance()
                .open("http://188.27.249.61:8090/?action=stream", TIMEOUT)
                .subscribe(inputStream -> {
                    mjpegView.setSource(inputStream);
                    mjpegView.setDisplayMode(DisplayMode.BEST_FIT);
                    mjpegView.showFps(true);
                });

        return view;
    }
}