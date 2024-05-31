import acm.graphics.GLabel;
import acm.program.GraphicsProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class pageRun extends GraphicsProgram {
    int gridLinesLeft;
    public static typingTest typingScreen;

    private static ArrayList<GLabel>[] displayGrid = new ArrayList[500];
    private static GLabel time;
    int timerCnt = 0;

    private static JButton numButton;
    boolean hasNumbers = false;

    private static JButton punctButton;
    boolean hasPunc = false;

    private static JButton timerButton;
    private boolean hasTimer = false;

    private static JButton wordsButton;
    private boolean hasWords = false;

    private static int menuChoice = 0;
    private static JButton oneButton;
    private static JButton twoButton;
    private static JButton threeButton;

    private static JButton start;

    private boolean hasTyped = false;
    private boolean hasPressed;

    Color BLUE = new Color(0,100,148);

    public static void main(String[] args) {
        new pageRun().start(args);
    }

    private static boolean test = false;
    public void run() {
//        addKeyListeners();
        setTitle("KType");
        this.resize(1000, 600);

        if(test){
                        typingScreen = new typingTest();
            try {
                printWords();
            } catch (FileNotFoundException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }else{
            startingScreen();
        }

//        setUpMenu();
//        typingScreen = new typingTest();
//        try {
//            printWords();
//        } catch (FileNotFoundException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void startingScreen(){

        GLabel selection = new GLabel("text selection");
        selection.setFont(new Font("Courier", Font.PLAIN, 22));
        selection.setColor(Color.DARK_GRAY);
        add(selection,150,200);
        punctButton = new JButton("punctuation");
        setUpButton(punctButton);
        add(punctButton,150,220);
        numButton = new JButton("numbers");
        setUpButton(numButton);
        add(numButton,170,260);

        GLabel mode = new GLabel("playing modes");
        mode.setFont(new Font("Courier", Font.PLAIN, 22));
        mode.setColor(Color.DARK_GRAY);
        add(mode,420,200);
        timerButton = new JButton("time");
        setUpButton(timerButton);
        add(timerButton,450,220);
        wordsButton = new JButton("words");
        setUpButton(wordsButton);
        add(wordsButton,450,260);

        oneButton = new JButton("10");
        setUpButton(oneButton);
        add(oneButton,710,200);
        twoButton = new JButton("33");
        setUpButton(twoButton);
        add(twoButton,710,240);
        threeButton = new JButton("66");
        setUpButton(threeButton);
        add(threeButton,710,280);

        start = new JButton("start");
        start.setFont(new Font("Courier", Font.PLAIN, 22));
        start.setForeground(Color.GRAY);
        start.setBorderPainted(false);
        start.setFocusPainted(false);
        add(start,830,430);

        addActionListeners();
    }

    public void setUpMenu(){
        // displays gray menu bar and buttons on top

        System.out.println(hasWords+" "+menuChoice);
        hasTyped = false;

//        GRoundRect rectMenu = new GRoundRect(700,30);
//        rectMenu.setFilled(true);
//        rectMenu.setFillColor(Color.DARK_GRAY);
//        rectMenu.setVisible(true);
//        add(rectMenu,155,50);
//
//        punctButton = new JButton("punctuation");
//        setUpButton(punctButton,hasPunc);
//        punctButton.setSize(140, 30);
//        add(punctButton,160,50);
//
//        numButton = new JButton("numbers");
//        setUpButton(numButton,hasNumbers);
//        numButton.setSize(100, 30);
//        add(numButton,290,50);
//
//        timerButton = new JButton("time");
//        setUpButton(timerButton,hasTimer);
//        timerButton.setSize(90,30);
//        add(timerButton,410,50);
//
//        wordsButton = new JButton("words");
//        setUpButton(wordsButton,hasWords);
//        wordsButton.setSize(90,30);
//        add(wordsButton,490,50);
//
//        oneButton = new JButton("10");
//        setUpButton(oneButton,menuChoice == 1);
//        oneButton.setSize(60,30);
//        add(oneButton,610,50);
//
//        twoButton = new JButton("33");
//        setUpButton(twoButton,menuChoice == 2);
//        twoButton.setSize(60,30);
//        add(twoButton,670,50);
//
//        threeButton = new JButton("66");
//        setUpButton(threeButton,menuChoice == 3);
//        threeButton.setSize(60,30);
//        add(threeButton,730,50);


        timerCnt = 0;
        addActionListeners();
    }

    public void setUpButton(JButton button){
        button.setFont(new Font("Courier", Font.PLAIN, 18));
        button.setForeground(Color.DARK_GRAY);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }

    public void printWords() throws FileNotFoundException, InterruptedException {
//        addActionListeners();
        addKeyListeners();
        displayGrid = typingScreen.setUpWords(hasNumbers,hasPunc,hasTimer,hasWords,menuChoice);
        gridLinesLeft = typingScreen.getGridMax();

        int x = 80, y = 200;
        int tlength = 0, twidth = 0;

        int maxLine;
        if(gridLinesLeft < 3) maxLine = gridLinesLeft;
        else maxLine = 3;

        for (int i = 0; i < maxLine; i++) {
            tlength = x;
            for (int j = 0; j < displayGrid[i].size(); j++) {
                add(displayGrid[i].get(j), x + tlength, y + twidth);
                tlength += displayGrid[i].get(j).getWidth();
            }
            twidth += displayGrid[i].get(0).getHeight();
        }
        time = typingScreen.setUpTime();
        time.setLabel(timerCnt+"");
        add(time, 160, 170);
        while(true){
            System.out.print("");
            if(hasTyped){
//                hasPressed = false;
                break;
            }
        }
//        System.out.println(displayGrid[0].get(0));

        while(timerCnt<=10){
            Thread.sleep(1000);
            timerCnt++;
            System.out.println(timerCnt);
            time.setLabel(timerCnt+"");
        }
//        System.out.println(typingScreen.getWordCnt());
//        removeAll();
//        while(true){
//            System.out.print("");
//            if(hasTyped){
//                while(timerCnt<=20){
//                    Thread.sleep(1000);
//                    timerCnt++;
//                    time.setLabel(timerCnt+"");
//                }
//                System.out.println(typingScreen.getWordCnt());
//                removeAll();
//            break;
//            }
//            if(hasPressed){
//                break;
//            }
//        }
//        System.out.println("exit pw");
    }

    public void startTimer() throws InterruptedException {
//        addKeyListeners();
//        System.out.print("z");
//        while(true){
//            System.out.print("");
//            if(hasTyped){
//                break;
//            }
//        }
//        System.out.println(displayGrid[0].get(0));
//
//        while(timerCnt<=10){
//            Thread.sleep(1000);
//            timerCnt++;
//            System.out.println(timerCnt);
//            time.setLabel(timerCnt+"");
//            if(hasPressed) break;
//        }
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
                    if(gridLinesLeft>1 ){
                        updateLine(0);
                        updateLine(1);

                        newLine();
                    }

                    gridLinesLeft --;
                }
        }
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("punctuation")){
            System.out.println("punc");
            hasPunc = !hasPunc;
            if(hasPunc) punctButton.setForeground(BLUE);
            else punctButton.setForeground(Color.DARK_GRAY);
        }else if(command.equals("numbers")){
            System.out.println("number");
            hasNumbers = !hasNumbers;
            if(hasNumbers) numButton.setForeground(BLUE);
            else numButton.setForeground(Color.DARK_GRAY);
        }else if(command.equals("time")){
            System.out.println("time");
            hasTimer = true;
            hasWords = false;
            timerButton.setForeground(BLUE);
            wordsButton.setForeground(Color.DARK_GRAY);
        }else if(command.equals("words")){
            System.out.println("words");
            hasTimer = false;
            hasWords = true;
            wordsButton.setForeground(BLUE);
            timerButton.setForeground(Color.DARK_GRAY);
        }else if(command.equals("10")){
            menuChoice = 1;
            oneButton.setForeground(BLUE);
            twoButton.setForeground(Color.DARK_GRAY);
            threeButton.setForeground(Color.DARK_GRAY);
        }else if(command.equals("33")){
            menuChoice = 2;
            twoButton.setForeground(BLUE);
            oneButton.setForeground(Color.DARK_GRAY);
            threeButton.setForeground(Color.DARK_GRAY);
        }else if(command.equals("66")){
            menuChoice = 3;
            threeButton.setForeground(BLUE);
            oneButton.setForeground(Color.DARK_GRAY);
            twoButton.setForeground(Color.DARK_GRAY);
        }else if(command.equals("start")){
            if(menuChoice != 0 && (hasTimer || hasWords)){
                start.removeActionListener(start.getActionListeners()[0]);
                System.out.println("start");
//                removeAll();
                exit();
                test = true;
                new pageRun().start();
                //                setTitle("KType");
//                typingScreen = new typingTest();
//                try {
//                    printWords();
//                } catch (FileNotFoundException | InterruptedException ex) {
//                    throw new RuntimeException(ex);
//                }
//                try {
//                    startTimer();
//                } catch (InterruptedException ex) {
//                    throw new RuntimeException(ex);
//                }

            }
        }
        if(menuChoice != 0 && (hasTimer || hasWords)){
            start.setForeground(Color.DARK_GRAY);
        }
    }

    public void endingPage(){
        removeAll();
        JButton end = new JButton();
    }
}
