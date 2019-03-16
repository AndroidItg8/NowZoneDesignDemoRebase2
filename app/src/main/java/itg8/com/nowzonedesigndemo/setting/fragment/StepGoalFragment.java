package itg8.com.nowzonedesigndemo.setting.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.common.SharePrefrancClass;
import itg8.com.nowzonedesigndemo.steps.widget.CustomFontTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepGoalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepGoalFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.flag)
    ImageView flag;
    @BindView(R.id.btn_remove)
    Button btnRemove;
    @BindView(R.id.editText)
    CustomFontTextView editText;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_save)
    Button btnSave;

    Unbinder unbinder;
    private int stepGoalValue;
    private int minimumGoalStep = 1000;
    private int maximumGoalStep = 10000;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public StepGoalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StepGoalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StepGoalFragment newInstance(String param1, String param2) {
        StepGoalFragment fragment = new StepGoalFragment();
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
        View view = inflater.inflate(R.layout.fragment_step_goal, container, false);
        unbinder = ButterKnife.bind(this, view);
        setInit();
        return view;
    }
    private void setInit() {

        btnAdd.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        getActivity().setTitle("Step Goal");
        if(SharePrefrancClass.getInstance(getActivity()).getIPreference(CommonMethod.GOAL)>0)
        {
            stepGoalValue= SharePrefrancClass.getInstance(getActivity()).getIPreference(CommonMethod.GOAL);
            editText.setText(String.valueOf(stepGoalValue));
        }else {
            stepGoalValue = Integer.parseInt(editText.getText().toString());
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_add:
                AddStepGoalValue();
                break;
            case R.id.btn_remove:
                RemoveStepGoalValue();
                break;
            case R.id.btn_save:
                saveStepGoal();
                break;

        }

    }

    private void saveStepGoal() {
        int stepVale= Integer.parseInt(editText.getText().toString());
        SharePrefrancClass.getInstance(getActivity()).setIPreference(CommonMethod.GOAL,stepVale);



    }

    private void RemoveStepGoalValue() {

        if(stepGoalValue==minimumGoalStep)
        {
            editText.setText(String.valueOf(stepGoalValue));
            return;
        }
        stepGoalValue-=500;
        editText.setText(String.valueOf(stepGoalValue));

    }

    private void AddStepGoalValue() {
        if(stepGoalValue== maximumGoalStep)
        {
            editText.setText(String.valueOf(stepGoalValue));
            return;
        }
        stepGoalValue+=500;
        editText.setText(String.valueOf(stepGoalValue));
    }
}
