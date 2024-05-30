import acm.graphics.GLabel;
import acm.graphics.GRoundRect;
import acm.program.GraphicsProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class menuRun extends GraphicsProgram {
    int gridLinesLeft;
    public static typingTest typingScreen;

    private static ArrayList<GLabel>[] displayGrid = new ArrayList[500];
    private static GLabel time;

    private static JButton numButton;
    boolean hasNumbers = false;

    private static JButton punctButton;
    boolean hasPunc = false;

    private static JButton timerButton;
    private boolean hasTimer = true;

    private static JButton wordsButton;
    private boolean hasWords = false;

    private static int menuChoice = 1;
    private static JButton oneButton;
    private static JButton twoButton;
    private static JButton threeButton;

    private boolean hasTyped = false;
    private boolean hasPressed = false;

    private Timer gameTimer;
    private TimerTask timerTask;


    public static void main(String[] args) {
        new menuRun().start(args);
    }

    public void run() {
        addKeyListeners();
        setTitle("KType");
        this.resize(1000, 500);

        setUpMenu();

        typingScreen = new typingTest();
        try {
            printWords();
        } catch (FileNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setUpMenu(){
        // displays gray menu bar and buttons on top
        hasTyped = false;

        GRoundRect rectMenu = new GRoundRect(700,30);
        rectMenu.setFilled(true);
        rectMenu.setFillColor(Color.DARK_GRAY);
        rectMenu.setVisible(true);
        add(rectMenu,155,50);

        punctButton = new JButton("punctuation");
        setUpButton(punctButton,hasPunc);
        punctButton.setSize(140, 30);
        add(punctButton,160,50);

        numButton = new JButton("numbers");
        setUpButton(numButton,hasNumbers);
        numButton.setSize(100, 30);
        add(numButton,290,50);

        timerButton = new JButton("time");
        setUpButton(timerButton,hasTimer);
        timerButton.setSize(90,30);
        add(timerButton,410,50);

        wordsButton = new JButton("words");
        setUpButton(wordsButton,hasWords);
        wordsButton.setSize(90,30);
        add(wordsButton,490,50);

        oneButton = new JButton("10");
        setUpButton(oneButton,menuChoice == 1);
        oneButton.setSize(60,30);
        add(oneButton,610,50);

        twoButton = new JButton("33");
        setUpButton(twoButton,menuChoice == 2);
        twoButton.setSize(60,30);
        add(twoButton,670,50);

        threeButton = new JButton("66");
        setUpButton(threeButton,menuChoice == 3);
        threeButton.setSize(60,30);
        add(threeButton,730,50);

        addActionListeners();
    }

    public void setUpButton(JButton button,boolean isOn){
        // default button settings

        button.setBackground(Color.darkGray);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Courier", Font.PLAIN, 13));

        if(isOn) button.setForeground(Color.YELLOW);
        else button.setForeground(Color.WHITE);
    }

    public void printWords() throws FileNotFoundException, InterruptedException {
        addActionListeners();

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

        startTimer();
    }

    public void startTimer() throws InterruptedException {
        time = typingScreen.setUpTime();
        add(time, 160, 170);

        AtomicInteger timeCount = new AtomicInteger();
        timeCount.set(0);
        java.util.Timer timer = new java.util.Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (!hasTyped) {
                    timeCount.set(0);
                }
                else{
                    int currentTime = timeCount.incrementAndGet();
                    time.setLabel(currentTime + "");

                    System.out.println("timer " + currentTime);
                    if (currentTime == 20) {
                        timer.cancel();
                        timer.purge();
                        System.out.println("DONE");
                        removeAll();
                        return;
                    }
                }
                if (hasPressed) {
                    hasPressed = false;
                    timeCount.set(0);
                    timer.cancel();
                    timer.purge();
                }
            }
        };

        timer.schedule(task, 0, 1000);
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
            hasPressed = true;
            hasPunc = !hasPunc;
            restartTyping();
        }else if(command.equals("numbers")){
            hasPressed = true;
            hasNumbers = !hasNumbers;
            restartTyping();
        }else if(command.equals("time")){
            hasPressed = true;
            hasTimer = true;
            hasWords = false;
            restartTyping();
        }else if(command.equals("words")){
            hasPressed = true;
            hasTimer = false;
            hasWords = true;
            restartTyping();
        }else if(command.equals("10")){
            hasPressed = true;
            menuChoice = 1;
            restartTyping();
        }else if(command.equals("33")){
            hasPressed = true;
            menuChoice = 2;
            restartTyping();
        }else if(command.equals("66")){
            hasPressed = true;
            menuChoice = 3;
            restartTyping();
        }
    }

    public void restartTyping(){
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

    public void endingPage(){
        removeAll();
        JButton end = new JButton();
    }
}