import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Character  extends JComponent implements KeyListener, GameLoop {


    private Timer timer;
    private double xVel = 0;
    private double yVel = 0;
    private int xCoord = 100;
    private int yCoord = 100;
    private boolean collision = false;
    private boolean isVisible = false;
    private boolean allowsMovement = false;
    private Font font = new Font("Arial", Font.BOLD, 19);


    public Character() {
        this.setSize(700,720);
        this.addKeyListener(this);
        this.setFocusable(true);
    }

    public void setMovement(boolean movement) {
        this.allowsMovement = movement;
    }

    public void paint(Graphics g) {

        if (this.isVisible == true) {
            super.paint(g);
            g.setColor(Color.white);
            g.fillRect(xCoord, yCoord,20,20);

            g.setFont(this.font);

            g.setColor(new Color(112, 112, 112, 112));
            g.fillRect(560,0,140,40);

            g.setColor(Color.white);
            g.drawString("Time: " + Integer.toString( Globals.score ), 570, 25);


        }


    }

    public void show() {
        this.isVisible = true;
    }

    public void setCoords(int x, int y) {
        this.xCoord = x;
        this.yCoord = y;
    }

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public Rectangle getBounds() {

        return new Rectangle(this.xCoord,this.yCoord,20,20);

    }


    public void setCollision() {

        xCoord += -xVel;
        yCoord += -yVel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {


        if (collision == false && allowsMovement == true) {

            if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                xVel = Globals.gameSpeed;
                yVel = 0;
            }
            else if (e.getKeyCode() == KeyEvent.VK_LEFT){
                xVel = -Globals.gameSpeed;
                yVel = 0;
            }
            else if (e.getKeyCode() == KeyEvent.VK_UP){
                yVel = -Globals.gameSpeed;
                xVel = 0;
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN){
                yVel = Globals.gameSpeed;
                xVel = 0;
            }


        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT){
            xVel = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN ||e.getKeyCode() == KeyEvent.VK_UP) {
            yVel = 0;
        }




    }

    @Override
    public void update() {
        super.repaint();
        xCoord += xVel;
        yCoord += yVel;
    }
}