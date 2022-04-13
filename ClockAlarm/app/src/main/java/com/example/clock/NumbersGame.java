package com.example.clock;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;




    public class NumbersGame extends AppCompatActivity {

        TextView tv_level,tv_number; // level, number to remember
        EditText et_number; // user input
        Button b_confirm;
        MediaPlayer music;

        Random r;

        int currentLevel=1;
        String generatedNumber; // convert the number to string


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_numbers);

            tv_level=findViewById(R.id.tv_level);
            tv_number=findViewById(R.id.tv_number);
            et_number=findViewById(R.id.et_number);
            b_confirm=findViewById(R.id.b_confirm);

            r=new Random();

            et_number.setVisibility(View.GONE);
            b_confirm.setVisibility(View.GONE);
            tv_number.setVisibility(View.VISIBLE);


            tv_level.setText("Level: " + currentLevel);

            generatedNumber= generateNumber(currentLevel); // Number of digits by level
            tv_number.setText(generatedNumber);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    et_number.setVisibility(View.VISIBLE);
                    b_confirm.setVisibility(View.VISIBLE);
                    tv_number.setVisibility(View.GONE); //The number is gone

                    et_number.requestFocus();
                }
            },2000);


            b_confirm.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    if (generatedNumber.equals(et_number.getText().toString())) { // If the user number is equal to the number
                        et_number.setVisibility(View.GONE);
                        b_confirm.setVisibility(View.GONE);
                        tv_level.setVisibility(View.VISIBLE);

                        currentLevel++;


                        if (currentLevel < 6) {
                            et_number.setText("");

                            tv_level.setText("Level " + currentLevel);
                            tv_number.setVisibility(View.VISIBLE);

                            generatedNumber = generateNumber(currentLevel);
                            tv_number.setText(generatedNumber);


                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    et_number.setVisibility(View.VISIBLE);
                                    b_confirm.setVisibility(View.VISIBLE);
                                    tv_number.setVisibility(View.GONE);

                                    et_number.requestFocus();
                                }
                            }, 2000);

                        }
                        else {
                            tv_level.setText("You WIN!");
                            switchActivities();
                            onStop(); // stop the music
                        }
                    }

                    else{
                        tv_level.setText("Game Over! The number was-" + generatedNumber);
                        currentLevel=1;

                        tv_number.setVisibility(View.VISIBLE);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tv_level.setText("Level "+currentLevel);
                                et_number.setVisibility(View.VISIBLE);
                                b_confirm.setVisibility(View.VISIBLE);
                                tv_number.setVisibility(View.GONE);

                                et_number.requestFocus();
                            }
                        },5000);

                        generatedNumber=generateNumber(currentLevel);
                        tv_number.setText(generatedNumber);
                    }
                }
            });
        }



        // switch to the second game!
        private void switchActivities() {
            Intent switchActivityIntent = new Intent(this, MemoryGame.class);
            startActivity(switchActivityIntent);
            finish(); //free stack
        }





       protected void onStart(){
           super.onStart();
           music = MediaPlayer.create(this, R.raw.wakeup);
           music.setLooping(true);
           music.start();
      }


        protected void onStop(){
            super.onStop();

            if (currentLevel < 6) { //Designed if the user went back to the previous screen

                music.stop();

                new Handler().postDelayed(new Runnable() { // Sycamore for two minutes
                    @Override
                    public void run() {
                        final Intent intent =new Intent(NumbersGame.this, NumbersGame.class);

                        NumbersGame.this.startActivity(intent);
                        NumbersGame.this.finish();

                    }
                }, 5000);
            }

            else if(music.isPlaying()){
                music.stop();
            }
        }



        private String generateNumber(int digits){ // Function responsible for the number displayed to the user according to the level at which it is located
            String output="";

            for (int j = 0; j < digits; j++) {
                int randomDigit = r.nextInt(10);
                output = output + "" + randomDigit;
            }
            return output;
        }
    }




