package com.example.nisha.lumohacks2017;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.nisha.lumohacks2017.R.id.editTextEmail;
import static com.example.nisha.lumohacks2017.R.id.editTextEmailSignin;
import static com.example.nisha.lumohacks2017.R.id.editTextPassword;
import static com.example.nisha.lumohacks2017.R.id.textviewSignin;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // create view objects
    private Button signinButton;
    private EditText editTextEmailSignin;
    private EditText editTextPasswordSignin;
    private TextView textviewRegister;

    // progress bar
    private ProgressDialog progressDialog;

    // set up firebase auth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize progress bar
        progressDialog = new ProgressDialog(this);

        // initialize firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, LandingMenu.class));
        }

        // initialize buttons and text views
        signinButton = (Button) findViewById(R.id.signinButton);
        editTextEmailSignin = (EditText) findViewById(R.id.editTextEmailSignin);
        editTextPasswordSignin = (EditText) findViewById(R.id.editTextPasswordSignin);

        textviewRegister = (TextView) findViewById(R.id.textviewRegister);
        textviewRegister.setOnClickListener(this);

        signinButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if(v == signinButton) {
            userLogin();
        }

        if(v == textviewRegister) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

    }

    private void userLogin() {
        String email = editTextEmailSignin.getText().toString().trim();
        String password = editTextPasswordSignin.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            // email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            // stopping the function execution further
            return;
        }

        if(TextUtils.isEmpty(password)) {
            // password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            // stopping the function execution further
            return;
        }

        if(!email.contains("@")) {
            // email is not valid
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            // stopping the function execution further
            return;
        }

        // if validations are ok
        progressDialog.setMessage("Signing in...");
        progressDialog.show();

        // check if firebase has signin info
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), LandingMenu.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Login unsuccessful. Please try again or register.", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }
}
