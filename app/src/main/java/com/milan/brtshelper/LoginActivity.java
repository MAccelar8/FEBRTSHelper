package com.milan.brtshelper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String codeSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText loginedit = (EditText)findViewById(R.id.loginphone);
        final Button loginbutton = findViewById(R.id.loginbutton);
        final Button verifybutton = findViewById(R.id.verifybutton);
        loginbutton.setEnabled(true);

        mAuth = FirebaseAuth.getInstance();


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phno = loginedit.getText().toString().trim();

                if (phno.isEmpty()){
                    loginedit.setError("Phone Number is required!!");
                    loginedit.requestFocus();

                }
                else if (phno.length()<10){
                    loginedit.setError("Please Enter a valid Phone Number");
                    loginedit.requestFocus();
                }
                else {
                        phno = "+91" + phno;
                        Log.d("PHno00",phno);
                        sendVerificationCode(phno);
                }

            }
        });


        verifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifycode();
            }
        });


    }

    private void verifycode() {
        EditText editTextVerify = findViewById(R.id.verifyphonetext);
        String verifycode = editTextVerify.getText().toString().trim();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent,verifycode);
        Log.d("VerifyCode22", credential.toString());
        signInWithPhone(credential);
    }

    private void signInWithPhone(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(LoginActivity.this,"Success", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(LoginActivity.this , NavigationActivity.class);
//                            Log.d("Login_Success", "signInWithCredential:success");
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("Verify_error", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(LoginActivity.this,"Entered Code was Invalid....", Toast.LENGTH_LONG);
                            }
                        }
                    }
                });

    }


    private void sendVerificationCode(String phoneNumber) {
        Log.d("Ver111","Inside Send Ver");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            Log.d("Ver222","Code Sent");
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
            Toast toast = Toast.makeText(LoginActivity.this, codeSent, Toast.LENGTH_LONG);
            toast.show();

            Log.d("qsssss",codeSent);
//            Intent i = new Intent(LoginActivity.this , VerifyPhoneActivity.class);
//            i.putExtra("codeSent", codeSent);
//            startActivity(i);

        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Log.d("Ver333","Ver Comp");
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.d("Ver444","Err");
        }
    };
}
