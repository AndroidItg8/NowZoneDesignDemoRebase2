package itg8.com.nowzonedesigndemo.setting.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.steps.widget.CustomFontTextView;
import itg8.com.nowzonedesigndemo.widget.BatteryView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDevice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDevice extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.lbl_device_title)
    CustomFontTextView lblDeviceTitle;
    @BindView(R.id.lbl_nowZone)
    CustomFontTextView lblNowZone;
    @BindView(R.id.lbl_about_device)
    CustomFontTextView lblAboutDevice;
    @BindView(R.id.img_nowzone)
    ImageView imgNowzone;
    @BindView(R.id.lbl_device_name)
    CustomFontTextView lblDeviceName;
    @BindView(R.id.lbl_status)
    CustomFontTextView lblStatus;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.img_battery)
    BatteryView imgBattery;
    @BindView(R.id.lbl_battery)
    CustomFontTextView lblBattery;
    @BindView(R.id.lbl_battery_status)
    CustomFontTextView lblBatteryStatus;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FragmentDevice() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDevice.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDevice newInstance(String param1, String param2) {
        FragmentDevice fragment = new FragmentDevice();
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
        View view = inflater.inflate(R.layout.fragment_fragment_device, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        imgBattery.setmLevel(5);
        imgBattery.setmCharging(false);
        getActivity().setTitle("Device");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
