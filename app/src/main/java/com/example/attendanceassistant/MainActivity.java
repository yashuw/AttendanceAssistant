package com.example.attendanceassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private Button login;
    private EditText phonenumber_login;
    private FirebaseFirestore fstore;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=(Button)findViewById(R.id.login);
        phonenumber_login=(EditText) findViewById(R.id.phoneno_login);


        fstore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPhoneVerification();
            }
        });
    }
    void sendPhoneVerification()
    {
        String phone="+91"+phonenumber_login.getText().toString();

        if(phone.isEmpty())
        {
            phonenumber_login.setError("Phone Number is Required");
            phonenumber_login.requestFocus();
            return ;
        }

        if((phone.length()<13) || (phone.length()>13)){
            phonenumber_login.setError("Phone Number is Invalid");
            phonenumber_login.requestFocus();
            return ;
        }


       Intent intent=new Intent(MainActivity.this,OTPverify.class);
        intent.putExtra("phone",phone);
        startActivity(intent);

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
//        {
//            DocumentReference docref=fstore.collection("users").document(mAuth.getCurrentUser().getUid());
//            docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                @Override
//                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                    if(documentSnapshot.exists()){
//                        startActivity(new Intent(MainActivity.this,HomeActivity.class));
//                        finish();
////                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
//                    }else{
//                        startActivity(new Intent(MainActivity.this,signup.class));
//                        finish();
//                    }
//
//                }
//            });
//        }

    //}
}
