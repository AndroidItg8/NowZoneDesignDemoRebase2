package itg8.com.nowzonedesigndemo.breath;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by Android itg 8 on 7/11/2017.
 */

public class BreathPagerAdapter  extends FragmentPagerAdapter {
    private Context context;


    public BreathPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
    }


    @Override
    public Fragment getItem(int position) {

        BreathHistoryFragment fragment = new BreathHistoryFragment();
        return fragment;
    }

}
