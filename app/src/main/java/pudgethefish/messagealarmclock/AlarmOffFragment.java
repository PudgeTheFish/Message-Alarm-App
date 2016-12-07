package pudgethefish.messagealarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlarmOffFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlarmOffFragment} factory method to
 * create an instance of this fragment.
 */
public class AlarmOffFragment extends Fragment {

    private EditText mInputView;
    private Context context;
    View mainView;
    MainActivity main = new MainActivity();

    private OnFragmentInteractionListener mListener;

    public AlarmOffFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_alarm_off, container, false);
        mainView = inflater.inflate(R.layout.activity_main, container, false);

        mInputView = (EditText) rootView.findViewById(R.id.input_message);
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

        Button button_submit = (Button) rootView.findViewById(R.id.submit_button);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        return rootView;
    }

    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    */

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
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
            Intent myIntent = new Intent(getActivity(), AlarmReceiver.class);
            PendingIntent pending_intent = PendingIntent
                    .getBroadcast(getActivity(), 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = MainActivity.getAlarmManager();
            alarmManager.cancel(pending_intent);

            //change the button color to indicate the alarm is turned off

            //Button alarm_button = main.getAlarm_button();
            //Button off_button = main.getOff_button();

            Context mContext = main.getContext();
            Button alarm_button = (Button) mainView.findViewById(R.id.time_button);
            Button off_button = (Button) mainView.findViewById(R.id.cancel_button);
            int mColor = ContextCompat.getColor(getActivity(), R.color.colorCancelAlarm);
            alarm_button.setBackgroundColor(mColor);
            off_button.setBackgroundColor(mColor);

            myIntent.putExtra("extra", false);
            getActivity().sendBroadcast(myIntent);

            getActivity().finish();
        }
        else {
            mInputView.setError(getString(R.string.error_incorrect_input));
            mInputView.requestFocus();
        }
    }
}
