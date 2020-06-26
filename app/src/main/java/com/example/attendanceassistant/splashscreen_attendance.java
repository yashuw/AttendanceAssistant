package com.example.attendanceassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class splashscreen_attendance extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=500 ;
    private FirebaseFirestore fstore;
    private FirebaseAuth mAuth;
    Intent splashIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen_attendance);
        fstore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                splashIntent =new Intent(splashscreen_attendance.this,MainActivity.class);
//                startActivity(splashIntent);
//                finish();
                if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                {
                    DocumentReference docref=fstore.collection("users").document(mAuth.getCurrentUser().getUid());
                    docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists()){
                                startActivity(new Intent(splashscreen_attendance.this,HomeActivity.class));
                                finish();
//                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                            }else{
                                startActivity(new Intent(splashscreen_attendance.this,signup.class));
                                finish();
                            }

                        }
                    });
                }
                else{
                    startActivity(splashIntent);
                    finish();
                }

            }
        },SPLASH_TIME_OUT);
    }
}
