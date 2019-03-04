package com.milan.brtshelper;

import android.arch.core.executor.TaskExecutor;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressBar pbar;
    private EditText codeEntered;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private  String codeRecieved;
    private  Button verifyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        verifyButton = findViewById(R.id.verifybutton);
        codeEntered = findViewById(R.id.verifyphonetext);
        final String codeTyped  = codeEntered.getText().toString().trim();

        codeRecieved = getIntent().getStringExtra("codeSent");
        Log.d("xxxxxxx",codeRecieved);
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifycode(codeTyped,codeRecieved);
            }
        });
    }
    private void verifycode(String codeTyped , String codeRecieved) {
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeRecieved, codeTyped);
            Log.d("VerifyCode22", credential.toString());
            signInWithPhone(credential);
        }catch (Exception e){
            Toast.makeText(VerifyPhoneActivity.this,e.toString(),Toast.LENGTH_LONG);
        }
    }
    private void signInWithPhone(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                                Toast.makeText(VerifyPhoneActivity.this,"Success", Toast.LENGTH_LONG);
//                            Intent i = new Intent(VerifyPhoneActivity.this , SignupActivity.class);
//                            Log.d("Login_Success", "signInWithCredential:success");
//
//                            startActivity(i);

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("Verify_error", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerifyPhoneActivity.this,"Entered Code was Invalid....", Toast.LENGTH_LONG);
                            }
                        }
                    }
                });
    }

}