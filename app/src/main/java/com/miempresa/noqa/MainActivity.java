package com.miempresa.noqa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnMenu, btnCrear;
    private EditText txtEmail;
    private TextInputLayout txtPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        mAuth = FirebaseAuth.getInstance();
        btnMenu = (Button) findViewById(R.id.btnMain);
        btnCrear = findViewById(R.id.btnCrear);

        btnMenu.setOnClickListener(view-> {
//            userLogin();
            startActivity(new Intent(MainActivity.this, Menu.class));
        });

        btnCrear.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, Register.class));
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)){

            }else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){

            }else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            return;
        }
    }

    public void userLogin(){
        String mail = txtEmail.getText().toString();
        String password = txtPassword.getEditText().getText().toString();

        if (TextUtils.isEmpty(mail)){
            txtEmail.setError("Ingrese un correo");
            txtEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(MainActivity.this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
            txtPassword.requestFocus();
        }else{

            mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Bienvenid@", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, Menu.class));
                    }else {
                        Log.w("TAG", "Error:", task.getException());
                    }
                }
            });
        }
    }
}