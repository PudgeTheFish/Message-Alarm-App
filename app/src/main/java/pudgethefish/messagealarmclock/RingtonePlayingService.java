package pudgethefish.messagealarmclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.security.Provider;
import java.util.List;
import java.util.Map;


public class RingtonePlayingService extends Service {

    MediaPlayer media_song;
    boolean isRunning;
    int startId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        //fetch the extra bool values
        boolean state = intent.getExtras().getBoolean("extra");        

        if (state){
            startId = 1;
        }
        else{
            startId = 0;
        }

        //if there is no music playing and the user pressed alarm on
        //music should start playing
        if (!this.isRunning && startId==1){
            media_song = MediaPlayer.create(this, R.raw.arab);
            media_song.start();

            this.isRunning = true;
            this.startId = 0;

            //notification
            NotificationManager notify_manager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);
            //set up an intent that goes to the main activity
            Intent intent_main_activity = new Intent(this.getApplicationContext(), MainActivity.class);
            //set up pending intent
            PendingIntent pending_intent_main_activity = PendingIntent.getActivity(this, 0,
                    intent_main_activity, 0);
            //make the notification parameters
            Notification notification_popup = new Notification.Builder(this)
                    .setContentTitle("an alarm is going off!")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText("Click me!").setContentIntent(pending_intent_main_activity)
                    .setAutoCancel(true).build();
            //set up notification call command
            notify_manager.notify(0, notification_popup);
        }
        //if there is music playing and the user pressed alarm off
        //music should stop playing
        else if (this.isRunning && startId==0){
            media_song.stop();
            media_song.reset();

            this.isRunning = false;
            this.startId = 0;
        }

        //these are if the user presses random buttons
        //if there is no music playing and the user presses alarm off
        else if (!this.isRunning && startId==0){
            this.startId = 0;
        }
        //if there is music playing and the user presses alarm on
        else {
            this.startId = 1;
        }




        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        // Tell the user we stopped.
        Toast.makeText(this, "on destroy called", Toast.LENGTH_SHORT).show();
    }

}
