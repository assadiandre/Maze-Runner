import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.Map;


class data {
    public static Map<String, DataSnapshot> opponentVals = new HashMap<String, DataSnapshot>();

    public static int[][] coordinates = new int[1][2];
    public static int[][] velocities = new int[1][2];
}

public class DataGather implements GameLoop {

    private String sessionID;
    private String opponentID;
    private Boolean ready = false;

    public DataGather(String sessionID, String opponentID) {
        this.sessionID = sessionID;
        this.opponentID = opponentID;
        this.pullPlayData();
    }


    public Boolean isReady() {
        return ready;
    }

    public void pullPlayData()  {

        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance(Main.firebaseApp);
        defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = defaultDatabase.getReference("/Sessions" + "/" + sessionID + "/" + opponentID );

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override public void onDataChange(DataSnapshot snapshot) {


                        //data.opponentVals.put(child.getKey(), child );

                    data.coordinates[0][0] = Integer.parseInt(snapshot.child("x").getValue().toString());
                    data.coordinates[0][1] = Integer.parseInt(snapshot.child("y").getValue().toString());



                      //System.out.println( data.allData[0][0] ) ;
//                    System.out.println( "Character: " + data.opponentVals.get("character").getValue());
//                    System.out.println( "xVel: " + data.opponentVals.get("xVel").getValue());
//                    System.out.println( "yVel: " + data.opponentVals.get("yVel").getValue());

                    ready = true;

            }

            @Override public void onCancelled(DatabaseError error) {
                System.out.println(error);
            }
        });
    }

    @Override
    public void update() {
        pullPlayData();
    }
}
