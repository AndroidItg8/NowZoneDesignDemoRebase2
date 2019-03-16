package itg8.com.nowzonedesigndemo.setting.fragment;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.common.SharePrefrancClass;
import itg8.com.nowzonedesigndemo.steps.widget.CustomFontTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlarmSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlarmSettingFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.lbl_awakeAlarm)
    CustomFontTextView lblAwakeAlarm;
    @BindView(R.id.txtAmPm)
    CustomFontTextView txtAmPm;
    @BindView(R.id.txt_alarm_time)
    CustomFontTextView txtAlarmTime;
    @BindView(R.id.txt_am)
    CustomFontTextView txtAm;
    @BindView(R.id.releative)
    RelativeLayout releative;
    //    @BindView(R.id.progress_bar)
//    ProgressBar progressBar;

    @BindView(R.id.btn_alarmSetting)
    Button btnAlarmSetting;
    @BindView(R.id.btn_alarmCalibarating)
    Button btnAlarmCalibarating;
    @BindView(R.id.btn_alarmStarted)
    Button btnAlarmStarted;
    @BindView(R.id.btn_alarmFinished)
    Button btnAlarmFinished;
    Unbinder unbinder;
    @BindView(R.id.lbl_message)
    CustomFontTextView lblMessage;
    @BindView(R.id.lbl_start_time)
    CustomFontTextView lblStartTime;
    @BindView(R.id.txt_start_time)
    CustomFontTextView txtStartTime;
    @BindView(R.id.lbl_end_time)
    CustomFontTextView lblEndTime;
    @BindView(R.id.txt_end_time)
    CustomFontTextView txtEndTime;
    @BindView(R.id.lbl_remain_time)
    CustomFontTextView lblRemainTime;
    @BindView(R.id.txt_remain_time)
    CustomFontTextView txtRemainTime;
    @BindView(R.id.rl_time_status)
    RelativeLayout rlTimeStatus;
    @BindView(R.id.frameLayout_animation)
    FrameLayout frameLayoutAnimation;
    @BindView(R.id.txt_alarm_status)
    CustomFontTextView txtAlarmStatus;
    SimpleDateFormat formatDate = new SimpleDateFormat("hh:mm", Locale.getDefault());
    SimpleDateFormat formatDate2 = new SimpleDateFormat("a", Locale.getDefault());
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Animation zoomIn, zoomOut;
    private Thread thread;
    private Calendar c;


    public AlarmSettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlarmSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlarmSettingFragment newInstance(String param1, String param2) {
        AlarmSettingFragment fragment = new AlarmSettingFragment();
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
        View view = inflater.inflate(R.layout.fragment_alarm_setting, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_alarmSetting) {
            startAnimation();
        }
        if (v.getId() == R.id.txt_alarm_time) {
            OpenTimePickerDia();
        }
        if (v.getId() == R.id.btn_alarmStarted) {
            startAnimation();
        }

    }

    private void OpenTimePickerDia() {
        // Get Current Time
        c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                        String time = (hourOfDay + ":" + minute);
                        SharePrefrancClass.getInstance(getActivity()).savePref(CommonMethod.ALARM_AP, formatDate2.format(c.getTime()));
                        c = CommonMethod.ConvertTime(getActivity(), hourOfDay, minute);
                        txtAm.setText(formatDate2.format(c.getTime()));
                        txtAlarmTime.setText(time);
                        time = formatDate.format(c.getTime());
                        txtAlarmTime.setText(time);
                        Log.d(getClass().getSimpleName(), "Time:" + time);
                        SharePrefrancClass.getInstance(getActivity()).savePref(CommonMethod.SAVEALARMTIME, time);
                        try {
                            Date d = formatDate.parse(time);
                            SharePrefrancClass.getInstance(getActivity()).savePref(CommonMethod.SAVETIMEINMILI,d.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }



                        sendBroadCast(true);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    private void sendBroadCast(boolean b) {
        Intent intent = new Intent(CommonMethod.ACTION_ALARM_NOTIFICATION);
        intent.putExtra(CommonMethod.ALARM_FROMTIMEPICKER, b);
        //  LocalBroadcastManager.getInstance(getApplicationContext()).
        getActivity().sendBroadcast(intent);
    }

    private void startAnimation() {
        if (btnAlarmSetting.getText().equals("Calibarating")) {
            btnAlarmSetting.startAnimation(zoomOut);
            btnAlarmSetting.startAnimation(zoomIn);

        } else {
            thread = new Thread(new Runnable() {

                @Override
                public void run() {

                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
//                            progressBar.setVisibility(View.VISIBLE);
//                            progressBar.setProgress(0);
                            txtAlarmStatus.setVisibility(View.VISIBLE);
                            // txtAlarmStatus.setText("Calibrating Device");
                            txtAlarmStatus.setText("Alarm Started");
                            btnAlarmSetting.setVisibility(View.GONE);
                            // btnAlarmStarted.setVisibility(View.GONE);
                            btnAlarmStarted.setVisibility(View.VISIBLE);
                            btnAlarmCalibarating.setVisibility(View.GONE);
//                            btnAlarmCalibarating.startAnimation(zoomOut);
//                            btnAlarmCalibarating.startAnimation(zoomIn);
                            btnAlarmStarted.startAnimation(zoomOut);
                            btnAlarmStarted.startAnimation(zoomIn);


                        }

                    });

                    Calendar calendar = Calendar.getInstance();
//                    calendar.add(Calendar.MINUTE, 1);
//                    long timerEnd = calendar.getTimeInMillis();
//                    while (timerEnd > System.currentTimeMillis()) {
//                        int progress = (int) (timerEnd - System.currentTimeMillis()) / 600;
//                        progress = 100 - progress;
//                        //  progressBar.setProgress(progress);
//                    }


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            btnAlarmSetting.setVisibility(View.GONE);
                            btnAlarmCalibarating.setVisibility(View.GONE);
                            btnAlarmStarted.setVisibility(View.VISIBLE);
//                            btnAlarmStarted.startAnimation(zoomOut);
//                            btnAlarmStarted.startAnimation(zoomIn);
                            txtAlarmStatus.setVisibility(View.VISIBLE);
                            txtAlarmStatus.setText(" Alarm Started");
                            lblMessage.startAnimation(zoomOut);
                            rlTimeStatus.setVisibility(View.VISIBLE);
                            rlTimeStatus.startAnimation(zoomIn);

                            txtStartTime.setText(formatDate.format(calendar.getTimeInMillis()));
                            txtRemainTime.setText(CommonMethod.calculateHours(calendar.getTimeInMillis(), SharePrefrancClass.getInstance(getActivity()).getLPref(CommonMethod.START_ALARM_TIME)));
                            txtEndTime.setText(SharePrefrancClass.getInstance(getActivity()).getPref(CommonMethod.SAVEALARMTIME));
//                            ObjectAnimator objectAnimator = ObjectAnimator.ofInt(progressBar, "progress", 100);
//                            objectAnimator.start();
                            sendBroadCast(true);
                        }


                    });

                }


            });
            thread.start();


        }
    }

    private void init() {
        getActivity().setTitle("Alarm");
        Log.d(getClass().getSimpleName(), String.valueOf(SystemClock.elapsedRealtime()));

        zoomIn = AnimationUtils.loadAnimation(getActivity(), R.animator.zoom_in_text);
        zoomOut = AnimationUtils.loadAnimation(getActivity(), R.animator.zoomout_text);
        Log.d(getClass().getSimpleName(), "Calender:" + Calendar.getInstance().getTimeInMillis());
        Log.d(getClass().getSimpleName(), "Calender:" + System.currentTimeMillis());
        btnAlarmSetting.setOnClickListener(this);
        btnAlarmStarted.setOnClickListener(this);
        btnAlarmCalibarating.setOnClickListener(this);
        txtAlarmTime.setOnClickListener(this);
        if (SharePrefrancClass.getInstance(getActivity()).getPref(CommonMethod.SAVEDAYS) != null) {
            c = Calendar.getInstance();
            c.setTimeInMillis(SharePrefrancClass.getInstance(getActivity()).getLPref(CommonMethod.START_ALARM_TIME));
            txtAlarmTime.setText(formatDate.format(c.getTime()));
            txtAm.setText(formatDate2.format(c.getTime()));
            int hour = c.get(Calendar.HOUR);
            int minute = c.get(Calendar.MINUTE);
            Log.d(getClass().getSimpleName(), "ShareTime:" + hour + " " + minute);
            // txtAlarmTime.setText(hour+":"+ minute);


        }
        if(SharePrefrancClass.getInstance(getActivity()).getLPref(CommonMethod.START_ALARM_TIME)>0)
        {
            lblMessage.setVisibility(View.GONE);
            rlTimeStatus.setVisibility(View.VISIBLE);
            btnAlarmStarted.setVisibility(View.VISIBLE);
            btnAlarmStarted.setClickable(false);
            Calendar calendar = Calendar.getInstance();
            txtStartTime.setText(formatDate.format(calendar.getTimeInMillis()));
            txtRemainTime.setText(CommonMethod.calculateHours(calendar.getTimeInMillis(), SharePrefrancClass.getInstance(getActivity()).getLPref(CommonMethod.START_ALARM_TIME)));
            txtEndTime.setText(SharePrefrancClass.getInstance(getActivity()).getPref(CommonMethod.SAVEALARMTIME));
            btnAlarmSetting.setVisibility(View.GONE);


        }else
        {
            lblMessage.setVisibility(View.GONE);
            btnAlarmSetting.setVisibility(View.VISIBLE);
            btnAlarmSetting.setClickable(true);



        }
    }
}
