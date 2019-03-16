package itg8.com.nowzonedesigndemo.breath.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ismaeltoe.FlowLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.breath.BreathHistoryListActivity;
import itg8.com.nowzonedesigndemo.breath.timeline.InMemoryCursor;
import itg8.com.nowzonedesigndemo.breath.timeline.TimelineChartView;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.db.tbl.TblState;
import itg8.com.nowzonedesigndemo.steps.widget.CustomFontTextView;
import itg8.com.nowzonedesigndemo.utility.Helper;

import static itg8.com.nowzonedesigndemo.utility.BreathState.CALM;
import static itg8.com.nowzonedesigndemo.utility.BreathState.FOCUSED;
import static itg8.com.nowzonedesigndemo.utility.BreathState.STRESS;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BreathFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BreathFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = BreathFragment.class.getSimpleName();
    @BindView(R.id.graph)
    TimelineChartView graph;
    @BindView(R.id.item_series)
    FlowLayout itemSeries;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.lbl_calm)
    CustomFontTextView lblCalm;
    @BindView(R.id.txt_calm_value)
    CustomFontTextView txtCalmValue;
    @BindView(R.id.lbl_calm_time)
    CustomFontTextView lblCalmTime;
    @BindView(R.id.rl_calm)
    RelativeLayout rlCalm;
    @BindView(R.id.card_calm)
    CardView cardCalm;
    @BindView(R.id.lbl_focus)
    CustomFontTextView lblFocus;
    @BindView(R.id.txt_focus_value)
    CustomFontTextView txtFocusValue;
    @BindView(R.id.lbl_focus_time)
    CustomFontTextView lblFocusTime;
    @BindView(R.id.rl_focus)
    RelativeLayout rlFocus;
    @BindView(R.id.card_focus)
    CardView cardFocus;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.lbl_stress)
    CustomFontTextView lblStress;
    @BindView(R.id.txt_stress_value)
    CustomFontTextView txtStressValue;
    @BindView(R.id.lbl_stress_time)
    CustomFontTextView lblStressTime;
    @BindView(R.id.rl_stress)
    RelativeLayout rlStress;
    @BindView(R.id.card_stress)
    CardView cardStress;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private final SimpleDateFormat DATETIME_FORMATTER =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private final String[] COLUMN_NAMES = {"timestamp", "Compose", "Attentive", "Stress"};

    private List<TblState> listState = new ArrayList<>();
    private int[] mColor = new int[]{Color.parseColor("#81C784"), Color.parseColor("#64B5F6"), Color.parseColor("#E57373")};
    private Unbinder unbinder;


    public BreathFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BreathFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BreathFragment newInstance(String param1, String param2) {
        BreathFragment fragment = new BreathFragment();
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
        View view = inflater.inflate(R.layout.fragment_breath, container, false);
        unbinder = ButterKnife.bind(this, view);
        init(view);
        return view;
    }

    private void init(View view) {
        getActivity().setTitle("Breath");

        rlCalm.setOnClickListener(this);
        rlFocus.setOnClickListener(this);
        rlStress.setOnClickListener(this);
        initGraphWithTimeline(view);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();


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
        Intent intent = new Intent(getActivity(), BreathHistoryListActivity.class);
        intent.putParcelableArrayListExtra(CommonMethod.BREATH, (ArrayList<? extends Parcelable>) listState);
        startActivity(intent);
    }

    private void initGraphWithTimeline(View view) {
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
                double[] object = item.mSeries;
                putValuesTotxt(object, null);

            }
        });

        setUpCalender(view);


    }

    private void putValuesTotxt(@Nullable double[] object, @Nullable Object[] data) {
        if (object != null) {
            txtCalmValue.setText(String.valueOf(object[1]));
            txtFocusValue.setText(String.valueOf(object[2]));
            txtStressValue.setText(String.valueOf(object[3]));
        } else if (data != null) {
            txtCalmValue.setText(String.valueOf(data[1]));
            txtFocusValue.setText(String.valueOf(data[2]));
            txtStressValue.setText(String.valueOf(data[3]));
        }
    }

    private InMemoryCursor createInMemoryCursor() {
        return new InMemoryCursor(COLUMN_NAMES);

    }

    private void setUpCalender(View view) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        ViewGroup series = (ViewGroup) view.findViewById(R.id.item_series);

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

            }
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
        putValuesTotxt(null, dateWiseStates.get(Helper.getCurrentDate()));

        cursor.addAll(data);
    }
}
