package itg8.com.nowzonedesigndemo.sanning.mvp;

import android.annotation.TargetApi;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Build;
import android.util.Log;

import java.util.List;

import static android.os.SystemClock.sleep;
import static itg8.com.nowzonedesigndemo.sanning.mvp.ScanDevicePresenter.SCAN_TIME;

/**
 * Created by itg_Android on 2/21/2017.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ScanDeviceModelImp implements ScanDeviceModel {


    private static final String TAG = ScanDeviceModelImp.class.getSimpleName();
    private ScanDeviceModelListener listener;

    private int splashTime=0;
    private boolean scanningCompleted;

    private ScanCallback callback = new ScanCallback() {
        @Override
        public void onScanResult(final int callbackType, final ScanResult result) {
//                super.onScanResult(callbackType, result);
            Log.d(TAG, "Scan result got " + result);
            if(listener!=null)
                listener.onScanResult(callbackType, result);
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
//                super.onBatchScanResults(results);
            Log.d(TAG, "BatchScanResult got " + results);
            if(listener!=null)
                listener.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
//                super.onScanFailed(errorCode);
            Log.d(TAG, "Scan failed  " + errorCode);
            if(listener!=null)
                listener.onScanFailed(errorCode);
        }
    };


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onLeScanCall(BluetoothLeScanner scanner, final ScanDeviceModelListener listener) {
        Log.d(TAG, "Scanner arrived " + scanner);
        this.listener = listener;
        scanningCompleted=false;
        initStartScanningText();
        scanner.startScan(callback);
    }

    private void initStartScanningText() {
        if(scanningCompleted){
            splashTime=7000;
            if(listener!=null)
                listener.showButton();
            return;
        }
        listener.showLoading();
        new Thread(){
            @Override
            public void run() {
                while(splashTime < 6000){

                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(splashTime < 2000){
                        setText("Scanning nearby Nowzone device.");
                    }
                    else if(splashTime >= 2000 && splashTime < 4000 ){
                        setText("Scanning nearby Nowzone device..");
                    }else if (splashTime >= 4000){
                        setText("Scanning nearby Nowzone device...");
                        splashTime=0;
                    }
                    splashTime = splashTime + 100;
                }
            }
        }.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onStopScanning(BluetoothLeScanner scanner) {
        scanner.stopScan(callback);
        scanningCompleted=true;
        initStartScanningText();
    }

    @Override
    public void cancelScanning() {
        scanningCompleted=true;
        initStartScanningText();
    }

    @Override
    public void onDestroy() {
        listener=null;
    }

    private void setText(final CharSequence text) {
        if(listener!=null)
                listener.setLoadingText(text);
    }


}
