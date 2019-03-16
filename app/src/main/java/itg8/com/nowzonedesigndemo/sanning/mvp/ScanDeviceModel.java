package itg8.com.nowzonedesigndemo.sanning.mvp;

import android.bluetooth.le.BluetoothLeScanner;

/**
 * Created by itg_Android on 2/21/2017.
 */
public interface ScanDeviceModel {
    void onLeScanCall(BluetoothLeScanner callback, ScanDeviceModelListener listener);

    void onStopScanning(BluetoothLeScanner scanner);

    void cancelScanning();

    void onDestroy();
}
