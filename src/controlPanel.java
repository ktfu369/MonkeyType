import acm.graphics.GLabel;
import acm.program.GraphicsProgram;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class controlPanel extends GraphicsProgram {
    public static printwords test = new printwords();
    public static typingTest test2;

    private static ArrayList<GLabel>[] displayGrid = new ArrayList[500];
    private static GLabel time;

    public static void main(String[] args) {
        new controlPanel().start(args);
    }

    public void init(){

    }
    public void run(){
        this.resize(1000,600);
        test2 = new typingTest();
        try {
            printWords();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void printWords() throws FileNotFoundException {
        displayGrid = test2.setUpWords();
        time = test2.setUpTime();
        int x = 80,y = 200;
        int tlength = 0, twidth = 0;

        for(int i=0;i<3;i++){
            tlength = x;
            for(int j=0;j<displayGrid[i].size();j++){
                add(displayGrid[i].get(j),x+tlength,y+twidth);
                tlength += displayGrid[i].get(j).getWidth();
            }
            twidth += displayGrid[i].get(0).getHeight();
        }
        add(time,160,170);
        addKeyListeners();

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
        System.out.println(test2.getWordCnt());
        removeAll();

    }

    public void updateLine(int line){
        for(int i=0;i<displayGrid[line].size();i++){
            remove(displayGrid[line].get(i));
        }
        displayGrid = test2.updateLine(line);
        for(int i=0;i<displayGrid[line].size();i++){
            add(displayGrid[line].get(i));
        }
    }
    public void newLine(){
        for(int i=0;i<displayGrid[2].size();i++){
            remove(displayGrid[2].get(i));
        }
        displayGrid = test2.newLine();
        int x = 80;
        int tlength = 80;
        for(int j=0;j<displayGrid[2].size();j++){
            add(displayGrid[2].get(j),x+tlength,displayGrid[1].get(0).getY()+29);
            tlength += displayGrid[2].get(j).getWidth();
        }
    }

    public void keyPressed(KeyEvent keyPressed){
        switch(keyPressed.getKeyCode()){
            case KeyEvent.VK_SHIFT:
                break;
            case KeyEvent.VK_BACK_SPACE:
                test2.backspace();
                break;
            default:
                if(test2.typed(keyPressed.getKeyChar()+"")){
                    updateLine(0);
                    updateLine(1);
                    newLine();
                }
        }
    }

}
