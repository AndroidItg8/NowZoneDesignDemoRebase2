package itg8.com.nowzonedesigndemo.setting;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;

import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.common.SharePrefrancClass;
import itg8.com.nowzonedesigndemo.setting.notification.AlarmNotification;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {
    private static final int ALARM_ID = 1234;
    private static final String TAG = AlarmReceiver.class.getSimpleName();
    private static final int PENDING_RQ = 234;
    private int requestCode = 123;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder noti;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Intent intent1;

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                Log.d(TAG, String.format("%s %s (%s)", key,
                        value.toString(), value.getClass().getName()));
            }
        }
        // an Intent broadcast.
        if (intent.hasExtra(CommonMethod.ALARM_FROMTIMEPICKER)) {
            long startTime = SharePrefrancClass.getInstance(context).getLPref(CommonMethod.START_ALARM_TIME);
            long endTime = SharePrefrancClass.getInstance(context).getLPref(CommonMethod.END_ALARM_TIME);
            Log.d(getClass().getSimpleName(), "startTime:" + startTime);

            String time = SharePrefrancClass.getInstance(context).getPref(CommonMethod.SAVEALARMTIME);
            String amPm = SharePrefrancClass.getInstance(context).getPref(CommonMethod.ALARM_AP);


            PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.item_rv_alarm_notification);
            remoteView.setTextViewText(R.id.txt_app_name, "NowZone");
            remoteView.setTextViewText(R.id.txt_times, time + " " + amPm);
            remoteView.setTextViewText(R.id.txt_hours_diff, "Hours");
//              remoteView.setInt(R.id.relative,"setBackgroundResource",R.drawable.sun);
            Calendar c = Calendar.getInstance();
            long seconds = c.getTimeInMillis();

            Log.d(getClass().getSimpleName(), "seconds:" + c.getTime());
            Log.d(getClass().getSimpleName(), "seconds:" + c.getTime());
            remoteView.setTextViewText(R.id.txt_hours, calculateHours(seconds, startTime));
            //remoteView.setImageViewResource(R.id.img_close,R.drawable.ic_closes);
            remoteView.setOnClickPendingIntent(R.id.btn_close, createSelfPendingIntent(context));
            Log.d(getClass().getSimpleName(), "Time:" + time);
            noti = new NotificationCompat.Builder(context)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_alarms)
                    .setContentIntent(pendingIntent)
                    .setCustomBigContentView(remoteView)
                    .setPriority(Notification.PRIORITY_MIN)
                    .setCustomContentView(remoteView)
                    .setVisibility(Notification.PRIORITY_MIN)
                    .setFullScreenIntent(pendingIntent, true);


                  //  .setContent(remoteView)


            // hide the notification after its selected
            SharePrefrancClass.getInstance(context).savePref(CommonMethod.SLEEP_STARTED, "ss");
            intent1 = new Intent(context.getResources().getString(R.string.action_device_sleep_start));
            intent1.putExtra(CommonMethod.START_ALARM_TIME, startTime);
            intent1.putExtra(CommonMethod.END_ALARM_TIME, endTime);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);
            notificationManager.notify(ALARM_ID, noti.build());
           // notificationManager.cancel(ALARM_ID);

        } else if (intent.hasExtra(CommonMethod.ALARM_END)) {
            notificationManager.cancel(ALARM_ID);
            intent1 = new Intent(context.getResources().getString(R.string.action_device_sleep_end));
            intent1.putExtra(CommonMethod.ALARM_END, System.currentTimeMillis());
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);
            SharePrefrancClass.getInstance(context).clearPref(CommonMethod.SLEEP_STARTED);

        }


    }


    private PendingIntent createSelfPendingIntent(Context context) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(CommonMethod.ACTION_ALARM_NOTIFICATION);
        intent.putExtra(CommonMethod.ALARM_END, "00");
        return PendingIntent.getBroadcast(context, PENDING_RQ, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private CharSequence calculateHours(long startTime, long endTime) {
        String hourses;

        long diff = endTime - startTime;

        long seconds = diff / 1000; // seconds is milliseconds / 1000
        long milliseconds = (endTime - startTime) % 1000; // remainder is milliseconds that are not composing seconds.
        long minutes = seconds / 60;
        seconds = seconds % 60;
        long hours = minutes / 60;
        minutes = minutes % 60;

        long hr = 0;
        long min = 0;
        if (hours < 0) {
            hr = hours * 60;
            Log.d(getClass().getSimpleName(), "diff:" + diff);

        }
        if (minutes < 0) {
            hr = hr + minutes;
        }

        hr = 1440 + hr;
        hr = hr / 60;
        min = hr % 60;
        Log.d(getClass().getSimpleName(), "diff:" + hr + "min" + min);
        if (hours < 0 || minutes < 0) {
//            hr = hours*60;
//            hr= hr+minutes;
//            hr+=1440;
//             min=  hr%60;
//           hr= hr/60;

            Log.d(getClass().getSimpleName(), "diff:" + hr + "min" + min);
            return hr + ":" + min;
        }
        Log.d(getClass().getSimpleName(), "diff:" + diff);
        hourses = hours + ":" + minutes;


        return hourses;
    }



}
