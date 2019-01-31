import javax.swing.*;
import java.awt.*;

public class Opponent extends JComponent implements GameLoop {


    private int xCoord = 100;
    private int yCoord = 100;
    private boolean isVisible = false;

    public Opponent() {
        this.setSize(700,720);
    }


    public void paint(Graphics g) {
        if (this.isVisible == true) {
            super.paint(g);
            g.setColor(Color.red);
            g.fillRect(xCoord, yCoord,20,20);
        }
    }


    public Rectangle getBounds() {

        return new Rectangle(this.xCoord,this.yCoord,20,20);

    }

    public void show() {
        this.isVisible = true;
    }


    public void setCoords(int x, int y) {
        this.xCoord = x;
        this.yCoord = y;
    }

    @Override
    public void update() {

        if (xCoord > data.coordinates[0][0]) {
            xCoord -= Globals.gameSpeed;
        }
        else if (xCoord < data.coordinates[0][0]) {
            xCoord += Globals.gameSpeed;
        }

        if (yCoord > data.coordinates[0][1]) {
            yCoord -= Globals.gameSpeed;
        }
        else if (yCoord < data.coordinates[0][1]) {
            yCoord += Globals.gameSpeed;
        }

        super.repaint();

    }


}
