package com.in.patient.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.in.patient.R;

import java.io.File;

public class VideoPlayer extends AppCompatActivity {
    String videoUrl;
    VideoView videoView;
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        getSupportActionBar().hide();


        Intent intent = getIntent();
        videoUrl = intent.getStringExtra("videoUrl");
        videoView = findViewById(R.id.videoView);


        downloadOrPlay();
    }

    public void downloadOrPlay() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            String title = URLUtil.guessFileName(videoUrl, null, null);
            File s = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/" + title);
            Log.e("TAG", "playVideo() returned: " + s);

            if (s.exists()) {

                playVideo(String.valueOf(s));

            } else {
                Log.e("premitionnotgranted ", "onClick: " + "granted");
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(videoUrl));
                request.setTitle(title);
                request.setDescription("Downloading file please wail.....");
                String cookie = CookieManager.getInstance().getCookie(videoUrl);
                request.addRequestHeader("cookie", cookie);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);

                Toast.makeText(getApplicationContext(), "Downloading Started", Toast.LENGTH_SHORT).show();
                Log.e("dfghjkl", "playVideo: " + Environment.DIRECTORY_DOWNLOADS + title);

                playVideo(videoUrl);

            }


        } else {
            ActivityCompat.requestPermissions(VideoPlayer.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            Log.e("premitionnotgranted ", "onClick: " + "premitionnotgranted");
        }

    }

    public void playVideo(String urii) {

//         finding videoview by its id
        // Uri object to refer the
        // resource from the videoUrl
        Uri uri = Uri.parse(urii);

        // sets the resource from the
        // videoUrl to the videoView
        videoView.setVideoURI(uri);

        // creating object of
        // media controller class
        MediaController mediaController = new MediaController(this, false);

        // sets the anchor view
        // anchor view for the videoView
        mediaController.setAnchorView(videoView);

        // sets the media player to the videoView
        mediaController.setMediaPlayer(videoView);

        // sets the media controller to the videoView
        videoView.setMediaController(mediaController);

        // starts the video
        videoView.start();
    }

}
