package itg8.com.nowzonedesigndemo.home.mvp;

import android.content.BroadcastReceiver;
import android.content.Context;

/**
 * Created by itg_Android on 3/2/2017.
 */

public interface BreathFragmentModel {
    void checkBLEConnected(Context context);

    void onInitStateTime();

    void initDB(Context context);

    void onDestroy();

    void dataStarted(boolean b);
}
