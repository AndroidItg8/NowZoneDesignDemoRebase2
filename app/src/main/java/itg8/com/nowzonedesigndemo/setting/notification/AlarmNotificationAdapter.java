package itg8.com.nowzonedesigndemo.setting.notification;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.steps.widget.CustomFontTextView;

/**
 * Created by Android itg 8 on 7/3/2017.
 */

public class AlarmNotificationAdapter extends RecyclerView.Adapter<AlarmNotificationAdapter.MyAlarmViewHolder> {


    private Context context;

    public AlarmNotificationAdapter(Context applicationContext) {

        this.context = applicationContext;
    }

    @Override
    public MyAlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_alarm_notification, parent, false);
        MyAlarmViewHolder holder = new MyAlarmViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyAlarmViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyAlarmViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_app_name)
        CustomFontTextView txtAppName;
        @BindView(R.id.img_close)
        ImageView imgClose;
        @BindView(R.id.txt_alarmStarted)
        CustomFontTextView txtAlarmStarted;
        public MyAlarmViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
}
