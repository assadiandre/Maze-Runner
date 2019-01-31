import com.google.firebase.database.*;

import javax.swing.*;

public class CoinMap extends JComponent {


    private FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance(Main.firebaseApp);

    public CoinMap() {

       this.setSize(700,720);
       this.defaultDatabase = FirebaseDatabase.getInstance();

    }


    public void updateCoinSpawn(CallBack event) {


        DatabaseReference ref = defaultDatabase.getReference("Users" );


        ref.addChildEventListener(new ChildEventListener() {
            int allDataCount = 0;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            public void onCancelled(DatabaseError databaseError) { }
        });
    }





}
