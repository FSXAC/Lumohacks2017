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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // create view objects
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textviewSignin;

    // progress bar
    private ProgressDialog progressDialog;

    // set up firebase auth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize progress bar
        progressDialog = new ProgressDialog(this);

        // initialize firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this,LandingMenu.class));
        }

        // initialize buttons and text views
        buttonRegister = (Button) findViewById(R.id.registerButton);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        textviewSignin = (TextView) findViewById(R.id.textviewSignin);
        textviewSignin.setOnClickListener(this);

        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == buttonRegister) {
            registerUser();
        }

        if (v == textviewSignin) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

    }

    private void registerUser() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

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
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Registered Successfully.", Toast.LENGTH_SHORT).show();;
                } else {
                    // display user registered msg
                    Toast.makeText(MainActivity.this, "Could not register. Please try again.", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });

    }
}
