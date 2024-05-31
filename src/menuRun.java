import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.graphics.GRoundRect;
import acm.program.GraphicsProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
    int choices[] = {0,10,33,66};
    private static JButton oneButton;
    private static JButton twoButton;
    private static JButton threeButton;

    private static JButton reset;

    private boolean hasTyped = false;
    private boolean hasPressed = false;
//    private boolean pressTab = false;

    private int endReq;
    private GRect cursor;

    Color MYYELLOW = new Color(255,209,0);

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
//        pressTab = false;

        GLabel logo = new GLabel("ktype");
        logo.setFont(new Font("Avenir Next",Font.PLAIN,20));
        logo.setColor(MYYELLOW);
        add(logo,20,30);

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

        reset = new JButton("reset");
        reset.setForeground(MYYELLOW);
        reset.setBorderPainted(false);
        reset.setFocusPainted(false);
        reset.setFont(new Font("Courier", Font.PLAIN,22));
        add(reset,450,350);

        addActionListeners();
    }

    public void setUpButton(JButton button,boolean isOn){
        // default button settings

        button.setBackground(Color.darkGray);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Courier", Font.PLAIN, 13));

        if(isOn) button.setForeground(MYYELLOW);
        else button.setForeground(Color.WHITE);
    }

    public void printWords() throws FileNotFoundException, InterruptedException {
        cursor = new GRect(1,24);
        cursor.setFilled(true);
        cursor.setColor(MYYELLOW);
        cursor.setVisible(true);
        add(cursor,159,176);

        addActionListeners();

        displayGrid = typingScreen.setUpWords(hasNumbers,hasPunc,hasTimer,hasWords,menuChoice);
        gridLinesLeft = typingScreen.getGridMax();
        if(hasTimer){
            endReq = choices[menuChoice];
        }else{
            endReq = typingScreen.getCharTotal();
        }

        int x = 80, y = 200;
        int tlength = 0, twidth = 0;

        int maxLine = Math.min(gridLinesLeft,2);

        for (int i = 0; i <= maxLine; i++) {
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

        java.util.Timer timer = new java.util.Timer();
        timeCount.set(0);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (!hasTyped) {
                    timeCount.set(0);
                }
                else{
                    int currentTime = timeCount.incrementAndGet();
                    time.setLabel(currentTime + "");

                    if (hasTimer && currentTime == endReq) {
                        timer.cancel();
                        timer.purge();
                        endingPage(choices[menuChoice]);
                        return;
                    }
                    if(hasWords && typingScreen.getLastX() == typingScreen.getCursorX() && typingScreen.getLastY() == typingScreen.getCursorY()){
                        timer.cancel();
                        timer.purge();
                        endingPage(currentTime);
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
            case KeyEvent.VK_ENTER:
                restartTyping();
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
        int cursorX = typingScreen.getCursorX();
        int cursorY = typingScreen.getCursorY();
        int newX = (int)displayGrid[cursorY].get(cursorX).getX();
        int newY = (int)displayGrid[cursorY].get(cursorX).getY() - 24;
        cursor.setLocation(newX-1,newY);
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
        }else if(command.equals("reset")){
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

    public void endingPage(int timeTaken){
        removeAll();
        int wordCnt = typingScreen.getWordCnt();
        System.out.println(wordCnt);

        GLabel logo = new GLabel("ktype");
        logo.setFont(new Font("Avenir Next",Font.PLAIN,20));
        logo.setColor(MYYELLOW);
        add(logo,20,30);

        int netWPM = (int)(wordCnt * 60/timeTaken);
        System.out.println("time taken: "+timeTaken);
        System.out.println("WPM Net: "+netWPM);

        GLabel text1 = new GLabel("raw");
        text1.setColor(Color.GRAY);
        text1.setFont(new Font("Courier", Font.PLAIN,30));
        add(text1,100,230);
        GLabel netWPMLabel = new GLabel(netWPM + "");
        netWPMLabel.setFont(new Font("Courier", Font.PLAIN,50));
        netWPMLabel.setColor(MYYELLOW);
        add(netWPMLabel,100,290);


        int corChar = typingScreen.getCorChar();
        int incorChar = typingScreen.getIncorChar();
        int accuracy = 100 * corChar / (corChar + incorChar);

        GLabel text2 = new GLabel("acc");
        text2.setColor(Color.GRAY);
        text2.setFont(new Font("Courier", Font.PLAIN,30));
        add(text2,300,230);
        GLabel accLabel = new GLabel(accuracy + "%");
        accLabel.setFont(new Font("Courier", Font.PLAIN,50));
        accLabel.setColor(MYYELLOW);
        add(accLabel,300,290);

        GLabel text3 = new GLabel("correct");
        text3.setColor(Color.GRAY);
        text3.setFont(new Font("Courier", Font.PLAIN,30));
        add(text3,500,230);
        GLabel corr = new GLabel(corChar + "");
        corr.setFont(new Font("Courier", Font.PLAIN,50));
        corr.setColor(MYYELLOW);
        add(corr,500,290);

        GLabel text4 = new GLabel("incorrect");
        text4.setColor(Color.GRAY);
        text4.setFont(new Font("Courier", Font.PLAIN,30));
        add(text4,700,230);
        GLabel incorr = new GLabel(incorChar + "");
        incorr.setFont(new Font("Courier", Font.PLAIN,50));
        incorr.setColor(MYYELLOW);
        add(incorr,700,290);

        int wpm = netWPM * accuracy/100;
        GLabel text5 = new GLabel("wpm");
        text5.setColor(Color.GRAY);
        text5.setFont(new Font("Courier", Font.PLAIN,30));
        add(text5,100,100);
        GLabel wpmLabel = new GLabel(wpm + "");
        wpmLabel.setFont(new Font("Courier", Font.PLAIN,50));
        wpmLabel.setColor(MYYELLOW);
        add(wpmLabel,100,160);

        String testType = "";
        if(hasWords) testType += "words ";
        else testType += "time ";
        testType += choices[menuChoice];
        GLabel text6 = new GLabel("test type");
        text6.setColor(Color.GRAY);
        text6.setFont(new Font("Courier", Font.PLAIN,30));
        add(text6,300,100);
        GLabel testLabel = new GLabel(testType);
        testLabel.setFont(new Font("Courier", Font.PLAIN,50));
        testLabel.setColor(MYYELLOW);
        add(testLabel,300,160);

        add(reset,850,380);
        try{
            updateHighScore(wpm,accuracy);
        }
        catch(Exception e){
        }

    }

    public void updateHighScore(int wpm, int accuracy) throws IOException {
        File file = new File("highScore.txt");
        FileWriter fr = new FileWriter(file,true);
        String ans = "";
        if(hasWords) ans += "words ";
        else ans += "time ";
        ans += choices[menuChoice] + ", ";
        ans += wpm + ", " + accuracy + "%\n";
        fr.write(ans);
        fr.close();
    }
}