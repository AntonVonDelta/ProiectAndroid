package com.example.proiectandroid.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proiectandroid.R;
import com.example.proiectandroid.Services.LocationsService;
import com.github.niqdev.mjpeg.DisplayMode;
import com.github.niqdev.mjpeg.Mjpeg;
import com.github.niqdev.mjpeg.MjpegSurfaceView;
import com.github.niqdev.mjpeg.MjpegView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Optional;

import rx.Subscription;

public class VideoTutorialFragment extends Fragment {
    private View view;

    public VideoTutorialFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate view
        view = inflater.inflate(R.layout.fragment_video, container, false);

        MjpegSurfaceView mjpegView = view.findViewById(R.id.video);
        TextInputEditText textInputEditText = view.findViewById(R.id.video_name);


        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    mjpegView.stopPlayback();
                    mjpegView.setVisibility(View.INVISIBLE);
                } else {
                    LocationsService locationsService=LocationsService.getInstance();
                    Optional<String> videoUrl=locationsService.getVideoUrlFromLocation(editable.toString());

                    if(videoUrl.isPresent()){
                        try{
                            mjpegView.stopPlayback();
                            Mjpeg.newInstance()
                                    .open(videoUrl.get(),5)
                                    .subscribe(inputStream -> {
                                        mjpegView.setSource(inputStream);
                                        mjpegView.setDisplayMode(DisplayMode.BEST_FIT);
                                    });

                            mjpegView.setVisibility(View.VISIBLE);
                        }catch(Exception ex){

                        }
                    }
                }
            }
        });


        return view;
    }

    @Override
    public void onStop() {
        Log.d("Video","stopped");

        MjpegSurfaceView mjpegView = view.findViewById(R.id.video);
        mjpegView.stopPlayback();

        super.onStop();
    }
}