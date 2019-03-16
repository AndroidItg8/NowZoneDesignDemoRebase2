package itg8.com.nowzonedesigndemo.steps;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import itg8.com.nowzonedesigndemo.R;

/**
 * Created by Android itg 8 on 5/24/2017.
 */

public class MyPageTabLayoutAdapter extends FragmentPagerAdapter {
Context context;

    public final int PAGE_COUNT = 3;

    private final String[] mTabsTitle = {"Week", "Month", "Yearly"};
    private final int[] micons={R.drawable.ic_week,R.drawable.ic_month, R.drawable.ic_year};

    public MyPageTabLayoutAdapter(FragmentManager fm, Context context) {
        super(fm);
         this.context = context;
    }

    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(mTabsTitle[position]);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        icon.setImageResource(micons[position]);
        return view;
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos) {

            case 0:
                return PagerHistoryFragment.newInstance("1","1");

            case 1:
                return PagerHistoryFragment.newInstance("2","2");
            case 2:
                return PagerHistoryFragment.newInstance("3","3");

        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabsTitle[position];
    }
}
