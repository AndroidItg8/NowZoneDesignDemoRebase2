package itg8.com.nowzonedesigndemo.sanning;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.common.BaseActivity;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.connection.BleService;
import itg8.com.nowzonedesigndemo.connection.DeviceModel;
import itg8.com.nowzonedesigndemo.home.HomeActivity;
import itg8.com.nowzonedesigndemo.sanning.mvp.ScanDeviceModelListener;
import itg8.com.nowzonedesigndemo.sanning.mvp.ScanDevicePresenter;
import itg8.com.nowzonedesigndemo.sanning.mvp.ScanDeviceView;
import itg8.com.nowzonedesigndemo.utility.Helper;
import me.alexrs.wavedrawable.WaveDrawable;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ScanDeviceActivity extends BaseActivity implements ScanDeviceView, EasyPermissions.PermissionCallbacks, View.OnClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final int RC_ACCESS_COURSE_LOCATION = 101;
    private static final int REQUEST_ENABLE_BT = 201;
    private static final String TAG = ScanDeviceActivity.class.getSimpleName();
    ScanDeviceModelListener presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ic_bluetooth)
    ImageView icBluetooth;
    @BindView(R.id.ic_bluetooth_animate)
    ImageView icBluetoothAnimate;
    @BindView(R.id.frame_inside)
    FrameLayout frameInside;
    @BindView(R.id.listOfBluetoothDevices)
    ListView listOfBluetoothDevices;
    @BindView(R.id.frame_listview)
    FrameLayout frameListview;
    @BindView(R.id.bt_status)
    TextView btStatus;
    @BindView(R.id.btn_connect_with_bt)
    Button btnConnectWithBt;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.txtScanningForDevice)
    TextView txtScanningForDevice;
    @BindView(R.id.btn_retry)
    Button btnRetry;
    @BindView(R.id.swipeToRefresh)
    SwipeRefreshLayout swipeToRefresh;
    private WaveDrawable waveDrawable;
    private BluetoothAdapter bluetoothAdapter;
    private DeviceListAdapter deviceListAdapter;
    private boolean firstResult = true;
    private ProgressDialog dialog;
    private BluetoothAdapter mBluetoothAdapter;
    private android.bluetooth.BluetoothGattCallback mGattCallback= new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            Log.d(TAG,"Connection state changed: "+(newState== BluetoothProfile.STATE_CONNECTED?"Connected":"Disconnected"));
        }
    };
    private BluetoothGatt mGatt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_device);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        //Initialise ScanDevicePresenter
        presenter = new ScanDevicePresenter(this);
        presenter.checkAlreadyConnectedOnce(getApplicationContext());
        presenter.onResume();

        initAnimation();
        initBluetoothAdapter();
        btnConnectWithBt.setOnClickListener(this);
        btnRetry.setOnClickListener(this);
        listOfBluetoothDevices.setOnItemClickListener(this);
        swipeToRefresh.setOnRefreshListener(this);

    }

    private void initBluetoothAdapter() {
//        BluetoothManager manager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
//        bluetoothAdapter = manager.getAdapter();
        deviceListAdapter = new DeviceListAdapter(this, new DeviceListAdapter.ConnectClickedListener() {
            @Override
            public void connectClicked(DeviceModel model) {
//                Intent intent = new Intent(getApplicationContext(), BleService.class);
//                intent.putExtra(CommonMethod.DEVICE_ADDRESS, model.getAddress());
//                intent.putExtra(CommonMethod.DEVICE_NAME, model.getName());
//                startService(intent);
//                presenter.cancelScanning();
                presenter.selectedDevice(model,ScanDeviceActivity.this);
                presenter.refreshBtnClicked(linearLayout);
            }
        });
        listOfBluetoothDevices.setAdapter(deviceListAdapter);
        listOfBluetoothDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    private void initAnimation() {
        waveDrawable = new WaveDrawable(Color.parseColor("#8e44ad"), 500);
        presenter.startAnimation(icBluetoothAnimate, waveDrawable);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void checkBleAvailability() {
          /*
        * Check for bluetooth LE Support.In production, our manifest entry will keep this
        * from installing on these devices, but this will allow test devices or other
        * sideloads to report whether or not the feature exists.
        * */
        if (Helper.checkBLE(this)) {
            BluetoothManager bluetoothManager =
                    (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            bluetoothAdapter = bluetoothManager.getAdapter();
            checkBleAdapter();
        } else {
            Snackbar snackbar = Snackbar.make(linearLayout, getResources().getString(R.string.not_have_bluetooth), Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    /*
          * We need to enforce that bluetooth is first enabled, and takes the user
          * to settings to enable it if they have not done so
   * */
    private void checkBleAdapter() {

        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            checkPermission();
        }

    }

    @AfterPermissionGranted(RC_ACCESS_COURSE_LOCATION)
    private void checkPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // Have permission, do the thing!
            checkForLocationOnOff();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.rationale_location),
                    RC_ACCESS_COURSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            checkBleAdapter();
        }
    }

    private void checkForLocationOnOff() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        } else {
            startBleScan();
        }


    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        startBleScan();
    }

    private void startBleScan() {
        Log.d(TAG, "Scan Started");
        presenter.startLEScan(this, bluetoothAdapter);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Snackbar snackbar = Snackbar.make(linearLayout, R.string.rationale_location, Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            snackbar.setActionTextColor(getResources().getColor(R.color.text_color, null));
        } else {
            snackbar.setActionTextColor(getResources().getColor(R.color.text_color));
        }
        snackbar.show();
    }

    @Override
    public void onNewDeviceResult(BluetoothDevice result, int rssi) {
        Log.d(TAG, "New Device Arrived:" + result + " with range: " + rssi);
        toggleView(frameInside, frameListview);
        DeviceModel model = new DeviceModel();
        model.setName(result.getName());
        model.setAddress(result.getAddress());
        model.setRssi(rssi);
        deviceListAdapter.addItemToList(model);
    }

    private void toggleView(View gone, View visible) {
        if (gone.getVisibility() != View.GONE)
            gone.setVisibility(View.GONE);
        if (visible.getVisibility() != View.VISIBLE) {
            visible.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onScanningFail(int errorCode) {
        Log.d(TAG, "BLE Scan Error: " + errorCode);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onSnackbarOkClicked() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onScanningStopped() {
        Log.d(TAG, "BLE Scan Stopped");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_connect_with_bt) {
            presenter.refreshBtnClicked(view);
        } else if (view.getId() == R.id.btn_retry) {
            startBleScan();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        view.setSelected(true);
        DeviceModel model = deviceListAdapter.getItem(position);
        presenter.selectedDevice(model, getBaseContext());
    }



    @Override
    public void startHomeActivity() {
        stopConnectingDialog();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void startConnectingDialog() {
        if(dialog==null)
        {
            showDialog();
        }
        if(!dialog.isShowing()){
            showDialog();
        }
        
    }

    private void showDialog() {
        dialog= ProgressDialog.show(ScanDeviceActivity.this,"","Connecting...",true,true);
    }

    @Override
    public void stopConnectingDialog() {
        if(dialog!=null && dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public void onScanningStarted(CharSequence text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtScanningForDevice.setText(text);
            }
        });
    }

    @Override
    public void onShowScanning() {
        txtScanningForDevice.setVisibility(View.VISIBLE);
        btnConnectWithBt.setVisibility(View.GONE);
        btnRetry.setVisibility(View.GONE);
    }

    @Override
    public void onShowButton() {
        if (deviceListAdapter.getCount() > 0) {
            txtScanningForDevice.setVisibility(View.GONE);
            btnConnectWithBt.setVisibility(View.VISIBLE);
            btnRetry.setVisibility(View.GONE);
        } else {
            txtScanningForDevice.setVisibility(View.GONE);
            btnConnectWithBt.setVisibility(View.GONE);
            btnRetry.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {
        startBleScan();
        swipeToRefresh.setRefreshing(false);
    }
}
