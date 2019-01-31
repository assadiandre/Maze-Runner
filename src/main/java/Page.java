import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

public class Page extends JComponent implements KeyListener {
    private int showPending;
    private int countDownTime;
    private Timer timer = new Timer();


    public Page (){
        this.addKeyListener(this);
        this.setFocusable(true);
        this.showPending = -1;
        this.countDownTime = 5;
        this.setSize(700, 720);
    }


    public void setPage(int num){
        this.showPending = num;
        this.repaint();

        if (num == 1) {
            this.startGame();
        }

    }

    private void startGame()  {

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (countDownTime == 0) {
                    timer.cancel();
                    Globals.page.setVisible(false);
                    Main.player.postGameStart(true);
                    Globals.mainCharacter.setMovement(true);
                    Globals.gameStart = true;

                    // add function for pushing ready
                    // check ready
                    // start match

                }
                countDownTime--;
            }
        }, 1000, 1000);

    }

    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.white);
        g.fillRect(175, 250,350,200);
        //pairing
        if (showPending == -1){
            g.setColor(Color.black);
            Font font = new Font("Arial", Font.BOLD, 19);
            g.setFont(font);
            g.drawString("Press space to start.", 260, 300);
            g.drawString("Use arrow keys to move.", 240, 330);
        }
        else if (showPending == 0) {
            g.setColor(Color.black);
            Font font = new Font("Arial", Font.BOLD, 19);
            g.setFont(font);
            g.drawString("We are pairing you", 260, 300);
            g.drawString("with another player", 260, 330);
            g.drawString("Pending ...", 310, 380);
        }
        //paired
        else if (showPending == 1){
            g.setColor(Color.black);
            Font font = new Font("Arial", Font.BOLD, 19);
            g.setFont(font);
            g.drawString("You have been paired", 248, 300);
            g.drawString("with another player", 260, 330);
            g.drawString("Objective: ", 265, 380);
            //runner chaser
            g.setColor(Color.red);
            g.drawString(Main.player.getSessionCharacter(), 365, 380);
            g.setColor(Color.black);
            g.drawString("Game starting in: " + Integer.toString(this.countDownTime), 260, 425);
        }
        //end
        else if (showPending == 2){
            g.setColor(Color.black);
            Font font = new Font("Arial", Font.BOLD, 35);
            g.setFont(font);
            g.drawString("GAME OVER!", 245, 300);
            if (true){
                Font font2 = new Font("Arial", Font.BOLD, 19);
                g.setFont(font2);
                g.drawString("Time: " + Integer.toString(Globals.score), 315, 350);

            }
        }

    }

    public void keyTyped(KeyEvent e) { }

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Globals.page.setPage(0);
            Main.player = new Player();
            Thread playerThread = new Thread(Main.player);
            playerThread.start();
        }

    }

    public void keyReleased(KeyEvent e) {

    }
}
