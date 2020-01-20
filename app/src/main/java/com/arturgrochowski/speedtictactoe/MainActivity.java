package com.arturgrochowski.speedtictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioButton button3x3;
    private RadioButton button6x6;
    private RadioButton button9x9;
    private RadioButton buttonCustom;
    private ImageButton buttonStart;
    private RadioButton buttonPlayers2;
    private RadioButton buttonPlayers3;
    private RadioButton buttonPlayers4;
    private RadioButton buttonPlayers5;
    private RadioButton buttonPlayers6;
    private ImageButton buttonTimer;
    static int GRID_ROWS = 3;
    static int GRID_COLUMNS = 3;
    static int IN_A_LINE_TO_WIN = 3;
    static int NUMBER_OF_PLAYERS = 2;
    static int TIMER_SECONDS = 7;
    static boolean DARK_MODE;
    static boolean TIMER_ON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupButtons();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        MenuItem itemSwitch = menu.findItem(R.id.mySwitch);
        itemSwitch.setActionView(R.layout.use_switch);
        SwitchCompat sw = menu.findItem(R.id.mySwitch).getActionView().findViewById(R.id.action_switch);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DARK_MODE = isChecked;
                setBackgroundMode();
        }
        });

        return true;
    }


    private void setBackgroundMode() {
        LinearLayout layout = findViewById(R.id.main_activity_layout);

        if(DARK_MODE){
            layout.setBackgroundResource(R.color.colorDark);
            setButtonsToDarkMode();
        } else {
            layout.setBackgroundResource(R.color.colorWhite);
            setButtonsToLightMode();
        }
    }


    private void setButtonsToDarkMode() {
        button3x3.setBackgroundResource(R.drawable.button_3x3_dark);
        button6x6.setBackgroundResource(R.drawable.button_6x6_dark);
        button9x9.setBackgroundResource(R.drawable.button_9x9_dark);
        buttonCustom.setBackgroundResource(R.drawable.button_custom_dark);
        buttonStart.setBackgroundResource(R.drawable.button_go_dark);
        setDarkModeForTimer();
        buttonPlayers2.setBackgroundResource(R.drawable.button_players2_dark);
        buttonPlayers3.setBackgroundResource(R.drawable.button_players3_dark);
        buttonPlayers4.setBackgroundResource(R.drawable.button_players4_dark);
        buttonPlayers5.setBackgroundResource(R.drawable.button_players5_dark);
        buttonPlayers6.setBackgroundResource(R.drawable.button_players6_dark);
    }


    private void setDarkModeForTimer() {
        if(TIMER_ON){
            setImageForTimerButton();
        }else{
            buttonTimer.setBackgroundResource(R.drawable.timer_dark);
        }
    }


    private void setButtonsToLightMode() {
        button3x3.setBackgroundResource(R.drawable.button_3x3);
        button6x6.setBackgroundResource(R.drawable.button_6x6);
        button9x9.setBackgroundResource(R.drawable.button_9x9);
        buttonCustom.setBackgroundResource(R.drawable.button_custom);
        buttonStart.setBackgroundResource(R.drawable.button_go);
        setLightModeForTimer();
        buttonPlayers2.setBackgroundResource(R.drawable.button_players2);
        buttonPlayers3.setBackgroundResource(R.drawable.button_players3);
        buttonPlayers4.setBackgroundResource(R.drawable.button_players4);
        buttonPlayers5.setBackgroundResource(R.drawable.button_players5);
        buttonPlayers6.setBackgroundResource(R.drawable.button_players6);
    }

    private void setLightModeForTimer() {
        if(TIMER_ON){
            setImageForTimerButton();
        }else{
            buttonTimer.setBackgroundResource(R.drawable.timer);
        }
    }


    private void setupButtons() {
        button3x3 = findViewById(R.id.imageButton3x3);
        button6x6 = findViewById(R.id.imageButton6x6);
        button9x9 = findViewById(R.id.imageButton9x9);
        buttonCustom = findViewById(R.id.imageButtonCustom);
        buttonStart = findViewById(R.id.imageButtonStart);
        buttonTimer = findViewById(R.id.imageButtonTimer);
        buttonPlayers2 = findViewById(R.id.imageButtonPlayers2);
        buttonPlayers3 = findViewById(R.id.imageButtonPlayers3);
        buttonPlayers4 = findViewById(R.id.imageButtonPlayers4);
        buttonPlayers5 = findViewById(R.id.imageButtonPlayers5);
        buttonPlayers6 = findViewById(R.id.imageButtonPlayers6);
        buttonStart.setBackgroundResource(R.drawable.button_go);
        buttonTimer.setBackgroundResource(R.drawable.timer);
        buttonStart.setScaleType(ImageView.ScaleType.FIT_CENTER);
        buttonTimer.setScaleType(ImageView.ScaleType.FIT_CENTER);
        button3x3.setOnClickListener(this);
        button6x6.setOnClickListener(this);
        button9x9.setOnClickListener(this);
        buttonCustom.setOnClickListener(this);
        buttonStart.setOnClickListener(this);
        buttonTimer.setOnClickListener(this);
        buttonPlayers2.setOnClickListener(this);
        buttonPlayers3.setOnClickListener(this);
        buttonPlayers4.setOnClickListener(this);
        buttonPlayers5.setOnClickListener(this);
        buttonPlayers6.setOnClickListener(this);
    }


    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imageButton3x3:
                GRID_ROWS = 3;
                GRID_COLUMNS = 3;
                IN_A_LINE_TO_WIN = 3;
                button6x6.setChecked(false);
                button9x9.setChecked(false);
                buttonCustom.setChecked(false);
                break;

            case R.id.imageButton6x6:
                GRID_ROWS = 6;
                GRID_COLUMNS = 6;
                IN_A_LINE_TO_WIN = 4;
                button3x3.setChecked(false);
                button9x9.setChecked(false);
                buttonCustom.setChecked(false);
                break;

            case R.id.imageButton9x9:
                GRID_ROWS = 9;
                GRID_COLUMNS = 9;
                IN_A_LINE_TO_WIN = 5;
                button3x3.setChecked(false);
                button6x6.setChecked(false);
                buttonCustom.setChecked(false);
                break;

            case R.id.imageButtonCustom:

                button3x3.setChecked(false);
                button6x6.setChecked(false);
                button9x9.setChecked(false);
                customSize();

                break;

            case R.id.imageButtonPlayers2:
                NUMBER_OF_PLAYERS = 2;
                break;

            case R.id.imageButtonPlayers3:
                NUMBER_OF_PLAYERS = 3;
                break;

            case R.id.imageButtonPlayers4:
                NUMBER_OF_PLAYERS = 4;
                break;

            case R.id.imageButtonPlayers5:
                NUMBER_OF_PLAYERS = 5;
                break;

            case R.id.imageButtonPlayers6:
                NUMBER_OF_PLAYERS = 6;
                break;

            case R.id.imageButtonTimer:
                changeStateOfTimer();
                break;

            case R.id.imageButtonStart:
                startTheGame();
                break;

        }
    }


    private void customSize(){
        Intent intentPopup = new Intent(this, Popup.class);
        startActivity(intentPopup);
    }


    private void changeStateOfTimer() {
        if(buttonTimer.isSelected()){
            buttonTimer.setSelected(false);
            TIMER_ON = false;
            setBackgroundMode();
        }else{
            setBackgroundMode();
            buttonTimer.setSelected(true);
            TIMER_ON = true;
            setTheTimer();
        }
    }


    private void setTheTimer() {
        Intent intentTimer = new Intent(this, TimerClass.class);
        startActivityForResult(intentTimer, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            setImageForTimerButton();
    }


    private void setImageForTimerButton() {
        switch (TIMER_SECONDS){

            case 7:
                changeStateOfTimer();
                break;

            case 1:
                buttonTimer.setBackgroundResource(R.drawable.button_timer_red_1);
                break;

            case 2:
                buttonTimer.setBackgroundResource(R.drawable.button_timer_red_2);
                break;

            case 3:
                buttonTimer.setBackgroundResource(R.drawable.button_timer_red_3);
                break;

            case 4:
                buttonTimer.setBackgroundResource(R.drawable.button_timer_red_4);
                break;

            case 5:
                buttonTimer.setBackgroundResource(R.drawable.button_timer_red_5);
                break;

            case 6:
                buttonTimer.setBackgroundResource(R.drawable.button_timer_red_6);
                break;

        }
    }


    private void startTheGame() {
        Intent intentGame = new Intent(this, PlayGrid.class);
        startActivity(intentGame);
    }

}
