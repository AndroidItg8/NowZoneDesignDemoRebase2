package itg8.com.nowzonedesigndemo.sleep;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.sleep.adapter.ViewPagerSleepAdapter;
import itg8.com.nowzonedesigndemo.steps.widget.CustomMarkerView;

import static android.R.attr.data;

public class SleepActivity extends BaseSleepClass implements OnChartValueSelectedListener, View.OnClickListener {

    private static final String TAG = SleepActivity.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_left)
    ImageView imgLeft;
    @BindView(R.id.txt_day)
    TextView txtDay;
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.barchart)
    LineChart mChart;
    @BindView(R.id.txt_sleepTime)
    TextView txtSleepTime;
    @BindView(R.id.txt_sleepValue)
    TextView txtSleepValue;
    @BindView(R.id.txt_sleepEndValue)
    TextView txtSleepEndValue;
    @BindView(R.id.txt_sleepEndTime)
    TextView txtSleepEndTime;
    @BindView(R.id.rl_time)
    RelativeLayout rlTime;

  //  private final String[] mLabels = {"Deep", "Awake", "Light"};
    ArrayList<String> mLabels =new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtDay.setText("Yesterday");

        if(getSupportActionBar()!=null) {
            this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        imgLeft.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        setUpViewPager();
       // setPieChart();

     //   initBarChart();
     //  initPositiveNegativeChart();
        cubicLineChart();



    }

    private void cubicLineChart() {
        mChart.setViewPortOffsets(60, 05, 60, 05);
//        mChart.setBackgroundColor(Color.TRANSPARENT);
        // no description text
        // enable touch gestures
        mChart.setTouchEnabled(true);
        mChart.getDescription().setEnabled(false);
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(false);
        mChart.setHighlightPerTapEnabled(true);
        mChart.setHorizontalScrollBarEnabled(true);
        mChart.setVisibleXRangeMaximum(10);
        mChart.moveViewToX(10);
        mChart.setMaxHighlightDistance(300);

        XAxis x = mChart.getXAxis();
        x.setEnabled(true);
        x.setDrawGridLines(false);
        x.setAvoidFirstLastClipping(true);
        x.setLabelCount(6, true);
        x.setAxisLineColor(Color.GRAY);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawAxisLine(true);
        x.setAxisLineWidth(1.3f);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);




        YAxis y = mChart.getAxisLeft();
        y.setLabelCount(3, true);
        y.setTextColor(Color.WHITE);
        y.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.GRAY);
        y.setYOffset(20f);
        mLabels.add("Deep");
        mLabels.add("Awake");
        mLabels.add("Light");
        mChart.getAxisRight().setEnabled(false);
        mChart.getLegend().setEnabled(false);
        mChart.animateXY(2000, 2000);
        mChart.invalidate();
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        final List<Data> data = new ArrayList<>();
        data.add(new Data(0f,50f, "11-1"));
        data.add(new Data(1f, 2338.5f, "1-3"));
        data.add(new Data(2f, -2438.1f, "3-5"));
        data.add(new Data(3f,  50f, "5-6"));
        data.add(new Data(4f, -2238.1f, "6-8"));
        data.add(new Data(5f, 50f, "11-1"));
        data.add(new Data(6f, 2338.5f, "1-3"));
        data.add(new Data(7f, -2438.1f, "3-5"));
        data.add(new Data(8f,  -1538.1f, "5-6"));
        data.add(new Data(9f, 50f, "6-8"));
        List<Integer> colors = new ArrayList<Integer>();

        List<String> timeList = new ArrayList<>();
        timeList.add("11-1");
        timeList.add("1-3");
        timeList.add("3-5");
        timeList.add("5-6");
        timeList.add("6-8");

        x.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return timeList.get(Math.min(Math.max((int) value, 0), timeList.size()-1));
            }
        });

        y.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mLabels.get(Math.min(Math.max((int) value, 0), mLabels.size()-1));
            }
        });
        setDataForCubicLineChart(colors,yVals, data);



    }

    private void setDataForCubicLineChart(List<Integer> colors, ArrayList<Entry> yVals, List<Data> data) {

        int blue = Color.parseColor("#00B0EC");
        //rgb(110, 190, 102);
        int blueLight =  Color.parseColor("#7f6ecded");
        int awakeColor =  Color.parseColor("#F39CDEF4");

        Data d = null;
        for (int i = 0; i < data.size(); i++) {
        d = data.get(i);
            BarEntry entry = new BarEntry(d.xValue, d.yValue);
            Log.d(TAG, "xValue:" + d.xValue + "yValue:" + d.yValue);
            yVals.add(entry);
        }
        LineDataSet set1;
            if (mChart.getData() != null &&
                    mChart.getData().getDataSetCount() > 0) {
                set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
                set1.setValues(yVals);
                mChart.getData().notifyDataChanged();
                mChart.notifyDataSetChanged();
            } else {
                // create a dataset and give it a type
                set1 = new LineDataSet(yVals, "DataSet 1");

                set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                set1.setCubicIntensity(0.2f);

                set1.setDrawFilled(true);
                set1.setDrawCircles(false);
                set1.setLineWidth(0.5f);
                set1.setHighLightColor(Color.TRANSPARENT);
                set1.setCircleRadius(0.5f);
                set1.setCircleColor(Color.WHITE);
//                set1.setHighLightColor(Color.rgb(244, 117, 117));


                // specific colors

                set1.setColor(Color.WHITE);

                if (d.yValue >= 0) {
                    colors.add(blue);
                    set1.setFillColor(colors.get(0));
                }
                else {
                    colors.add(blueLight);
                    set1.setFillColor(colors.get(1));
                }

                if (d.yValue >= 0 && d.yValue <= 100) {
                    colors.add(awakeColor);
                    set1.setFillColor(Color.WHITE);
                }

//                set1.setFillAlpha(50);
                set1.setDrawFilled(true);

                set1.setDrawHorizontalHighlightIndicator(true);
//
                LineData dataLine = new LineData(set1);
                dataLine.setValueTextSize(9f);
                dataLine.setDrawValues(false);

                CustomMarkerView mv = new CustomMarkerView(getApplicationContext(), R.layout.linechart_three_tooltip);
                mv.setChartView(mChart);
                mv.setPadding(0, 0, 0, 0);
                mChart.setMarkerView(mv);
                // set data
                mChart.setData(dataLine);
            }
        }


    private void initPositiveNegativeChart() {
        mChart.setBackgroundColor(Color.TRANSPARENT);
        mChart.setExtraTopOffset(-30f);
        mChart.setExtraBottomOffset(10f);
        mChart.setExtraLeftOffset(70f);
        mChart.setExtraRightOffset(70f);
     //   mChart.setDrawValueAboveBar(true);
        mChart.getLegend().setEnabled(false);
        mChart.getDescription().setEnabled(false);
        mChart.setPinchZoom(false);
        mChart.animateY(3000);
        //Add now
      //  mChart.setDrawBarShadow(true);
        mChart.setClickable(false);
        mChart.setTouchEnabled(false);


        mChart.setDrawGridBackground(false);
//        mChart.setDrawBarShadow(false);
//        mChart.setDrawValueAboveBar(false);
//        mChart.setHighlightFullBarEnabled(false);
        mChart.setDrawBorders(false);
        mChart.setHighlightPerTapEnabled(false);
        mChart.setDoubleTapToZoomEnabled(false);
        mChart.setScaleEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);


        // scaling can now only be done on x- and y-axis separately

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(Color.LTGRAY);
        xAxis.setTextSize(13f);
        xAxis.setLabelCount(4);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f);

        YAxis left = mChart.getAxisLeft();
        left.setEnabled(true);
        left.setDrawLabels(true);
        left.setSpaceTop(25f);
        left.setSpaceBottom(25f);
        left.setDrawAxisLine(true);
        left.setDrawGridLines(false);
        left.setDrawZeroLine(true); // draw a zero line
        left.setZeroLineColor(Color.GRAY);
        left.setZeroLineWidth(0.7f);
        left.setTextColor(Color.WHITE);
        left.setTextSize(12f);
        left.setCenterAxisLabels(true);
        left.setLabelCount(3,true);
        left.setGranularity(1f);
        mLabels.add("Deep");
        mLabels.add("Awake");
        mLabels.add("Light");


        left.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return  mLabels.get(Math.min(Math.max((int) value, 0), mLabels.size()-1));
            }
        });

        mChart.getAxisRight().setEnabled(false);
        mChart.getLegend().setEnabled(false);
    //    mChart.setFitBars(true);
        mChart.setHighlightPerTapEnabled(false);



        // THIS IS THE ORIGINAL DATA YOU WANT TO PLOT
        final List<Data> data = new ArrayList<>();
        data.add(new Data(0f, -1538.1f, "11-1"));
        data.add(new Data(1f, 2338.5f, "1-3"));
        data.add(new Data(2f, -2438.1f, "3-5"));
        data.add(new Data(3f, 50f, "5-6"));
        data.add(new Data(4f, -2238.1f, "6-8"));

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return data.get(Math.min(Math.max((int) value, 0), data.size()-1)).xAxisValue;
            }
        });

        setData(data);
    }

    private void initBarChart() {
//        mChart.setOnChartValueSelectedListener(this);

        mChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
//        // drawn
//        mChart.setMaxVisibleValueCount(40);
//
//
//        // scaling can now only be done on x- and y-axis separately
//        mChart.setPinchZoom(false);
//        mChart.animateY(3000);
//
//
//        mChart.setDrawGridBackground(false);
////        mChart.setDrawBarShadow(false);
////
////        mChart.setDrawValueAboveBar(false);
////        mChart.setHighlightFullBarEnabled(false);
//        mChart.setDrawBorders(false);
//        mChart.setHighlightPerTapEnabled(false);
//        mChart.setDoubleTapToZoomEnabled(false);
//        mChart.setScaleEnabled(false);
//        mChart.getLegend().setEnabled(false);
//
//
//        // change the position of the y-labels
//        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setDrawGridLines(false);
//        leftAxis.setTextColor(Color.TRANSPARENT);
//        leftAxis.setValueFormatter(new MyAxisValueFormatter());
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        mChart.getAxisRight().setEnabled(false);
//
//        XAxis xLabels = mChart.getXAxis();
//        xLabels.setDrawGridLines(false);
//        xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xLabels.setTextColor(Color.WHITE);


        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
       // mChart.setMaxVisibleValueCount(40);



        onChartReadyToFillData();

    }

    //    private void setPieChart() {
//        mChart.setUsePercentValues(true);
//        mChart.getDescription().setEnabled(false);
//
//        mChart.setDrawHoleEnabled(true);
//        mChart.setHoleColor(Color.TRANSPARENT);
//
//        mChart.setTransparentCircleColor(Color.WHITE);
//        mChart.setTransparentCircleAlpha(110);
//
//        mChart.setHoleRadius(30f);
//        mChart.setTransparentCircleRadius(30f);
//
//        mChart.setDrawCenterText(true);
//
//        mChart.setRotationEnabled(false);
//        mChart.setHighlightPerTapEnabled(true);
//
//        mChart.setMaxAngle(360f); // HALF CHART
//        mChart.setRotationAngle(180f);
//        mChart.setCenterTextOffset(0, -20);
//
//        setDataForPieChart(3, 100);
//
//        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
//
//        Legend l = mChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(4f);
//
//        // entry label styling
//        mChart.setEntryLabelColor(Color.WHITE);
//        mChart.setEntryLabelTextSize(12f);
//    }



    private void setDataForPieChart(int count, int range) {

        ArrayList<PieEntry> values = new ArrayList<PieEntry>();

        for (int i = 0; i < count; i++) {
            String[] mParties={"","",""};
            values.add(new PieEntry((float) ((Math.random() * range) + range / 5), mParties[i % mParties.length]));
        }

        PieDataSet dataSet = new PieDataSet(values, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        // mChart.setData(data);

        mChart.invalidate();
    }

    private void setUpViewPager() {
        FragmentManager fm = getSupportFragmentManager();
        viewPager.setAdapter(new ViewPagerSleepAdapter(this, fm));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onChartReadyToFillData() {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

//        for (int i = 0; i < mSeekBarX.getProgress() + 1; i++) {
//            float mult = (mSeekBarY.getProgress() + 1);
//            float val1 = (float) (Math.random() * mult) + mult / 3;
//            float val2 = (float) (Math.random() * mult) + mult / 3;
//            float val3 = (float) (Math.random() * mult) + mult / 3;
//
//            yVals1.add(new BarEntry(
//                    i,
//                    new float[]{val1, val2, val3},
//                    getResources().getDrawable(R.drawable.star)));
//        }

        yVals1.add(new BarEntry(1,new float[]{3f}));
        yVals1.add(new BarEntry(2,new float[]{3f}));
        yVals1.add(new BarEntry(3,new float[]{3f}));
        yVals1.add(new BarEntry(4,new float[]{3f}));
        yVals1.add(new BarEntry(5,new float[]{3f}));
        yVals1.add(new BarEntry(6,new float[]{3f}));

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
         //   set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
         //   set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "");
            set1.setDrawIcons(false);
            set1.setColors(getColors());
            set1.setValueTextSize(Color.WHITE);


            set1.setStackLabels(new String[]{"Awake", "Light", "Deep"});
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setBarWidth(1f);

         //  data.setValueFormatter(new MyValueFormatter());
            data.setValueTextColor(Color.WHITE);
       //     mChart.setData(data);
        }

      //  mChart.setFitBars(true);
        mChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private int[] getColors() {

        int stacksize = 6;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];
//            <color name="colorGreen"># 00CBAC</color>
//    <color name="colorBlue">#00B0EC</color>
//    <color name="colorOrange">#D4A280</color>

        colors[0] = Color.parseColor("#D4A280");
        colors[1] = Color.parseColor("#00B0EC");
        colors[2] = Color.parseColor("#00CBAC");
//        colors[3] = Color.parseColor("#D4A280");
//        colors[4] = Color.parseColor("#00B0EC");
//        colors[5] = Color.parseColor("#00CBAC");

//        for (int i = 0; i < colors.length; i++) {
//            colors[i] = ColorTemplate.MATERIAL_COLORS[i];
//
//        }
        return colors;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img_left:
                txtDay.setText("Today");
                break;

            case R.id.img_right:
                txtDay.setText("Tomorrow");
                break;
        }
    }

    private class Data {

        public String xAxisValue;
        public float yValue;
        public float xValue;

        public Data(float xValue, float yValue, String xAxisValue) {
            this.xAxisValue = xAxisValue;
            this.yValue = yValue;
            this.xValue = xValue;
        }
    }

    private void setData(List<Data> dataList) {

        ArrayList<BarEntry> values = new ArrayList<BarEntry>();
        List<Integer> colors = new ArrayList<Integer>();

        int blue = Color.parseColor("#00B0EC");
                //rgb(110, 190, 102);
        int blueLight =  Color.parseColor("#7f6ecded");
        int awakeColor =  Color.parseColor("#F39CDEF4");

        for (int i = 0; i < dataList.size(); i++) {

            Data d = dataList.get(i);
            BarEntry entry = new BarEntry(d.xValue, d.yValue);
            values.add(entry);

            // specific colors
            if (d.yValue >= 0)
                colors.add(blue);
            else
                colors.add(blueLight);

             if(d.yValue>=0 && d.yValue<=100)
                colors.add(awakeColor);
        }

        BarDataSet set;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
//            set = (BarDataSet)mChart.getData().getDataSetByIndex(0);
//            set.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set = new BarDataSet(values, "Values");
            set.setColors(colors);
            set.setValueTextColor(Color.TRANSPARENT);

            BarData data = new BarData(set);
            data.setValueTextSize(13f);
            //data.setValueFormatter(new ValueFormatter());
           // data.setBarWidth(0.8f);
            data.setBarWidth(1f);

       //    mChart.setData(data);
            mChart.invalidate();
        }
    }




}
