package itg8.com.nowzonedesigndemo.steps;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.db.tbl.TblStepCount;
import itg8.com.nowzonedesigndemo.steps.stickyHeader.DividerDecoration;
import itg8.com.nowzonedesigndemo.steps.stickyHeader.StickyHeaderDecoration;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryMonthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryMonthFragment extends Fragment implements RecyclerView.OnItemTouchListener, MonthFragmentCommunicator {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Unbinder unbinder;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private StickyHeaderDecoration decor;
    private MonthHistoryListAdapter adapter;


    public HistoryMonthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryMonthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryMonthFragment newInstance(String param1, String param2) {
        HistoryMonthFragment fragment = new HistoryMonthFragment();
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
        View view = inflater.inflate(R.layout.fragment_history_steps, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((StepsActivity)getActivity()).setMonthListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setStikyHeader();
    }

    private void setStikyHeader() {
        final DividerDecoration divider = new DividerDecoration.Builder(this.getActivity())
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.default_header_color)
                .build();

        recyclerview.setHasFixedSize(true);
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerview.addItemDecoration(divider);

        setAdapterAndDecor();
    }

    private void setAdapterAndDecor() {
        adapter = new MonthHistoryListAdapter(this.getActivity());
        decor = new StickyHeaderDecoration(adapter);
        recyclerview.addItemDecoration(decor, 1);
//        setHasOptionsMenu(true);

        recyclerview.setAdapter(adapter);
        recyclerview.addOnItemTouchListener(this);
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


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View v = rv.findChildViewUnder(e.getX(), e.getY());
        return v == null;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        if (e.getAction() != MotionEvent.ACTION_UP) {
            return;
        }

        // find the header that was clicked
        View view = decor.findHeaderViewUnder(e.getX(), e.getY());

//        if (view instanceof TextView) {
//            Toast.makeText(this.getActivity(), ((TextView) view).getText() + " clicked", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    @Override
    public void onMonthStepHistoryGot(List<TblStepCount> counts) {
        setStikyHeader();
        adapter.addAll(counts);
    }
}
