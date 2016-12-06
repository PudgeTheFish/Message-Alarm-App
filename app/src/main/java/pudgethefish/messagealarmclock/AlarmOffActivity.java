package pudgethefish.messagealarmclock;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.app.Activity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class AlarmOffActivity extends Activity  {

    // UI references.
    private EditText mInputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_off);
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
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mInputView.setError(null);

        // Store values at the time of the login attempt.
        String input = mInputView.getText().toString();

        String testStr = "testing";
        // return true if the input is correct
        if (testStr.equals(input)){
            Log.e("MESSAGE ENTERED", "the strings are equal***********************");

            finish();
        }
        else {
            mInputView.setError(getString(R.string.error_incorrect_input));
            mInputView.requestFocus();
        }

    }
}

