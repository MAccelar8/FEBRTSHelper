package com.milan.brtshelper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileDisplayActivity extends AppCompatActivity {
    private String uid;
    TextView  ev;
    TextView phno;
    TextView email;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_display);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();




        if (user != null) {
            uid = user.getPhoneNumber().toString().trim();

        } else {
            Intent i =new Intent(ProfileDisplayActivity.this,LoginActivity.class);
            startActivity(i);
        }

        phno = (TextView) findViewById(R.id.profiledisplayphnotextview);
        phno.setText(uid);

        final DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getString("userfname"));
                        String str = document.getString("userfname");
                        str = str + " " + document.getString("userlname");
                        ev = (TextView) findViewById(R.id.profiledisplayname);
                        ev.setText(str);
                        email = (TextView) findViewById(R.id.profiledisplayemailtextview);

                        email.setText(document.getString("useremail"));

                    } else {
                        Log.d("switchcaseexp", "get failed with ", task.getException());
                    }
                }
            }
        });



        btn = (Button) findViewById(R.id.profiledisplayupdatebutton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ProfileDisplayActivity.this, ProfilePageActivity.class);
                startActivity(i);
            }
        });


        }

    }
