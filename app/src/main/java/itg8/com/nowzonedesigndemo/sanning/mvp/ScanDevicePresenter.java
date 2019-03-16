package itg8.com.nowzonedesigndemo.sanning.mvp;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import java.util.List;

import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.common.SharePrefrancClass;
import itg8.com.nowzonedesigndemo.connection.BleService;
import itg8.com.nowzonedesigndemo.connection.DeviceModel;
import itg8.com.nowzonedesigndemo.utility.BreathState;
import itg8.com.nowzonedesigndemo.utility.DeviceState;
import me.alexrs.wavedrawable.WaveDrawable;


public class ScanDevicePresenter implements ScanDeviceModelListener, BluetoothAdapter.LeScanCallback {

    private static final String TAG = ScanDevicePresenter.class.getSimpleName();
    public static final long SCAN_TIME = 10000;
    private ScanDeviceView view;
    private BluetoothLeScanner scanner;
    private DeviceModel mSelectedDevice;
    private boolean stopped = true;

    private BleService mBluetoothService;
    private boolean connected;
    //    private final ServiceConnection mServiceConnection = new ServiceConnection() {
//
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder service) {
//            mBluetoothService = ((BleService.LocalBinder) service).getService();
//            if (!mBluetoothService.initialize()) {
//
//
//                Log.e(TAG, "Unable to initialize Bluetooth");
//            }
//            // Automatically connects to the device upon successful start-up initialization.
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            mBluetoothService = null;
//            Log.d(TAG, "Service disconnected");
//        }
//    };
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"broadcast received:");
            if (intent.getAction().equals(BleService.ACTION_DEVICE_CONNECTED)) {
                if(!connected)
                    setDeviceConnected();

                connected=true;
            }
        }
    };
    private ScanDeviceModelImp model;
    private Handler handler;
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            if (view != null) {
                stopScanning();
            }
        }
    };


    public ScanDevicePresenter(ScanDeviceView view) {
        this.view = view;
        model = new ScanDeviceModelImp();
        handler = new Handler();
    }

    private void stopScanning() {
        if (scanner != null)
            model.onStopScanning(scanner);
        if (view != null) {
            stopped = true;
            view.onScanningStopped();
        }
    }


    @Override
    public void startAnimation(View view, WaveDrawable waveDrawable) {
        if (view != null && this.view != null) {
            ((ImageView) view).setImageDrawable(waveDrawable);
            waveDrawable.setWaveInterpolator(new LinearInterpolator());
            waveDrawable.startAnimation();
        }
    }

    @Override
    public void startLEScan(Context context, BluetoothAdapter bluetoothAdapter) {
        if (stopped) {
            stopped = false;
            handler.postDelayed(r, SCAN_TIME);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if(scanner==null)
                    scanner = bluetoothAdapter.getBluetoothLeScanner();
                Log.d(TAG, "Scan Started on 23");
                model.onLeScanCall(scanner, this);
            } else {
                //noinspection deprecation
               // bluetoothAdapter.startLeScan(this);
                Log.d(TAG, "Scan Started on <23");
            }
        }
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        if (view != null) {
            view.onNewDeviceResult(result.getDevice(), calculateByRange(result.getRssi()));
        }
    }

    @Override
    public void onBatchScanResults(List<ScanResult> results) {
        //Need to study it further
    }

    @Override
    public void onScanFailed(int errorCode) {
        if (view != null) {
            view.onScanningFail(errorCode);
        }
    }

    @Override
    public void onStart() {
        if (view != null) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(BleService.ACTION_DEVICE_CONNECTED);
            LocalBroadcastManager.getInstance(((Context) view)).registerReceiver(receiver, filter);
            Log.d(TAG, "Receiver Registered");
        }
    }

    @Override
    public void onResume() {
        if (view != null) {
            view.checkBleAvailability();
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {
        if (view != null) {
            LocalBroadcastManager.getInstance(((Context) view)).unregisterReceiver(receiver);
            Log.d(TAG, "Receiver unregistered");
        }
    }

    @Override
    public void onDestroy() {
        model.onDestroy();
        handler.removeCallbacks(r);
    }


    @Override
    public void onLeScan(BluetoothDevice bluetoothDevice, int rssi, byte[] bytes) {
        if (view != null) {
            view.onNewDeviceResult(bluetoothDevice, calculateByRange(rssi));
        }
    }

    private int calculateByRange(int rssi) {
        return ((100 + rssi) * 10) / 100;
    }

    @Override
    public void refreshBtnClicked(View view) {
        if (mSelectedDevice == null && view != null) {
            Snackbar.make(view, R.string.selected_device, Snackbar.LENGTH_LONG).show();
        } else if ((mSelectedDevice != null ? mSelectedDevice.getAddress() : null) != null) {
            if(this.view!=null)
            {
                this.view.startConnectingDialog();
            }
            Intent intent = new Intent(view.getContext(), BleService.class);
            intent.putExtra(CommonMethod.DEVICE_ADDRESS,mSelectedDevice.getAddress());
            intent.putExtra(CommonMethod.DEVICE_NAME,mSelectedDevice.getName());
            //TODO commment: test service DONE
            view.getContext().startService(intent);
            handler.removeCallbacks(r);
        } else {
            Log.d(TAG, "Something is wrong:");
        }
    }

    @Override
    public void selectedDevice(DeviceModel model, Context baseContext) {
        mSelectedDevice = model;

    }

    private void setDeviceConnected() {
        if (view != null) {
            view.startHomeActivity();
        }
    }

    @Override
    public void checkAlreadyConnectedOnce(Context context) {
        if(SharePrefrancClass.getInstance(context).getPref(CommonMethod.STATE)!=null &&
                !SharePrefrancClass.getInstance(context).getPref(CommonMethod.STATE).equalsIgnoreCase(DeviceState.DISCONNECTED.name())){
            //
            //TODO commment: test service
//            view.getContext().startService(intent);
            if(view!=null)
                view.startHomeActivity();
        }
    }

    @Override
    public void setLoadingText(CharSequence text) {
        if(view!=null) {
            view.onScanningStarted(text);
        }
    }

    @Override
    public void showButton() {
        if(view!=null)
            view.onShowButton();
    }

    @Override
    public void showLoading() {
            if(view!=null)
                view.onShowScanning();
    }

    @Override
    public void cancelScanning() {
        if(handler!=null)
            handler.removeCallbacks(r);
        stopScanning();
    }

}
