package pudgethefish.messagealarmclock;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;



public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    //private Activity mActivity;
    //private TimePickerDialog.OnTimeSetListener mListener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), android.R.style.Theme_Holo, this, hour,
                minute, DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
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
        Button alarm_butt = (Button) getActivity().findViewById(R.id.time_button);
        alarm_butt.setText(hour_string + ":" + minute_string + " " + am_pm);

    }
}
