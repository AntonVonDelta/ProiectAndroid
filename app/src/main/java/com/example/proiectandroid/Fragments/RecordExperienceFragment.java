package com.example.proiectandroid.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiectandroid.Adapters.DeletableRecyclerViewAdapter;
import com.example.proiectandroid.R;
import com.example.proiectandroid.Services.TravelService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;

public class RecordExperienceFragment extends Fragment {
    private int VIDEO_REQUEST = 101;

    private View view;
    private Uri videoUri;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate view
        view = inflater.inflate(R.layout.fragment_experience, container, false);

        Button recordButton = view.findViewById(R.id.record);
        Button playbackButton = view.findViewById(R.id.play);

        recordButton.setOnClickListener(v -> recordVideo());
        playbackButton.setOnClickListener(v -> playbackVideo());
        return view;
    }

    @Override
    public void onPause() {
        VideoView videoHolder = view.findViewById(R.id.video_holder);
        videoHolder.stopPlayback();

        super.onPause();
    }

    private void recordVideo() {
        Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (videoIntent.resolveActivity(getActivity().getPackageManager()) == null) {
            return;
        }

        // Start recording
        startActivityForResult(videoIntent, VIDEO_REQUEST);
    }

    private void playbackVideo() {
        VideoView videoHolder = view.findViewById(R.id.video_holder);

        File newfile = new File(getActivity().getExternalFilesDir(null), "saved_video.mp4");
        if (!newfile.exists()) return;

        videoHolder.setVideoURI(Uri.fromFile(newfile));
        videoHolder.start();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == VIDEO_REQUEST && resultCode == RESULT_OK) {
            videoUri = data.getData();

            saveVideoData(data);
        }
    }


    private void saveVideoData(Intent data) {
        try {
            // Store locally
            //https://stackoverflow.com/questions/41606939/android-saving-video-to-internal-storage
            File newfile;

            AssetFileDescriptor videoAsset = getActivity().getContentResolver().openAssetFileDescriptor(data.getData(), "r");
            FileInputStream in = videoAsset.createInputStream();

            newfile = new File(getActivity().getExternalFilesDir(null), "saved_video.mp4");
            if (newfile.exists()) newfile.delete();
            else newfile.createNewFile();

            OutputStream out = new FileOutputStream(newfile);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            in.close();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
