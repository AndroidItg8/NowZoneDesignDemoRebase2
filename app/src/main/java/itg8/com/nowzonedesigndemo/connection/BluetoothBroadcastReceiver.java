package itg8.com.nowzonedesigndemo.connection;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.common.CommonMethod;

/**
 * Created by itg_Android on 7/20/2017.
 */

public class BluetoothBroadcastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
            if(intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)
                    == BluetoothAdapter.STATE_OFF){
                Intent i=new Intent(context.getResources().getString(R.string.action_device_disconnect));
                i.putExtra(CommonMethod.BLUETOOTH_OFF,"true");
                LocalBroadcastManager.getInstance(context).sendBroadcast(i);
            }else {
                context.startService(new Intent(context, BleService.class));
            }
            // Bluetooth is disconnected, do handling here
        }
    }
}
