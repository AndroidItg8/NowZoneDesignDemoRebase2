package itg8.com.nowzonedesigndemo.audio.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.audio.ViewPagerMeditationAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeditationAllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeditationAllFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.lbl_minute)
    TextView lblMinute;
    @BindView(R.id.lbl_meditation)
    TextView lblMeditation;
    @BindView(R.id.lbl_life)
    TextView lblLife;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.rl_first)
    RelativeLayout rlFirst;
    //    @BindView(R.id.lbl_meditation_start)
//    TextView lblMeditationStart;
//    @BindView(R.id.img_play)
//    ImageView imgPlay;
//    @BindView(R.id.img_pause)
//    ImageView imgPause;

    Unbinder unbinder;

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.titlestrip)
    PagerTitleStrip titlestrip;
    @BindView(R.id.rl_second)
    RelativeLayout rlSecond;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Animation animFadeIn;
    private Animation animFadeOut;
    private MeditationAllFragment meditationAllFragment;
    private MeditationFragment meditationFragment;
    private ViewPagerMeditationAdapter adapter;


    public MeditationAllFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeditationAllFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeditationAllFragment newInstance(String param1, String param2) {
        MeditationAllFragment fragment = new MeditationAllFragment();
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
        View view = inflater.inflate(R.layout.fragment_meditation_all, container, false);
        unbinder = ButterKnife.bind(this, view);
        setInit();
        return view;
    }

    private void setInit() {
        getActivity().setTitle("Meditation");

        setupViewpager();

        btnStart.setOnClickListener(this);
        animFadeIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.fade_out);


    }

    private void setupViewpager() {
        adapter = new ViewPagerMeditationAdapter(getActivity().getSupportFragmentManager(), getActivity());
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                rlSecond.setVisibility(View.VISIBLE);
                rlSecond.startAnimation(animFadeIn);
                rlFirst.startAnimation(animFadeOut);
                break;
            case R.id.img_play:
                ViewCompat.setTransitionName(v.findViewById(R.id.img_play), "1");
                FragmentTransaction ft = getFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, meditationFragment)
                        .addToBackStack(getClass().getSimpleName())
                        .addSharedElement(v.findViewById(R.id.img_play), "1");
                ft.commit();
                break;
//            case R.id.img_pause:
//                ViewCompat.setTransitionName(v.findViewById(R.id.img_pause), "2");
//                FragmentTransaction fts = getFragmentManager().beginTransaction()
//                        .replace(R.id.frameLayout, meditationFragment)
//                        .addToBackStack("transaction")
//                        .addSharedElement(v.findViewById(R.id.img_pause), "2");
//                // Apply the transaction
//                fts.commit();
            //break;


            default:
                break;

        }


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        adapter.setSelectedItem(position);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
