package com.example.clock;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class MemoryGame extends AppCompatActivity {

    MediaPlayer music;
    ImageView curView = null;
    private int countPair = 0;

    final int[] drawable = new int[]{R.drawable.a,R.drawable.b,R.drawable.c, // array of images
            R.drawable.d,R.drawable.e,R.drawable.ff};

    int[] pos = {0,1,2,3,4,5,0,1,2,3,4,5}; // Array of image position
    int currentPos = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        ImageAdapter imageAdapter = new ImageAdapter(this); // start the ImageAdapter class
        GridView gridView = (GridView)findViewById(R.id.gridView);

        gridView.setAdapter(imageAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (currentPos < 0 ) //Show first image
                {
                    currentPos = position;
                    curView = (ImageView) view; //View an image
                    ((ImageView) view).setImageResource(drawable[pos[position]]); //Sets a drawable as the content of this ImageView.
                }
                else {
                    if (currentPos == position) //When you click on the same image twice the image is hidden
                    {
                        ((ImageView) view).setImageResource(R.drawable.hidden);
                    }
                    else if (pos[currentPos] != pos[position]) //If the two images are not identical, hide the last image
                    {
                        curView.setImageResource(R.drawable.hidden);
                        Toast.makeText(MemoryGame.this, "Not Match!", Toast.LENGTH_LONG).show();
                    }
                    else { //If the pictures are the same
                        ((ImageView) view).setImageResource(drawable[pos[position]]);
                        countPair++;
                        if (countPair == 6)
                        {
                            Toast.makeText(MemoryGame.this, "You Win!", Toast.LENGTH_LONG).show();
                            switchActivities();
                            onStop();
                        }
                    }
                    currentPos = -1; //Reset image position next time
                }
            }
        });
    }



    // switch to the end screen!
    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, AlarmActivity.class);
        startActivity(switchActivityIntent);
        finish();
    }

    protected void onStart(){
        super.onStart();
        music = MediaPlayer.create(this, R.raw.wakeup);
        music.setLooping(true);
        music.start();
    }


    protected void onStop(){

        super.onStop();

        if (countPair != 6) { // //Designed if the user went back to the previous screen

            music.stop();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    final Intent intent = new Intent(MemoryGame.this, MemoryGame.class);
                    MemoryGame.this.startActivity(intent);
                    MemoryGame.this.finish();
                    music.stop();
                }
            }, 5000);
        }

        else if(music.isPlaying()){
            music.stop();
        }
    }
}
