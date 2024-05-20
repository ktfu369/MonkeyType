import acm.graphics.GLabel;
import acm.graphics.GRoundRect;
import acm.program.GraphicsProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class controlPanel extends GraphicsProgram {
    public static typingTest typingScreen;

    private static ArrayList<GLabel>[] displayGrid = new ArrayList[500];
    private static GLabel time;
    int timerCnt = 0;

    boolean isRandom = true;

    private static JButton numButton;
    boolean hasNumbers = false;

    private static JButton punctButton;
    boolean hasPunc = false;

    private boolean hasTyped = false;


    public static void main(String[] args) {
        new controlPanel().start(args);
    }

    public void init() {

    }

    public void run() {
        addActionListeners();
        addKeyListeners();
        setTitle("KType");
        this.resize(1000, 600);
        setUpMenu();

        typingScreen = new typingTest();
        try {
            printWords();
        } catch (FileNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setUpMenu(){
        GRoundRect rectMenu = new GRoundRect(960,30);
        rectMenu.setFilled(true);
        rectMenu.setFillColor(Color.DARK_GRAY);
        rectMenu.setVisible(true);
        add(rectMenu,20,20);

        punctButton = new JButton("punctuation");
        punctButton.setBackground(Color.darkGray);
        punctButton.setSize(120, 30);
        if(hasPunc){
            punctButton.setForeground(Color.YELLOW);
        }else{
            punctButton.setForeground(Color.WHITE);
        }
        punctButton.setOpaque(true);
        punctButton.setBorderPainted(false);
        punctButton.setFocusPainted(false);
        add(punctButton,30,20);

        numButton = new JButton("numbers");
        numButton.setBackground(Color.darkGray);
        numButton.setSize(120, 30);
        if(hasNumbers){
            numButton.setForeground(Color.YELLOW);
        }else{
            numButton.setForeground(Color.WHITE);
        }
        numButton.setOpaque(true);
        numButton.setBorderPainted(false);
        numButton.setFocusPainted(false);
        add(numButton,130,20);

        addActionListeners();
    }

    public void printWords() throws FileNotFoundException, InterruptedException {
        displayGrid = typingScreen.setUpWords(isRandom,hasNumbers,hasPunc);
        time = typingScreen.setUpTime();
        int x = 80, y = 200;
        int tlength = 0, twidth = 0;

        for (int i = 0; i < 3; i++) {
            tlength = x;
            for (int j = 0; j < displayGrid[i].size(); j++) {
                add(displayGrid[i].get(j), x + tlength, y + twidth);
                tlength += displayGrid[i].get(j).getWidth();
            }
            twidth += displayGrid[i].get(0).getHeight();
        }
        add(time, 160, 170);
//        while(true){
//            System.out.print("");
//            if(hasTyped){
//                break;
//            }
//        }
//        while(timerCnt<=20){
//            Thread.sleep(1000);
//            timerCnt++;
//            time.setLabel(timerCnt+"");
//        }
//        System.out.println(typingScreen.getWordCnt());
//        removeAll();
    }


    public void updateLine(int line) {
        for (int i = 0; i < displayGrid[line].size(); i++) {
            remove(displayGrid[line].get(i));
        }
        displayGrid = typingScreen.updateLine(line);
        for (int i = 0; i < displayGrid[line].size(); i++) {
            add(displayGrid[line].get(i));
        }
    }

    public void newLine() {
        for (int i = 0; i < displayGrid[2].size(); i++) {
            remove(displayGrid[2].get(i));
        }
        displayGrid = typingScreen.newLine();
        int x = 80;
        int tlength = 80;
        for (int j = 0; j < displayGrid[2].size(); j++) {
            add(displayGrid[2].get(j), x + tlength, displayGrid[1].get(0).getY() + 29);
            tlength += displayGrid[2].get(j).getWidth();
        }
    }

    public void keyPressed(KeyEvent keyPressed) {
        switch (keyPressed.getKeyCode()) {
            case KeyEvent.VK_SHIFT:
                break;
            case KeyEvent.VK_BACK_SPACE:
                typingScreen.backspace();
                break;
            default:
                hasTyped = true;
                if (typingScreen.typed(keyPressed.getKeyChar() + "")) {
                    updateLine(0);
                    updateLine(1);
                    newLine();
                }
        }
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("punctuation")){
            System.out.println("ounc");
            hasPunc = !hasPunc;
            removeAll();
            setUpMenu();
            try {
                printWords();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }else if(command.equals("numbers")){
            System.out.println("number");
            hasNumbers = !hasNumbers;
            removeAll();
            setUpMenu();
            try {
                printWords();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
