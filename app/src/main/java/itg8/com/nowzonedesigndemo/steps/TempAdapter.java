package itg8.com.nowzonedesigndemo.steps;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.steps.widget.CustomFontTextView;

/**
 * Created by Android itg 8 on 5/25/2017.
 */

public class TempAdapter extends RecyclerView.Adapter<TempAdapter.MYViewHolder> {



    private Context activity;

    public TempAdapter(FragmentActivity activity) {

        this.activity = activity;
    }

    @Override
    public MYViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_history_sleep, parent, false);
        MYViewHolder holder = new MYViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MYViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 50;
    }

    public class MYViewHolder extends RecyclerView.ViewHolder {



        @BindView(R.id.txt_miles_value)
        CustomFontTextView txtMilesValue;
        @BindView(R.id.ll_miles)
        RelativeLayout llMiles;
        @BindView(R.id.txt_goals)
        CustomFontTextView txtGoals;
        @BindView(R.id.txt_golas_value)
        CustomFontTextView txtGolasValue;
        @BindView(R.id.txt_avg)
        CustomFontTextView txtAvg;
        @BindView(R.id.txt_avg_value)
        CustomFontTextView txtAvgValue;


        public MYViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
