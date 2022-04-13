package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.VideoView;

import java.util.Calendar;




public class MainActivity extends AppCompatActivity implements View.OnClickListener , TimePicker.OnTimeChangedListener {


    Button btnSet,btnCancel;
    TextView tv;
    LinearLayout ll;
    TimePicker timePicker;
    PendingIntent pendingIntent=null;
    AlarmManager alarmManager;

    public static final int RECEIVER_REQ=280192;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        ll=(LinearLayout)findViewById(R.id.ll);
        btnSet=(Button)findViewById(R.id.btnSet);
        btnCancel=(Button)findViewById(R.id.btnCancel);
        tv=(TextView)findViewById(R.id.tv);

        timePicker=new TimePicker(this);
        ll.addView(timePicker);

        btnSet.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        timePicker.setOnTimeChangedListener(this);
    }




    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnSet:
                setAlarm();
                break;

            case  R.id.btnCancel:
                cancelAlarm();
                break;
        }
    }



    private void setAlarm(){

        Intent intent=new Intent(this,MyReceiver.class);
        pendingIntent=PendingIntent.getBroadcast(this,RECEIVER_REQ,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,timePicker.getHour());
        calendar.set(Calendar.MINUTE,timePicker.getMinute());

        int seconds=5;

        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(seconds*1000),pendingIntent);

        tv.setText("Alarm will set in "+seconds+" seconds");
    }




    private void cancelAlarm(){

        if(pendingIntent!=null){
            alarmManager.cancel(pendingIntent);
        }

        tv.setText("alarm disabled");
    }





    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

        String format=" ";

        if(hourOfDay==0){
            hourOfDay+=12;
            format="AM";
        }

        else if(hourOfDay==12){
            format="PM";
        }
    }
}





