package com.example.attendanceassistant;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewFragment extends Fragment {
    FloatingActionButton fab1;
    FirebaseAuth mAuth;
    DatabaseReference ref;
    String userId;
    ArrayList<SubjectData>subjectdata;
    int sum_atten=0,sum_conduct=0;


    public ViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_view, container, false);

        subjectdata=new ArrayList<SubjectData>();

        mAuth=FirebaseAuth.getInstance();
        userId=mAuth.getCurrentUser().getUid();
        ref= FirebaseDatabase.getInstance().getReference().child(userId);
        final TableLayout l1=(TableLayout)v.findViewById(R.id.displayLinear);
        TableRow tr1=new TableRow(getActivity());
        tr1.setPadding(10,10,10,10);
        tr1.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView subject1=new TextView(getActivity());
        subject1.setText("SUBJECT");
        subject1.setBackgroundResource(R.drawable.cell_shape);
        subject1.setTextSize(16);
        subject1.setWidth(230);
        subject1.setTypeface(Typeface.DEFAULT_BOLD);
        subject1.setPadding(5,5,5,5);
        tr1.addView(subject1);

        TextView attended1=new TextView(getActivity());
        attended1.setText("ATTENDED");
        attended1.setTextSize(16);
        attended1.setTypeface(Typeface.DEFAULT_BOLD);
        attended1.setBackgroundResource(R.drawable.cell_shape);
        attended1.setPadding(5,5,5,5);
        tr1.addView(attended1);

        TextView conducted1=new TextView(getActivity());
        conducted1.setText("CONDUCTED");
        conducted1.setTextSize(16);
        conducted1.setTypeface(Typeface.DEFAULT_BOLD);
        conducted1.setBackgroundResource(R.drawable.cell_shape);
        conducted1.setPadding(5,5,5,5);
        tr1.addView(conducted1);

        TextView percent1=new TextView(getActivity());
        percent1.setText("PERCENT(%)");
        percent1.setTextSize(16);
        percent1.setTypeface(Typeface.DEFAULT_BOLD);
        percent1.setBackgroundResource(R.drawable.cell_shape);
        percent1.setPadding(5,5,5,5);
        tr1.addView(percent1);
        l1.addView(tr1);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Toast.makeText(getActivity(), "count : "+dataSnapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Subject subdata=dataSnapshot1.getValue(Subject.class);
                    //subjectdata.add(subdata);
//                    Toast.makeText(getActivity(), "value : "+subdata.getSubname(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "value : "+subdata.getAttended(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "value : "+subdata.getConducted(), Toast.LENGTH_SHORT).show();
                    sum_atten=sum_atten+subdata.getAttended();
                    sum_conduct=sum_conduct+subdata.getConducted();
                    float atten=subdata.getAttended();
                    float conduct=subdata.getConducted();
                    float percent=0;
                    if(conduct!=0)
                    {
                        percent=(atten/conduct)*100;

                    }

                    TableRow tr=new TableRow(getActivity());
                    tr.setPadding(10,10,10,10);
                    tr.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                    TextView subject=new TextView(getActivity());
                    subject.setText(subdata.getSubname().toUpperCase());
                    subject.setBackgroundResource(R.drawable.cell_shape);
                    subject.setTextSize(16);
                    subject.setWidth(230);
                    subject.setTypeface(Typeface.DEFAULT_BOLD);
                    subject.setPadding(5,5,5,5);
                    tr.addView(subject);

                    TextView attended=new TextView(getActivity());
                    String att=Integer.toString(subdata.getAttended());
                    attended.setText(att);
                    attended.setTextSize(16);
                    attended.setBackgroundResource(R.drawable.cell_shape);
                    attended.setPadding(5,5,5,5);
                    tr.addView(attended);

                    TextView conducted=new TextView(getActivity());
                    String con=Integer.toString(subdata.getConducted());
                    conducted.setText(con);
                    conducted.setTextSize(16);
                    conducted.setBackgroundResource(R.drawable.cell_shape);
                    conducted.setPadding(5,5,5,5);
                    tr.addView(conducted);


                    TextView percentage=new TextView(getActivity());
                    String per=Float.toString(percent);
                    percentage.setText(per);
                    percentage.setTextSize(16);
                    percentage.setBackgroundResource(R.drawable.cell_shape);
                    percentage.setPadding(5,5,5,5);
                    tr.addView(percentage);

                    l1.addView(tr);
               }
                TableRow tr2=new TableRow(getActivity());
                tr2.setPadding(10,10,10,10);
                tr2.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                TextView subject=new TextView(getActivity());
                subject.setText("TOTAL");
                subject.setBackgroundResource(R.drawable.cell_shape);
                subject.setTextSize(16);
                subject.setWidth(230);
                subject.setTypeface(Typeface.DEFAULT_BOLD);
                subject.setPadding(5,5,5,5);
                tr2.addView(subject);

                TextView attended=new TextView(getActivity());
                attended.setText(Integer.toString(sum_atten));
                attended.setTextSize(16);
                attended.setBackgroundResource(R.drawable.cell_shape);
                attended.setPadding(5,5,5,5);
                tr2.addView(attended);

                TextView conducted=new TextView(getActivity());
                conducted.setText(Integer.toString(sum_conduct));
                conducted.setTextSize(16);
                conducted.setBackgroundResource(R.drawable.cell_shape);
                conducted.setPadding(5,5,5,5);
                tr2.addView(conducted);

                float sum_atten1=(float)sum_atten;
                float sum_conduct1=(float)sum_conduct;
                float sum_percent=(sum_atten1/sum_conduct1)*100;
                String sum_per=Float.toString(sum_percent);


                TextView percentage=new TextView(getActivity());
                percentage.setText(sum_per);
                percentage.setTextSize(18);
                percentage.setTypeface(Typeface.DEFAULT_BOLD);
                percentage.setBackgroundResource(R.drawable.cell_shape);
                percentage.setPadding(5,5,5,5);
                tr2.addView(percentage);

                l1.addView(tr2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error Ocurred "+databaseError, Toast.LENGTH_SHORT).show();

            }
        });





        return v;
    }


}
