package com.example.attendanceassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class OTPverify extends AppCompatActivity {
    private EditText otpbox;
    private Button verify;
    private EditText phno_login;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    String verificationid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverify);
        mAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        otpbox=(EditText)findViewById(R.id.verificationcode);
        verify=(Button)findViewById(R.id.verify_otp);
        phno_login=(EditText)findViewById(R.id.phoneno_login);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);

        String phonenumber= getIntent().getStringExtra("phone");
        sendVerification(phonenumber);



        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=otpbox.getText().toString().trim();
                progressBar.setVisibility(View.VISIBLE);
                verify.setVisibility(View.INVISIBLE);
                if(code.isEmpty()){
                    otpbox.setError("Invalid code ...");
                    otpbox.requestFocus();
                    return;
                }
                VerifyCode(code);

            }
        });
    }
    private void VerifyCode(String code)
    {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationid,code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            DocumentReference docref=fstore.collection("users").document(mAuth.getCurrentUser().getUid());
                            docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        startActivity(new Intent(OTPverify.this,HomeActivity.class));
//                                        Toast.makeText(OTPverify.this, "satisfied", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else{
                                        startActivity(new Intent(OTPverify.this,signup.class));
                                        finish();
                                    }

                                }
                            });

                        }else{
                            Toast.makeText(OTPverify.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }



    private void sendVerification(String number){
        verify.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number,60,TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,mCallBack);

    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationid=s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode();
            Toast.makeText(OTPverify.this, "VerificationCompleted", Toast.LENGTH_SHORT).show();
            if(code!=null)
            {
                otpbox.setText(code);
                progressBar.setVisibility(View.VISIBLE);
                VerifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OTPverify.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }

    };


}
