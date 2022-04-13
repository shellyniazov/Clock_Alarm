package com.example.clock;



import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;



public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {


    TextView tvAlarm;
    MediaPlayer music;
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        tvAlarm = (TextView) findViewById(R.id.tvAlarm);
        mVideoView = (VideoView) findViewById(R.id.bgVideoView);

        // video
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.sea);

        mVideoView.setVideoURI(uri);
        mVideoView.start();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
    }



    protected void onStart(){
        super.onStart();
        music = MediaPlayer.create(this, R.raw.calm);
        music.setLooping(true);
        music.start();
        mVideoView.start();
    }


    @Override
    public void onClick(View v) {
        finish();
    }


    protected void onStop(){
        super.onStop();

        if(music.isPlaying()){
            music.stop();
        }
    }
}