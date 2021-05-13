package com.selfowner.ccatt_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Main3Activity extends AppCompatActivity {
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> namelist;
    Spinner SelectSemester,SelectStream;
    Button LoadReport;
    ListView listView;
    private DatabaseReference mDatabaseReference;
    int flag;
    String stream="",semester="";
    String s1,s2,s3,s4,s5,s6;
    String a1="0",a2="0",a3="0",a4="0",a5="0",a6="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        setTitle("View Student Attendance");
        listView=findViewById(R.id.ListView);
        SelectSemester=findViewById(R.id.SelectSemester);
        SelectStream=findViewById(R.id.SelectStream);
        LoadReport=findViewById(R.id.LoadReport);
        LoadReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOADDATA();
            }
        });
    }
    private void LOADDATA(){
        flag=1;
        //HashMap<String,String> data=new HashMap<>();
        namelist=new ArrayList<>();
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, namelist);
        listView.setAdapter(adapter);
        stream=SelectStream.getSelectedItem().toString();
        semester=SelectSemester.getSelectedItem().toString();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("STUDENT");
        listener = mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    if(item.child("stream").getValue().toString().equals(stream) && item.child("semester").getValue().toString().equals(semester)) {
                        s1=item.child("sub1").getValue().toString();
                        s2=item.child("sub2").getValue().toString();
                        s3=item.child("sub3").getValue().toString();
                        s4=item.child("sub4").getValue().toString();
                        s5=item.child("sub5").getValue().toString();
                        s6=item.child("sub6").getValue().toString();
                        if(!TextUtils.isEmpty(s1))
                        {
                            a1=item.child(s1).getValue().toString();
                        }
                        if(!TextUtils.isEmpty(s2))
                        {
                            a2=item.child(s2).getValue().toString();
                        }
                        if(!TextUtils.isEmpty(s3))
                        {
                            a3=item.child(s3).getValue().toString();
                        }
                        if(!TextUtils.isEmpty(s4))
                        {
                            a4=item.child(s4).getValue().toString();
                        }
                        if(!TextUtils.isEmpty(s5))
                        {
                            a5=item.child(s5).getValue().toString();
                        }
                        if(!TextUtils.isEmpty(s6))
                        {
                            a6=item.child(s6).getValue().toString();
                        }
                        namelist.add("Registration Number:"+item.child("regNumber").getValue().toString() + "\nName:" + item.child("name").getValue().toString()+"\n"+s1+":"+a1+"\n"+s2+":"+a2+"\n"+s3+":"+a3+"\n"+s4+":"+a4+"\n"+s5+":"+a5+"\n"+s6+" :"+a6);
                        a1="0";
                        a2="0";
                        a3="0";
                        a4="0";
                        a5="0";
                        a6="0";
                    }

                }
                adapter.notifyDataSetChanged();
                //progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //progressBar.setVisibility(View.INVISIBLE);
            }
        });
        if(flag==1)
        {
            Toast.makeText(Main3Activity.this, "There Are No Record Of Students", Toast.LENGTH_LONG).show();
        }
    }
}
