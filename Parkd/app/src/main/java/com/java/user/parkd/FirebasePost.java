package com.java.user.parkd;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by kkuczkowski on 13/04/2017.
 */

public class FirebasePost extends AppCompatActivity {

        public void run(DatabaseReference m_ConditionRef, final Button m_MessageButton) {
//            m_ConditionRef.addValueEventListener(new ValueEventListener() {
//
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    String text = dataSnapshot.getValue(String.class);
//                    m_MessageButton.setText(text);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
        }
}
