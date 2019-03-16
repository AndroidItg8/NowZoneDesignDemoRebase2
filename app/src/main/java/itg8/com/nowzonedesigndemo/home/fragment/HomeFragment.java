package itg8.com.nowzonedesigndemo.home.fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.breath.fragment.BreathFragment;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.common.SharePrefrancClass;
import itg8.com.nowzonedesigndemo.sleep.fragment.SleepMainFragment;
import itg8.com.nowzonedesigndemo.steps.StepsActivity;
import itg8.com.nowzonedesigndemo.steps.widget.CustomFontTextView;
//import itg8.com.nowzonedesigndemo.widget.bottomnavigation.BottomNavigationItem;
//import itg8.com.nowzonedesigndemo.widget.bottomnavigation.BottomNavigationView;
//import itg8.com.nowzonedesigndemo.widget.bottomnavigation.OnBottomNavigationItemClickListener;
import itg8.com.nowzonedesigndemo.widget.wave.BreathwaveView;
import itg8.com.nowzonedesigndemo.widget.wave.WaveLoadingView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Unbinder unbinder;
    Fragment fragment;
    @BindView(R.id.frameLayout_home)
    FrameLayout frameLayoutHome;
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
    @BindView(R.id.main_FrameLayout)
    FrameLayout mainFrameLayout;
    @BindView(R.id.txt_breath)
    CustomFontTextView txtBreath;
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
    @BindView(R.id.rl_main_top)
    RelativeLayout rlMainTop;

//    @BindView(R.id.frameLayout_home)
//    FrameLayout frameLayoutHome;
//    @BindView(R.id.waveLoadingView)
//    WaveLoadingView waveLoadingView;
//    @BindView(R.id.breathview)
//    BreathwaveView breathview;
//    @BindView(R.id.rl_wave)
//    FrameLayout rlWave;
//
//    @BindView(R.id.txt_breathRate)
//    TextView txtBreathRate;
//    @BindView(R.id.txt_statusValue)
//    TextView txtStatusValue;
//    @BindView(R.id.txt_status)
//    TextView txtStatus;
//    @BindView(R.id.breathValue)
//    TextView breathValue;
//    @BindView(R.id.txt_minute)
//    TextView txtMinute;
//    @BindView(R.id.rl_breath)
//    RelativeLayout rlBreath;
//    @BindView(R.id.txt_calm)
//    TextView txtCalm;
//    @BindView(R.id.txt_calm_value)
//    TextView txtCalmValue;
//    @BindView(R.id.txt_focus)
//    CustomFontTextView txtFocus;
//    @BindView(R.id.txt_focus_value)
//    CustomFontTextView txtFocusValue;
//    @BindView(R.id.txt_stress)
//    TextView txtStress;
//    @BindView(R.id.txt_stress_value)
//    TextView txtStressValue;
//    @BindView(R.id.main_FrameLayout)
//    FrameLayout mainFrameLayout;
//    @BindView(R.id.rl_main_top)
//    RelativeLayout rlMainTop;
//    @BindView(R.id.img_breath)
//    ImageView imgBreath;
//    @BindView(R.id.txt_breath)
//    CustomFontTextView txtBreath;
//    @BindView(R.id.txt_breathCount)
//    CustomFontTextView txtBreathCount;
//    @BindView(R.id.txt_AvgBreathValue)
//    TextView txtAvgBreathValue;
//    @BindView(R.id.ll_breath_avg)
//    RelativeLayout llBreathAvg;
//    @BindView(R.id.img_sleep)
//    ImageView imgSleep;
//    @BindView(R.id.txt_forth)
//    CustomFontTextView txtForth;
//    @BindView(R.id.txt_hour)
//    TextView txtHour;
//    @BindView(R.id.txt_hourValue)
//    TextView txtHourValue;
//    @BindView(R.id.ll_sleep_main)
//    RelativeLayout llSleepMain;
//    @BindView(R.id.img_step)
//    ImageView imgStep;
//    @BindView(R.id.txt_step)
//    CustomFontTextView txtStep;
//    @BindView(R.id.txt_stepCount)
//    TextView txtStepCount;
//    @BindView(R.id.txt_stepCountValue)
//    TextView txtStepCountValue;
//    @BindView(R.id.rlSteps)
//    RelativeLayout rlSteps;
//    @BindView(R.id.rl_main_bottom)
//    LinearLayout rlMainBottom;
    @BindView(R.id.bottom_navigation)
BottomNavigationView bottomNavigationView;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentManager fm;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        setType();
        initOtherView();
//        callBottomNavigation();

        return view;
    }

//    private void callBottomNavigation() {
//        int[] image = {R.drawable.ic_breaths, R.drawable.ic_sleeping,
//                R.drawable.ic_steps};
//        int[] color = {ContextCompat.getColor(getActivity(), R.color.firstColor), R.drawable.gradient_block,
//                R.drawable.gradientblocktwo};
//
//        if (bottomNavigationView != null) {
//            bottomNavigationView.isWithText(true);
//            // bottomNavigationView.activateTabletMode();
//            bottomNavigationView.isColoredBackground(true);
//            bottomNavigationView.setTextActiveSize(getResources().getDimension(R.dimen.text_active));
//            bottomNavigationView.setTextInactiveSize(getResources().getDimension(R.dimen.text_inactive));
//            bottomNavigationView.setItemActiveColorWithoutColoredBackground(ContextCompat.getColor(getActivity(), R.color.firstColor));
//
//            // bottomNavigationView.setFont(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Noh_normal.ttf"));
//        }
//
//        BottomNavigationItem bottomNavigationItem = new BottomNavigationItem
//                ("Breath", color[0], image[0], "hr", "7:30", "2000");
//        BottomNavigationItem bottomNavigationItem1 = new BottomNavigationItem
//                ("Sleep", color[1], image[1], "hr", "8", "Sleep");
//        BottomNavigationItem bottomNavigationItem2 = new BottomNavigationItem
//                ("Steps", color[2], image[2], "hr", "5", "1450");
//
//
//        bottomNavigationView.addTab(bottomNavigationItem);
//        bottomNavigationView.addTab(bottomNavigationItem1);
//        bottomNavigationView.addTab(bottomNavigationItem2);
//
//        bottomNavigationView.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
//            @Override
//            public void onNavigationItemClick(int index) {
//                fm = getActivity().getSupportFragmentManager();
//                switch (index) {
//                    case 0:
//                        //  startActivity(new Intent(getActivity(), BreathHistoryActivity.class));
//                        fm.beginTransaction().replace(R.id.frameLayout, BreathFragment.newInstance("", "")).addToBackStack(BreathFragment.class.getSimpleName()).commit();
//                        break;
//                    case 1:
//                        fm.beginTransaction().replace(R.id.frameLayout, SleepMainFragment.newInstance("", "")).addToBackStack(SleepMainFragment.class.getSimpleName()).commit();
//                        break;
//                    case 2:
//                        Intent intent = new Intent(getActivity(), StepsActivity.class);
//                        startActivity(intent);
//                        break;
//
//                }
//            }
//        });
//    }

    private void init() {
        rlSteps.setOnClickListener(this);
        llSleepMain.setOnClickListener(this);
        llBreathAvg.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void setType() {

//        waveLoadingView.setWaveBgColor(getResources().getColor(R.color.color_wave_normal, null));
//        waveLoadingView.setWaveColor(getResources().getColor(R.color.color_wave_normal_bg, null));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            waveLoadingView.setWaveBgColor(getResources().getColor(R.color.color_wave_normal, null));
            waveLoadingView.setWaveColor(getResources().getColor(R.color.color_wave_normal_bg, null));

        } else {

            waveLoadingView.setWaveBgColor(ContextCompat.getColor(getActivity(), R.color.color_wave_normal));
            waveLoadingView.setWaveColor(ContextCompat.getColor(getActivity(), R.color.color_wave_normal_bg));

        }


        // Change Now
        waveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);

        waveLoadingView.setAmplitudeRatio(20);
        waveLoadingView.setProgressValue(10);
        waveLoadingView.setBorderWidth(1f);


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
    public void onClick(View v) {
        fm = getActivity().getSupportFragmentManager();
        switch (v.getId()) {
            case R.id.rlSteps:
                Intent intent = new Intent(getActivity(), StepsActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_sleep_main:
                // startActivity(new Intent(getActivity(), SleepActivity.class));
                fm.beginTransaction().replace(R.id.frameLayout, SleepMainFragment.newInstance("", "")).addToBackStack(SleepMainFragment.class.getSimpleName()).commit();
                break;
            case R.id.ll_breath_avg:
                fm.beginTransaction().replace(R.id.frameLayout, BreathFragment.newInstance("", "")).addToBackStack(SleepMainFragment.class.getSimpleName()).commit();

//              startActivity(new Intent(getActivity(), BreathHistoryActivity.class));
                break;

        }
    }


    private void initOtherView() {
        int mAvgCount = SharePrefrancClass.getInstance(getActivity()).getIPreference(CommonMethod.USER_CURRENT_AVG);
        if (mAvgCount > 0) {
            setAvgValue(mAvgCount);
        }
    }

    private void setAvgValue(int mAvgCount) {
        txtAvgBreathValue.setVisibility(View.VISIBLE);
        txtAvgBreathValue.setText(String.valueOf(mAvgCount));
    }
}
