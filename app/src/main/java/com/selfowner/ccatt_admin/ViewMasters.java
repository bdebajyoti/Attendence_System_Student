package com.selfowner.ccatt_admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.audiofx.EnvironmentalReverb;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


public class ViewMasters extends AppCompatActivity {
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> namelist;
    private List<Data_Upload_Helper_Class> uploadList;
    ListView listView;
    Spinner Stream,Course,Semester;
    Button LoadView;
    private DatabaseReference mDatabaseReference;
    //ArrayAdapter<String> adapter;
    private String st="",cr="",sem="";
    private String Coll="";
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_masters);
        setTitle("View Subjects");
        Intent intent = getIntent();
        Coll = intent.getStringExtra("CollegeName");
        Toast.makeText(ViewMasters.this, ""+Coll, Toast.LENGTH_SHORT).show();
        progressBar=findViewById(R.id.progressBarNotes);
        listView=findViewById(R.id.ListView);
        Stream=findViewById(R.id.SelectStream);
        Course=findViewById(R.id.SelectCourse);
        Semester=findViewById(R.id.SelectSemester);
        LoadView=findViewById(R.id.LoadReport);

        LoadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 ViewData();
            }
        });
    }
    @Override
    public void onBackPressed() {
       startActivity(new Intent(ViewMasters.this,Main2Activity.class));
       finish();
    }

    private void ViewData(){
        namelist=new ArrayList<>();
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, namelist);
        listView.setAdapter(adapter);
        st=Stream.getSelectedItem().toString();
        cr=Course.getSelectedItem().toString();
        sem=Semester.getSelectedItem().toString();
        Toast.makeText(this, ""+sem+" "+st+" "+cr,  Toast.LENGTH_SHORT).show();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(""+Coll).child(""+st).child(""+cr).child(""+sem);
        listener = mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    namelist.add(item.child("subject").getValue().toString());
                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        /*mDatabaseReference = FirebaseDatabase.getInstance().getReference(""+Coll).child(""+st).child(""+cr).child(""+sem);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Data_Upload_Helper_Class upload = postSnapshot.getValue(Data_Upload_Helper_Class.class);

                    uploadList.add(upload);
                }

                String[] uploads = new String[uploadList.size()];

                for (int i = 0; i < uploads.length; i++) {
                   uploads[i] = uploadList.get(i).getSubject();
                }

                //displaying it to list
                //adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, uploads);
               // listView.setAdapter(adapter);

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });*/

    }
}
