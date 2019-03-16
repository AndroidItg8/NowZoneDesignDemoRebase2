package itg8.com.nowzonedesigndemo.steps;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Android itg 8 on 6/1/2017.
 */

public class WeekFragmnetPager extends FragmentPagerAdapter {

    private Context mContext;

    public WeekFragmnetPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        WeekFragment weekFragment = new WeekFragment();
        return weekFragment;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
