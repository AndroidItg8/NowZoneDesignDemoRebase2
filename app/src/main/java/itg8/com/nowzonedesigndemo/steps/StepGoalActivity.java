package itg8.com.nowzonedesigndemo.steps;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.common.SharePrefrancClass;
import itg8.com.nowzonedesigndemo.steps.widget.CustomFontTextView;

public class StepGoalActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
    @BindView(R.id.line_1)
    View line1;
    @BindView(R.id.lbl_notify)
    CustomFontTextView lblNotify;
    @BindView(R.id.switch_notify)
    ToggleButton switchNotify;
    private int stepGoalValue;
     private int minimumGoalStep = 1000;
     private int maximumGoalStep = 10000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_goal);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        ButterKnife.bind(this);
        setInit();



    }

    private void setInit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnAdd.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        if(SharePrefrancClass.getInstance(getApplicationContext()).getIPreference(CommonMethod.GOAL)>0)
        {
            stepGoalValue= SharePrefrancClass.getInstance(getApplicationContext()).getIPreference(CommonMethod.GOAL);
            editText.setText(String.valueOf(stepGoalValue));
        }else {
            stepGoalValue = Integer.parseInt(editText.getText().toString());
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
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
        SharePrefrancClass.getInstance(getApplicationContext()).setIPreference(CommonMethod.GOAL,stepVale);



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
