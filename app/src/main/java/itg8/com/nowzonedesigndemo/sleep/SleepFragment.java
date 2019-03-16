package itg8.com.nowzonedesigndemo.sleep;


import android.graphics.Color;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.custom_widget.AutoSizeTextView;
import itg8.com.nowzonedesigndemo.sleep.widget_custom_progressbar.CustomProgressBar;
import itg8.com.nowzonedesigndemo.sleep.widget_custom_progressbar.DonutProgress;
import itg8.com.nowzonedesigndemo.sleep.widget_custom_progressbar.ProgressItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class SleepFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.txt_durations)
    TextView txtDurations;
    @BindView(R.id.ll_percent_durations)
    LinearLayout llPercentDurations;
    @BindView(R.id.txt_sleepValue)
    TextView txtSleepValue;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.ll_percent)
    LinearLayout llPercent;
    @BindView(R.id.txt_wakeupValue)
    TextView txtWakeupValue;
    @BindView(R.id.txt_wakeup)
    TextView txtWakeup;
    @BindView(R.id.txt_goals)
    TextView txtGoals;
    @BindView(R.id.circularProgressGoal)
    DonutProgress circularProgressGoal;
    @BindView(R.id.rl_main)
    PercentRelativeLayout rlMain;
    @BindView(R.id.txt_break)
    AutoSizeTextView txtBreak;
    @BindView(R.id.custom_progressbar)
    CustomProgressBar customProgressbar;
    @BindView(R.id.txt_awake)
    TextView txtAwake;

    @BindView(R.id.txt_deep)
    TextView txtDeep;

    @BindView(R.id.txt_deep_time)
    TextView txtDeepTime;
    @BindView(R.id.txt_light)
    TextView txtLight;
    @BindView(R.id.txt_light_time)
    TextView txtLightTime;
    @BindView(R.id.rl_overView)
    RelativeLayout rlOverView;
    private ArrayList<ProgressItem> progressItemList;
    private ProgressItem mProgressItem;
    private Timer timer;


    public SleepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sleep, container, false);
        unbinder = ButterKnife.bind(this, view);
        customProgressbar.getThumb().mutate().setAlpha(0);
        initDataToSeekbar();
        setCircularProgress();
        return view;
    }

    private void setCircularProgress() {
      //  circularProgressGoal.setTextColor(R.color.colorWhite);
//        circularProgressGoal.setInnerBottomText("10");
        //circularProgressGoal.setInnerBottomTextColor(R.color.colorWhite);
        // circularProgressGoal.setInnerBottomTextColor(R.color.colorWhite);
        // circularProgressGoal.setDonut_progress("80");
        //circularProgressGoal.setPrefixText("10");

        circularProgressGoal.setProgress(60);
        circularProgressGoal.setUnfinishedStrokeColor(R.color.color_green);
        circularProgressGoal.setUnfinishedStrokeWidth(4.0f);
        circularProgressGoal.setFinishedStrokeWidth(4.0f);



    }


    private void initDataToSeekbar() {
        progressItemList = new ArrayList<ProgressItem>();

        // green span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = 20;
        mProgressItem.color = R.color.color_skin;
        progressItemList.add(mProgressItem);


        // red span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = 50;
        Log.i("Mainactivity", mProgressItem.progressItemPercentage + "");
        mProgressItem.color = R.color.color_sky;
        progressItemList.add(mProgressItem);
        // blue span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = 30;
        mProgressItem.color = R.color.color_green;
        progressItemList.add(mProgressItem);

        customProgressbar.initData(progressItemList);
        customProgressbar.invalidate();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home)
        {
            getActivity().onBackPressed();
        }


            return super.onOptionsItemSelected(item);
    }
}
