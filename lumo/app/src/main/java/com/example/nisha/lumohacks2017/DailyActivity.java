package com.example.nisha.lumohacks2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DailyActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private Button logoutBtn;
    private Button submitBtn;
    private EditText editTextHoursSlept;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        logoutBtn = (Button) findViewById(R.id.logoutBtn);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        editTextHoursSlept = (EditText) findViewById(R.id.editTextHoursSlept);

        logoutBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v == logoutBtn) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        if(v == submitBtn) {
            submitUserInfo();
        }

    }

    private void submitUserInfo() {

        String hoursOfSleep = editTextHoursSlept.getText().toString().trim();

        UserInformation userInformation = new UserInformation(Integer.parseInt(hoursOfSleep));

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference.child(firebaseUser.getUid()).setValue(userInformation);

        Toast.makeText(this, "Information Saved", Toast.LENGTH_SHORT).show();

    }
}
