package com.java.user.parkd;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by kkuczkowski on 13/04/2017.
 */

public class FirebasePost extends AppCompatActivity {


        public void run(final TextView m_MessageView) {

            // Creating reference to root of the json tree in firebase
            DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference m_ConditionRef = mRootRef.child("type");

            m_ConditionRef.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String text = dataSnapshot.getValue(String.class);
                    m_MessageView.setText(text);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
}
