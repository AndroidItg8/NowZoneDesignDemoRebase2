package itg8.com.nowzonedesigndemo.tosort;

import itg8.com.nowzonedesigndemo.common.DataModel;

/**
 * Created by itg_Android on 3/7/2017.
 */

public interface RDataManagerListener {
    void onDataProcessed(DataModel m2MAData);

    void onCountAvailable(int count, long timestamp);

    void onStepCountReceived(int step);

    void onSleepInterrupted(long timestamp);

    void onStartWakeupSevice();

    void onSocketInterrupted();

    void onMovement(float mAccel);

    void onNoMovement(float i);

    void onDeviceNotAttached();

    void onDeviceAttached();

    void onAxisDataAvail(double y, double z);

}
