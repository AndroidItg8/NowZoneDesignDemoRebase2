package itg8.com.nowzonedesigndemo.connection;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCallback;

import itg8.com.nowzonedesigndemo.utility.DeviceState;


public interface ConnectionStateListener {
    void onDeviceConnected(String address);
    void onDeviceDiscovered();
    void onDeviceConnectionChanged();
    void onDataAvail(byte[] data);
    void currentState(DeviceState state);
    void connectGatt(BluetoothDevice device, BluetoothGattCallback callback);

    void onFail(DeviceState state, int status);
}
