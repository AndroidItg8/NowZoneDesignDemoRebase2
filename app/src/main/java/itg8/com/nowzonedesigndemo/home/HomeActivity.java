package itg8.com.nowzonedesigndemo.home;

import android.Manifest;
import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.breath.BreathHistoryActivity;
import itg8.com.nowzonedesigndemo.common.BaseActivity;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.common.Prefs;
import itg8.com.nowzonedesigndemo.common.SharePrefrancClass;
import itg8.com.nowzonedesigndemo.connection.BleService;
import itg8.com.nowzonedesigndemo.home.fragment.HomeFragment;
import itg8.com.nowzonedesigndemo.home.mvp.BreathPresenter;
import itg8.com.nowzonedesigndemo.home.mvp.BreathPresenterImp;
import itg8.com.nowzonedesigndemo.home.mvp.BreathView;
import itg8.com.nowzonedesigndemo.home.mvp.StateTimeModel;
import itg8.com.nowzonedesigndemo.login.LoginActivity;
import itg8.com.nowzonedesigndemo.sanning.ScanDeviceActivity;
import itg8.com.nowzonedesigndemo.setting.SettingMainActivity;
import itg8.com.nowzonedesigndemo.sleep.SleepActivity;
import itg8.com.nowzonedesigndemo.steps.StepsActivity;
import itg8.com.nowzonedesigndemo.steps.widget.CustomFontTextView;
import itg8.com.nowzonedesigndemo.utility.BreathState;
import itg8.com.nowzonedesigndemo.utility.DeviceState;
import itg8.com.nowzonedesigndemo.utility.Rolling;
import itg8.com.nowzonedesigndemo.widget.steps.CustomStepImage;
import itg8.com.nowzonedesigndemo.widget.wave.BreathwaveView;
import itg8.com.nowzonedesigndemo.widget.wave.WaveLoadingView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.Timber;

import static itg8.com.nowzonedesigndemo.connection.BleService.ACTION_SERFVER_IP_CHANGED;

public class HomeActivity extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, BreathView, EasyPermissions.PermissionCallbacks, ComponentCallbacks2 {


    //    public static final String COLOR_CALM_M = "#240CB700";
//    public static final String COLOR_STRESS_M = "#24B70F00";
//    public static final String COLOR_FOCUSED_M = "#240C00B7";
//    public static final double CONST_1 = -8.02d;
//    public static final double CONST_2 = 8.02d;
    private static final int RC_STORAGE_PERM = 20;


    private static final String COLOR_NORMAL_M = "#24006bb7";
    private static final String COLOR_NORMAL_S = "#27BEFB";
    public static final String COLOR_CALM_M = "#240CB700";
    private static final String COLOR_CALM_S = "#FF35FB27";
    public static final String COLOR_STRESS_M = "#24B70F00";
    private static final String COLOR_STRESS_S = "#FFF92E27";
    public static final String COLOR_FOCUSED_M = "#240C00B7";
    private static final String COLOR_FOCUSED_S = "#FF4027FB";
    private static final int LAST_333 = 333;
    public static final double CONST_1 = -8.02d;
    public static final double CONST_2 = 8.02d;
    private static final double PI_MIN = -8.02d;
    private static final double PI_MAX = 8.02d;
    private static final double MIN_PRESSURE = 1100;
    private static final double MAX_PRESSURE = 8100;
    private static final float MAX_CIRCLE_SIZE = 100f;
    private static final float MIN_CIRCLE_SIZE = 1f;
    private static final String IP_ADRESS_LBL = "Ip Address : ";
    private static final String PORT = "8080";
    private final String TAG = this.getClass().getSimpleName();

    long n = 0;
    double mu = 0.0;
    double sq = 0.0;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.waveLoadingView)
    WaveLoadingView waveLoadingView;
    @BindView(R.id.breathview)
    BreathwaveView breathview;
    @BindView(R.id.rl_wave)
    FrameLayout rlWave;
    @BindView(R.id.img_breath)
    ImageView imgBreath;
    @BindView(R.id.txt_breathRate)
    TextView txtBreathRate;
    @BindView(R.id.txt_statusValue)
    TextView txtStatusValue;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.txtConnectionStatus)
    TextView txtConnectionStatus;
    @BindView(R.id.breathValue)
    TextView breathValue;
    @BindView(R.id.txt_minute)
    TextView txtMinute;
    @BindView(R.id.rl_breath)
    RelativeLayout rlBreath;
    @BindView(R.id.txt_calm)
    TextView txtCalm;
    @BindView(R.id.txt_calm_value)
    TextView txtCalmValue;
    @BindView(R.id.txt_focus)
    CustomFontTextView txtFocus;
    @BindView(R.id.txt_focus_value)
    CustomFontTextView txtFocusValue;
    @BindView(R.id.txt_stress)
    TextView txtStress;
    @BindView(R.id.txt_stress_value)
    TextView txtStressValue;
    @BindView(R.id.txtIpAddress)
    EditText txtIpAddress;
    @BindView(R.id.txtStartSocket)
    Button btnStartSocket;
    @BindView(R.id.main_FrameLayout)
    FrameLayout mainFrameLayout;
    @BindView(R.id.rl_main_top)
    RelativeLayout rlMainTop;
    @BindView(R.id.txt_breath)
    CustomFontTextView txtBreath;
    @BindView(R.id.txt_device_not_attached)
    CustomFontTextView mTxtDeviceNotAttached;
    @BindView(R.id.txt_breathCount)
    CustomFontTextView txtBreathCount;
    @BindView(R.id.txt_AvgBreathValue)
    TextView txtAvgBreathValue;
    @BindView(R.id.ll_breath_avg)
    RelativeLayout llBreathAvg;
    @BindView(R.id.txt_forth)
    CustomFontTextView txtForth;
    @BindView(R.id.txt_hour)
    TextView txtHour;
    @BindView(R.id.txt_hourValue)
    TextView txtHourValue;
    @BindView(R.id.ll_sleep_main)
    RelativeLayout llSleepMain;
    @BindView(R.id.txt_step)
    CustomFontTextView txtStep;
    @BindView(R.id.txt_stepCount)
    CustomFontTextView txtStepCount;
    @BindView(R.id.txt_stepCountValue)
    TextView txtStepCountValue;
    @BindView(R.id.rlSteps)
    RelativeLayout rlSteps;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.image)
    CustomStepImage mStepImage;
    @BindView(R.id.rl_breathView)
    RelativeLayout mRlBreathView;
    private ActionBarDrawerToggle toggle;
    private double lastMax = 10;
    private int count = 1;
    private double lastMin = -10;
    private int lastCount = 1;
    private long lastUpdate = 0;
    private double smoothed = 0;
    private static final double smoothing = 50;
    Rolling rolling;
    private double dLast;
    private float a = 0.96f;
    private boolean socketStarted = false;
    private Double lastPressure;
    private Fragment fragment;
    private FragmentManager fm;
    private BreathPresenter presenter;

//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ButterKnife.bind(this);

        presenter = new BreathPresenterImp(this);
        presenter.passContext(HomeActivity.this);
        presenter.onCreate();

        // setIds();
        setFragment();

        Timber.tag(TAG);

        rolling = new Rolling(8);
        if (!getIntent().hasExtra(CommonMethod.FROMWEEk)) {
            startService(new Intent(this, BleService.class));
            checkDeviceConnection(rlWave);

            //   checkDeviceConnection(rlWave);
        }

//        Intent u=new Intent(this, LoginActivity.class);
//        startActivity(u);


        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        checkStoragePermission();
        setType();

        btnStartSocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(txtIpAddress.getText().toString())) {
                    Toast.makeText(HomeActivity.this, "Please insert ip address", Toast.LENGTH_SHORT).show();
                    return;
                }
                String ip = txtIpAddress.getText().toString();
                Intent intent = new Intent(ACTION_SERFVER_IP_CHANGED);
                if (!socketStarted) {
                    socketStarted = true;
                    btnStartSocket.setText("STOP");
                    txtIpAddress.setFocusableInTouchMode(false);
                    txtIpAddress.setFocusable(false);
                    intent.putExtra(CommonMethod.IP_ADDRESS, ip);
                } else {
                    socketStarted = false;
                    btnStartSocket.setText("START");
                    txtIpAddress.setFocusableInTouchMode(true);
                    txtIpAddress.setFocusable(true);
                    intent.putExtra(CommonMethod.SOCKET_STOP_CLICKED, true);
                }
                LocalBroadcastManager.getInstance(HomeActivity.this).sendBroadcast(intent);
            }
        });


//        String ipAdd = Utils.getIPAddress(true);
//        SharePrefrancClass.getInstance(this).savePref(CommonMethod.IP_ADDRESS, ipAdd);
//        ipAdd = IP_ADRESS_LBL + ipAdd + ":" + PORT;
//        txtIpAddress.setText(ipAdd);

//        setType();
//        waveLoadingView.setWaveBgColor(getResources().getColor(R.color.color_wave_normal,null));
//        waveLoadingView.setWaveColor(getResources().getColor(R.color.color_wave_normal_bg,null));
        initOtherView();

        llBreathAvg.setOnClickListener(this);
        rlSteps.setOnClickListener(this);
    }

    private void setIds() {
        View includeBottom = findViewById(R.id.layout_include);
        ImageView imgSleep = (ImageView) includeBottom.findViewById(R.id.img_sleep);
        ImageView imgBreath = (ImageView) includeBottom.findViewById(R.id.img_breath);
        RelativeLayout rlMainBottom = (RelativeLayout) includeBottom.findViewById(R.id.rl_main_bottom);
        TextView txtAvgBreathValue = (TextView) includeBottom.findViewById(R.id.txt_AvgBreathValue);

    }


    @AfterPermissionGranted(RC_STORAGE_PERM)
    private void checkStoragePermission() {
        if (EasyPermissions.hasPermissions(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            onPermissionGrantedForStorage();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_storage), RC_STORAGE_PERM, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void setFragment() {
        fragment = new HomeFragment();
        fm = getSupportFragmentManager();
//        fm.beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    private void onPermissionGrantedForStorage() {
        File extStorageDir = Environment.getExternalStorageDirectory();
        File newExternalStorageDir = new File(extStorageDir, getResources().getString(R.string.app_name));
        if (!newExternalStorageDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            newExternalStorageDir.mkdir();
        }
        if (newExternalStorageDir.exists()) {
            newExternalStorageDir = new File(newExternalStorageDir, getResources().getString(R.string.breath));
            if (!newExternalStorageDir.exists())
                //noinspection ResultOfMethodCallIgnored
                newExternalStorageDir.mkdir();
        }

        SharePrefrancClass.getInstance(getApplicationContext()).savePref(CommonMethod.STORAGE_PATH, newExternalStorageDir.getAbsolutePath());

    }


    private void initOtherView() {
        int mAvgCount = SharePrefrancClass.getInstance(getApplicationContext()).getIPreference(CommonMethod.USER_CURRENT_AVG);
        if (mAvgCount > 0) {
            setAvgValue(mAvgCount);
        }
    }


    @Override
    protected void onPause() {
        if(mStepImage!=null)
            mStepImage.onRemove();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        waveLoadingView.cancelAnimation();
        presenter.onDetach();
        super.onDestroy();
    }

    private void setType() {


        // Change Now
//        waveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
//
//        waveLoadingView.setAmplitudeRatio(20);
//        waveLoadingView.setProgressValue(10);
//        waveLoadingView.setBorderWidth(1f);


//        waveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
//        waveLoadingView.setAmplitudeRatio(20);
//        waveLoadingView.setProgressValue(50);

//        waveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
//        waveLoadingView.setTopTitle("Top Title");
//        waveLoadingView.setCenterTitleColor(Color.GRAY);
//        waveLoadingView.setBottomTitleSize(18);
//        waveLoadingView.setProgressValue(50);
//        waveLoadingView.setBorderWidth(10);
//        waveLoadingView.setAmplitudeRatio(60);
//        waveLoadingView.setWaveColor(Color.GREEN);
//        waveLoadingView
//        waveLoadingView.setBorderColor(Color.GRAY);
//        waveLoadingView.setTopTitleStrokeColor(Color.BLUE);
//        waveLoadingView.setTopTitleStrokeWidth(3);

    }

    /**
     * this is set by me
     */
    private void setAnimator() {

        //mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
        // Sets the length of the animation, default is 1000.


//        waveLoadingView.setAnimDuration(3000);
//        waveLoadingView.startAnimation();


        //  waveLoadingView.cancelAnimation();
        // waveLoadingView.resumeAnimation();
        //                    waveLoadingView.pauseAnimation();


    }

    @Override
    protected void onResume() {
        if (breathview != null)
            breathview.reset();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_settings:
                callSettingActvity(CommonMethod.FROM_ALARM_HOME);

                // startActivity(new Intent(getApplicationContext(), AlarmSettingActivity.class));

                break;
            case R.id.action_profile:
                callSettingActvity(CommonMethod.FROM_PROFILE);
//                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                break;
            case R.id.action_device:
                callSettingActvity(CommonMethod.FROM_DEVICE);
                break;
            case R.id.action_meditation:
                //startActivity(new Intent(getApplicationContext(),AudioActivity.class));
                callSettingActvity(CommonMethod.FROM_MEDITATION);

                break;
            case R.id.action_alram:
                //startActivity(new Intent(getApplicationContext(), AlarmActivity.class));
                callSettingActvity(CommonMethod.FROM_ALARM_SETTING);
                break;
            case R.id.action_step_goal:
                callSettingActvity(CommonMethod.FROM_STEP_GOAL);
                //  startActivity(new Intent(getApplicationContext(), StepGoalActivity.class));
                break;
            case R.id.action_about:
                startActivity(new Intent(getApplicationContext(), StepMovingActivity.class));
                break;
            case R.id.action_logout:
                onDeviceDisconnected();
                break;
            case R.id.action_device_history:
                callSettingActvity(CommonMethod.FROM_DEVICE_HISTORY);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void callSettingActvity(String fromDevice) {
        Intent intent = new Intent(getApplicationContext(), SettingMainActivity.class);
        intent.putExtra(CommonMethod.BREATH, fromDevice);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onPressureDataAvail(double pressure, long ts) {
//            firstPreference(pressure);
        //Second Preference

        Observable.create(new ObservableOnSubscribe<Double>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<Double> e) throws Exception {
//                firstPreference(pressure);
                secondPref(pressure);
                e.onNext(calculateProportion(pressure));
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Double>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Double aDouble) {
                        if (count > 30) {
//            Log.d(TAG, "Presssure: "+pressure+" value after smoothing: " + smoothedValue(pressure) + " proportion:" + calculateProportion(smoothedValue(pressure)));
//            secondPref(pressure);
//            breathview.addSample(SystemClock.elapsedRealtime(),calculateProportion(smoothedValue(pressure)));
                            breathview.addSample(SystemClock.elapsedRealtime(), aDouble);
//                            Log.d(TAG,"Progress wave: "+aDouble.intValue());
//
//                                waveLoadingView.setProgressValue(aDouble.intValue());
//                            }
//                            lastPressure=aDouble;
                        } else {
                            count++;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
//        breathview.addSample(SystemClock.elapsedRealtime(), calculateProportion(pressure));

    double smoothedValue(double pressure) {
//        long now = Calendar.getInstance().getTimeInMillis();
//        long elapsedTime = now - lastUpdate;
        smoothed += 33 * ((pressure - smoothed) / smoothing);
//        lastUpdate = now;
        return smoothed;
    }

    private void secondPref(double pressure) {
        rolling.add(pressure);
        lastMax = ((int) rolling.getaverage() + 500) + 1;
        lastMin = (int) rolling.getaverage() - 500;
    }

    private void firstPreference(double pressure) {
//        if(lastMax<pressure || lastMax-2000>pressure) {
        if (lastMax < pressure) {
            lastMax = pressure;
        } else {
            lastMax = lastMax - 1;
        }
        if (count > 30) {
            if (lastMin == 0)
                lastMin = pressure;


//            if(lastMin-pressure>lastMax-1000)
//                lastMin=lastMax-1000;
//            if(lastMin)
//            if(count%LAST_333==0)
//                lastMin=lastMax-500;
//            else

//
//                if(lastMin>lastMax-1000)
//                    lastMax=lastMin+1000;

//            if(lastMax-lastMin>2000) {
//                lastMin = lastMax - 2000;
//            }
//            else
//                lastMax=lastMin+2000;
            if (lastMin > pressure)
                lastMin = pressure;
            else
                lastMin = lastMin + 1;
//
////            if (lastMin > pressure || lastMin == 0){
////                lastMin = pressure;
////        }
        } else {
            count++;
        }


    }

    private double calculateProportion(double pressure) {
//        return (-0.02+(1.02*((pressure-(lastMax-500))/(lastMax-(lastMax-500)))));
//        double d=(double) Math.round((CONST_1+(CONST_2*((pressure-(lastMin))/(lastMax-lastMin)))) * 1000000000000000000d) / 1000000000000000000d;
//        s(i)=a*y(i)+(1-a)*s(i-1)


        double d = a * pressure + ((1 - a) * dLast);
//        double d=pressure;
//        rolling.add(pressure);
//        d=rolling.getaverage();
        dLast = d;
        // Log.d(TAG,"ds:"+d);
        return (PI_MIN + ((PI_MAX - PI_MIN) * ((d - (MIN_PRESSURE)) / (MAX_PRESSURE - MIN_PRESSURE))));

//        update(pressure);
//        Log.d(TAG, String.valueOf(var()));

//        return (MIN_CIRCLE_SIZE + ((MAX_CIRCLE_SIZE- MIN_CIRCLE_SIZE) * ((d - (lastMin)) / (lastMax - lastMin))));
//        return (lastMin + ((lastMax- lastMin) * ((d - (MIN_PRESSURE)) / (MAX_PRESSURE - MIN_PRESSURE))));
    }

    void update(double x) {
        ++n;
        double muNew = mu + (x - mu) / n;
        sq += (x - mu) * (x - muNew);
        mu = muNew;
    }

    double mean() {
        return mu;
    }

    double var() {
        return n > 1 ? sq / n : 0.0;
    }

    @Override
    public void onDeviceConnected() {
        Log.d(TAG, "Device connected");
    }

    @Override
    public void onDeviceDisconnected() {
        Log.d(TAG, "Device disconnected");
        Intent intent = new Intent("ACTION_NW_DEVICE_DISCONNECT");
        intent.putExtra(CommonMethod.ENABLE_TO_CONNECT, true);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
//        Prefs.remove(CommonMethod.ISLOGIN);
//        Prefs.remove(CommonMethod.TOKEN);
    }

    @Override
    public void onDeviceDisconnectedInTime() {
        Log.d(TAG, "Device disconnected");
        Intent intent = new Intent("ACTION_NW_DEVICE_DISCONNECT");
        intent.putExtra(CommonMethod.ENABLE_TO_CONNECT_IN_TIME, true);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    @Override
    public void setSocketClosed() {
        Log.i(TAG, "Socket Closed");
        btnStartSocket.setText("START");
        txtIpAddress.setFocusableInTouchMode(true);
        txtIpAddress.setFocusable(true);
        socketStarted = false;
    }


    @Override
    public void onMovementStarted() {
        mStepImage.startSteps();
        mRlBreathView.setVisibility(View.GONE);
        mStepImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMovementStopped() {
        mStepImage.stopSteps();
        mRlBreathView.setVisibility(View.VISIBLE);
        mStepImage.setVisibility(View.GONE);
    }

    @Override
    public void onDeviceNotAttachedToBody() {
        mTxtDeviceNotAttached.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDeviceAttached() {
        mTxtDeviceNotAttached.setVisibility(View.GONE);
    }


    @Override
    public void onNotLoginYet() {
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDeviceStateAvail(DeviceState deviceState) {
        if (deviceState.name() != null)
            txtConnectionStatus.setText(deviceState.name());
        if (deviceState == DeviceState.CONNECTED) {
            hideSnackbar();
        }
    }

    @Override
    public void onBreathCountAvailable(int intExtra) {
        Log.d(TAG, "Breath count: " + intExtra);
        new Handler().postDelayed(() -> breathValue.setText(String.valueOf(intExtra)), 30);
    }

    @Override
    public void onStepCountReceived(int intExtra) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                txtStepCountValue.setText(String.valueOf(intExtra));
            }
        });
        Log.d(TAG, "Step count: " + intExtra);
    }

    @Override
    public void onStartDeviceScanActivity() {
        Timber.i("Start device activity");
//        startActivity(new Intent(this, ScanDeviceActivity.class));
//        finish();
        checkDeviceConnection(rlWave);
    }


    @Override
    public void onBreathingStateAvailable(BreathState state) {
        initOtherView();
        setStateRelatedDetails(state);
        // presenter.onInitTimeHistory();
    }

    @Override
    public void onStateTimeHistoryReceived(StateTimeModel stateTimeModel) {
        if (stateTimeModel != null) {
            txtCalmValue.setText(stateTimeModel.getCalm());
            txtFocusValue.setText(stateTimeModel.getFocus());
            txtStressValue.setText(stateTimeModel.getStress());
        }
    }

    @Override
    public void onRemoveSnackbar() {
        hideSnackbar();
    }

    private void setStateRelatedDetails(BreathState state) {
        switch (state) {
            case CALM:
                reactCalmState();
                break;
            case FOCUSED:
                reactFocusedState();
                break;
            case STRESS:
                reactStressState();
                break;
        }

    }

    private void reactStressState() {
        setState(BreathState.STRESS.name(), Color.parseColor(COLOR_STRESS_M), Color.parseColor(COLOR_STRESS_S));
    }

    private void setState(String name, int m, int s) {
        txtStatusValue.postDelayed(new Runnable() {
            @Override
            public void run() {
                txtStatusValue.setText(name);
            }
        }, 60);
//        waveLoadingView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                waveLoadingView.setWaveColor(m);
//            }
//        },30);
//        waveLoadingView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                waveLoadingView.setWaveBgColor(s);
//            }
//        },90);
    }

    private void reactFocusedState() {
        setState(BreathState.FOCUSED.name(), Color.parseColor(COLOR_FOCUSED_M), Color.parseColor(COLOR_FOCUSED_S));
    }

    private void reactCalmState() {
        setState(BreathState.CALM.name(), Color.parseColor(COLOR_CALM_M), Color.parseColor(COLOR_CALM_S));
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        onPermissionGrantedForStorage();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }


    @Override
    public void onSnackbarOkClicked() {
        onDeviceDisconnected();
        SharePrefrancClass.getInstance(getApplicationContext()).clearPref(CommonMethod.CONNECTED);
        SharePrefrancClass.getInstance(getApplicationContext()).clearPref(CommonMethod.DEVICE_ADDRESS);
        startActivity(new Intent(getBaseContext(), ScanDeviceActivity.class));
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlSteps:
                Intent intent = new Intent(getApplicationContext(), StepsActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_sleep_main:
                startActivity(new Intent(getApplicationContext(), SleepActivity.class));
                break;
            case R.id.ll_breath_avg:
                startActivity(new Intent(getApplicationContext(), BreathHistoryActivity.class));
                break;

        }
    }


    private void setAvgValue(int mAvgCount) {
        txtAvgBreathValue.setVisibility(View.VISIBLE);
        txtAvgBreathValue.setText(String.valueOf(mAvgCount));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
