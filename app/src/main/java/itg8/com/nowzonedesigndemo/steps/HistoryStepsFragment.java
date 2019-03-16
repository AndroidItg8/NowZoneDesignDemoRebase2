package itg8.com.nowzonedesigndemo.steps;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.common.CommonMethod;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryStepsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryStepsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Unbinder unbinder;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.releative_viewPager)
    RelativeLayout releativeViewPager;

    @BindView(R.id.img_left)
    ImageView imgLeft;
    @BindView(R.id.img_right)
    ImageView imgRight;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public HistoryStepsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     *               //     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryStepsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryStepsFragment newInstance(String param1) {
        HistoryStepsFragment fragment = new HistoryStepsFragment();
        Bundle args = new Bundle();
        args.putString(CommonMethod.FROMWEEk, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(CommonMethod.FROMWEEk);
//            mParam1 = getArguments().getString(CommonMethod.FROMDAY);


        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_steps, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (mParam1 != null) {
            releativeViewPager.setVisibility(View.VISIBLE);
            imgLeft.setVisibility(View.VISIBLE);
            imgRight.setVisibility(View.VISIBLE);
            FragmentManager fm = getChildFragmentManager();
            viewPager.setAdapter(new WeekFragmnetPager(fm));
        }
        if (mParam1 != null) {
            recyclerview.setVisibility(View.VISIBLE);
        }
        setStikyHeader();


        return view;
    }

    private void setStikyHeader() {

        // Set layout manager
        int orientation = getLayoutManagerOrientation(getResources().getConfiguration().orientation);
        //final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), orientation, isReverseButton.isChecked());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), orientation, false);
        recyclerview.setLayoutManager(layoutManager);

//        final StepsHeaderAdapter adapter = new StepsHeaderAdapter();
//        //  adapter.add("Months below!");
//        adapter.addAll(getDummyDataSet());
//        recyclerview.setAdapter(adapter);
//
//        // Add the sticky headers decoration
//        //  final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter, this);
//
//        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
//        recyclerview.addItemDecoration(headersDecor);
//        recyclerview.setNestedScrollingEnabled(false);
//
//        // Add decoration for dividers between list items
//        recyclerview.addItemDecoration(new DividerDecoration(getActivity()));
//
//
//        // Set adapter populated with example dummy data
//        // Add touch listeners
//        StickyRecyclerHeadersTouchListener touchListener =
//                new StickyRecyclerHeadersTouchListener(recyclerview, headersDecor);
//        touchListener.setOnHeaderClickListener(
//                new StickyRecyclerHeadersTouchListener.OnHeaderClickListener() {
//                    @Override
//                    public void onHeaderClick(View header, int position, long headerId) {
//                        Toast.makeText(getActivity(), "Header position: " + position + ", id: " + headerId,
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//        recyclerview.addOnItemTouchListener(touchListener);
//        recyclerview.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                //adapter.remove(adapter.getItem(position));
//            }
//        }));
//        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//            @Override
//            public void onChanged() {
//                headersDecor.invalidateHeaders();
//                adapter.notifyDataSetChanged();
//
//            }
//        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private String[] getDummyDataSet() {
        return getResources().getStringArray(R.array.month);
    }

    private int getLayoutManagerOrientation(int activityOrientation) {
        if (activityOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            return LinearLayoutManager.VERTICAL;
        } else {
            return LinearLayoutManager.HORIZONTAL;
        }
    }


}
