package itg8.com.nowzonedesigndemo.breath;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.breath.mvp.BreathHistoryMVP;
import itg8.com.nowzonedesigndemo.breath.mvp.BreathHistoryPresenterImp;
import itg8.com.nowzonedesigndemo.breath.timeline.InMemoryCursor;
import itg8.com.nowzonedesigndemo.breath.timeline.TimelineChartView;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.db.tbl.TblState;
import itg8.com.nowzonedesigndemo.steps.widget.CustomFontTextView;
import itg8.com.nowzonedesigndemo.utility.Helper;

import static itg8.com.nowzonedesigndemo.utility.BreathState.CALM;
import static itg8.com.nowzonedesigndemo.utility.BreathState.FOCUSED;
import static itg8.com.nowzonedesigndemo.utility.BreathState.STRESS;


public class BreathHistoryActivity extends AppCompatActivity implements BreathHistoryMVP.BreathHistoryView, View.OnClickListener {

    private static final String TAG = BreathHistoryActivity.class.getSimpleName();
    BreathHistoryMVP.BreathHistoryPresenter presenter;
    //
//    @BindView(R.id.recyclerview)
//    RecyclerView recyclerview;

    //
//    @BindView(R.id.frameLayout)
//    FrameLayout frameLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //    @BindView(R.id.viewPager)
//    ViewPager viewPager;
    @BindView(R.id.graph)
    TimelineChartView graph;
    @BindView(R.id.card_calm)
    CardView cardCalm;

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.rl_calm)
    RelativeLayout rlCalm;
    @BindView(R.id.rl_focus)
    RelativeLayout rlFocus;
    @BindView(R.id.rl_stress)
    RelativeLayout rlStress;
    @BindView(R.id.lbl_calm)
    CustomFontTextView lblCalm;
    @BindView(R.id.txt_calm_value)
    CustomFontTextView txtCalmValue;
    @BindView(R.id.lbl_calm_time)
    CustomFontTextView lblCalmTime;
    @BindView(R.id.lbl_focus)
    CustomFontTextView lblFocus;
    @BindView(R.id.txt_focus_value)
    CustomFontTextView txtFocusValue;
    @BindView(R.id.lbl_focus_time)
    CustomFontTextView lblFocusTime;
    @BindView(R.id.lbl_stress)
    CustomFontTextView lblStress;
    @BindView(R.id.txt_stress_value)
    CustomFontTextView txtStressValue;
    @BindView(R.id.lbl_stress_time)
    CustomFontTextView lblStressTime;


    private final SimpleDateFormat DATETIME_FORMATTER =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private final String[] COLUMN_NAMES = {"timestamp", "Compose", "Attentive", "Stress"};

//    private final int[] MODES = {
//            TimelineChartView.GRAPH_MODE_BARS,
//            TimelineChartView.GRAPH_MODE_BARS_STACK,
//            TimelineChartView.GRAPH_MODE_BARS_SIDE_BY_SIDE};


    private List<TblState> listState = new ArrayList<>();
    private int[] mColor = new int[]{Color.parseColor("#81C784"), Color.parseColor("#64B5F6"), Color.parseColor("#E57373")};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath_history);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        ButterKnife.bind(this);
        presenter = new BreathHistoryPresenterImp(this);
        init();
        presenter.initListOfState();

//        FragmentManager fm = getSupportFragmentManager();
//        viewPager.setAdapter(new BreathPagerAdapter(getApplicationContext(),fm));
//fm.beginTransaction().replace(R.id.frameLayout, new BreathHistoryFragment(), getClass().getSimpleName()).commit();


    }

    private void checkScreenDensity(TextView... textView) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        switch (displayMetrics.densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                for (TextView text : textView
                        ) {
                    text.setTextSize(15);

                }
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                for (TextView text : textView
                        ) {
                    text.setTextSize(18);
                }
                break;
            case DisplayMetrics.DENSITY_HIGH:
                for (TextView text : textView
                        ) {

                    text.setTextSize(20);
                }
                break;
        }
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        checkScreenDensity(lblCalm, lblCalmTime, lblFocus, lblFocusTime, lblStress, lblStressTime, txtCalmValue, txtFocusValue, txtStressValue);

        rlCalm.setOnClickListener(this);
        rlFocus.setOnClickListener(this);
        rlStress.setOnClickListener(this);

    }


    private void initGraphWithTimeline() {
        graph.setBarItemSpace(8);
        graph.setBarItemWidth(133);
        graph.setFollowCursorPosition(false);
        graph.animate();
        graph.setGraphMode(1);
        graph.setGraphAreaBackground(Color.TRANSPARENT);

        graph.setUserPalette(new int[]{Color.parseColor("#81C784"), Color.parseColor("#64B5F6"), Color.parseColor("#E57373")});
        //E53935
        InMemoryCursor mCursor = createInMemoryCursor();
        createRandomData(mCursor, listState);
        graph.observeData(mCursor);

        graph.setOnClickItemListener(new TimelineChartView.OnClickItemListener() {
            @Override
            public void onClickItem(TimelineChartView.Item item, int serie) {
                final String timestamp = DATETIME_FORMATTER.format(item.mTimestamp);
                System.out.println(timestamp);
                Log.d(getClass().getSimpleName(), "TimesStamped:" + timestamp);
                graph.smoothScrollTo(item.mTimestamp);
                double[] object=item.mSeries;
                putValuesTotxt(object,null);

            }
        });

        setUpCalender();


    }

    private void putValuesTotxt(@Nullable double[] object, @Nullable Object[] data) {
        if(object!=null) {
            txtCalmValue.setText(object.length>=1?String.valueOf(object[0]):String.valueOf(0));
            txtFocusValue.setText(object.length>=2?String.valueOf(object[1]):String.valueOf(0));
            txtStressValue.setText(object.length>=3?String.valueOf(object[2]):String.valueOf(0));
        }else if(data!=null){
            txtCalmValue.setText(data.length>=1?String.valueOf(data[0]):String.valueOf(0));
            txtFocusValue.setText(data.length>=2?String.valueOf(data[1]):String.valueOf(0));
            txtStressValue.setText(data.length>=3?String.valueOf(data[2]):String.valueOf(0));
        }
    }

    private InMemoryCursor createInMemoryCursor() {
        return new InMemoryCursor(COLUMN_NAMES);

    }

    private void setUpCalender() {
        LayoutInflater inflater = LayoutInflater.from(this);
        ViewGroup series = (ViewGroup) findViewById(R.id.item_series);

        TextView[] mSeries = new TextView[COLUMN_NAMES.length - 1];
        View[] mSeriesColors = new View[COLUMN_NAMES.length - 1];
        for (int i = 1; i < COLUMN_NAMES.length; i++) {
            View v = inflater.inflate(R.layout.serie_item_layout, series, false);
            TextView title = (TextView) v.findViewById(R.id.title);
            title.setText(COLUMN_NAMES[i]);
            title.setTextColor(Color.WHITE);
            mSeries[i - 1] = (TextView) v.findViewById(R.id.value);
            mSeriesColors[i - 1] = v.findViewById(R.id.color);
            mSeriesColors[i - 1].setBackgroundColor(mColor[i - 1]);
            series.addView(v);
        }

    }

    private void createRandomData(InMemoryCursor cursor, List<TblState> list) {
        List<Object[]> data = new ArrayList<>();
        HashMap<String, Object[]> dateWiseStates = new LinkedHashMap<>();
        Log.d(TAG, "creating data....");
        Object[] object = new Object[COLUMN_NAMES.length];
        for (TblState states :
                list) {
            if (dateWiseStates.containsKey(states.getDate())) {
                object = dateWiseStates.get(states.getDate());
            }else
                object=new Object[COLUMN_NAMES.length];
            object[0] = states.getTimestampEnd();
            if (states.getState().equalsIgnoreCase(CALM.toString())) {
                if (object[1] == null)
                    object[1] = 1;
                else
                    object[1] = (int) object[1] + 1;
            } else {
                if (object[1] == null) {
                    object[1] = 0;
                }
            }
            if (states.getState().equalsIgnoreCase(FOCUSED.toString())) {
                if (object[2] == null)
                    object[2] = 1;
                else
                    object[2] = (int) object[2] + 1;
            } else {
                if (object[2] == null) {
                    object[2] = 0;
                }
            }
            if (states.getState().equalsIgnoreCase(STRESS.toString())) {
                if (object[3] == null)
                    object[3] = 1;
                else
                    object[3] = (int) object[3] + 1;
            } else {
                if (object[3] == null) {
                    object[3] = 0;
                }
            }
            dateWiseStates.put(states.getDate(), object);
        }

        //noinspection Convert2streamapi
        for (Map.Entry<String, Object[]> entry : dateWiseStates.entrySet()) {
            data.add(entry.getValue());
        }
        putValuesTotxt(null,dateWiseStates.get(Helper.getCurrentDate()));

        cursor.addAll(data);
    }



    @Override
    public void onListAvailable(List<TblState> list) {
        this.listState = list;
        initGraphWithTimeline();
    }


    @Override
    public void onErrorLoading(String error) {
        Log.e(TAG, "Error:" + error);
    }

    @Override
    public void onConnectionFailed(String issue) {
        Log.e(TAG, "connection Error :" + issue);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_calm:
                callHistoryActivity();
                break;
            case R.id.rl_focus:
                callHistoryActivity();
                break;
            case R.id.rl_stress:
                callHistoryActivity();
                break;
        }
    }

    private void callHistoryActivity() {
        Intent intent = new Intent(getApplicationContext(), BreathHistoryListActivity.class);
        intent.putParcelableArrayListExtra(CommonMethod.BREATH, (ArrayList<? extends Parcelable>) listState);
        startActivity(intent);
    }
}
