package com.scollon.motiv;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private boolean mTimerRunning;
    private Long mTimeLeftInMillis;
    private Long StopTimeLeftInMillis;
    Button start, stop;
    TextView tv, tv_h, tv_m, tv_s;
    TimePicker timePicker;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stop = findViewById(R.id.button2);
        start =findViewById(R.id.button);
        timePicker = findViewById(R.id.timePicker);
        tv_m = findViewById(R.id.tv_m);
        final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        timePicker.setIs24HourView(true);
        stop.setVisibility(View.GONE);

        timePicker.setHour(0);
        timePicker.setMinute(15);

        start.setText("play");

        start.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

              Integer hrs = timePicker.getHour();
                Integer min = timePicker.getMinute();

             // tv_h.setText(hrs.toString());
                tv_m.setText(min.toString());
                long millisHrs = Long.parseLong(String.valueOf(hrs)) * 3600000;
                long millisMin = Long.parseLong(String.valueOf(min)) * 60000;
                long millisInput = millisHrs + millisMin;

                        startTimer(millisInput);
                        start.setVisibility(View.GONE);
                        stop.setVisibility(View.VISIBLE);

            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StopTimeLeftInMillis = mTimeLeftInMillis;
            timer.cancel();
                r.stop();

                start.setVisibility(View.VISIBLE);
                stop.setVisibility(View.GONE);

            }
           });

         }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
        tv_m.setText(timeLeftFormatted);
    }




    private void startTimer(Long input) {
     timer = new CountDownTimer(input, 1000) {

            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();

                // mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {

                tv_m.setText("done!");
            }
        }.start();

    }



}