package com.selfowner.ccatt_admin;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import static android.Manifest.permission.READ_PHONE_NUMBERS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;

public class Main2Activity extends AppCompatActivity {
    RadioButton Semester, Course, Subjects, Stream;
    TextView mobileNumber,AdminName;
    private DatabaseReference databaseReference;
    String ImageUri,CollegeName;
    private ImageView ImageView;
    String Sem ,Cour,Sub,Str;
    TextView OutputText;
    String mob;
    Button  AddDetails,ViewMaster,ViewDeveloper,ViewTeachers,ViewRecord,ViewStudents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setTitle("Attendance Administrator");
        mobileNumber = findViewById(R.id.MobilNumber);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            mob=FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
            mobileNumber.setText(""+FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
            mobileNumber.setText(""+mob);
            LoadImage();
            LoadAdminInfo();
        }
        ViewRecord=findViewById(R.id.ViewRecord);
        ViewRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this,Main3Activity.class));
            }
        });
        ViewStudents=findViewById(R.id.ViewStudents);
        ViewStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this,Student_List.class));
            }
        });
        AdminName=findViewById(R.id.AdminName);
        ViewDeveloper=findViewById(R.id.ViewDeveloper);
        ViewTeachers=findViewById(R.id.ViewTeachers);
        ViewMaster=findViewById(R.id.ViewDetails);
        AddDetails=findViewById(R.id.AddDetails);
        ImageView=findViewById(R.id.ImageView);
        Semester=findViewById(R.id.Semester);
        Course=findViewById(R.id.Course);
        Subjects=findViewById(R.id.Subjects);
        Stream=findViewById(R.id.Stream);
        ViewTeachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                startActivity(new Intent(Main2Activity.this,Teachers_List.class));
            }
        });
        ViewDeveloper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Main2Activity.this,View_Developer.class));
            }
        });
        Stream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Main2Activity.this);
                View mview=getLayoutInflater().inflate(R.layout.stream_page,null);
                builder.setTitle("STREAM MASTER");
                builder.setIcon(R.drawable.diploma);
                //builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                final Spinner semSpinner=mview.findViewById(R.id.StreamSpinner);
                ArrayAdapter<String> semAdapter=new ArrayAdapter<String>(Main2Activity.this, R.layout.support_simple_spinner_dropdown_item,
                        getResources().getStringArray(R.array.SETSTREAM));
                semSpinner.setAdapter(semAdapter);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Str=semSpinner.getSelectedItem().toString();

                        Toast.makeText(Main2Activity.this, "Selected Stream: "+Str, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setView(mview);
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        Subjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Main2Activity.this);
                View mview=getLayoutInflater().inflate(R.layout.subject_page,null);
                builder.setTitle("SUBJECT NAME/PAPER NAME");
                builder.setIcon(R.drawable.student);
                final EditText subject=mview.findViewById(R.id.SubjectEdit);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Sub=subject.getText().toString();

                        Toast.makeText(Main2Activity.this, "Subject Mentioned: "+Sub, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setView(mview);
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        Course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Main2Activity.this);
                View mview=getLayoutInflater().inflate(R.layout.course_page,null);
                builder.setTitle("COURSE MASTER");
                builder.setIcon(R.drawable.graduation_certificate);
                final Spinner semSpinner=mview.findViewById(R.id.CourseSpinner);
                ArrayAdapter<String> semAdapter=new ArrayAdapter<String>(Main2Activity.this, R.layout.support_simple_spinner_dropdown_item,
                        getResources().getStringArray(R.array.SETCOURSE));
                semSpinner.setAdapter(semAdapter);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Cour=semSpinner.getSelectedItem().toString();

                        Toast.makeText(Main2Activity.this, "Selected Course Type: "+Cour, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setView(mview);
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        Semester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Main2Activity.this);
                View mview=getLayoutInflater().inflate(R.layout.semester_page,null);
                builder.setTitle("SEMESTER MASTER");
                builder.setIcon(R.drawable.book);
                final Spinner semSpinner=mview.findViewById(R.id.SemesterSpinner);
                ArrayAdapter<String> semAdapter=new ArrayAdapter<String>(Main2Activity.this, R.layout.support_simple_spinner_dropdown_item,
                        getResources().getStringArray(R.array.SETSEMESTER));
                semSpinner.setAdapter(semAdapter);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Sem=semSpinner.getSelectedItem().toString();

                        Toast.makeText(Main2Activity.this, "Selected Semester: "+Sem, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                     }
                });
                builder.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setView(mview);
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });

        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
            }
        });

        AddDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(Sem) || TextUtils.isEmpty(Cour) || TextUtils.isEmpty(Sub))
                {
                    Toast.makeText(Main2Activity.this, "Please Select The Master Correctly", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(Sem.equals("SELECT SEMESTER") || Cour.equals("SELECT COURSE TYPE") || Str.equals("SELECT STREAM"))
                {
                    Toast.makeText(Main2Activity.this, "Invalid Selection", Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    //OutputText.setText("Semester: "+Sem+",Stream: "+Str+",CourseType: "+Cour+",Subject: "+Sub);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                    builder.setTitle("Confirm");
                    builder.setMessage("Semester: "+Sem+",Stream: "+Str+",CourseType: "+Cour+",Subject: "+Sub+"\nLet Confirm The Choice You Made?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            AddSemester();

                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            startActivity(new Intent(Main2Activity.this,Main2Activity.class));
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                }
            }
        });

        ViewMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main2Activity.this,ViewMasters.class);
                intent.putExtra("CollegeName", CollegeName);
                startActivity( intent);
                finish();
            }
        });
    }
   private void LoadImage(){
        databaseReference= FirebaseDatabase.getInstance().getReference().child(mobileNumber.getText().toString());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("INFO")) {
                    CollegeName=dataSnapshot.child("INFO").child("collegeName").getValue().toString();
                    ImageUri=dataSnapshot.child("INFO").child("imageUri").getValue().toString();
                   loadImage();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void loadImage(){
        String x=""+ImageUri;
        Glide.with(this)
                .load(x)
                .into(ImageView);
    }
    private void AddSemester(){
        //databaseReference= FirebaseDatabase.getInstance().getReference().child(""+CollegeName).child(Sem).child(Str).child(Cour);
        databaseReference= FirebaseDatabase.getInstance().getReference().child(""+AdminName.getText().toString()).child(Str).child(Cour).child(Sem).child(Sub);
        Data_Upload_Helper_Class uinfo = new Data_Upload_Helper_Class(Sem,Sub,Str,Cour);
        databaseReference.setValue(uinfo);
        Toast.makeText(this, "Congratulation..Data Added...!", Toast.LENGTH_LONG).show();
        //OutputText.setText("");
    }

    private void LoadAdminInfo(){
        databaseReference= FirebaseDatabase.getInstance().getReference().child(mobileNumber.getText().toString());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("INFO")) {
                    String value=dataSnapshot.child("INFO").child("collegeName").getValue().toString();
                    AdminName.setText(value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
