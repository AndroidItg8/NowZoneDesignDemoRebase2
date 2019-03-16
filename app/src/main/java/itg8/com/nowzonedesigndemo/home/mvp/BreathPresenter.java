package itg8.com.nowzonedesigndemo.home.mvp;

import android.content.Context;

import itg8.com.nowzonedesigndemo.utility.BreathState;

/**
 * Created by itg_Android on 3/2/2017.
 */

public interface BreathPresenter {
    void onCreate();
    void onPause();
    void passContext(Context context);
    void onAttach();
    void onDetach();
    void onInitTimeHistory();

    void initGraphData();

    public interface BreathFragmentModelListener{
        void onPressureReceived(double pressure, long ts);

        void onCountReceived(int intExtra);

        void onStepReceived(int intExtra);

        void startShowingDevicesList();

        void onStateReceived(BreathState state);

        void onStateTimeReceived(StateTimeModel stateTimeModel);

        void onDeviceNotConnectedInTime();

        void onDataReceivingStarted();
    }
}
