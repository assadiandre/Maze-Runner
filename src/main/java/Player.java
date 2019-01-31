import com.google.firebase.database.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


interface CallBack {

    public void onComplete();

}

public class Player implements Runnable  {

    private String Player_ID;
    private String Opponent_ID;
    private Boolean Pairing_Status;
    private String Session_ID;
    private String Session_Character;

    private FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance(Main.firebaseApp);

    public Player() {
        this.defaultDatabase = FirebaseDatabase.getInstance();
        this.Player_ID = "";
        this.Opponent_ID = "";
        this.Pairing_Status = false;
        this.Session_ID = "";
        this.Session_Character = "";
    }


    public String getPlayerID() { return Player_ID; }

    public String getOpponentID() { return Opponent_ID; }

    public Boolean getPairingStatus() { return Pairing_Status; }

    public String getSessionID() { return Session_ID; }

    public String getSessionCharacter() { return Session_Character; }

    public void postXvel(int xVel) {

        defaultDatabase.getReference("Sessions/" + Session_ID + "/" + Player_ID + "/xVel" ).setValue(xVel, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                // do nothing
            }
        });
    }

    public void postYvel(int yVel) {

        defaultDatabase.getReference("Sessions/" + Session_ID + "/" + Player_ID + "/yVel" ).setValue(yVel, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                //do nothing
            }
        });
    }

    public void postGameStatus(Boolean gameStatus) {


        defaultDatabase.getReference("Sessions/" + Session_ID + "/" + Player_ID + "/game_over" ).setValue(gameStatus, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                //do nothing
            }
        });
    }

    public void postGameStart(Boolean gameStatus) {

        defaultDatabase.getReference("Sessions/" + Session_ID + "/" + Player_ID + "/game_start" ).setValue(gameStatus, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                //do nothing
            }
        });

    }

    public void postScore(int score) {
        defaultDatabase.getReference("Sessions/" + Session_ID + "/score"  ).setValue(score, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                //do nothing
            }
        });
    }

    public void postXCoord(int x) {

        defaultDatabase.getReference("Sessions/" + Session_ID + "/" + Player_ID + "/x" ).setValue(x, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                //do nothing
            }
        });
    }

    public void postYCoord(int y) {

        defaultDatabase.getReference("Sessions/" + Session_ID + "/" + Player_ID + "/y" ).setValue(y, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                //do nothing
            }
        });
    }



    public void setup(String urlString, CallBack event) {
        try {
            URL url = new URL(urlString );
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                this.Player_ID = output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        event.onComplete();


    }


    public void getData(final CallBack event) {


        DatabaseReference ref = defaultDatabase.getReference("Users/" + Player_ID );


        ref.addChildEventListener(new ChildEventListener() {
            int allDataCount = 0;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if ( dataSnapshot.getKey().equals("Opponent_ID") ) {
                    Opponent_ID = (String) dataSnapshot.getValue();
                }
                else if ( dataSnapshot.getKey().equals("Pairing_Status") ) {
                    Pairing_Status = (Boolean) dataSnapshot.getValue();
                }
                else if ( dataSnapshot.getKey().equals("Session_ID") ) {
                    Session_ID = (String) dataSnapshot.getValue();
                }
                else if ( dataSnapshot.getKey().equals("Session_Character") ) {
                    Session_Character = (String) dataSnapshot.getValue();
                }
                allDataCount++;

                if (allDataCount % 4 == 0) {
                    event.onComplete();
                }

            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                if ( dataSnapshot.getKey().equals("Opponent_ID") ) {
                    Opponent_ID = (String) dataSnapshot.getValue();
                }
                else if ( dataSnapshot.getKey().equals("Pairing_Status") ) {
                    Pairing_Status = (Boolean) dataSnapshot.getValue();
                }
                else if ( dataSnapshot.getKey().equals("Session_ID") ) {
                    Session_ID = (String) dataSnapshot.getValue();
                }
                else if ( dataSnapshot.getKey().equals("Session_Character") ) {
                    Session_Character = (String) dataSnapshot.getValue();
                }
                allDataCount++;

                if (allDataCount % 4 == 0) {
                    Main.runGame();
                }

            }

            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            public void onCancelled(DatabaseError databaseError) { }
        });
    }


    public void getScore() {

        DatabaseReference ref = defaultDatabase.getReference("Sessions/" + Session_ID   );


        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //Globals.score = Integer.parseInt(dataSnapshot.child("score").getValue().toString());

                if ( dataSnapshot.getKey().equals("score") ) {
                    Globals.score =  Integer.parseInt(dataSnapshot.getValue().toString());
                }

            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            public void onCancelled(DatabaseError databaseError) { }
        });

    }


    public void getGameStatus(final CallBack event) {


        DatabaseReference ref = defaultDatabase.getReference("Sessions/" + Session_ID + "/" + Opponent_ID );


        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.getKey().equals("game_over")){
                    event.onComplete();
                }

            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    public void getGameStart(final CallBack event) {


        DatabaseReference ref = defaultDatabase.getReference("Sessions/" + Session_ID + "/" + Opponent_ID );


        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.getKey().equals("game_start")){
                    event.onComplete();
                }

            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            public void onCancelled(DatabaseError databaseError) { }
        });
    }


    @Override
    public void run() {

        //Main.player = new Player();
        Main.player.setup("https://us-central1-javagame-8c4cc.cloudfunctions.net/createPlayer", new CallBack() {
            @Override
            public void onComplete() {

                Main.player.getData(new CallBack() {
                    @Override
                    public void onComplete() {
                        System.out.println("Search Completed...");
                        Main.runGame();
                    }

                });

            }

        });


    }


}
