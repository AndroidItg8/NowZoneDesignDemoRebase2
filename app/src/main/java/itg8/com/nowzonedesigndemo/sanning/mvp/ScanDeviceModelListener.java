package itg8.com.nowzonedesigndemo.sanning.mvp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.view.View;

import java.util.List;

import itg8.com.nowzonedesigndemo.connection.DeviceModel;
import me.alexrs.wavedrawable.WaveDrawable;


public interface ScanDeviceModelListener {
    void startAnimation(View view, WaveDrawable drawable);

    void startLEScan(Context context, BluetoothAdapter bluetoothAdapter);

    void onScanResult(int callbackType, ScanResult result);

    void onBatchScanResults(List<ScanResult> results);

    void onScanFailed(int errorCode);

    void onStart();
    void onResume();
    void onPause();
    void onStop();
    void onDestroy();

    void refreshBtnClicked(View view);

    void selectedDevice(DeviceModel model, Context baseContext);

    void checkAlreadyConnectedOnce(Context context);

    void setLoadingText(CharSequence text);

    void showButton();

    void showLoading();

    void cancelScanning();
}
