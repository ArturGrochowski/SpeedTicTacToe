package com.gmail.speedtictactoe;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

class WinningEngine {
    private int rows;
    private int columns;
    private int inLineToWin;
    private int previousShape;
    private int numberOfPlayers;
    private int tmpRowBtn2;
    private int tmpRowBtnIncreased;
    private int tmpColBtn2;
    private int tmpColBtnIncreased;
    private int tmpColBtnDecreased;
    private int tmpRowBtn;
    private int tmpColBtn;
    private boolean doesSomebodyWinFlag;
    private HashSet<Integer> listOfWinners;
    private CustomButton[][] buttonsArray2D;
    private ArrayList<CustomButton> usedButtons;
    private HashSet<CustomButton> inOneLineHashSet;
    private HashMap<Integer, HashMap<Integer, HashSet<CustomButton>>> hashMapOfPlayersMoves;



    WinningEngine(CustomButton[][] buttonsArray2D, int numberOfPlayers, int previousShape, int inLineToWin, int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.inLineToWin = inLineToWin;
        this.previousShape = previousShape;
        this.numberOfPlayers = numberOfPlayers;
        this.buttonsArray2D = buttonsArray2D;
        inOneLineHashSet  = new HashSet<>();
        listOfWinners = new HashSet<>();
    }


    void start(int previousShape){
        this.previousShape = previousShape;
        doesSomebodyWinFlag = false;
        assignHashMapAndLists();
        checkForWin(previousShape);
    }


    private void assignHashMapAndLists() {
        usedButtons = new ArrayList<>();
        hashMapOfPlayersMoves = new HashMap<>(numberOfPlayers);
        for(int i = 0; i<numberOfPlayers; i++){
            hashMapOfPlayersMoves.put(i, createAndAssignHashMapOfHashSets() );
        }
    }


    private HashMap<Integer, HashSet<CustomButton>> createAndAssignHashMapOfHashSets() {
        HashMap<Integer, HashSet<CustomButton>> hashMap= new HashMap<>();
        createAssignAndPutHashSetsToHashMap(hashMap);
        return hashMap;
    }


    private void createAssignAndPutHashSetsToHashMap(HashMap<Integer, HashSet<CustomButton>> hashMap) {
        HashSet<CustomButton> winInRowInner = new HashSet<>(3);
        HashSet<CustomButton> winInColumnInner = new HashSet<>(3);
        HashSet<CustomButton> winInDecreasingInner = new HashSet<>(3);
        HashSet<CustomButton> winInIncreasingInner = new HashSet<>(3);
        hashMap.put(0, winInRowInner);
        hashMap.put(1, winInColumnInner);
        hashMap.put(2, winInDecreasingInner);
        hashMap.put(3, winInIncreasingInner);
    }


    private void checkForWin(int previousShape) {
        usedButtons.clear();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (buttonsArray2D[row][col].getMyShape() == previousShape){
                    usedButtons.add(buttonsArray2D[row][col]);
                }
            }
        }
        searchForWinningButtons();
    }


    private void searchForWinningButtons() {
        for(int i = 0; i < usedButtons.size()-1; i++){
            CustomButton tmpButton1 = usedButtons.get(i);
            assignTmpButtons1(tmpButton1);
            addButtonToTheRightHashSet(tmpButton1);
        }
    }


    private void assignTmpButtons1(CustomButton tmpButton1) {
        tmpRowBtn = tmpButton1.getImInRow();
        tmpColBtn = tmpButton1.getImInColumn();
    }


    private void addButtonToTheRightHashSet(CustomButton tmpButton1) {
        for(int i = 0; i < usedButtons.size()-1; i++) {
            CustomButton tmpButton2 = usedButtons.get(i + 1);
            assignTmpButtons2(tmpButton2);

            if (tmpRowBtn == tmpRowBtn2 && tmpColBtn == tmpColBtnIncreased) {
                hashMapOfPlayersMoves.get(previousShape - 1).get(0).add(tmpButton1);
                hashMapOfPlayersMoves.get(previousShape - 1).get(0).add(tmpButton2);
            }
            if (tmpRowBtn == tmpRowBtnIncreased && tmpColBtn == tmpColBtn2) {
                hashMapOfPlayersMoves.get(previousShape - 1).get(1).add(tmpButton1);
                hashMapOfPlayersMoves.get(previousShape - 1).get(1).add(tmpButton2);
            }
            if (tmpRowBtn == tmpRowBtnIncreased && tmpColBtn == tmpColBtnIncreased) {
                hashMapOfPlayersMoves.get(previousShape - 1).get(2).add(tmpButton1);
                hashMapOfPlayersMoves.get(previousShape - 1).get(2).add(tmpButton2);
            }
            if (tmpRowBtn == tmpRowBtnIncreased && tmpColBtn == tmpColBtnDecreased) {
                hashMapOfPlayersMoves.get(previousShape - 1).get(3).add(tmpButton1);
                hashMapOfPlayersMoves.get(previousShape - 1).get(3).add(tmpButton2);
            }
        }

        checkNumberOfButtonsInTheLine();
    }


    private void assignTmpButtons2(CustomButton tmpButton2) {

        tmpRowBtn2 = tmpButton2.getImInRow();
        tmpRowBtnIncreased = tmpButton2.getImInRow() - 1;
        tmpColBtn2 = tmpButton2.getImInColumn();
        tmpColBtnIncreased = tmpButton2.getImInColumn() - 1;
        tmpColBtnDecreased = tmpButton2.getImInColumn() + 1;

    }


    private void checkNumberOfButtonsInTheLine() {
        checkRowInLine();
        checkColumnInLine();
        checkDecreaseInLine();
        checkIncreaseInLine();

    }


    private void checkRowInLine() {
        ArrayList<CustomButton> rowButtons = new ArrayList<>(hashMapOfPlayersMoves.get(previousShape-1).get(0));
        Collections.sort(rowButtons);

        for(int i = 0; i<rowButtons.size(); i++){
            inOneLineHashSet.clear();
            searchRowIncrease(rowButtons, i);
        }
    }


    private void searchRowIncrease(List<CustomButton> incButtons, int index){

        int row = incButtons.get(index).getImInRow();
        int column = incButtons.get(index).getImInColumn()+1;

        for(int i = 0; i<incButtons.size(); i++){
            if(incButtons.get(i).getImInRow() == row && incButtons.get(i).getImInColumn() == column){
                inOneLineHashSet.add(incButtons.get(index));
                inOneLineHashSet.add(incButtons.get(i));
                column++;
                inOneLineToWin();
            }
        }
    }


    private void checkColumnInLine(){
        List<CustomButton> colButtons = new ArrayList<>(hashMapOfPlayersMoves.get(previousShape-1).get(1));
        Collections.sort(colButtons);

        for(int i = 0; i<colButtons.size(); i++){
            inOneLineHashSet.clear();
            searchColIncrease(colButtons, i);
        }
    }


    private void searchColIncrease(List<CustomButton> incButtons, int index){

        int row = incButtons.get(index).getImInRow()+1;
        int column = incButtons.get(index).getImInColumn();

        for(int i = 0; i<incButtons.size(); i++){
            if(incButtons.get(i).getImInRow() == row && incButtons.get(i).getImInColumn() == column){
                inOneLineHashSet.add(incButtons.get(index));
                inOneLineHashSet.add(incButtons.get(i));
                row++;
                inOneLineToWin();
            }
        }
    }


    private void checkDecreaseInLine(){
        List<CustomButton> decButtons = new ArrayList<>(hashMapOfPlayersMoves.get(previousShape-1).get(2));
        Collections.sort(decButtons);

        for(int i = 0; i<decButtons.size(); i++){
            inOneLineHashSet.clear();
            searchRowAndColDecrease(decButtons, i);
        }

    }


    private void searchRowAndColDecrease(List<CustomButton> decButtons, int index){

        int rowPlus1 = decButtons.get(index).getImInRow()+1;
        int columnPlus1 = decButtons.get(index).getImInColumn()+1;
        for(int i = 0; i<decButtons.size(); i++){
            if(decButtons.get(i).getImInRow() == rowPlus1 && decButtons.get(i).getImInColumn() == columnPlus1){
                inOneLineHashSet.add(decButtons.get(index));
                inOneLineHashSet.add(decButtons.get(i));
                rowPlus1++;
                columnPlus1++;
                inOneLineToWin();
            }
        }
    }


    private void checkIncreaseInLine(){
        ArrayList<CustomButton> incButtons = new ArrayList<>(hashMapOfPlayersMoves.get(previousShape-1).get(3));
        Collections.sort(incButtons);

        for(int i = 0; i<incButtons.size(); i++){
            inOneLineHashSet.clear();
            searchRowAndColIncrease(incButtons, i);
        }
    }


    private void searchRowAndColIncrease(List<CustomButton> incButtons, int index){

        int row = incButtons.get(index).getImInRow()+1;
        int column = incButtons.get(index).getImInColumn()-1;

        for(int i = 0; i<incButtons.size(); i++){
            if(incButtons.get(i).getImInRow() == row && incButtons.get(i).getImInColumn() == column){
                inOneLineHashSet.add(incButtons.get(index));
                inOneLineHashSet.add(incButtons.get(i));
                row++;
                column--;
                inOneLineToWin();
            }
        }
    }


    private void inOneLineToWin() {
        if(inOneLineHashSet.size()>= inLineToWin){
            setWinner();
        }
    }


    private void setWinner() {
        listOfWinners.add(previousShape);
        doesSomebodyWinFlag = true;
        for (CustomButton buttons : inOneLineHashSet) {
            buttons.setBackgroundResource(R.drawable.gradient_background);

        }
    }


    boolean getFlagDoesSomebodyWin(){
        return doesSomebodyWinFlag;
    }


    HashSet<Integer> getListOfWinners() {
        return listOfWinners;
    }
}
