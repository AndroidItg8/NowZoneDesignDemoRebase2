package itg8.com.nowzonedesigndemo.sleep.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import itg8.com.nowzonedesigndemo.sleep.SleepFragment;

/**
 * Created by Android itg 8 on 5/19/2017.
 */

public class ViewPagerSleepAdapter extends FragmentPagerAdapter {
    private Context context;


    public ViewPagerSleepAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
    }


    @Override
    public Fragment getItem(int position) {

        SleepFragment fragment = new SleepFragment();
        return fragment;
    }


}
