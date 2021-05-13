package com.selfowner.ccatt_admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class College_Info extends AppCompatActivity {
    EditText CollegeName,Address;
    Uri imageUri;
    private ImageView ImageView;
    TextView ChoosePic;
    Button  SaveButton;
    FirebaseStorage storage;
    StorageReference storageRef;
    DatabaseReference databaseStudent;
    private String filelocation="";
    String mobile="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college__info);
         Intent intent = getIntent();
         mobile = intent.getStringExtra("phonenumber");
        CollegeName=findViewById(R.id.CollegeName);
        Address=findViewById(R.id.CollegeAddress);
        ImageView=findViewById(R.id.ImageView);
        ChoosePic=findViewById(R.id.ChoosePic);
        SaveButton=findViewById(R.id.SaveButton);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        ChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadImage();
            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadRecord();
            }
        });
    }
    private void LoadImage(){
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ImageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private void UploadRecord(){
        if (imageUri != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference riversRef = storageRef.child("COLLEGE/"+CollegeName.getText().toString());
            riversRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                            while(!uri.isComplete());
                            Uri url=uri.getResult();
                            filelocation=url.toString();
                            //fileLocation=taskSnapshot.getUploadSessionUri().toString();
                            progressDialog.dismiss();

                            //and displaying a success toast
                           AddRecord();
                            Toast.makeText(getApplicationContext(), "Record Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }
    private void AddRecord(){
        if(imageUri!=null) {
            String a = CollegeName.getText().toString();
            String b = Address.getText().toString();

            if (!a.isEmpty() || !b.isEmpty()) {
                //String sid=databaseStudent.push().getKey(""+email);
                databaseStudent= FirebaseDatabase.getInstance().getReference(""+mobile).child("INFO");
                College_Upload_Helper_Class uinfo = new College_Upload_Helper_Class(a, b,mobile,filelocation);
                databaseStudent.setValue(uinfo);
                Toast.makeText(this, "Congratulation..Data Added...!", Toast.LENGTH_LONG).show();
                this.finish();
                Intent intent = new Intent(College_Info.this, Verify_Page.class);
                intent.putExtra("phonenumber", mobile);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Please The Form Correctly", Toast.LENGTH_LONG).show();
                CollegeName.setText("");
                Address.setText("");
                CollegeName.requestFocus();
                return;
            }
        }

    }
}
