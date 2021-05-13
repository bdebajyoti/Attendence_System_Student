package com.selfowner.ccatt_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class Student_List extends AppCompatActivity {
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> namelist;
    Spinner SelectCourse,SelectStream,SelectSemester;
    TextView student_count;
    Button  LoadReport;
    ListView listView;
    private DatabaseReference mDatabaseReference;
    private ProgressBar progressBar;
    int count=0;
    int flag;
    String stream="",ctype="",semester="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__list);
        setTitle("Student List");
        listView=findViewById(R.id.ListView);
        SelectCourse=findViewById(R.id.SelectCourse);
        SelectStream=findViewById(R.id.SelectStream);
        SelectSemester=findViewById(R.id.SelectSemester);
        student_count=findViewById(R.id.student_count);
        LoadReport=findViewById(R.id.LoadReport);
        LoadReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //namelist.clear();
                LOADDATA();
            }
        });
    }
    private void LOADDATA(){
        flag=1;
        count=0;
        namelist=new ArrayList<>();
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, namelist);
        listView.setAdapter(adapter);
        stream=SelectStream.getSelectedItem().toString();
        ctype=SelectCourse.getSelectedItem().toString();
        semester=SelectSemester.getSelectedItem().toString();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("STUDENT");
        listener = mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    if(!semester.equals("SELECT SEMESTER")) {
                        if (item.child("stream").getValue().toString().equals(stream) && item.child("courseType").getValue().toString().equals(ctype) && item.child("semester").getValue().toString().equals(semester)) {

                            namelist.add(item.child("rollNumber").getValue().toString() + "-" + item.child("name").getValue().toString());
                            count++;
                        }
                    }
                    if(semester.equals("SELECT SEMESTER")){
                        if (item.child("stream").getValue().toString().equals(stream) && item.child("courseType").getValue().toString().equals(ctype)) {

                            namelist.add(item.child("rollNumber").getValue().toString() + "-" + item.child("name").getValue().toString());
                            count++;
                        }
                    }

                }


                student_count.setText("No Of Students Are : "+count);
                adapter.notifyDataSetChanged();
                //progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
}
