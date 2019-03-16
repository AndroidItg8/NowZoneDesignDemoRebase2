package itg8.com.nowzonedesigndemo.setting.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.common.Prefs;
import itg8.com.nowzonedesigndemo.setting.SettingMainActivity;
import itg8.com.nowzonedesigndemo.widget.SnippingRecyclerview;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeditationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeditationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = MeditationFragment.class.getSimpleName();
    @BindView(R.id.rv_audio_stages)
    SnippingRecyclerview mRvAudioStages;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;
    private AudioAdapter audioAdapter;
    private int overallXScroll = 0;
    private RecyclerView.LayoutManager linearLayoutManager;


    public MeditationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeditationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeditationFragment newInstance(String param1, String param2) {
        MeditationFragment fragment = new MeditationFragment();
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
//        setSelectedPosition();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_meditation2, container, false);
        unbinder = ButterKnife.bind(this, view);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        linearLayoutManager=new CenterZoomLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        mRvAudioStages.setLayoutManager(linearLayoutManager);
        mRvAudioStages.enableViewScaling(true);
        audioAdapter = new AudioAdapter(context);
        mRvAudioStages.setAdapter(audioAdapter);


        audioAdapter.setListener(new AudioAdapter.AudioFileClicked() {
            @Override
            public void onAudioClicked(int position, Object o) {
                View[] views = (View[]) o;
                Prefs.putInt(CommonMethod.STAGE_HEARED,position);
                ((SettingMainActivity) getActivity()).startStransaction(views,position);
            }
        });
        return view;
    }

    private void setSelectedPosition() {
        int i= Prefs.getInt(CommonMethod.STAGE_HEARED,0);
        if(i>0 && mRvAudioStages!=null && mRvAudioStages.getLayoutManager().getChildCount()>=i){
            mRvAudioStages.getLayoutManager().scrollToPosition(i);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setSelectedPosition();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
