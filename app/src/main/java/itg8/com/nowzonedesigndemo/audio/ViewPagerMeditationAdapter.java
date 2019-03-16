package itg8.com.nowzonedesigndemo.audio;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableStringBuilder;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;


import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.audio.fragment.MeditationFragment;

/**
 * Created by Android itg 8 on 7/8/2017.
 */

public class ViewPagerMeditationAdapter extends FragmentPagerAdapter {


    private Context mContext;
    private int position;

    public ViewPagerMeditationAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Fragment getItem(int position) {
        // Create fragment object
        MeditationFragment fragment = new MeditationFragment ();

//        Bundle args = new Bundle();
//        args.putInt("page_position", position + 1);
//
//     fragment.setArguments(args);

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        SpannableStringBuilder sb = new SpannableStringBuilder(" "+position+1 ); // space added before text for convenience
        Drawable drawables;
        if(this.position == position) {
           drawables = mContext.getResources().getDrawable(R.drawable.ic_play_dp);
        }else
        {
            drawables = mContext.getResources().getDrawable( R.drawable.ic_play_arrow_black_24dp );

        }
        drawables.setBounds(4, 0, drawables.getIntrinsicWidth(), drawables.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(drawables);
       sb.setSpan(new ForegroundColorSpan(Color.WHITE),0,sb.length(), 0);
        sb.setSpan(new RelativeSizeSpan(2f),0, sb.length(), 0);
        sb.setSpan(span, sb.length()-1, sb.length(), 0);

        return sb ;
    }


    public void setSelectedItem(int selectedPosition) {
        position = selectedPosition;
    }



}
