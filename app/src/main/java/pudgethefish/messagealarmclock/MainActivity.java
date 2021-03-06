package pudgethefish.messagealarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    //to make our alarm manager
    static AlarmManager alarm_manager;
    private PendingIntent pending_intent;
    private Button alarm_button, off_button;
    private Calendar cal;
    private Intent myIntent;
    private Context context;

    public static AlarmManager getAlarmManager(){
        return alarm_manager;
    }
    public Button getAlarm_button(){
        alarm_button = (Button) findViewById(R.id.time_button);
        return alarm_button;
    }
    public Button getOff_button(){
        off_button = (Button) findViewById(R.id.cancel_button);
        return off_button;
    }
    public Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        cal = Calendar.getInstance();

        myIntent = new Intent(this.context, AlarmReceiver.class);

        //get the buttons
        alarm_button = (Button) findViewById(R.id.time_button);
        off_button = (Button) findViewById(R.id.cancel_button);

        alarm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
                int mColor = ContextCompat.getColor(context, R.color.colorButtonNormal);
                alarm_button.setBackgroundColor(mColor);
                off_button.setBackgroundColor(mColor);

            }
        });


        //turn the alarm off
        off_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                turnOffAlarm();
            }
        });

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);

        String hour_string = String.valueOf(hourOfDay);
        String minute_string = String.valueOf(minute);
        String am_pm = "AM";

        if (hourOfDay > 12){
            hour_string = String.valueOf(hourOfDay - 12);
        }
        if (hourOfDay > 11){
            am_pm = "PM";
        }
        if (hourOfDay == 0){
            hour_string = "12";
        }
        if (minute < 10){
            minute_string = "0" + minute_string;
        }
        Button alarm_butt = (Button) findViewById(R.id.time_button);
        alarm_butt.setText(hour_string + ":" + minute_string + " " + am_pm);

        //put in extra string into my intent
        //tells the app that you pressed alarm on
        myIntent.putExtra("extra", true);

        //create a pending intent that delays intent until alarm time
        pending_intent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        alarm_manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pending_intent);

    }



    public void turnOffAlarm(){
        //cancel the pending alarm
        alarm_manager.cancel(pending_intent);

        //change the button color to indicate the alarm is turned off
        int mColor = ContextCompat.getColor(context, R.color.colorCancelAlarm);
        alarm_button.setBackgroundColor(mColor);
        off_button.setBackgroundColor(mColor);

        //put extra string into my intent, tells app to turn off the alarm
        myIntent.putExtra("extra", false);
        sendBroadcast(myIntent);
    }


}

