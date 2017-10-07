package com.jabespauya.stopwatchandroid;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import java.util.Timer;
import java.util.TimerTask;

import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;

public class MainActivity extends AppCompatActivity {

    CircleProgressView _mCircleProgressView;
    Chronometer _mChronometer;
    Button _mButtonStart;
    Button _mButtonStop;
    long time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();
    }

    void initLayout(){
        _mChronometer = (Chronometer) findViewById(R.id.mChronometer);
        _mButtonStart = (Button) findViewById(R.id.btnStart);
        _mButtonStop = (Button) findViewById(R.id.btnStop);
        _mCircleProgressView = (CircleProgressView) findViewById(R.id.circleProgressView);

        _mCircleProgressView.setTextMode(TextMode.VALUE);
        final Stopwatch s = new Stopwatch();



        _mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button v = (Button) view;
                if(v.getText().equals("Start")){
                    _mChronometer.setBase(SystemClock.elapsedRealtime() + time);
                    _mChronometer.start();
                    s.startCount();
                    v.setText("Stop");
                }else {
                    time = _mChronometer.getBase() - SystemClock.elapsedRealtime();
                    _mChronometer.stop();
                    s.stopCount();
                    v.setText("Start");
                }
            }

        });
    }


    class Stopwatch{
        private Timer mTimer;
        private int progressValue = -1;


        public void startCount(){
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    progressValue++;
                    _mCircleProgressView.setValue(progressValue);
                    if(progressValue == 59){
                        progressValue = -1;
                    }
                }
            },0,1000);
        }

        public void stopCount(){
            mTimer.cancel();
            mTimer = null;
        }
    }
}
