package itg8.com.nowzonedesigndemo.tosort;

import android.content.Context;

import itg8.com.nowzonedesigndemo.common.DataModel;
import itg8.com.nowzonedesigndemo.utility.BreathState;


/**
 * Created by itg_Android on 3/7/2017.
 */

public interface RDataManager {
    void onRawDataModel(DataModel model, Context applicationContext);
    void onSleepStarted(boolean b);
    void onStartAlarmTime(long startAlarm);
    void onEndAalrmTime(long endAlarm);

    void setServerIp(String ip);
    void startSendingData(String stringExtra);

    void onSocketStopped();

    void arrangeBreathingForServer(String string, int count, long timestamp, BreathState currentState);

    void arrangeStepsForServer(String url, int step);

    void resetCountAndAverage();
}
