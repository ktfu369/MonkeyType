import acm.graphics.GLabel;
import acm.program.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


public class printwords  extends GraphicsProgram implements ActionListener {
    private static ArrayList<GLabel>[] displayGrid = new ArrayList[500];
    int gridMax;
    int cursorX = 0,cursorY = 0;

    private static findWords findWord;
    static {
        try {
            findWord = new findWords(true,true,true);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void run(){
        this.resize(1000,600);
        ArrayList<Character>[] wordGrid = sortGrid();
        showWords(wordGrid);
        addKeyListeners();

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

    public void showWords(ArrayList<Character>[] wordGrid){
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


    public void keyPressed(KeyEvent keyPressed){
        System.out.println(cursorX + " " + cursorY);
        switch(keyPressed.getKeyCode()){
            case KeyEvent.VK_SHIFT:
                break;
            case KeyEvent.VK_BACK_SPACE:
                if(cursorX>0){
                    cursorX--;
                    displayGrid[cursorY].get(cursorX).setColor(Color.GRAY);
                }
                break;
            default:
                String typed = keyPressed.getKeyChar()+"";
                if(typed.equals(displayGrid[cursorY].get(cursorX).getLabel())){
                    displayGrid[cursorY].get(cursorX).setColor(Color.BLUE);
                }else{
                    displayGrid[cursorY].get(cursorX).setColor(Color.RED);
                }
                displayGrid[cursorY].get(cursorX).setLabel(typed);
                cursorX++;
                if(cursorX>=displayGrid[cursorY].size()){
                    cursorX=0;
                    cursorY++;
                }
        }
    }


    public static void main(String[] args) {
        new printwords().start(args);
    }
}
