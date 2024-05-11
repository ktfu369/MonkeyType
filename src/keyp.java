import acm.graphics.GLabel;
import acm.program.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class keyp extends GraphicsProgram implements ActionListener {
    private GLabel label = new GLabel("testing!");

    public void run(){
        add(label,10,30);
        addKeyListeners();
    }

    public void keyPressed(KeyEvent keyPressed){
        switch(keyPressed.getKeyCode()){
            case KeyEvent.VK_UP:
                label.setLabel("up!");
                break;
            case KeyEvent.VK_DOWN:
                label.setLabel("down!");
                break;
            case KeyEvent.VK_RIGHT:
                label.setLabel("right!");
                label.setLocation(label.getX()+1,label.getY());
                break;
            default:
                label.setLabel(keyPressed.getKeyChar()+"");
        }
    }

    public static void main(String[] args) {
        new keyp().start(args);
    }
}
