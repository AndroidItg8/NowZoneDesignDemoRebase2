package itg8.com.nowzonedesigndemo.setting.fragment;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.alarm.AlarmActivity;
import itg8.com.nowzonedesigndemo.alarm.adapter.AlarmDaysAdapter;
import itg8.com.nowzonedesigndemo.alarm.model.AlarmDaysModel;
import itg8.com.nowzonedesigndemo.alarm.model.AlarmModel;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.common.SharePrefrancClass;
import itg8.com.nowzonedesigndemo.steps.widget.CustomFontTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlarmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlarmFragment extends Fragment implements View.OnClickListener, CommonMethod.alarmListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = AlarmFragment.class.getSimpleName();

    @BindView(R.id.rl_alarm_smart_main)
    RelativeLayout rlAlarmSmartMain;
    @BindView(R.id.img_alarm)
    ImageView imgAlarm;
    @BindView(R.id.txt_alarm_smart)
    CustomFontTextView txtAlarmSmart;
    @BindView(R.id.txt_alarm_time)
    CustomFontTextView txtAlarmTime;
    @BindView(R.id.txt_alarm_days)
    CustomFontTextView txtAlarmDays;
    @BindView(R.id.switch_time)
    SwitchCompat switchTime;
    @BindView(R.id.rl_alarm_meditation_main)
    RelativeLayout rlAlarmMeditationMain;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private List<AlarmDaysModel> days;
    private List<AlarmDaysModel> daysForMeditation;

    private AlarmDaysAdapter adapter;
    private String bc = "";
    private String share = "";
    private CustomFontTextView textView;
    private CustomFontTextView txtDaysSmart;
    private CustomFontTextView txtDaysMed;
    private AlarmModel alarmModel;
    private Type type;
    private Calendar c;
    SimpleDateFormat formatDate = new SimpleDateFormat("hh:mm", Locale.getDefault());
    SimpleDateFormat formatDate2 = new SimpleDateFormat("a", Locale.getDefault());
    private CustomFontTextView txtAlarmTimeSmart, txtAlarmTimeMed;
    private String time = "";


    public AlarmFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlarmFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlarmFragment newInstance(String param1, String param2) {
        AlarmFragment fragment = new AlarmFragment();
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
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        unbinder = ButterKnife.bind(this, view);
        init(view);
        return view;
    }


    private void init(View view) {
        getActivity().setTitle("Alarm");
        RelativeLayout tmpRl = (RelativeLayout)view.findViewById(R.id.rl_include_smart);
        CustomFontTextView txtTitleSmart = (CustomFontTextView) tmpRl.findViewById(R.id.txt_alarm_smart);
        txtDaysSmart = (CustomFontTextView) tmpRl.findViewById(R.id.txt_alarm_days);
        txtAlarmTimeSmart = (CustomFontTextView) tmpRl.findViewById(R.id.txt_alarm_time);
        txtTitleSmart.setText(getResources().getString(R.string.action_smart));


        tmpRl = (RelativeLayout) view.findViewById(R.id.rl_include_meditation);
        txtTitleSmart = (CustomFontTextView) tmpRl.findViewById(R.id.txt_alarm_smart);
        txtDaysMed = (CustomFontTextView) tmpRl.findViewById(R.id.txt_alarm_days);
        txtAlarmTimeMed = (CustomFontTextView) tmpRl.findViewById(R.id.txt_alarm_time);
        txtTitleSmart.setText(getResources().getString(R.string.action_meditation));
        checkSharePrefAlarmTime();
        alarmModel = new AlarmModel();
        days = new ArrayList<>();
        daysForMeditation = new ArrayList<>();
        AlarmDaysModel model;
        String[] dy = {"Mon", "Tue", "Wed", "Thr", "Fri", "Sat", "Sun"};
        for (String aDy : dy) {
            model = new AlarmDaysModel();
            model.setDays(aDy);
            model.setChecked(false);
            Log.d(TAG, "Check Days :" + aDy);
            days.add(model);
            daysForMeditation.add(model);
        }


        //noinspection ConstantConditions
        rlAlarmMeditationMain.setOnClickListener(this);
        rlAlarmSmartMain.setOnClickListener(this);
        type = new TypeToken<AlarmModel>() {
        }.getType();
        if (SharePrefrancClass.getInstance(getActivity()).getPref(CommonMethod.SAVEDAYS) != null) {
            String bc = SharePrefrancClass.getInstance(getActivity()).getPref(CommonMethod.SAVEDAYS);

            AlarmModel share = new Gson().fromJson(bc, type);
            Log.d(getClass().getSimpleName(), "share" + new Gson().toJson(share));

            if (share.getModel() != null && share.getModel().size() > 0) {
                days.clear();
                days.addAll(share.getModel());
            }
            txtDaysSmart.setText(getCheckDays(days, CommonMethod.FROMSMARTALARM));
            checkSharePrefAlarmTime();
            // txtAlarmTimeSmart.setText(convertLongToDate(share.getAlarmTime(),formatDate)+" "+convertLongToDate(share.getAlarmTime(),formatDate2));

        }
        if (SharePrefrancClass.getInstance(getActivity()).getPref(CommonMethod.SAVE_DAYS_FOR_MEDITATION) != null) {
            String bc = SharePrefrancClass.getInstance(getActivity()).getPref(CommonMethod.SAVE_DAYS_FOR_MEDITATION);

            AlarmModel shares = new Gson().fromJson(bc, type);
            Log.d(getClass().getSimpleName(), "share" + new Gson().toJson(shares));
            if (shares.getModel() != null && shares.getModel().size() > 0) {
                daysForMeditation.clear();
                daysForMeditation.addAll(shares.getModel());
            }
            txtDaysMed.setText(getCheckDays(daysForMeditation, CommonMethod.FROMMEDITATION));
            txtAlarmTimeMed.setText(convertLongToDate(shares.getAlarmTime(), formatDate) + " " + convertLongToDate(shares.getAlarmTime(), formatDate2));
        }


    }

    private void checkSharePrefAlarmTime() {
        if (SharePrefrancClass.getInstance(getActivity()).getLPref(CommonMethod.START_ALARM_TIME) > 0) {
            c = Calendar.getInstance();
            c.setTimeInMillis(SharePrefrancClass.getInstance(getActivity()).getLPref(CommonMethod.START_ALARM_TIME));
            int hour = c.get(Calendar.HOUR);
            int minute = c.get(Calendar.MINUTE);
            Log.d(getClass().getSimpleName(), "ShareTime:" + hour + " " + minute);
            txtAlarmTimeSmart.setText(formatDate.format(c.getTime()) + " " + formatDate2.format(c.getTime()));
        }
    }

    private void openDateTimeDialogue(View v, String fromsmartalarm) {
        // Get Current Time
        c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        TextView txt = (TextView) v.findViewById(R.id.lbl_alarmTime);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        c = CommonMethod.ConvertTime(getActivity(), hourOfDay, minute);
                        txt.setText(formatDate.format(c.getTime()) + " " + formatDate2.format(c.getTime()));
                        if (fromsmartalarm.equalsIgnoreCase(CommonMethod.FROMSMARTALARM)) {
                            txtAlarmTimeSmart.setText(formatDate.format(c.getTime()) + " " + formatDate2.format(c.getTime()));
                        } else {
                            txtAlarmTimeMed.setText(formatDate.format(c.getTime()) + " " + formatDate2.format(c.getTime()));
                            alarmModel.setAlarmTime(c.getTimeInMillis());

                        }
                        saveToSharePref(fromsmartalarm);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    private void saveToSharePref(String fromsmartalarm) {
        share = new Gson().toJson(alarmModel, type);
        if (fromsmartalarm.equalsIgnoreCase(CommonMethod.FROMSMARTALARM)) {
            SharePrefrancClass.getInstance(getActivity()).savePref(CommonMethod.SAVEDAYS, share);
        } else {
            SharePrefrancClass.getInstance(getActivity()).savePref(CommonMethod.SAVE_DAYS_FOR_MEDITATION, share);

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_alarm_smart_main:
                openBottomSheetForSmartAlarm(CommonMethod.FROMSMARTALARM);
                break;
            case R.id.rl_alarm_meditation_main:
                openBottomSheetForSmartAlarm(CommonMethod.FROMMEDITATION);
                break;
        }

    }


    private void openBottomSheetForSmartAlarm(String fromsmartalarm) {
        final Dialog mBottomSheetDialog = new Dialog(getActivity(),
                R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(R.layout.layout_alarm_set);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);

        GridView gridView = (GridView) mBottomSheetDialog.findViewById(R.id.gridView);
        CustomFontTextView txtAlarmTimeS = (CustomFontTextView) mBottomSheetDialog.findViewById(R.id.lbl_alarmTime);
        SeekBar seekBarS = (SeekBar) mBottomSheetDialog.findViewById(R.id.seekbar);
        SwitchCompat switchCompactS = (SwitchCompat) mBottomSheetDialog.findViewById(R.id.switch_vibration);
        ImageView imgSpeaker = (ImageView) mBottomSheetDialog.findViewById(R.id.img_speaker);
        mBottomSheetDialog.show();
        seekBarS.setMax(100);

        setAlarmForSmart(seekBarS, switchCompactS, fromsmartalarm, txtAlarmTimeS);
        gridViewClickListner(txtAlarmTimeS, switchCompactS, seekBarS, fromsmartalarm);

        if (fromsmartalarm.equalsIgnoreCase(CommonMethod.FROMSMARTALARM)) {
            adapter = new AlarmDaysAdapter(getActivity(), days, this, CommonMethod.FROMSMARTALARM);
            gridView.setAdapter(adapter);

        } else {
            adapter = new AlarmDaysAdapter(getActivity(), daysForMeditation, this, CommonMethod.FROMMEDITATION);
            gridView.setAdapter(adapter);

        }


    }

    private void setAlarmForSmart(SeekBar seekBar, SwitchCompat switchCompact, String fromsmartalarm, CustomFontTextView txtAlarmTimeS) {
        if (fromsmartalarm.equalsIgnoreCase(CommonMethod.FROMSMARTALARM)) {
            if (SharePrefrancClass.getInstance(getActivity()).getPref(CommonMethod.SAVEDAYS) != null) {
                String bc = SharePrefrancClass.getInstance(getActivity()).getPref(CommonMethod.SAVEDAYS);
                AlarmModel share = new Gson().fromJson(bc, type);
                seekBar.setProgress(share.getSound());
                alarmModel.setSound(share.getSound());
                alarmModel.setVibration(share.isVibration());
                switchCompact.setChecked(share.isVibration());

            }

        } else {
            if (SharePrefrancClass.getInstance(getActivity()).getPref(CommonMethod.SAVE_DAYS_FOR_MEDITATION) != null) {
                String bc = SharePrefrancClass.getInstance(getActivity()).getPref(CommonMethod.SAVE_DAYS_FOR_MEDITATION);
                AlarmModel share = new Gson().fromJson(bc, type);
                seekBar.setProgress(share.getSound());
                alarmModel.setSound(share.getSound());
                alarmModel.setVibration(share.isVibration());
                switchCompact.setChecked(share.isVibration());
                txtAlarmTimeS.setText(convertLongToDate(share.getAlarmTime(), formatDate) + " " + convertLongToDate(share.getAlarmTime(), formatDate2));


            }
        }
         if(fromsmartalarm.equalsIgnoreCase(CommonMethod.FROMSMARTALARM))
         {
             if(SharePrefrancClass.getInstance(getActivity()).getLPref(CommonMethod.START_ALARM_TIME)>0)
             {
                 c = Calendar.getInstance();
                 c.setTimeInMillis(SharePrefrancClass.getInstance(getActivity()).getLPref(CommonMethod.START_ALARM_TIME));
                 int hour = c.get(Calendar.HOUR);
                 int minute = c.get(Calendar.MINUTE);
                 Log.d(getClass().getSimpleName(), "ShareTime:" + hour + " " + minute);
                 txtAlarmTimeS.setText(formatDate.format(c.getTime()) + " " + formatDate2.format(c.getTime()));
             }
         }


    }

    private void setVibration(boolean vibration) {
        if (vibration) {
            Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);
        }
    }

    private String convertLongToDate(long alarmTime, SimpleDateFormat formatDate) {
        c = Calendar.getInstance();
        c.setTimeInMillis(alarmTime);

        return formatDate.format(c.getTime());
    }


    private void gridViewClickListner(CustomFontTextView txtAlarmTime, SwitchCompat switchCompact, SeekBar seekBar, String fromsmartalarm) {
        txtAlarmTime.setOnClickListener(v -> {
            openDateTimeDialogue(v, fromsmartalarm);
        });
        setAlarmWidget(switchCompact, seekBar, txtAlarmTime, fromsmartalarm);

    }

    private void setAlarmWidget(SwitchCompat switchCompact, SeekBar seekBar, CustomFontTextView txtAlarmTime, String fromsmartalarm) {

        switchCompact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alarmModel.setVibration(isChecked);
                setVibration(isChecked);
                saveToSharePref(fromsmartalarm);

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alarmModel.setSound(progress);
                saveToSharePref(fromsmartalarm);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


    }


    @Override
    public void onAlarmListener(List<AlarmDaysModel> abc, String from) {
        Log.d(getClass().getSimpleName(), "Days:" + bc);
        bc = "";
        if (abc != null && abc.size() > 0) {
            bc = getCheckDays(abc, from);
            alarmModel.setModel(abc);
            if (from.equalsIgnoreCase(CommonMethod.FROMSMARTALARM)) {
                txtDaysSmart.setText(bc);
            } else {
                txtDaysMed.setText(bc);
            }

            saveToSharePref(from);


        }
    }

    private String getCheckDays(List<AlarmDaysModel> abc, String from) {
        String days = "";
        for (int i = 0; i < abc.size(); i++) {
            if (abc.get(i).isChecked()) {
                days += abc.get(i).getDays() + " ";
            }
        }
        return days;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
