package com.selfowner.ccatt_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Teachers_List extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Teacher_Adapter mAdapter;
    private DatabaseReference mDatabaseReference;
    private List<Teacher_Helper_Class> mUploads;
    private ProgressBar teacherPBAR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers__list);
        setTitle("Registered Teacher List");
        teacherPBAR=findViewById(R.id.teacherPBAR);
        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUploads=new ArrayList<>();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("TEACHERS");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Teacher_Helper_Class teacherAddHelperClass=postSnapshot.getValue(Teacher_Helper_Class.class);
                    mUploads.add(teacherAddHelperClass);
                }
                mAdapter=new Teacher_Adapter(getApplicationContext(),mUploads);
                mRecyclerView.setAdapter(mAdapter);
                teacherPBAR.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Teachers_List.this,"Error In Database Loading...!",Toast.LENGTH_LONG).show();
                teacherPBAR.setVisibility(View.INVISIBLE);
            }
        });

    }
}