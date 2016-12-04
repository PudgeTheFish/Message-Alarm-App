package pudgethefish.messagealarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.wtf("We are in the receiver", "wooo");

        //fetch extra strings from the intent
        boolean get_your_bool = intent.getExtras().getBoolean("extra");

        //create an intent to the ringtone service
        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        //Pass the extra string from main to ringtoneplayingservice
        service_intent.putExtra("extra", get_your_bool);

        context.startService(service_intent);
    }
}
