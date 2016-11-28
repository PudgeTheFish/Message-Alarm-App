package pudgethefish.messagealarmclock;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //to make our alarm manager
    AlarmManager alarm_manager;
    Button alarm_butt;
    private AlarmReceiver alarm;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        final Intent myIntent = new Intent(this.context, AlarmReceiver.class);

        //turn the alarm on
        alarm_butt = (Button) findViewById(R.id.time_button);

        alarm_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");

            }
        });


        //turn the alarm off
        Button off_butt = (Button) findViewById(R.id.cancel_button);

        off_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: change color of the buttons and cancel the alarm
            }
        });

    }

}

