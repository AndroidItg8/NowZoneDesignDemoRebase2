package itg8.com.nowzonedesigndemo.sleep.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.sleep.SleepActivity;
import itg8.com.nowzonedesigndemo.sleep.adapter.ViewPagerSleepAdapter;
import itg8.com.nowzonedesigndemo.steps.widget.CustomMarkerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SleepMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SleepMainFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = SleepMainFragment.class.getSimpleName();
    @BindView(R.id.img_left)
    ImageView imgLeft;
    @BindView(R.id.txt_day)
    TextView txtDay;
    @BindView(R.id.img_right)
    ImageView imgRight;
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
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<String> mLabels= new ArrayList<>();


    public SleepMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SleepMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SleepMainFragment newInstance(String param1, String param2) {
        SleepMainFragment fragment = new SleepMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sleep_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        getActivity().setTitle("Sleep");

        imgLeft.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        setUpViewPager();
        // setPieChart();

        //   initBarChart();
        //  initPositiveNegativeChart();
        cubicLineChart();
        return view;
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

            CustomMarkerView mv = new CustomMarkerView(getActivity(), R.layout.linechart_three_tooltip);
            mv.setChartView(mChart);
            mv.setPadding(0, 0, 0, 0);
            mChart.setMarkerView(mv);
            // set data
            mChart.setData(dataLine);
        }
    }


    private void setUpViewPager() {
        FragmentManager fm = getChildFragmentManager();
        viewPager.setAdapter(new ViewPagerSleepAdapter(getActivity(), fm));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Sleep");
    }
}
