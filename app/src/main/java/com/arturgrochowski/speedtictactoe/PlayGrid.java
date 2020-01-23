package com.arturgrochowski.speedtictactoe;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;


public class PlayGrid extends AppCompatActivity implements View.OnClickListener {

    private ImageButton nextShapeButton;
    private ImageButton imgButtonUndo;
    private ImageButton imgButtonExit;
    private CustomButton tmpButton;
    private CustomButton[][] buttonsArray2D;
    private ImageView order1stPlace;
    private ImageView order2ndPlace;
    private ImageView firstPlaceFor;
    private ImageView secondPlaceFor;
    private ProgressBar progressBar;
    private boolean lastPlayer = false;
    private int rows;
    private int columns;
    private int marginSize;
    private int nextPlayer = 1;
    private int nextShape = 1;
    private int currentShape = 1;
    private int buttonBackgroundColor;
    private int numberOfMoves = 0;
    private int buttonWidth;
    private int buttonHeight;
    private int inLineToWin = MainActivity.IN_A_LINE_TO_WIN;
    private int numberOfPlayers = MainActivity.NUMBER_OF_PLAYERS;
    private final int timeForMove = MainActivity.TIMER_SECONDS*1000;
    private CountDownTimer brakeTimer;
    private CountDownTimer moveTimer;
    private LinearLayout backgroundColor;
    private LinearLayout slotForUndoButtonOrProgressBar;
    private TableRow.LayoutParams tableRowParams;
    private WinningEngine winningEngine;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_grid);
        setRowsAndColumnsOrientation();
        createButtonsInArray2D();
        findLinearLayoutForUndoButtonOrProgressBar();
        setupUndoButtonOrProgressBar();
        setupExitButton();
        setupNextShapeButton();
        setupImageViews1stAnd2ndPlaceOrders();
        setMarginsSize();
        setBackgroundMode();
        tableGridCreator();
        nextPlayer();
        nextShape();
        createWinningEngine();
    }


    private void setRowsAndColumnsOrientation() {
        if(MainActivity.GRID_ROWS >= MainActivity.GRID_COLUMNS){
            rows = MainActivity.GRID_ROWS;
            columns = MainActivity.GRID_COLUMNS;
        }else {
            rows = MainActivity.GRID_COLUMNS;
            columns = MainActivity.GRID_ROWS;
        }
    }


    private void createButtonsInArray2D() {
        buttonsArray2D = new CustomButton[rows][columns];
    }


    private void findLinearLayoutForUndoButtonOrProgressBar() {
        slotForUndoButtonOrProgressBar = findViewById(R.id.slotForUndoButtonOrProgressBarLayout);
    }


    private void setupUndoButtonOrProgressBar() {
        if(MainActivity.TIMER_ON){
            createAndSetupProgressBar();
        }else {
            createAndSetupUndoButton();
        }
    }


    private void createAndSetupProgressBar() {
        progressBar = new ProgressBar(this, null,android.R.attr.progressBarStyleHorizontal);
        progressBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Drawable draw = getResources().getDrawable(R.drawable.progress_bar);
        progressBar.setProgressDrawable(draw);
        progressBar.setMax(timeForMove);
        slotForUndoButtonOrProgressBar.addView(progressBar);
        createAndSetupTimers();
        moveTimer.start();

    }

    private void createAndSetupTimers() {
        moveTimer = new CountDownTimer(timeForMove,10) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress((int)(timeForMove-millisUntilFinished));
            }
            @Override
            public void onFinish() {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                brakeTimer.start();
                moveTimer.cancel();
                progressBar.setProgress(timeForMove);
                setBackgroundColorToRed();
                skipWinners();
                setNextShapeButton(nextPlayer);
                nextPlayer();
                nextShape();


            }
        };

        brakeTimer = new CountDownTimer(500,500) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                startMoveTimer();
            }
        };
    }


    private void setBackgroundColorToRed() {
        if(MainActivity.DARK_MODE){
            backgroundColor.setBackgroundResource(R.color.colorRedDark);

        } else {
            backgroundColor.setBackgroundResource(R.color.colorRedWhite);
        }
    }


    private void startMoveTimer() {
        setBackgroundColor();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        moveTimer.cancel();
        brakeTimer.cancel();
        if(lastPlayer){
            progressBar.setProgress(timeForMove);
        }else {
            moveTimer.start();
        }
    }


    private void setBackgroundColor() {
        if(MainActivity.DARK_MODE){
            backgroundColor.setBackgroundResource(R.color.colorDark);
        } else {
            backgroundColor.setBackgroundResource(R.color.colorWhite);
        }
    }


    private void createAndSetupUndoButton() {
        imgButtonUndo = new ImageButton(this);
        imgButtonUndo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        slotForUndoButtonOrProgressBar.addView(imgButtonUndo);
        imgButtonUndo.setBackgroundResource(R.drawable.button_undo);
        imgButtonUndo.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgButtonUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undo();
            }
        });
    }


    private void setupExitButton() {
        imgButtonExit = findViewById(R.id.imageButtonExit);
        imgButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTimer.cancel();
                brakeTimer.cancel();
                finish();
            }
        });
    }


    private void setupNextShapeButton() {
        nextShapeButton = findViewById(R.id.imageButtonCurrentShape);
        nextShapeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        nextShapeButton.setClickable(false);
    }


    private void setupImageViews1stAnd2ndPlaceOrders() {
        order1stPlace = findViewById(R.id.imageOrder1st);
        order2ndPlace = findViewById(R.id.imageOrder2nd);
        firstPlaceFor = findViewById(R.id.imageFistPlacePlayer);
        secondPlaceFor = findViewById(R.id.imageSecondPlacePlayer);
    }


    private void setMarginsSize() {
        if(rows*columns>120)
            marginSize = 3;
        else if(rows*columns>60)
            marginSize = 5;
        else
            marginSize = 10;
    }


    private void setBackgroundMode() {
        backgroundColor = findViewById(R.id.playGridBackground);
        TableLayout playFiledBackground = findViewById(R.id.playFieldLayout);
        if(MainActivity.DARK_MODE){
            setDarkMode(playFiledBackground);
        } else {
            setLightMode(playFiledBackground);
        }
    }


    private void setDarkMode(TableLayout playFiledBackground) {
        backgroundColor.setBackgroundResource(R.color.colorDark);
        playFiledBackground.setBackgroundResource(R.color.colorLightGray);
        playFiledBackground.setPadding(-marginSize, -marginSize,-marginSize,-marginSize);
        buttonBackgroundColor = R.color.colorDark;
    }


    private void setLightMode(TableLayout playFiledBackground) {
        backgroundColor.setBackgroundResource(R.color.colorWhite);
        playFiledBackground.setBackgroundResource(R.color.colorDark);
        playFiledBackground.setPadding(-marginSize, -marginSize,-marginSize,-marginSize);
        buttonBackgroundColor = R.color.colorWhite;
    }


    private void tableGridCreator(){
        setupTableRowParams();
        createRowsColumnsAndButtons();
    }


    private void setupTableRowParams() {
        tableRowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f );
        tableRowParams.setMargins(marginSize, marginSize, marginSize, marginSize);
    }


    private void createRowsColumnsAndButtons() {
        TableLayout tablePlayField = findViewById(R.id.playFieldLayout);
        for(int row = 0; row < rows; row++){
            TableRow tableRow = createAndSetupTableRow(tablePlayField);
            createColumnsAndButtons(row, tableRow);
        }
    }


    private TableRow createAndSetupTableRow(TableLayout tablePlayField) {
        TableRow tableRow = new TableRow(this);
        tablePlayField.addView(tableRow);
        tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f ));
        return tableRow;
    }

    private void createColumnsAndButtons(int row, TableRow tableRow){
        for(int col = 0; col < columns; col++){
            CustomButton button = createAndSetupButton(tableRowParams, row, col);
            tableRow.addView(button);
            addButtonToArray2D(row, col, button);
        }
    }


    private CustomButton createAndSetupButton(TableRow.LayoutParams tableRowParams, int row, int col) {
        CustomButton button = new CustomButton(this);
        button.setLayoutParams(tableRowParams);
        button.setPadding(0,0,0,0 );
        String nameID = convertRowAndColumnToName(row, col);
        int buttonID = Integer.parseInt("9" + nameID);
        button.setId(buttonID);
        button.setBackgroundResource(buttonBackgroundColor);
        button.setOnClickListener(this);
        button.setName(nameID);
        button.setImInRow(row);
        button.setImInColumn(col);
        return button;
    }


    private String convertRowAndColumnToName(int row, int column) {
        String tmpRow = "00" + row;
        tmpRow = tmpRow.substring(tmpRow.length()-3);
        String tmpColumn = "00" + column;
        tmpColumn = tmpColumn.substring(tmpColumn.length()-3);

        return tmpRow + tmpColumn;
    }


    private void addButtonToArray2D(int row, int col, CustomButton button) {
        buttonsArray2D[row][col] = button;
    }


    private void nextPlayer(){
        if(nextPlayer <numberOfPlayers){
            nextPlayer++;
        }else {
            nextPlayer = 1;
        }
    }


    public void nextShape(){
        if(nextShape == 1 && currentShape == 2 && nextPlayer != 2){
            currentShape = 1;
        } else {

            if(nextShape == currentShape && nextShape !=1){
                nextShape--;
                currentShape--;
            }
            currentShape = nextShape;
            nextShape = nextPlayer;
        }
    }


    private void createWinningEngine() {
        winningEngine = new WinningEngine(buttonsArray2D, numberOfPlayers, currentShape, inLineToWin, rows, columns);
    }


    @Override
    public void onClick(View v) {
        movesCounter(1);
        v.setClickable(false);
        tmpButton = findViewById(v.getId());
        assignTmpButtonHeightAndWidth();
        lockButtonSizes();
        setButtonImage(currentShape);
        tmpButton.setMyShape(currentShape);
        winningEngine.start(currentShape);
        setUndoButtonClickable();
        setAwards();
        skipWinners();
        checkIsTheGameOver();
        setNextShapeButton(nextPlayer);
        nextPlayer();
        nextShape();
        startMoveTimer();
    }

    private void setUndoButtonClickable() {
        if(!MainActivity.TIMER_ON)
        imgButtonUndo.setClickable(!winningEngine.getFlagDoesSomebodyWin());
    }


    private void movesCounter(int add){
        numberOfMoves = numberOfMoves + add;
    }


    private void assignTmpButtonHeightAndWidth() {
        buttonHeight = tmpButton.getHeight();
        buttonWidth = tmpButton.getWidth();
    }


    private void lockButtonSizes() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                CustomButton button = buttonsArray2D[row][col];
                setButtonWidth(button);
                setButtonHeight(button);
            }
        }
    }


    private void setButtonWidth(CustomButton button) {
        button.setMinimumWidth(buttonWidth);
        button.setMaxWidth(buttonWidth);
    }


    private void setButtonHeight(CustomButton button) {
        button.setMinimumHeight(buttonHeight);
        button.setMaxHeight(buttonHeight);
    }


    public void setButtonImage(int shape){
        tmpButton.setImageDrawable(imageSizeForButton(imageChooserSwitch(shape)));
    }


    public BitmapDrawable imageSizeForButton(int drawableRes){
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), drawableRes);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, buttonWidth, buttonHeight, true);
        Resources resources = getResources();
        return new BitmapDrawable(resources, scaledBitmap);
    }


    private int imageChooserSwitch(int caseNumber){
        int resDrawableNumber = 0;
        switch (caseNumber) {
            case 1:
                resDrawableNumber = R.drawable.ring;
                break;
            case 2:
                resDrawableNumber = R.drawable.x;
                break;
            case 3:
                resDrawableNumber = R.drawable.triangle;
                break;

            case 4:
                resDrawableNumber = R.drawable.square;
                break;

            case 5:
                resDrawableNumber = R.drawable.star;
                break;

            case 6:
                resDrawableNumber = R.drawable.trapezoid;
                break;
        }

        return resDrawableNumber;
    }


    private void setAwards() {
        if(winningEngine.getListOfWinners().size() == 1 && winningEngine.getFlagDoesSomebodyWin()){
            order1stPlace.setImageResource(R.drawable.first_place);
            firstPlaceFor.setImageResource(imageChooserSwitch(currentShape));
        } else if (winningEngine.getListOfWinners().size() == 2 && winningEngine.getFlagDoesSomebodyWin()){
            order2ndPlace.setImageResource(R.drawable.secondt_place);
            secondPlaceFor.setImageResource(imageChooserSwitch(currentShape));
        }
    }


    private void skipWinners() {
        if(winningEngine.getListOfWinners().contains(nextPlayer) && winningEngine.getListOfWinners().size() < numberOfPlayers){
            nextPlayer();
            nextShape();
            skipWinners();
        }
    }


    private void checkIsTheGameOver() {
        if(winningEngine.getListOfWinners().size() == numberOfPlayers-1 || numberOfMoves == rows * columns){
            nextShapeButton.setClickable(true);
            lastPlayer = true;
            moveTimer.cancel();
            brakeTimer.cancel();
        }

        if (winningEngine.getListOfWinners().size() >= numberOfPlayers){
            finishTheGame();
            moveTimer.cancel();
            brakeTimer.cancel();
        }
    }


    private void finishTheGame() {
        for(CustomButton[] customButtonsRow : buttonsArray2D){
            for(CustomButton customButtonsColumns : customButtonsRow) {
                customButtonsColumns.setClickable(false);
            }
        }
    }


    private void setNextShapeButton(int player){

        if(lastPlayer){
            nextShapeButton.setImageResource(R.drawable.restart_light);

        } else {
            nextShapeButton.setImageResource(imageChooserSwitch(player));
        }
    }



    private void undo() {
        imgButtonUndo.setClickable(false);
        if(tmpButton !=null) {
            movesCounter(-1);
            previousPlayer();
            previousShape();
            skipWinnersBackwards();
            setNextShapeButton(currentShape);
            setButtonImage(currentShape);
            tmpButton.setClickable(true);
            tmpButton.setImageDrawable(null);
            tmpButton.setMyShape(0);
        }
    }


    public void previousPlayer(){
        if(nextPlayer >1){
            nextPlayer--;
        }else {
            nextPlayer = numberOfPlayers;
        }
    }


    public void previousShape(){
        if(currentShape >1){
            currentShape--;
        }else {
            currentShape = numberOfPlayers;
        }
        if(nextShape >1){
            nextShape--;
        }else {
            nextShape = numberOfPlayers;
        }
    }


    private void skipWinnersBackwards() {
        if(winningEngine.getListOfWinners().contains(currentShape)){
            previousPlayer();
            previousShape();
            skipWinnersBackwards();
        }
    }
}

