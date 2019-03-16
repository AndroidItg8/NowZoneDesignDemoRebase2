package itg8.com.nowzonedesigndemo.home.mvp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import java.util.Random;

import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.common.Prefs;
import itg8.com.nowzonedesigndemo.utility.BreathState;
import itg8.com.nowzonedesigndemo.utility.DeviceState;

import static itg8.com.nowzonedesigndemo.connection.BleService.ACTION_DEVICE_NOT_ATTACHED_TO_BODY;
import static itg8.com.nowzonedesigndemo.connection.BleService.ACTION_SOCKET_INERRUPTED;
import static itg8.com.nowzonedesigndemo.connection.BleService.ACTION_STATE_ARRIVED;
import static itg8.com.nowzonedesigndemo.connection.BleService.ACTION_STEP_COUNT;


public class BreathPresenterImp implements BreathPresenter, BreathPresenter.BreathFragmentModelListener {


    private BreathView view;
    private BreathFragmentModel model;
    private Context context;
//    private Runnable mTimer2;
//    private Handler mHandler;
    private double graph2LastXValue = 5d;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if(view==null)
                    return;
                if(intent.hasExtra(CommonMethod.ACTION_DATA_AVAILABLE)){
                    double model = intent.getDoubleExtra(CommonMethod.ACTION_DATA_AVAILABLE,0);
                    long timestamp=intent.getLongExtra(CommonMethod.ACTION_DATA_LONG,0);
//                    Log.d("BreathPresenterImp","data to presenter:"+model+"  "+timestamp);
                    BreathPresenterImp.this.model.dataStarted(true);
                    onPressureReceived(model,timestamp);
                }
                if(intent.hasExtra(CommonMethod.BPM_COUNT)){
                    onCountReceived(intent.getIntExtra(CommonMethod.BPM_COUNT,0));
                }if(intent.hasExtra(ACTION_STEP_COUNT)){
                    onStepReceived(intent.getIntExtra(ACTION_STEP_COUNT,0));
                }if(intent.hasExtra(ACTION_STATE_ARRIVED)){
                    onStateReceived((BreathState) intent.getSerializableExtra(ACTION_STATE_ARRIVED));
                }if(intent.hasExtra(CommonMethod.ACTION_GATT_DISCONNECTED)){
                    startShowingDevicesList();
                }if(intent.hasExtra(ACTION_SOCKET_INERRUPTED)){
                    if(view!=null){
                        view.setSocketClosed();
                    }
                }if(intent.hasExtra(CommonMethod.DEVICE_STATE)){
                    if(view!=null)
                        view.onDeviceStateAvail((DeviceState) intent.getSerializableExtra(CommonMethod.DEVICE_STATE));
                }if(intent.hasExtra(CommonMethod.ACTION_MOVEMENT)){
                    if(view!=null)
                        view.onMovementStarted();
                }if(intent.hasExtra(CommonMethod.ACTION_MOVEMENT_STOPPED)) {
                    if (view != null)
                        view.onMovementStopped();
                }if(intent.hasExtra(ACTION_DEVICE_NOT_ATTACHED_TO_BODY)){
                    boolean isNotAttached=intent.getBooleanExtra(ACTION_DEVICE_NOT_ATTACHED_TO_BODY,false);

                    if(view!=null) {
                        if (isNotAttached)
                            view.onDeviceNotAttachedToBody();
                        else
                            view.onDeviceAttached();
                    }
                }
            }
        }
    };

    public BreathPresenterImp(BreathView view) {
        this.view = view;
        model = new BreathModelImp(this);
    }


    @Override
    public void onDeviceNotConnectedInTime() {
        if(checkNotNull()){
            view.onDeviceDisconnectedInTime();
        }
    }

    @Override
    public void onDataReceivingStarted() {
        if(checkNotNull())
            view.onRemoveSnackbar();
    }

    @Override
    public void startShowingDevicesList() {
        if(view!=null){
            view.onStartDeviceScanActivity();
        }
    }

    @Override
    public void onStateReceived(BreathState state) {
        if(checkNotNull())
            view.onBreathingStateAvailable(state);
    }

    @Override
    public void onStateTimeReceived(StateTimeModel stateTimeModel) {
        if(checkNotNull())
            view.onStateTimeHistoryReceived(stateTimeModel);
    }


    @Override
    public void onCreate() {
            LocalBroadcastManager.getInstance(context).registerReceiver(receiver,new IntentFilter(context.getResources().getString(R.string.action_data_avail)));
        model.onInitStateTime();

//        mTimer2 = new Runnable() {
//            @Override
//            public void run() {
//                graph2LastXValue += 1d;
//                view.onPressureDataAvail(getRandom());
//                mHandler.postDelayed(this, 200);
//            }
//        };
//        mHandler.postDelayed(mTimer2, 1000);
    }



    @Override
    public void onPause() {

    }

    @Override
    public void passContext(Context context) {
        this.context=context;
        model.initDB(context);
        model.checkBLEConnected(context);
    }



    @Override
    public void onAttach() {
      //  model.check
    }

    @Override
    public void onDetach() {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
        model.onDestroy();
        context=null;
        view=null;
    }

    @Override
    public void onInitTimeHistory() {
        model.onInitStateTime();
    }

    @Override
    public void onPressureReceived(double pressure, long ts) {
        if (checkNotNull()) {
            view.onPressureDataAvail(pressure,ts);
        }
    }

    @Override
    public void onCountReceived(int intExtra) {
        if(intExtra>0 && checkNotNull()){
            view.onBreathCountAvailable(intExtra);
        }
    }



    @Override
    public void onStepReceived(int intExtra) {
        if(intExtra>0 && checkNotNull())
            view.onStepCountReceived(intExtra);
    }

    private boolean checkNotNull() {
        return view != null && context!=null;
    }

    private double mLastRandom = 2;
    private Random mRand = new Random();
    private double getRandom() {
        return mLastRandom += mRand.nextDouble()*0.5 - 0.25;
    }

    @Override
    public void initGraphData() {
//        List<Line> lines = new ArrayList<>();
//        Line line = new Line();
//        line.setHasLines(true);
//        line.setHasPoints(false);
//        line.setCubic(true);
//        line.setColor(Color.BLUE);
//        lines.add(line);
//        LineChartData data = new LineChartData(lines);
//        data.setAxisXBottom(null);
//        data.setAxisYLeft(null);
//        data.setBaseValue(Float.NEGATIVE_INFINITY);
//        if(view!=null)
//            view.onLineChartDataInit(data);
    }
}

