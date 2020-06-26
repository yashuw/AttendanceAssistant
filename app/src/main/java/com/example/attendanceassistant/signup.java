package com.example.attendanceassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {
    private EditText F_name,L_name,email_id,phno;
    Button signup_activation;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        F_name=(EditText)findViewById(R.id.fname);
        L_name=(EditText)findViewById(R.id.lname);
        email_id=(EditText)findViewById(R.id.emailid);
        phno=(EditText)findViewById(R.id.phno);
        signup_activation=(Button)findViewById(R.id.signup_activating_button);


        mAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        userId=mAuth.getCurrentUser().getUid();


        final DocumentReference docref=fstore.collection("users").document(userId);


        signup_activation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    final String f_name=F_name.getText().toString();
                    final String l_name=L_name.getText().toString();
                    final String Email_id=email_id.getText().toString().trim();
                    final String ph_no=phno.getText().toString().trim();


                    if(ph_no.length()<10 || ph_no.length()>10)
                    {
                        phno.setError("Phone Number should be of length 10 ");
                        return ;

                    }
                    Map<String,Object> user=new HashMap<>();
                    user.put("First Name",f_name);
                    user.put("Last Name",l_name);
                    user.put("Email-id",Email_id);
                    user.put("Phone Number",ph_no);
                    docref.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(signup.this, "Profile updated Successful", Toast.LENGTH_LONG).show();
                                Intent intent =new Intent(signup.this,HomeActivity.class);
                                startActivity(intent);
                                finish();
                                //Toast.makeText(signup.this, "successful", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(signup.this, "All the fields are required ", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

            }
        });

    }
    Boolean validate()
    {
        Boolean res=false;
        String f_name=F_name.getText().toString();
        String l_name=L_name.getText().toString();
        String email=email_id.getText().toString();
        String contact=phno.getText().toString();
        if(f_name.isEmpty() && l_name.isEmpty() && email.isEmpty() && contact.isEmpty())
        {
            Toast.makeText(this,"Enter all the details",Toast.LENGTH_SHORT).show();

        }else{
            res=true;
        }
        return res;
    }

}

