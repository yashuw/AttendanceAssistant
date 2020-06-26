package com.example.attendanceassistant;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment {
    private EditText edit_first_name,edit_last_name,edit_email_id,edit_contact_number;
    private Button edit_save_btn;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userId;


    public EditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View V= inflater.inflate(R.layout.fragment_edit, container, false);
        edit_first_name=(EditText)V.findViewById(R.id.edit_First_Name);
        edit_last_name=(EditText)V.findViewById(R.id.edit_Last_Name);
        edit_contact_number=(EditText)V.findViewById(R.id.edit_Contact_Number);
        edit_email_id=(EditText)V.findViewById(R.id.edit_Email_Id);
        edit_save_btn=(Button)V.findViewById(R.id.edit_save_btn);

        mAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        userId=mAuth.getCurrentUser().getUid();

        final DocumentReference documentReference=fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                edit_first_name.setText(documentSnapshot.getString("First Name"));
                edit_last_name.setText(documentSnapshot.getString("Last Name"));
                edit_email_id.setText(documentSnapshot.getString("Email-id"));
                edit_contact_number.setText(documentSnapshot.getString("Phone Number"));

            }
        });

        edit_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(validate())
                {
                    final String f_name=edit_first_name.getText().toString();
                    final String l_name=edit_last_name.getText().toString();
                    final String Email_id=edit_email_id.getText().toString().trim();
                    final String ph_no=edit_contact_number.getText().toString().trim();


                    if(ph_no.length()<10 || ph_no.length()>10)
                    {
                        edit_contact_number.setError("Phone Number should be of length 10 ");
                        return ;

                    }
                    Map<String,Object> user=new HashMap<>();
                    user.put("First Name",f_name);
                    user.put("Last Name",l_name);
                    user.put("Email-id",Email_id);
                    user.put("Phone Number",ph_no);
                    documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getActivity(), "Profile updated Successful", Toast.LENGTH_LONG).show();
                                Navigation.findNavController(V).navigate(R.id.action_nav_edit_to_nav_home);
                                //Toast.makeText(signup.this, "successful", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getActivity(), "All the fields are required ", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            }
        });
        return  V;
    }
    Boolean validate()
    {
        Boolean res=false;
        String f_name=edit_first_name.getText().toString();
        String l_name=edit_last_name.getText().toString();
        String email=edit_email_id.getText().toString();
        String contact=edit_contact_number.getText().toString();
        if(f_name.isEmpty() && l_name.isEmpty() && email.isEmpty() && contact.isEmpty())
        {
            Toast.makeText(getActivity(),"Enter all the details",Toast.LENGTH_LONG).show();

        }else{
            res=true;
        }
        return res;
    }

}
