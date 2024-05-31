import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Date;


public class typingTest{
    private static ArrayList<GLabel>[] displayGrid = new ArrayList[500];
    private static ArrayList<Character>[] wordGrid;

    private static GLabel time;
    int gridMax, curLine=0;
    int cursorX = 0, cursorY = 0;

    private static findWords findWord;

    private static int wordCnt=0;
    private static int corChar = 0, incorChar = 0;
    private static int lastX = -1, lastY = -1;

    private GRect cursor;

    Color MYBLUE = new Color(0,100,148);
    Color MYRED = new Color(128,0,0);

    public ArrayList<GLabel>[] setUpWords(boolean hasNumbers, boolean hasPunctuation, boolean hasTimer, boolean hasWords, int choice) throws FileNotFoundException {
        curLine = 0;
        cursorX = 0;
        cursorY = 0;
        wordCnt = 0;
        lastX = -1;
        lastY = -1;

        findWord = new findWords(hasNumbers,hasPunctuation,hasTimer,hasWords,choice);
        wordGrid = sortGrid();
        return showWords();
    }

    public GLabel setUpTime(){
        time = new GLabel("0");
        time.setFont(new Font("Courier", Font.PLAIN, 24));
        time.setColor(Color.GRAY);
        return time;
    }

    // splits into lines with max of 50 characters
    public ArrayList<Character>[] sortGrid(){
        String[] selectedWords = findWord.getWords();
        ArrayList<Character>[] wordGrid = new ArrayList[500];
        for(int i=0;i<500;i++){
            wordGrid[i] = new ArrayList<Character>();
        }

        int ypos = 0;
        int cnt=1;
        for(int i=0;i<selectedWords.length;i++){
            if(wordGrid[ypos].size() + selectedWords[i].length() >= 50){
                ypos++;
            }
            for(int j=0;j<selectedWords[i].length();j++){
                wordGrid[ypos].add(selectedWords[i].charAt(j));
            }
            wordGrid[ypos].add(' ');
        }
        gridMax = ypos;
        return wordGrid;
    }

    public ArrayList<GLabel>[] showWords(){
        for(int i=0;i<500;i++){
            displayGrid[i] = new ArrayList<GLabel>();
        }

        for(int i=0;i<3;i++){
            for(int j=0;j<wordGrid[i].size();j++){
                GLabel label = new GLabel(wordGrid[i].get(j)+"");
                label.setFont(new Font("Courier", Font.PLAIN, 24));
                label.setColor(Color.GRAY);
                displayGrid[i].add(label);
            }
        }
        return displayGrid;
    }

    public ArrayList<GLabel>[] updateLine(int line){
        displayGrid[line].clear();
        for(int i=0;i<displayGrid[line+1].size();i++){
            GLabel temp = new GLabel(displayGrid[line+1].get(i).getLabel());
            temp.setFont(new Font("Courier", Font.PLAIN, 24));
            temp.setColor(displayGrid[line+1].get(i).getColor());
            temp.setLocation(displayGrid[line+1].get(i).getX(),displayGrid[line+1].get(i).getY()-29);
            displayGrid[line].add(temp);
        }
        return displayGrid;
    }

    public ArrayList<GLabel>[] newLine(){
        displayGrid[2].clear();
        for(int j=0;j<wordGrid[curLine+1].size();j++){
            GLabel label = new GLabel(wordGrid[curLine+1].get(j)+"");
            label.setFont(new Font("Courier", Font.PLAIN, 24));
            label.setColor(Color.GRAY);
            displayGrid[2].add(label);
        }
        return displayGrid;
    }

    public int getWordCnt(){
        return wordCnt;
    }

    public int getGridMax(){
        return gridMax;
    }

    public int getCursorX(){
        return cursorX;
    }

    public int getCursorY(){
        return cursorY;
    }

    public int getCharCur(){
        return incorChar + corChar;
    }

    public int getCharTotal(){
        return findWords.getCharTotal();
    }

    public int getCorChar(){
        return corChar;
    }

    public int getIncorChar(){
        return incorChar;
    }

    public int getLastX(){
        return lastX;
    }

    public int getLastY(){
        return lastY;
    }

    public void backspace(){
        if(cursorX>0){
            cursorX--;
            displayGrid[cursorY].get(cursorX).setColor(Color.GRAY);
            displayGrid[cursorY].get(cursorX).setLabel(wordGrid[curLine].get(cursorX)+"");
        }
//        return displayGrid;
    }

    public boolean typed(String typed){
        if(typed.equals(" ")){
            wordCnt++;
        }
//        cursor.setLocation(cursor.getX()+1,cursor.getY());
        if(typed.equals(displayGrid[cursorY].get(cursorX).getLabel())){
            displayGrid[cursorY].get(cursorX).setColor(MYBLUE);
        }else{
            displayGrid[cursorY].get(cursorX).setColor(MYRED);
        }
        displayGrid[cursorY].get(cursorX).setLabel(typed);
        cursorX++;
        if(cursorX>=displayGrid[cursorY].size()){
            countRightWrong();

            curLine++;
            cursorX=0;
            if(cursorY!=1){
                cursorY++;
                if(curLine == gridMax){
                    lastY = cursorY;
                    lastX = displayGrid[cursorY].size() - 1;
                }
            }else{
                if(curLine == gridMax){
                    lastY = cursorY;
                    lastX = displayGrid[cursorY].size() - 1;
                }
                return true;
            }
        }
        return false;
    }

    public void countRightWrong(){
        for(int i=0;i<displayGrid[cursorY].size();i++){
            if(displayGrid[cursorY].get(i).getLabel().equals(" ")) continue;
            if(displayGrid[cursorY].get(i).getColor()==MYBLUE) corChar++;
            else if(displayGrid[cursorY].get(i).getColor()==MYRED) incorChar++;
        }
    }

}
