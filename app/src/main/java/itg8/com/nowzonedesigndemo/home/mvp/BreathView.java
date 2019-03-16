package itg8.com.nowzonedesigndemo.home.mvp;


import itg8.com.nowzonedesigndemo.utility.BreathState;
import itg8.com.nowzonedesigndemo.utility.DeviceState;

public interface BreathView {
    void onPressureDataAvail(double pressure, long ts);
    void onDeviceConnected();
    void onDeviceDisconnected();

//    void onLineChartDataInit(LineChartData data);

    void onBreathCountAvailable(int intExtra);

    void onStepCountReceived(int intExtra);

    void onStartDeviceScanActivity();

    void onBreathingStateAvailable(BreathState state);


    void onStateTimeHistoryReceived(StateTimeModel stateTimeModel);

    void onRemoveSnackbar();

    void onDeviceDisconnectedInTime();

    void setSocketClosed();

    void onDeviceStateAvail(DeviceState deviceState);

    void onMovementStarted();

    void onMovementStopped();

    void onDeviceNotAttachedToBody();

    void onDeviceAttached();

    void onNotLoginYet();
}
