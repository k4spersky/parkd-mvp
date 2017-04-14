package com.java.user.parkd;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is to be used in the future, only to PUT JSON object entries with push() into our
 * Firebase db. Otherwise disable in {@link Fragment1Activity}. 
 */
public class FirebasePost extends AppCompatActivity {

        public void run(final TextView m_MessageView) {


//            DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference("car_parks");
//            Map<String, Object> sendData = new HashMap<>();
//            sendData.put("title", "");
//
//            mRootRef.push().setValue(sendData);


            // Creating reference to root of the json tree in firebase
//            DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
//            DatabaseReference m_ConditionRef = mRootRef.child("type");
//
//            m_ConditionRef.addValueEventListener(new ValueEventListener() {
//
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    String text = dataSnapshot.getValue(String.class);
//                    m_MessageView.setText(text);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    // TODO Auto-generated method stub
//                }
//            });
        }
}
