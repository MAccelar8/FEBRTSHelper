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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfilePageActivity extends AppCompatActivity {

    private String uid;
    private String pemail;
    private String pfname;
    private String plname;
    private Button btn;
    FirebaseUser user;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);






        btn = (Button) findViewById(R.id.profilepageconfirmbutton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                db = FirebaseFirestore.getInstance();


                uid = user.getPhoneNumber().toString().trim();

                EditText email = (EditText) findViewById(R.id.edittextprofileemail);
                pemail = email.getText().toString().trim();

                EditText fname = (EditText) findViewById(R.id.edittextprofilefname);
                pfname = fname.getText().toString().trim();

                EditText lname = (EditText) findViewById(R.id.edittextprofilelname);
                plname = lname.getText().toString().trim();



                Map<String, Object> temp = new HashMap<>();
                temp.put("useremail", pemail);
                temp.put("userfname", pfname);
                temp.put("userlname", plname);
                db.collection("users").document(uid).set(temp)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ProfilePageActivity.this, "Added to DB", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(ProfilePageActivity.this,NavigationActivity.class);
                                startActivity(i);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("onDBaddFail","Error Occured",e);
                            }
                        });

            }
        });

    }
}
