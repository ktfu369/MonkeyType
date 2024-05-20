import acm.graphics.GLabel;
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
    private static int corChar = 0, incorChar = 0, extraChar = 0, missedChar = 0;


    public ArrayList<GLabel>[] setUpWords(boolean isRandom, boolean hasNumbers, boolean hasPunctuation) throws FileNotFoundException {
       cursorX =0;
       cursorY = 0;
        findWord = new findWords(isRandom,hasNumbers,hasPunctuation);
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
        for(int i=0;i<selectedWords.length;i++){
            if(wordGrid[ypos].size() + selectedWords[i].length() >= 50){
                ypos++;
            }else{
                for(int j=0;j<selectedWords[i].length();j++){
                    wordGrid[ypos].add(selectedWords[i].charAt(j));
                }
                wordGrid[ypos].add(' ');
            }
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

    public int getCursorX(){
        return cursorX;
    }

    public int getCursorY(){
        return cursorY;
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
        if(typed.equals(displayGrid[cursorY].get(cursorX).getLabel())){
            displayGrid[cursorY].get(cursorX).setColor(Color.BLUE);
        }else{
            displayGrid[cursorY].get(cursorX).setColor(Color.RED);
        }
        displayGrid[cursorY].get(cursorX).setLabel(typed);
        cursorX++;
        if(cursorX>=displayGrid[cursorY].size()){
            curLine++;
            cursorX=0;
            if(cursorY!=1){
                cursorY++;
            }else{
                return true;
            }
        }
        return false;
    }




}
