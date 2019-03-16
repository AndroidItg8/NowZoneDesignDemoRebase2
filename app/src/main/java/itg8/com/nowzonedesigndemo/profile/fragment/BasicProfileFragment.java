package itg8.com.nowzonedesigndemo.profile.fragment;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.steps.widget.CustomFontTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasicProfileFragment extends BottomSheetDialogFragment implements View.OnClickListener {


    @BindView(R.id.lbl_gender)
    CustomFontTextView lblGender;
    @BindView(R.id.lbl_female)
    CustomFontTextView lblFemale;
    @BindView(R.id.lbl_male)
    CustomFontTextView lblMale;
    Unbinder unbinder;
    @BindView(R.id.btn_ok)
    Button btnOk;

    public BasicProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basic_profile, container, false);
        init();

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void init() {
//        lblFemale.setOnClickListener(this);
//        lblMale.setOnClickListener(this);
        btnOk.setOnClickListener(this);


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
            case  R.id.lbl_female:
                break;
             case  R.id.lbl_male:
             break;
             case  R.id.btn_ok:
                 dismiss();
             break;
         }

    }
}
