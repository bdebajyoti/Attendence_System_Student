package com.selfowner.ccatt_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class View_Developer extends AppCompatActivity {

    Button exitwindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__developer);
        setTitle("View Developer");
        exitwindow=findViewById(R.id.ExitWindow);
        exitwindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(View_Developer.this,Main2Activity.class));
            }
        });
    }

}
