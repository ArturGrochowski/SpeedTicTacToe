package com.gmail.speedtictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class TimerClass extends Activity implements View.OnClickListener {

    private RadioButton seconds1Button;
    private RadioButton seconds2Button;
    private RadioButton seconds3Button;
    private RadioButton seconds4Button;
    private RadioButton seconds5Button;
    private RadioButton seconds6Button;
    private LinearLayout backgroundColor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
        setWindowSize();
        setupButtons();
        setBackgroundMode();

    }


    private void setupButtons() {
        seconds1Button = findViewById(R.id.timer1secButton);
        seconds2Button = findViewById(R.id.timer2secButton);
        seconds3Button = findViewById(R.id.timer3secButton);
        seconds4Button = findViewById(R.id.timer4secButton);
        seconds5Button = findViewById(R.id.timer5secButton);
        seconds6Button = findViewById(R.id.timer6secButton);
        seconds1Button.setOnClickListener(this);
        seconds2Button.setOnClickListener(this);
        seconds3Button.setOnClickListener(this);
        seconds4Button.setOnClickListener(this);
        seconds5Button.setOnClickListener(this);
        seconds6Button.setOnClickListener(this);
    }


    private void setWindowSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenSize = (int) (displayMetrics.widthPixels * 0.8);
//        int screenHeight = displayMetrics.heightPixels;

        getWindow().setLayout(screenSize,screenSize);
    }


    private void setBackgroundMode() {
        backgroundColor = findViewById(R.id.timer);
        if(MainActivity.DARK_MODE){
            setDarkMode();
        } else {
            setLightMode();
        }
    }


    private void setDarkMode() {
        backgroundColor.setBackgroundResource(R.color.colorBlack);
        seconds1Button.setBackgroundResource(R.drawable.button_player1_dark);
        seconds2Button.setBackgroundResource(R.drawable.button_players2_dark);
        seconds3Button.setBackgroundResource(R.drawable.button_players3_dark);
        seconds4Button.setBackgroundResource(R.drawable.button_players4_dark);
        seconds5Button.setBackgroundResource(R.drawable.button_players5_dark);
        seconds6Button.setBackgroundResource(R.drawable.button_players6_dark);
    }


    private void setLightMode() {
        backgroundColor.setBackgroundResource(R.color.colorWhite);
        seconds1Button.setBackgroundResource(R.drawable.button_player1);
        seconds2Button.setBackgroundResource(R.drawable.button_players2);
        seconds3Button.setBackgroundResource(R.drawable.button_players3);
        seconds4Button.setBackgroundResource(R.drawable.button_players4);
        seconds5Button.setBackgroundResource(R.drawable.button_players5);
        seconds6Button.setBackgroundResource(R.drawable.button_players6);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.timer1secButton:
                MainActivity.TIMER_SECONDS = 1;
                finish();
                break;

            case R.id.timer2secButton:
                MainActivity.TIMER_SECONDS = 2;
                finish();
                break;

            case R.id.timer3secButton:
                MainActivity.TIMER_SECONDS = 3;
                finish();
                break;

            case R.id.timer4secButton:
                MainActivity.TIMER_SECONDS = 4;
                finish();
                break;

            case R.id.timer5secButton:
                MainActivity.TIMER_SECONDS = 5;
                finish();
                break;

            case R.id.timer6secButton:
                MainActivity.TIMER_SECONDS = 6;
                finish();
                break;
        }
    }
}
