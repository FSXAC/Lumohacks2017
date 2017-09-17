package kazoo.sleeptracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;


public class SleepSummary extends AppCompatActivity {
    int MAX = 8;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String[] question = new String[8];

        final int[] questionNumber = new int[1];
        final int[] hours = new int[8]; //time hours
        final int[] minutes = new int[8]; //time minutes
        final int[] timeAwake = new int [8]; //How many times the user woke up

        final int[] timeHours = new int[8]; //How long hours
        final int[] timeMinutes = new int[8]; //How long minutes

        final String[] comments = new String[1]; //Additional Comments

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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionNumber[0] = questionNumber[0] + 1;

                if(questionNumber[0] <= (MAX-1)) {
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
                    System.out.println(hours[questionNumber[0]]+":"+minutes[questionNumber[0]]);
                    timeAwake[questionNumber[0]-1] = ((NumberPicker)findViewById(R.id.wakePicker)).getValue();
                    timeHours[questionNumber[0]-1] = ((NumberPicker)findViewById(R.id.hourPicker)).getValue();
                    timeMinutes[questionNumber[0]-1] = ((NumberPicker)findViewById(R.id.minutePicker)).getValue();
                    //System.out.println(timeAwake[questionNumber[0]]);
                }
                else{
                    comments[0] = ((EditText)findViewById(R.id.additionalComments)).getText().toString();
                }
                System.out.println(comments[0]);
                /*String s = ""+((TextView)findViewById(R.id.numbers)).getText().charAt(0);
                char set;
                int number = Integer.parseInt(s);
                number--;
                ((TextView)findViewById(R.id.questions)).setText(question[number]);
                number+=2;
                set = (char) number;
                ((TextView)findViewById(R.id.numbers)).setText(set);

               /* if(((TextView)findViewById(R.id.questions)).getText() == "What time did you get into bed?") {
                    ((TextView)findViewById(R.id.questions)).setText(question[1]);
                    number=1;
                    //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            //.setAction("Action", null).show();
                }
                else {
                    ((TextView)findViewById(R.id.questions)).setText(question[number]);
                }*/


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sleep_summary, menu);
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
}
