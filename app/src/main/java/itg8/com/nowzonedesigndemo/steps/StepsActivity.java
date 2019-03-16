package itg8.com.nowzonedesigndemo.steps;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.common.SharePrefrancClass;
import itg8.com.nowzonedesigndemo.db.tbl.TblStepCount;
import itg8.com.nowzonedesigndemo.steps.mvp.StepMVP;
import itg8.com.nowzonedesigndemo.steps.mvp.StepPresenterImp;
import itg8.com.nowzonedesigndemo.steps.mvp.WeekStepModel;
import itg8.com.nowzonedesigndemo.steps.widget.ColorArcProgressBar;


public class StepsActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, AppBarLayout.OnOffsetChangedListener, StepMVP.StepView {


    private static final long ANIMATION_TIME = 500;
    @BindView(R.id.rgb_step_history)
    RadioButton rgbStepHistory;
    @BindView(R.id.rbg_main_steps)
    RadioGroup rbgMainSteps;
    @BindView(R.id.progressSteps)
    ColorArcProgressBar progressSteps;
    @BindView(R.id.rgb_steps_today)
    RadioButton rgbStepsToday;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
//    @BindView(R.id.tabLayout)
//    TabLayout tabLayout;
    @BindView(R.id.img_graph)
    ImageView imgGraph;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsing;
    private String TAG = StepsActivity.class.getSimpleName();
    private Fragment fragment;
    private FragmentManager fm;
    StepFragmentCommunicator todaysStepListener;
    DayWeekFragmentCommunicator dayWeekFragmentCommunicator;
    StepMVP.StepPresenter presenter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.nav_day:
                    fragment = TodayStepsFragment.newInstance(" "," ");
                    setFragmnet();
                    return true;
                case R.id.nav_week:
                    fragment = DayWeekFragment.newInstance("", "");
                    setFragmnet();
                    return true;
                case R.id.nav_month:
                    fragment = HistoryMonthFragment.newInstance("", "");
                    setFragmnet();
                    return true;
            }
            return false;
        }
    };
    private MonthFragmentCommunicator monthStepCommunicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//        );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        ButterKnife.bind(this);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        presenter=new StepPresenterImp(this);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (rgbStepsToday.isChecked()) {
            fragment = new TodayStepsFragment();
            rgbStepsToday.setTextColor(Color.WHITE);
            rgbStepHistory.setTextColor(Color.WHITE);
            setFragmnet();
            //  setTabLayout();
        }
        rbgMainSteps.setOnCheckedChangeListener(this);

    }




    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rgb_steps_today:
                imgGraph.setVisibility(View.GONE);
                navigation.setVisibility(View.GONE);
                progressSteps.setVisibility(View.VISIBLE);
                fragment = new TodayStepsFragment();
                rgbStepsToday.setTextColor(Color.WHITE);
                rgbStepHistory.setTextColor(Color.WHITE);
                break;

            case R.id.rgb_step_history:
//                imgGraph.setVisibility(View.VISIBLE);
                progressSteps.setVisibility(View.GONE);
                navigation.setVisibility(View.VISIBLE);
                fragment = new TodayStepsFragment();
                rgbStepHistory.setTextColor(Color.WHITE);
                rgbStepsToday.setTextColor(Color.WHITE);
                break;
        }
        setFragmnet();
    }


    private void setFragmnet() {
        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame_layout_steps, fragment).commit();
    }


    private void setView(int steps) {
        progressSteps.setCurrentValues(steps);
        progressSteps.setTitle("Steps covered");
        progressSteps.setTextSize(30);
        progressSteps.setIsNeedUnit(true);
        progressSteps.setUnit("%");
        progressSteps.setHintSize(30);
        progressSteps.setIsNeedTitle(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            overridePendingTransition(R.animator.slid_down, R.animator.slid_up);
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        appbar.addOnOffsetChangedListener(StepsActivity.this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

//        float alphaVal = verticalOffset /-552f;
        float alphaVal = verticalOffset / -276f;
        Log.d(TAG, "Offset : " + alphaVal + " vertical offset : " + verticalOffset);
        float newAlphaVal = 1 - alphaVal;
        progressSteps.setAlpha(newAlphaVal);
        imgGraph.setAlpha(newAlphaVal);
    }

    @Override
    protected void onStop() {
        super.onStop();
        appbar.removeOnOffsetChangedListener(this);
    }

    @Override
    public void onTodaysStepAvailable(int goal, int covered, int weekTotal, double calBurn) {
        if(todaysStepListener!=null){
            todaysStepListener.onTodaysDataReceived(goal,covered,weekTotal,calBurn);
            float stepsToCover=((float) ((float)covered/(float)goal)*100.0f);
            Log.d(TAG,"stepcover: "+stepsToCover);
            setView((int)stepsToCover);
        }
    }

    @Override
    public void onWeekStep(List<WeekStepModel> weekStepModelList) {
        dayWeekFragmentCommunicator.onWeekStepModelGot(weekStepModelList);
    }



    @Override
    public void onMonthStep(List<TblStepCount> monthStepsList) {
        monthStepCommunicator.onMonthStepHistoryGot(monthStepsList);
    }

    @Override
    public void onDaoUnableToConnect(String message) {

    }

    public void setListener(StepFragmentCommunicator communicator) {
        this.todaysStepListener=communicator;
        presenter.onTodaysStepReady();
    }


    public void removeTodaysListener() {
        todaysStepListener=null;
    }

    public void setDayWeekListener(DayWeekFragmentCommunicator communicator) {
        this.dayWeekFragmentCommunicator=communicator;
        presenter.onWeekDayReady();
    }

    public void setMonthListener(MonthFragmentCommunicator communicator){
        this.monthStepCommunicator=communicator;
        presenter.onMonthReady();
    }
}
