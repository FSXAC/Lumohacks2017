package com.example.nisha.lumohacks2017;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Questions extends AppCompatActivity {

    int MAX = 8;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    final String[] comments = new String[1]; //Additional Comments
    final Calendar c = Calendar.getInstance();

    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    int year = c.get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        final String[] question = new String[8];

        final int[] questionNumber = new int[1];
        final int[] hours = new int[8]; //time hours
        final int[] minutes = new int[8]; //time minutes
        final int[] timeAwake = new int [8]; //How many times the user woke up

        final int[] timeHours = new int[8]; //How long hours
        final int[] timeMinutes = new int[8]; //How long minutes



        //ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer> (this,android.R.layout.simple_spinner_dropdown_item,minSleep);
        //ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, minS);
       /* mspin=(Spinner) findViewById(R.id.spinner1);
        Integer[] items = new Integer[]{1,2,3,4};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
        mspin.setAdapter(adapter);*/

        questionNumber[0] = 0;
        populateQuestions(question);

        //((EditText)findViewById(R.id.wakeField)).setInputType(InputType.TYPE_CLASS_NUMBER);
        //((TextView)findViewById(R.id.timesAwake)).setMargins(250,0,0,0);


        ((NumberPicker)findViewById(R.id.minutePicker)).setMinValue(0);
        ((NumberPicker)findViewById(R.id.minutePicker)).setMaxValue(59);
        ((NumberPicker)findViewById(R.id.minutePicker)).setWrapSelectorWheel(true);

        ((NumberPicker)findViewById(R.id.hourPicker)).setMinValue(0);
        ((NumberPicker)findViewById(R.id.hourPicker)).setMaxValue(23);
        ((NumberPicker)findViewById(R.id.hourPicker)).setWrapSelectorWheel(true);

        ((NumberPicker)findViewById(R.id.wakePicker)).setMinValue(0);
        ((NumberPicker)findViewById(R.id.wakePicker)).setMaxValue(99);
        ((NumberPicker)findViewById(R.id.wakePicker)).setWrapSelectorWheel(true);

        //question[1] = "What time did you try to go to sleep?";
        //final int questionNumber = 1;
        // final String question2 = "What time did you try to go to sleep?";
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.next);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionNumber[0] = questionNumber[0] + 1;

                if(questionNumber[0] <= (MAX-1)) {
                    ((FloatingActionButton)findViewById(R.id.back)).setVisibility(View.VISIBLE);
                    if((questionNumber[0]==2)|| (questionNumber[0]==3) || (questionNumber[0]==4) || (questionNumber[0]==7)){
                        ((TimePicker)findViewById(R.id.timePicker)).setVisibility(View.GONE);

                    }
                    else{
                        ((TimePicker)findViewById(R.id.timePicker)).setVisibility(View.VISIBLE);
                    }

                    if(questionNumber[0]==3){
                        ((NumberPicker)findViewById(R.id.wakePicker)).setVisibility(View.VISIBLE);
                        ((TextView)findViewById(R.id.timesAwake)).setVisibility(View.VISIBLE);
                    }
                    else{
                        ((NumberPicker)findViewById(R.id.wakePicker)).setVisibility(View.GONE);
                        ((TextView)findViewById(R.id.timesAwake)).setVisibility(View.GONE);
                    }
                    if((questionNumber[0]==2)|| (questionNumber[0]==4)){
                        ((NumberPicker)findViewById(R.id.hourPicker)).setVisibility(View.VISIBLE);
                        ((TextView)findViewById(R.id.hourText)).setVisibility(View.VISIBLE);
                        ((NumberPicker)findViewById(R.id.minutePicker)).setVisibility(View.VISIBLE);
                        ((TextView)findViewById(R.id.minuteText)).setVisibility(View.VISIBLE);
                    }
                    else{
                        ((NumberPicker)findViewById(R.id.hourPicker)).setVisibility(View.GONE);
                        ((TextView)findViewById(R.id.hourText)).setVisibility(View.GONE);
                        ((NumberPicker)findViewById(R.id.minutePicker)).setVisibility(View.GONE);
                        ((TextView)findViewById(R.id.minuteText)).setVisibility(View.GONE);
                    }
                    if(questionNumber[0]==7){
                        ((EditText)findViewById(R.id.additionalComments)).setVisibility(View.VISIBLE);
                    }
                    else{
                        ((EditText)findViewById(R.id.additionalComments)).setVisibility(View.GONE);
                    }

                    ((TextView) findViewById(R.id.questions)).setText(question[questionNumber[0]]);
                    System.out.println(questionNumber[0]);
                    hours[questionNumber[0]-1] = ((TimePicker)findViewById(R.id.timePicker)).getCurrentHour();
                    minutes[questionNumber[0]-1] = ((TimePicker)findViewById(R.id.timePicker)).getCurrentMinute();
                    System.out.println(hours[questionNumber[0]-1]+":"+minutes[questionNumber[0]-1]);
                    timeAwake[questionNumber[0]-1] = ((NumberPicker)findViewById(R.id.wakePicker)).getValue();
                    timeHours[questionNumber[0]-1] = ((NumberPicker)findViewById(R.id.hourPicker)).getValue();
                    timeMinutes[questionNumber[0]-1] = ((NumberPicker)findViewById(R.id.minutePicker)).getValue();
                    //System.out.println(timeAwake[questionNumber[0]]);

                    if(questionNumber[0]==7){
                        ((FloatingActionButton)findViewById(R.id.next)).setImageResource((R.drawable.ic_done_black_24dp));
                    }
                }
                else{

                    comments[0] = ((EditText)findViewById(R.id.additionalComments)).getText().toString();

                    //Send all data to server here
                    int diff = hours[6]-hours[0];
                    if(diff<0){
                        diff = 24+diff;
                    }
                    diff*=60;
                    int diffMin = minutes[6]-minutes[0];
                    if(diffMin<0) {
                        diffMin = 60+diffMin;
                    }
                    diff+=diffMin;
                    int sleepmin = (timeHours[4]*60)+timeMinutes[4];
                    int eff = 100*sleepmin/diff;


                    UserInformation userInformation = new UserInformation(hours[0], minutes[0],hours[1],minutes[1],timeHours[2], timeMinutes[2], timeAwake[3],timeHours[4],timeMinutes[4],hours[5], minutes[5], hours[6], minutes[6], comments[0], diff, eff, month, day, year);
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    DatabaseReference newKey = databaseReference.child("users").child(user.getUid()).child("sleep").push();
                    newKey.setValue(userInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                //hours[]
                                Toast.makeText(Questions.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Questions.this, "Did not submit. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    returnToMain(view);

                }
                System.out.println(comments[0]);



            }
        });
        FloatingActionButton prev = (FloatingActionButton) findViewById(R.id.back);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FloatingActionButton)findViewById(R.id.next)).setImageResource((R.drawable.ic_fast_forward_black_24dp));
                hours[questionNumber[0]] = ((TimePicker)findViewById(R.id.timePicker)).getCurrentHour();
                minutes[questionNumber[0]] = ((TimePicker)findViewById(R.id.timePicker)).getCurrentMinute();
                timeAwake[questionNumber[0]] = ((NumberPicker)findViewById(R.id.wakePicker)).getValue();
                timeHours[questionNumber[0]] = ((NumberPicker)findViewById(R.id.hourPicker)).getValue();
                timeMinutes[questionNumber[0]] = ((NumberPicker)findViewById(R.id.minutePicker)).getValue();
                if(questionNumber[0]==7){
                    comments[0] = ((EditText)findViewById(R.id.additionalComments)).getText().toString();
                }
                questionNumber[0]=questionNumber[0]-1;
                if(questionNumber[0]==0){
                    ((FloatingActionButton)findViewById(R.id.back)).setVisibility(View.GONE);
                }
                if((questionNumber[0]==2)|| (questionNumber[0]==3) || (questionNumber[0]==4) || (questionNumber[0]==7)){
                    ((TimePicker)findViewById(R.id.timePicker)).setVisibility(View.GONE);

                }
                else{
                    ((TimePicker)findViewById(R.id.timePicker)).setVisibility(View.VISIBLE);
                }

                if(questionNumber[0]==3){
                    ((NumberPicker)findViewById(R.id.wakePicker)).setVisibility(View.VISIBLE);
                    ((TextView)findViewById(R.id.timesAwake)).setVisibility(View.VISIBLE);
                }
                else{
                    ((NumberPicker)findViewById(R.id.wakePicker)).setVisibility(View.GONE);
                    ((TextView)findViewById(R.id.timesAwake)).setVisibility(View.GONE);
                }
                if((questionNumber[0]==2)|| (questionNumber[0]==4)){
                    ((NumberPicker)findViewById(R.id.hourPicker)).setVisibility(View.VISIBLE);
                    ((TextView)findViewById(R.id.hourText)).setVisibility(View.VISIBLE);
                    ((NumberPicker)findViewById(R.id.minutePicker)).setVisibility(View.VISIBLE);
                    ((TextView)findViewById(R.id.minuteText)).setVisibility(View.VISIBLE);
                }
                else{
                    ((NumberPicker)findViewById(R.id.hourPicker)).setVisibility(View.GONE);
                    ((TextView)findViewById(R.id.hourText)).setVisibility(View.GONE);
                    ((NumberPicker)findViewById(R.id.minutePicker)).setVisibility(View.GONE);
                    ((TextView)findViewById(R.id.minuteText)).setVisibility(View.GONE);
                }
                if(questionNumber[0]==7){
                    ((EditText)findViewById(R.id.additionalComments)).setVisibility(View.VISIBLE);
                }
                else{
                    ((EditText)findViewById(R.id.additionalComments)).setVisibility(View.GONE);
                }

                ((TextView) findViewById(R.id.questions)).setText(question[questionNumber[0]]);
            }
        });
    }

    public void returnToMain(View view){
        Intent intent = new Intent(this, LandingMenu.class);
        startActivity(intent);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void populateQuestions (String[] Questions){
        Questions[0] = "What time did you get into bed?";               //Hours (24), Minutes
        Questions[1] = "What time did you try to go to sleep?";         //Hours (24), Minutes
        Questions[2] = "How long did it take you to fall asleep?";      //Hours and Minutes
        Questions[3] = "How many times did you wake up?";               //Number
        Questions[4] = "In total, how long did you sleep?";             //Hours and Minutes
        Questions[5] = "What time was your final awakening?";           //Hours(24), Minutes
        Questions[6] = "What time did you get out of bed for the day?"; //Hours(24), Minutes
        Questions[7] = "Additional Comments (Optional)";                //String

    }

      /*public void submitInfo (int timeHour){
        UserInformation userInformation = new UserInformation(timeHour);
        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInformation);

    }*/

}
