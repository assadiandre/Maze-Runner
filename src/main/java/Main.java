import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;


class Globals {
    static DataGather data;
    static Character mainCharacter;
    static Opponent mainOpponent;
    static BlockMap allBlocks;
    static int gameSpeed = 5;
    static JPanel panel;
    static Page page;
    static int score;
    static boolean gameStart = false;
}

public class Main {
    //1. Boundary +
    //2. Collision w/ walls and boundary +
    //3. Collision w/ opponent -> GAME OVER
    // SERVER CONTAINED CODE

    static FirebaseApp firebaseApp;
    static Player player;

    public static void initFirebase() throws IOException {

        FileInputStream serviceAccount = new FileInputStream("javagame-8c4cc-firebase-adminsdk-n3gwk-e1aa1c787f.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://javagame-8c4cc.firebaseio.com/")
                .build();

        firebaseApp = FirebaseApp.initializeApp(options);
    }


    public static void setPlayerCoords() {

        if (Main.player.getSessionCharacter().equals("Chaser")) {
            Globals.mainCharacter.setCoords(580, 580);
            Globals.mainOpponent.setCoords(100, 100);
        }
        else {
            Globals.mainCharacter.setCoords(100,100);
            Globals.mainOpponent.setCoords(580,580);
        }

        Globals.mainOpponent.show();
        Globals.mainCharacter.show();

    }


    public static void runGame() {

        if (!player.getSessionID().equals("")) {
            System.out.println("Pairing Success!");
            System.out.println("Session ID: " + player.getSessionID());
            System.out.println("Opponent ID: " + player.getOpponentID());
            System.out.println("Pulling Opponent Data...");
            setPlayerCoords();
            Main.player.postGameStart(false);

            // Make handshake connection for game.
            Main.player.getGameStart(new CallBack() {
                public void onComplete() {
                    Globals.page.setPage(1);
                    Globals.data = new DataGather(Main.player.getSessionID(), Main.player.getOpponentID());
                    Thread gameThread = new Thread(new Game());
                    gameThread.start();
                }
            });

        }




    }

    public static void main(String[] args) throws IOException {

        final JFrame frame = new JFrame();
        Globals.panel = new JPanel();
        Globals.panel.setLayout(null);
        Globals.panel.setBackground(Color.BLACK);
        initFirebase();



        Globals.mainCharacter = new Character();
        Globals.mainOpponent = new Opponent();
        Globals.allBlocks = new BlockMap();
        Globals.page = new Page();
//        Globals.page.setPage(-1);

        Globals.panel.add(Globals.page);
        Globals.panel.add(Globals.mainOpponent);
        Globals.panel.add(Globals.mainCharacter);
        Globals.panel.add(Globals.allBlocks );

        Thread gameThread = new Thread(new Game());
        gameThread.start();

        frame.add(Globals.panel);
        frame.setSize(new Dimension(700 ,720));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




    }



}
