package itg8.com.nowzonedesigndemo.connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import static itg8.com.nowzonedesigndemo.connection.BleService.ACTION_RESET_ALL;

public class DateChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        final String action=intent.getAction();
        if(action.equals(Intent.ACTION_DATE_CHANGED)){
            Intent intent1=new Intent(ACTION_RESET_ALL);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);
        }

    }
}
