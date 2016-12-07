package pudgethefish.messagealarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class AlarmOffActivity extends Activity  {

    private EditText mInputView;
    private Context context;
    MainActivity main = new MainActivity();

    /*
    public AlarmOffActivity(Context context){
        this.context = context;
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_off);
        this.context = this;
        // Set up the login form.

        mInputView = (EditText) findViewById(R.id.input_message);
        mInputView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button button_submit = (Button) findViewById(R.id.submit_button);
        button_submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }


    /**
     * Attempts to turn off the alarm based on the user's input
     * If the message entered is incorrect, the
     * errors are presented and no additional action is taken.
     */
    public void attemptLogin() {
        // Reset errors.
        mInputView.setError(null);

        // Store values at the time of the login attempt.
        String input = mInputView.getText().toString();

        String testStr = "testing";
        // return true if the input is correct
        if (testStr.equals(input)){
            Log.e("MESSAGE ENTERED", "the strings are equal***********************");
            //DO STUFF: turn alarm off, reset the pending intent
            Intent myIntent = new Intent(this, AlarmReceiver.class);
            PendingIntent pending_intent = PendingIntent
                    .getBroadcast(AlarmOffActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = MainActivity.getAlarmManager();
            alarmManager.cancel(pending_intent);

            //change the button color to indicate the alarm is turned off

            Button alarm_button = main.getAlarm_button();
            Button off_button = main.getOff_button();

            //Context mContext = main.getContext();
            //Button alarm_button = (Button) ((Activity)mContext).findViewById(R.id.time_button);
            //Button off_button = (Button) ((Activity)mContext).findViewById(R.id.cancel_button);
            int mColor = ContextCompat.getColor(this, R.color.colorCancelAlarm);
            alarm_button.setBackgroundColor(mColor);
            off_button.setBackgroundColor(mColor);

            myIntent.putExtra("extra", false);
            sendBroadcast(myIntent);

            finish();
        }
        else {
            mInputView.setError(getString(R.string.error_incorrect_input));
            mInputView.requestFocus();
        }
    }
}

