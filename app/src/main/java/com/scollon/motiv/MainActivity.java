package com.scollon.motiv;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private boolean mTimerRunning;
    private Long mTimeLeftInMillis;
    private Long StopTimeLeftInMillis;
    Button start, stop;
    ImageView iv_time;
    Spinner spinner;
    TextView tv, tv_h, tv_m, tv_s;
    TimePicker timePicker;
    CountDownTimer timer;
String[] muzyka = {"sea", "meditation"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stop = findViewById(R.id.button2);
        start =findViewById(R.id.button);
        timePicker = findViewById(R.id.timePicker);
        tv_m = findViewById(R.id.tv_m);
       // final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        final MediaPlayer ring= MediaPlayer.create(MainActivity.this,R.raw.zen);
        ring.isLooping();

        timePicker.setIs24HourView(true);
        stop.setVisibility(View.GONE);
        spinner  = findViewById(R.id.spinner);
        iv_time = findViewById(R.id.iv_time);
        timePicker.setHour(0);
        timePicker.setMinute(30);

        spinner.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.white, muzyka);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        start.setText("play");

        start.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                ring.start();
              Integer hrs = timePicker.getHour();
                Integer min = timePicker.getMinute();

             // tv_h.setText(hrs.toString());
                tv_m.setText(min.toString());
                long millisHrs = Long.parseLong(String.valueOf(hrs)) * 3600000;
                long millisMin = Long.parseLong(String.valueOf(min)) * 60000;
                long millisInput = millisHrs + millisMin;
                iv_time.animate().rotation(360).setDuration(millisInput);
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
                ring.stop();

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