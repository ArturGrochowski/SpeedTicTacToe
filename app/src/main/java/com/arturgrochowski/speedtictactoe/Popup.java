package com.arturgrochowski.speedtictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Popup extends Activity {


    private EditText editTextInOneLineToWin;
    private EditText editTextRows;
    private EditText editTextColumns;
    private String inOneLineToWin;
    private String rows;
    private String columns;
    private ImageButton imageButtonOK;
    private LinearLayout backgroundColor;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupwindow);
        setWindowSize();
        setEditTextView();
        setupOkButton();
        setBackgroundMode();
    }


    private void setWindowSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        getWindow().setLayout((int)(screenWidth*0.8),(int)(screenHeight*0.5));
    }


    private void setEditTextView() {
        editTextInOneLineToWin = findViewById(R.id.textInARaw);
        editTextRows = findViewById(R.id.textRawX);
        editTextColumns = findViewById(R.id.textRawY);
    }


    private void setupOkButton() {
        imageButtonOK = findViewById(R.id.imageButtonOK);
        imageButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignInputTextToStrings();
                closeThePopupWindow();
            }
        });
    }


    private void assignInputTextToStrings() {
        inOneLineToWin = editTextInOneLineToWin.getText().toString();
        rows = editTextRows.getText().toString();
        columns = editTextColumns.getText().toString();
    }


    private void closeThePopupWindow() {
        if(areAllTextAreasFilled()) {
            MainActivity.IN_A_LINE_TO_WIN = Integer.parseInt(inOneLineToWin);
            MainActivity.GRID_ROWS = Integer.parseInt(rows);
            MainActivity.GRID_COLUMNS = Integer.parseInt(columns);
            finish();
        }
    }


    private boolean areAllTextAreasFilled() {
        boolean areFilled = true;
        if(inOneLineToWin.isEmpty() || rows.isEmpty() || columns.isEmpty()){
            informPlayerToFillAllTextAreas();
            areFilled = false;
        }
        return  areFilled;
    }


    private void informPlayerToFillAllTextAreas() {
        Toast.makeText(getApplicationContext(), "All numbers required!", Toast.LENGTH_LONG).show();
    }


    private void setBackgroundMode() {
        backgroundColor = findViewById(R.id.popupWindow);
        if(MainActivity.DARK_MODE){
            setDarkMode();
        } else {
            setLightMode();
        }
    }


    private void setDarkMode() {
        backgroundColor.setBackgroundResource(R.color.colorDark);
        imageButtonOK.setBackgroundResource(R.drawable.button_ok_dark);
    }


    private void setLightMode() {
        backgroundColor.setBackgroundResource(R.color.colorWhite);
        imageButtonOK.setBackgroundResource(R.drawable.button_ok);
//        imageButtonOK.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }

}
