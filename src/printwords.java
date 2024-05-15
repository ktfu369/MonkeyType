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


public class printwords  extends GraphicsProgram implements ActionListener {
    private static ArrayList<GLabel>[] displayGrid = new ArrayList[500];
    private static ArrayList<Character>[] wordGrid;
    private static ArrayList<Character>[] curWordGrid = new ArrayList[3];
    int gridMax, curLine=0;
    int cursorX = 0, cursorY = 0;

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
        wordGrid = sortGrid();
        showWords();
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
                        System.out.println("update");
                        updateLine(0);
                        updateLine(1);
                        newLine();
                    }else{
                        cursorY++;
                    }
                }
        }
    }


    public static void main(String[] args) {
        new printwords().start(args);
    }
}
