package itg8.com.nowzonedesigndemo.alarm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Type;
import java.util.List;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.alarm.AlarmActivity;
import itg8.com.nowzonedesigndemo.alarm.model.AlarmDaysModel;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.common.SharePrefrancClass;
import itg8.com.nowzonedesigndemo.steps.widget.CustomFontTextView;

/**
 * Created by Android itg 8 on 6/22/2017.
 */

public class AlarmDaysAdapter extends BaseAdapter {

    private final List<AlarmDaysModel> days;
    private LayoutInflater inflater;

    private Context mContext;
     private CommonMethod.alarmListener listener;
    String from;



    public AlarmDaysAdapter(Context mContext, List<AlarmDaysModel> days, CommonMethod.alarmListener listener, String fromsmartalarm) {
        this.mContext = mContext;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.days = days;
         this.listener = listener;
        this.from= fromsmartalarm;

    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;


        if(convertView != null) {
            view = (View)convertView;
        }else {
        view = inflater.inflate(R.layout.item_alarmdays, parent, false);
        }

        CustomFontTextView txt = (CustomFontTextView) view.findViewById(R.id.txt_days);
//        ViewGroup container = (ViewGroup) view.findViewById(R.id.ll_container);
       ImageView img = (ImageView) view.findViewById(R.id.img_dots);
        txt.setText(days.get(position).getDays());
        if (days.get(position).isChecked()) {
            img.setVisibility(View.VISIBLE);

        } else {
            img.setVisibility(View.GONE);
        }



        view.setOnClickListener(new View.OnClickListener() {
            String dayselected=" ";

            @Override
            public void onClick(View v) {

                if (days.get(position).isChecked()) {
                    setImageViewFadeOut(img, position);
                } else {
                    setImageViewFadeIn(img, position);
                }
                listener.onAlarmListener(days, from);

                // List<AlarmDaysModel> list2 = gson.fromJson(s, listOfTestObject);

            }
        });




        return view;
    }

    private void setImageViewFadeIn(View img, int position) {
        img.setVisibility(View.GONE);

        days.get(position).setChecked(true);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
       // fadeIn.setDuration(100);
        img.setAnimation(fadeIn);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                img.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fadeIn.start();
        notifyDataSetInvalidated();
    }

    private void setImageViewFadeOut(View img, int position) {
        days.get(position).setChecked(false);
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        //fadeOut.setDuration(100);
        img.setAnimation(fadeOut);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                img.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fadeOut.start();
        notifyDataSetInvalidated();
    }



}
