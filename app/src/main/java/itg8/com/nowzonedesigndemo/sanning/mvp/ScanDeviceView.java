package itg8.com.nowzonedesigndemo.sanning.mvp;

import android.bluetooth.BluetoothDevice;

/**
 * Created by itg_Android on 2/21/2017.
 */
public interface ScanDeviceView {

    void onNewDeviceResult(BluetoothDevice result, int rssi);

    void onScanningFail(int errorCode);

    void checkBleAvailability();

    void onScanningStopped();

    void startHomeActivity();

    void onScanningStarted(CharSequence text);

    void onShowScanning();

    void onShowButton();

    void startConnectingDialog();

    void stopConnectingDialog();
}
