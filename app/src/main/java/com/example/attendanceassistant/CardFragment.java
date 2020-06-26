package com.example.attendanceassistant;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardFragment extends Fragment {
    private EditText edit_subject,edit_classes_attended,edit_classes_conducted;
    private Button create;
    Subject subject;
    Globalvariables var;
    FirebaseAuth mAuth;
    String userId;
    DatabaseReference reff;


    public CardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_card, container, false);
        edit_subject=(EditText)v.findViewById(R.id.card_edit_subject);
        edit_classes_attended=(EditText)v.findViewById(R.id.card_edit_classes_attended);
        edit_classes_conducted=(EditText)v.findViewById(R.id.card_edit_classes_conducted);
        create=(Button)v.findViewById(R.id.card_btn_create);

        subject=new Subject();
        var=new Globalvariables();
        mAuth= FirebaseAuth.getInstance();

        userId=mAuth.getCurrentUser().getUid();

        reff= FirebaseDatabase.getInstance().getReference().child(userId);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    String subname=edit_subject.getText().toString().trim();
                    int atten=0,conduct=0;
                    try
                    {
                        atten=Integer.parseInt(edit_classes_attended.getText().toString());
                        conduct=Integer.parseInt(edit_classes_conducted.getText().toString());
                    }
                    catch(NumberFormatException e)
                    {
                        Toast.makeText(getActivity(), "erro"+e, Toast.LENGTH_SHORT).show();
                    }
                    var.setSubname(subname);
                    subject.setAttended(atten);
                    subject.setConducted(conduct);
                    subject.setSubname(subname);

                    reff.child(subname).setValue(subject);
                    Bundle bundle=new Bundle();
                    bundle.putString("subname",subname);
                    Navigation.findNavController(v).navigate(R.id.action_nav_card_frag_to_nav_home,bundle);


                }

            }
        });
        return v;
    }
    boolean validate(){
        boolean res=false;
        String subject=edit_subject.getText().toString().trim();
        String attended=edit_classes_attended.getText().toString();
        String Conducted=edit_classes_conducted.getText().toString();
        if(subject.isEmpty() && attended.isEmpty() && Conducted.isEmpty()){
            Toast.makeText(getActivity(), "Enter All Details", Toast.LENGTH_LONG).show();
            return res;
        }
        else if(Integer.parseInt(attended)>Integer.parseInt(Conducted))
        {
            edit_classes_attended.setError("Attended can't be greater than conducted");
            return res;
        }

        return true;
    }
}
