import acm.graphics.GLabel;
import acm.program.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


public class printwords  extends GraphicsProgram implements ActionListener {
    String words = "I recently discovered I could make fudge with just chocolate chips, sweetened condensed milk, vanilla extract, and a thick pot on slow heat. I tried it with dark chocolate chunks and I tried it with semi-sweet chocolate chips. It's better with both kinds. It comes out pretty bad with just the dark chocolate. The best add-ins are crushed almonds and marshmallows -- what you get from that is Rocky Road. It takes about twenty minutes from start to fridge, and then it takes about six months to work off the twenty pounds you gain from eating it. All things in moderation, friends. All things in moderation.";

    private GLabel a[][] = new GLabel[4][21];
    private int posx=0,posy=0;
    public void run(){
        int x=30,y=30,cnt=0;
        int tlength = 0, twidth = 0;
        for(int i=0;i<3;i++){
            tlength=30;
            for(int j=0;j<20;j++){
                a[i][j] = new GLabel(words.charAt(cnt++)+"");
                a[i][j].setFont(new Font("Courier", Font.PLAIN, 18));
                a[i][j].setColor(Color.GRAY);
                add(a[i][j],y+tlength,x+twidth);
                tlength += a[i][j].getWidth();
            }
            twidth += a[i][0].getHeight();
        }

        addKeyListeners();

    }

    public void keyPressed(KeyEvent keyPressed){
        switch(keyPressed.getKeyCode()){
            case KeyEvent.VK_SHIFT:
                break;
            case KeyEvent.VK_BACK_SPACE:
                if(posx>0){
                    posx--;
                    a[posy][posx].setColor(Color.GRAY);
                }
                break;
            default:
                String typed = keyPressed.getKeyChar()+"";
                if(typed.equals(a[posy][posx].getLabel())){
                    a[posy][posx].setColor(Color.BLUE);
                }else{
                    a[posy][posx].setColor(Color.RED);
                }
                a[posy][posx].setLabel(typed);
                posx++;
                if(posx>=20){
                    posx=0;
                    posy++;
                }
        }
    }

    public static void main(String[] args) {
        new printwords().start(args);
    }
}
