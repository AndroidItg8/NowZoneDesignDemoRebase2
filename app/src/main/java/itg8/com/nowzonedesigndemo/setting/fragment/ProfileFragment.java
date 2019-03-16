package itg8.com.nowzonedesigndemo.setting.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.alarm.AlarmActivity;
import itg8.com.nowzonedesigndemo.common.AppApplication;
import itg8.com.nowzonedesigndemo.common.ProfileModel;
import itg8.com.nowzonedesigndemo.profile.ProfileActivity;
import itg8.com.nowzonedesigndemo.steps.widget.CustomFontTextView;
import itg8.com.nowzonedesigndemo.utility.Helper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment  implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.basic_detail)
    CustomFontTextView basicDetail;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.input_name)
    TextInputLayout inputName;
    @BindView(R.id.edt_height)
    EditText edtHeight;
    @BindView(R.id.input_height)
    TextInputLayout inputHeight;
    @BindView(R.id.edt_height_feet)
    EditText edtHeightFeet;
    @BindView(R.id.input_height_feet)
    TextInputLayout inputHeightFeet;
    @BindView(R.id.edt_height_inch)
    EditText edtHeightInch;
    @BindView(R.id.input_height_inch)
    TextInputLayout inputHeightInch;
    @BindView(R.id.ll_height_feet)
    LinearLayout llHeightFeet;
    @BindView(R.id.frame_height)
    FrameLayout frameHeight;
    @BindView(R.id.edt_weight)
    EditText edtWeight;
    @BindView(R.id.input_weight)
    TextInputLayout inputWeight;
    @BindView(R.id.edt_gender)
    EditText edtGender;
    @BindView(R.id.input_gender)
    TextInputLayout inputGender;
    @BindView(R.id.lbl_birth)
    CustomFontTextView lblBirth;
    @BindView(R.id.edt_day)
    EditText edtDay;
    @BindView(R.id.input_day)
    TextInputLayout inputDay;
    @BindView(R.id.edt_month)
    EditText edtMonth;
    @BindView(R.id.input_month)
    TextInputLayout inputMonth;
    @BindView(R.id.edt_year)
    EditText edtYear;
    @BindView(R.id.input_year)
    TextInputLayout inputYear;
    @BindView(R.id.ll_age)
    LinearLayout llAge;
    @BindView(R.id.button)
    Button button;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ProfileModel model;
    private float heightInFeet;
    private float weightInKg;
    private boolean isFromCm;
    private boolean isFromKG;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    private void init() {
        getActivity().setTitle("Profile");

        model = new ProfileModel();
        button.setOnClickListener(this);
        lblBirth.setOnClickListener(this);
        edtDay.setOnClickListener(this);
        edtMonth.setOnClickListener(this);
        edtYear.setOnClickListener(this);
        edtGender.setOnClickListener(this);
        edtHeight.setOnClickListener(this);
        edtWeight.setOnClickListener(this);
        edtHeightInch.setVisibility(View.GONE);
        edtHeightFeet.setVisibility(View.GONE);
        edtHeightFeet.setOnClickListener(this);
        edtHeightInch.setOnClickListener(this);
}

    private void openDateTimeDialogue() {

        // Get Current Time
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub

                mcurrentDate.set(Calendar.YEAR, selectedyear);
                mcurrentDate.set(Calendar.MONTH, selectedmonth);
                mcurrentDate.set(Calendar.DAY_OF_MONTH, selectedday);
                String myFormat = "dd/MMM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

                String[] entireDate = sdf.format(mcurrentDate.getTime()).split("/");
                String day = entireDate[0];
                String month = entireDate[1];
                String year = entireDate[2];
                edtDay.setText(day);
                edtMonth.setText(month);
                edtYear.setText(year);
                Log.d(getClass().getSimpleName(), "DatePicker:" + mcurrentDate.getTime());


            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select date");
        mDatePicker.show();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                if (validate()) {
                    model.setName(edtName.getText().toString());
                    model.setHeight(calculateHeightInFeet());
                    model.setWeight(calculateWeightInKg());
                    //((AppApplication) getApplication()).setProfileModel(model);
                  // getActivity().onBackPressed();
                   getActivity().finish();
                }
                break;
            case R.id.lbl_birth:
                //  openDateTimeDialogue();
                break;

            case R.id.edt_day:
                openDateTimeDialogue();
                break;
            case R.id.edt_month:
                openDateTimeDialogue();
                break;
            case R.id.edt_year:
                openDateTimeDialogue();
                break;

            case R.id.edt_gender:
                if (TextUtils.isEmpty(edtGender.getText())) {
                    openBottomSheetDialogueForGender();
                } else {
                    // edtGender.setText(" ");
                    openBottomSheetDialogueForGender();
                }
                break;
            case R.id.edt_height:
                if (TextUtils.isEmpty(edtHeight.getText())) {
                    openBottomSheetDialogueForHeight();
                }else
                {
                    //edtHeight.setText(" ");
                    edtHeight.setFocusableInTouchMode(false);
                    openBottomSheetDialogueForHeight();

                }
                break;
            case R.id.edt_height_feet:
                if (TextUtils.isEmpty(edtHeightFeet.getText())) {
                    openBottomSheetDialogueForHeight();
                }else
                {
                    // edtHeightFeet.setText(" ");
                    edtHeightFeet.setFocusableInTouchMode(false);
                    openBottomSheetDialogueForHeight();
                }

                break;
            case R.id.edt_height_inch:
                if (TextUtils.isEmpty(edtHeightInch.getText())) {
                    openBottomSheetDialogueForHeight();
                }else
                {
                    //edtHeightInch.setText(" ");
                    edtHeightInch.setFocusableInTouchMode(false);
                    openBottomSheetDialogueForHeight();
                }
                break;
            case R.id.edt_weight:
                if(TextUtils.isEmpty(edtWeight.getText()))
                {
                    OpenBottomSheetDialogueForWeight();
                }else
                {
                    // edtWeight.setText(" ");
                    edtWeight.setFocusableInTouchMode(false);
                    OpenBottomSheetDialogueForWeight();
                }
                break;


        }


     }

    private void OpenBottomSheetDialogueForWeight() {
        final Dialog mBottomSheetDialog = new Dialog(getActivity(),
                R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(R.layout.item_bottomsheet_weight);

        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);


      //  View view = getLayoutInflater().inflate(R.layout.item_bottomsheet_weight, null);
        CustomFontTextView txtKg = (CustomFontTextView) mBottomSheetDialog.findViewById(R.id.lbl_kg);
        CustomFontTextView txtPounds = (CustomFontTextView) mBottomSheetDialog.findViewById(R.id.lbl_pounds);

        Button btnOk = (Button) mBottomSheetDialog.findViewById(R.id.btn_ok);

        mBottomSheetDialog.show();


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();


            }
        });
        txtKg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromKG = true;
                mBottomSheetDialog.dismiss();
                setDataFromBottomSheetForWieght();

            }
        });
        txtPounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromKG = false;
                mBottomSheetDialog.dismiss();
                setDataFromBottomSheetForWieght();
            }
        });



    }

    private void openBottomSheetDialogueForHeight() {
        final Dialog mBottomSheetDialog = new Dialog(getActivity(),
                R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(R.layout.item_bottomsheet_height);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);

       // View view = getLayoutInflater().inflate(R.layout.item_bottomsheet_height, null);
        CustomFontTextView txtCm = (CustomFontTextView) mBottomSheetDialog.findViewById(R.id.lbl_cm);
        CustomFontTextView txtFeet = (CustomFontTextView) mBottomSheetDialog.findViewById(R.id.lbl_feet);

        Button btnOk = (Button) mBottomSheetDialog.findViewById(R.id.btn_ok);

        mBottomSheetDialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        txtCm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromCm= true;
                setDataFromBottomSheetForHeight();
                mBottomSheetDialog.dismiss();


            }
        });
        txtFeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromCm=false;
                setDataFromBottomSheetForHeight();
                mBottomSheetDialog.dismiss();


            }
        });

    }

    private void setDataFromBottomSheetForHeight() {
        if(isFromCm==true) {
            edtHeight.setVisibility(View.VISIBLE);
            llHeightFeet.setVisibility(View.GONE);
            edtHeight.setFocusable(true);
            edtHeight.setFocusableInTouchMode(true);
            edtHeight.setInputType(InputType.TYPE_CLASS_NUMBER);
        }else
        {
            edtHeight.setVisibility(View.GONE);
            llHeightFeet.setVisibility(View.VISIBLE);
            edtHeightFeet.setVisibility(View.VISIBLE);
            edtHeightInch.setVisibility(View.VISIBLE);
            llHeightFeet.setFocusableInTouchMode(true);
            edtHeightInch.setFocusableInTouchMode(true);
            edtHeightFeet.setFocusableInTouchMode(true);
            edtHeightInch.setFocusable(true);
            edtHeightInch.setInputType(InputType.TYPE_CLASS_NUMBER);
            edtHeightFeet.setFocusable(true);
            edtHeightFeet.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        isFromCm=!isFromCm;
    }

    private void setDataFromBottomSheet(String gender) {
        edtGender.setText(gender);
        Log.d(getClass().getSimpleName(), "BottomSheet:" + gender);

    }

    private void openBottomSheetDialogueForGender() {
        final Dialog mBottomSheetDialog = new Dialog(getActivity(),
                R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(R.layout.fragment_basic_profile);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        CustomFontTextView txtFemale = (CustomFontTextView) mBottomSheetDialog.findViewById(R.id.lbl_female);
        CustomFontTextView txtMale = (CustomFontTextView) mBottomSheetDialog.findViewById(R.id.lbl_male);

        Button btnOk = (Button) mBottomSheetDialog.findViewById(R.id.btn_ok);

        mBottomSheetDialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        txtFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fe = txtFemale.getText().toString();
                setDataFromBottomSheet(fe);
                mBottomSheetDialog.dismiss();


            }
        });
        txtMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma = txtMale.getText().toString();
                setDataFromBottomSheet(ma);
                mBottomSheetDialog.dismiss();

            }
        });


    }

    private void setDataFromBottomSheetForWieght() {
        edtWeight.setFocusableInTouchMode(true);
        edtWeight.setInputType(InputType.TYPE_CLASS_NUMBER);
    }


    private float calculateWeightInKg() {
        if (isFromKG)
            return (float) poundsToKilos(Double.parseDouble(edtWeight.getText().toString()));
        else
            return Float.parseFloat(edtWeight.getText().toString());
    }
    private static double poundsToKilos(double pounds) {
        return pounds * 0.454;
    }
    private float calculateHeightInFeet() {
        if (isFromCm)
            return Helper.centimeterToFeet(edtHeight.getText().toString());
        else
            return (float) (Float.parseFloat(edtHeight.getText().toString()) + (0.1 * Float.parseFloat(edtHeight.getText().toString())));
    }

    private boolean validate() {
        boolean valid = true;
        if (TextUtils.isEmpty(edtName.getText().toString())) {
            edtName.setError("Please provide name...");
            setFocus(edtName);
            valid = false;
        }
        if (TextUtils.isEmpty(edtHeight.getText().toString())) {
            edtHeight.setError("Please provide your height...");
            setFocus(edtHeight);
            valid = false;
        }
        if (TextUtils.isEmpty(edtWeight.getText().toString())) {
            edtWeight.setError("Please provide your weight...");
            setFocus(edtWeight);
            valid = false;
        }

        return valid;
    }

    private void setFocus(EditText edtName) {
        edtName.requestFocus();

    }

}
