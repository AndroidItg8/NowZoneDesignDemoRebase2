package itg8.com.nowzonedesigndemo.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;

import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.breath.BreathHistoryActivity;
import itg8.com.nowzonedesigndemo.utility.BreathState;

import static android.content.Context.NOTIFICATION_SERVICE;
import static itg8.com.nowzonedesigndemo.connection.BleService.ACTION_STATE_ARRIVED;
import static itg8.com.nowzonedesigndemo.home.HomeActivity.COLOR_CALM_M;
import static itg8.com.nowzonedesigndemo.home.HomeActivity.COLOR_FOCUSED_M;
import static itg8.com.nowzonedesigndemo.home.HomeActivity.COLOR_STRESS_M;

public class NotificationBroadcastReceiver extends BroadcastReceiver {

    private static final String CALM = "State: CALM";
    private static final String FOCUSED = "State: FOCUSED";
    private static final String STRESS = "State: STRESS";
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        this.context=context;
        if(intent!=null && intent.hasExtra(ACTION_STATE_ARRIVED))
        {
            buildNotificationForState((BreathState) intent.getSerializableExtra(ACTION_STATE_ARRIVED));
        }
    }

    private void buildNotificationForState(BreathState state) {
        if(state==BreathState.CALM){
            createNotification(CALM, COLOR_CALM_M,"You have calm for last 2 minutes");
        }else if(state==BreathState.FOCUSED){
            createNotification(FOCUSED, COLOR_FOCUSED_M,"You have focused for last 2 minutes");
        }else if(state == BreathState.STRESS){
            createNotification(STRESS, COLOR_STRESS_M,"You have stress for last 2 minutes");
        }
    }

    private void createNotification(String state, String color, String message) {
        Intent intent = new Intent(context, BreathHistoryActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(context)
                .setContentTitle(state)
                .setContentText(message).setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setColor(Color.parseColor(color)).build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);

    }
}
