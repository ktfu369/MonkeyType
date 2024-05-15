//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.Timer;
//
/////** @see http://stackoverflow.com/questions/4373493 */
//public class swingTimer extends JFrame implements ActionListener  {
//
//    private void display() {
//        this.setTitle("TimerFrame");
//        this.setLayout(new GridLayout(0, 1));
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.add(new TimerButton("Back in a second", 1000));
//        this.add(new TimerButton("Back in a minute", 60 * 1000));
//        this.add(new TimerButton("Back in an hour", 60 * 60 * 1000));
//        this.pack();
//        this.setLocationRelativeTo(null);
//        this.setVisible(true);
//    }
//
//    /** A button that hides it's enclosing Window for delay ms. */
//    private class TimerButton extends JButton {
//
//        private final Timer timer;
//
//        public TimerButton(String text, int delay) {
//            super(text);
//            this.addActionListener(new StartListener());
//            timer = new Timer(delay, new StopListener());
//        }
//
//        private class StartListener implements ActionListener {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
////                swingTimer.this.setVisible(false);
//                System.out.println("start");
//                timer.start();
//            }
//        }
//
//        private class StopListener implements ActionListener {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
////                timer.stop();
//                System.out.println("stop");
//                swingTimer.this.setVisible(true);
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new swingTimer().display();
//                addKeyListeners();
//            }
//        });
//    }
//
//    public void keyPressed(KeyEvent keyPressed){
//        switch(keyPressed.getKeyCode()){
//            case KeyEvent.VK_SHIFT:
//                break;
//            case KeyEvent.VK_BACK_SPACE:
//
//                break;
//            default:
//                String typed = keyPressed.getKeyChar()+"";
//                System.out.println(typed);
//        }
//    }
//}