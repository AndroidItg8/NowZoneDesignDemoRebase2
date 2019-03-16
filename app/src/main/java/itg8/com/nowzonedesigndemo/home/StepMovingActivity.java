package itg8.com.nowzonedesigndemo.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.common.SharePrefrancClass;
import itg8.com.nowzonedesigndemo.utility.Rolling;
import itg8.com.nowzonedesigndemo.widget.CompassView;

public class StepMovingActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    private static final String TAG = StepMovingActivity.class.getSimpleName();
    public static final String AXIS_Y = "AxisY";
    public static final String AXIS_Z = "AxisZ";
    private static final int ANGLE_MIN = 10;
    private static final int  ANGLE_MAX = 360;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.btn_add)
    Button btnAdd;
    List<Integer> images;
    @BindView(R.id.image)
    CompassView mImage;
    private LineChart mChart;
    private int count = 0;
    private float g = 0;
    private static final int MAX_BEND=150;
    private static final int MIN_BEND=100;
    private float accelY;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            accelY = intent.getFloatExtra(AXIS_Y, 0);
            float accelZ = intent.getFloatExtra(AXIS_Z, 0);
//            addEntry(mChart, accelY);
//            addEntry(mChart2, accelZ);
            //TODO create compass
            if(mImage!=null){
                accelY=calculate(accelY);
                Log.d(TAG,"accelyAngle "+accelY);
                mImage.setBearing((int) accelY);
            }

//            g = 0.9f * g + 0.1f * accel;
//            accel= (float) (accel-g);
//            Log.d(TAG,"GACC:-"+accel+" G:-"+g);

//            detectPeak(accel);

//            addEntry(mChart,accel);
//            double pressure=intent.getDoubleExtra(CommonMethod.BREATH,0);
//            addEntry(mChart2, (float) pressure);

        }
    };

    private float calculate(float accelY) {
        return (ANGLE_MIN + ((ANGLE_MAX - ANGLE_MIN) * ((accelY - (MIN_BEND)) / (MAX_BEND- MIN_BEND))));
    }

    private List<Double> accList = new ArrayList<>();
    private LineChart mChart2;
    private Rolling roll;
    private boolean shouldStart = true;

    private void detectPeak(float accel) {
        if (accList.size() >= 20) {
            detect();
        }
        accList.add((double) accel);
    }

    private boolean check(double avg) {
        return avg < 10 && Math.abs(avg) >= 0;
    }

    private void detect() {

        List<Map<Integer, Double>> result = CommonMethod.peak_detection(accList, 50.0);
        if (result.size() > 0) {
            if (result.get(0) != null && result.get(1) != null) {
                Log.i(TAG, "peaks in accel: " + result.get(0).size());
                if (result.get(0).size() >= 1 && result.get(0).size() <= 3 && result.get(1).size() >= 1 && result.get(1).size() <= 3) {

                    count += 1;
                    shouldStart = true;
//                    feedMultiple();
                    btnAdd.setText("Movement");
                } else if (result.get(0).size() > 3) {
                    shouldStart = true;
//                    feedMultiple();
                    btnAdd.setText("Movement");
                } else {
//                    shouldStart=false;
                    btnAdd.setText("No Movement");
                }
            }
        }
//        if(accel>80){
//        }
        accList.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_moving);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        images = new ArrayList<>();
        Log.v(getClass().getSimpleName(), "" + SharePrefrancClass.getInstance(getApplicationContext()).getPref("TEMP"));
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              image.addStep();
                //addValue();
                mImage.setCalibrated((int)accelY);
            }
        });

        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);
        mChart2 = (LineChart) findViewById(R.id.chart2);
        mChart2.setOnChartValueSelectedListener(this);

        // enable description text
        mChart.getDescription().setEnabled(true);
        mChart2.getDescription().setEnabled(true);

        // enable touch gestures
        mChart.setTouchEnabled(true);
        mChart2.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart2.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart2.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart2.setDrawGridBackground(false);

        setChart(mChart, -276, 277);
        setChart(mChart2, -276, 277);
    }

    private void setChart(LineChart mChart, float max, float min) {
        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.LTGRAY);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        mChart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.WHITE);

        XAxis xl = mChart.getXAxis();
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMaximum(max);
        leftAxis.setAxisMinimum(min);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
        feedMultiple();
    }
    //TODO


    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(CommonMethod.ACTION_AXIS_ACCEL));
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(receiver);
            thread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.actionAdd: {
//                break;
//            }
//            case R.id.actionClear: {
//                mChart.clearValues();
//                Toast.makeText(this, "Chart cleared!", Toast.LENGTH_SHORT).show();
//                break;
//            }
//            case R.id.actionFeedMultiple: {
//                feedMultiple();
//                break;
//            }
//        }
        return true;
    }

    private void addEntry(LineChart mChart, float yData) {

        LineData data = mChart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

//            data.addEntry(new Entry(set.getEntryCount(), (float) (Math.random() * 40) + 30f), 0);
            data.addEntry(new Entry(set.getEntryCount(), yData), 0);
            data.notifyDataChanged();

            // let the chart know it's data has changed
            mChart.notifyDataSetChanged();

            // limit the number of visible entries
            mChart.setVisibleXRangeMaximum(60);
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            mChart.moveViewToX(data.getEntryCount());

            // this automatically refreshes the chart (calls invalidate())
            // mChart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
//        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }

    private Thread thread;

    private void feedMultiple() {

        if (thread != null)
            thread.interrupt();

        final Runnable runnable = new Runnable() {

            @Override
            public void run() {
//                addEntry();
//                image.addStep();
//                addImage();
            }
        };

        thread = new Thread(new Runnable() {

            @Override
            public void run() {

                while (shouldStart) {
                    // Don't generate garbage runnables inside the loop.

                    try {
                        runOnUiThread(runnable);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
        });

//        thread.start();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }


    private void addValue() {
        for (int i = 0; i < 50; i++)
            SharePrefrancClass.getInstance(getApplicationContext()).savePref("TEMP", "VAL");

    }


    private void addImage() {

        images.add(R.drawable.ic_steps);
        setAnimation(images);
        if (images.size() > 3) {
            images.clear();
        }

        Log.d(getClass().getSimpleName(), "images:" + images.size());


    }

    private void setAnimation(List<Integer> images) {
        TranslateAnimation animation;
        float fromXDelta = 0.0f;
        float toXDelta = 60.0f;
        float increaseXDelta = +toXDelta;

        animation = new TranslateAnimation(fromXDelta, toXDelta,
                0.0f, 0.0f);
        if (images.size() > 1) {
            animation = new TranslateAnimation(toXDelta, increaseXDelta,
                    0.0f, 0.0f);
        }
//        image.startAnimation(animation);
        animation.setDuration(5000);
//        animation.setRepeatCount(1);
//        animation.setRepeatMode(1);
        animation.setFillAfter(true);

    }


}
