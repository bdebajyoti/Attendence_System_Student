package com.selfowner.ccatt_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner;
    private EditText editText;
    private Button ButtonMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Country_Data.countryNames));

        editText = findViewById(R.id.editTextPhone);

        ButtonMove = findViewById(R.id.ButtonMove);
        ButtonMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = Country_Data.countryAreaCodes[spinner.getSelectedItemPosition()];

                String number = editText.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }

                String phoneNumber = code + number;
                Intent intent = new Intent(MainActivity.this, College_Info.class);
                intent.putExtra("phonenumber", phoneNumber);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            Intent intent = new Intent(this, Main2Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
    }
    }