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


public class printwords extends GraphicsProgram implements ActionListener  {
    public static ArrayList<GLabel>[] displayGrid = new ArrayList[500];
    public static ArrayList<Character>[] wordGrid;
    public static ArrayList<Character>[] curWordGrid = new ArrayList[3];

    private static GLabel time;
    int gridMax, curLine=0;
    int cursorX = 0, cursorY = 0;

    private static findWords findWord;

    static {
        try {
            findWord = new findWords(true,false,true,false,1);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static int wordCnt=0;
    private static int corChar = 0, incorChar = 0, extraChar = 0, missedChar = 0;

    public void run() {
        this.resize(1000,500);
        wordGrid = sortGrid();
        showWords();
        addKeyListeners();
        time = new GLabel("0");
        time.setFont(new Font("Courier", Font.PLAIN, 24));
        time.setColor(Color.GRAY);
        add(time,160,170);
        int cnt = 0;
        while(cnt<=20){
            time.setLabel(cnt+"");
            cnt++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(wordCnt);
        removeAll();
//        exit();
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

    public void showWords(){
        for(int i=0;i<500;i++){
            displayGrid[i] = new ArrayList<GLabel>();
        }
        int x = 80,y = 200;
        int tlength = 0, twidth = 0;

        for(int i=0;i<3;i++){
            tlength = x;
            for(int j=0;j<wordGrid[i].size();j++){
                GLabel label = new GLabel(wordGrid[i].get(j)+"");
                label.setFont(new Font("Courier", Font.PLAIN, 24));
                label.setColor(Color.GRAY);
                displayGrid[i].add(label);
                add(displayGrid[i].get(j),x+tlength,y+twidth);
                tlength += displayGrid[i].get(j).getWidth();
            }
            twidth += displayGrid[i].get(0).getHeight();
        }
    }

    public void updateLine(int line){
        for(int i=0;i<displayGrid[line].size();i++){
            remove(displayGrid[line].get(i));
        }
        displayGrid[line].clear();
        for(int i=0;i<displayGrid[line+1].size();i++){
            GLabel temp = new GLabel(displayGrid[line+1].get(i).getLabel());
            temp.setFont(new Font("Courier", Font.PLAIN, 24));
            temp.setColor(displayGrid[line+1].get(i).getColor());
            temp.setLocation(displayGrid[line+1].get(i).getX(),displayGrid[line+1].get(i).getY()-29);
            displayGrid[line].add(temp);
            add(displayGrid[line].get(i));
        }
    }

    public void newLine(){
        for(int i=0;i<displayGrid[2].size();i++){
            remove(displayGrid[2].get(i));
        }
        displayGrid[2].clear();
        int x = 80;
        int tlength = 80;
        for(int j=0;j<wordGrid[curLine+1].size();j++){
            GLabel label = new GLabel(wordGrid[curLine+1].get(j)+"");
            label.setFont(new Font("Courier", Font.PLAIN, 24));
            label.setColor(Color.GRAY);
            displayGrid[2].add(label);
            add(displayGrid[2].get(j),x+tlength,displayGrid[1].get(0).getY()+29);
            tlength += displayGrid[2].get(j).getWidth();
        }
    }

    public void remove(){
//        removeKeyListener(getKeyListe);
        for(int i=0;i<3;i++){
            for(int j=0;j<displayGrid[i].size();j++){
                remove(displayGrid[i].get(j));
            }
            displayGrid[i].clear();
        }
    }

    public void keyPressed(KeyEvent keyPressed){
        switch(keyPressed.getKeyCode()){
            case KeyEvent.VK_SHIFT:
                break;
            case KeyEvent.VK_BACK_SPACE:
                if(cursorX>0){
                    cursorX--;
                    displayGrid[cursorY].get(cursorX).setColor(Color.GRAY);
                    displayGrid[cursorY].get(cursorX).setLabel(wordGrid[curLine].get(cursorX)+"");
                }
                break;
            default:
                String typed = keyPressed.getKeyChar()+"";
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
                    if(cursorY==1){
                        updateLine(0);
                        updateLine(1);
                        newLine();
                    }else{
                        cursorY++;
                    }
                }
        }
    }


    public static void main(String[] args)  {
        new printwords().start(args);
    }
}
