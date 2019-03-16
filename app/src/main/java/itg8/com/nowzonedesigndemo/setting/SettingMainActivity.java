package itg8.com.nowzonedesigndemo.setting;

import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.audio.fragment.MeditationSingleFragment;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.setting.fragment.AlarmFragment;
import itg8.com.nowzonedesigndemo.setting.fragment.AlarmSettingFragment;
import itg8.com.nowzonedesigndemo.setting.fragment.FragmentDevice;
import itg8.com.nowzonedesigndemo.setting.fragment.MeditationFragment;
import itg8.com.nowzonedesigndemo.setting.fragment.ProfileFragment;
import itg8.com.nowzonedesigndemo.setting.fragment.StepGoalFragment;

public class SettingMainActivity extends AppCompatActivity {

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_main);
        init();


    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkIntent();


    }

    private void checkIntent() {
        Fragment fragment = null;

        if (getIntent().hasExtra(CommonMethod.BREATH)) {
            switch (getIntent().getStringExtra(CommonMethod.BREATH)) {
                case CommonMethod.FROM_DEVICE:
                    fragment = new FragmentDevice();
                    callFragmentInstaance(fragment);
                    break;
                case CommonMethod.FROM_STEP_GOAL:
                    fragment = new StepGoalFragment();
                    callFragmentInstaance(fragment);
                    break;
                case CommonMethod.FROM_PROFILE:
                    fragment = new ProfileFragment();
                    callFragmentInstaance(fragment);
                    break;
                case CommonMethod.FROM_ALARM_SETTING:
                    fragment = new AlarmFragment();
                    callFragmentInstaance(fragment);
                    break;
                case CommonMethod.FROM_MEDITATION:
//                    fragment = new MeditationAllFragment();
                    fragment = new MeditationFragment();
                    callFragmentInstaance(fragment);
                    break;
                case CommonMethod.FROM_DEVICE_HISTORY:
                    //fragment = new DeviceHistoryFragment();
                    //  callFragmentInstaance(fragment);
                    break;
                case CommonMethod.FROM_ALARM_HOME:
                    fragment = new AlarmSettingFragment();
                    callFragmentInstaance(fragment);
                    break;

            }
        }
    }


    private void callFragmentInstaance(Fragment fragmentDevice) {
        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frameLayout_setting, fragmentDevice).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void startStransaction(View[] views, int position) {
//        Intent intent = new Intent(SettingMainActivity.this, AudioPlayerActivity.class);
        Pair<View, String> p1 = Pair.create(views[0], getString(R.string.logoTransitionName));
        Pair<View, String> p2 = Pair.create(views[1], getString(R.string.textLogoTransitionName));
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, p1,p2);
//        getWindow().setExitTransition(new Explode());
//        startActivity(intent, options.toBundle());

        fm.beginTransaction()
                .addSharedElement(views[0], ViewCompat.getTransitionName(views[0]))
                .addSharedElement(views[1], ViewCompat.getTransitionName(views[1]))
                .replace(R.id.frameLayout_setting,MeditationSingleFragment.newInstance(ViewCompat.getTransitionName(views[0]),ViewCompat.getTransitionName(views[1]),position))
                .addToBackStack(null)
                .commit();
    }
}
