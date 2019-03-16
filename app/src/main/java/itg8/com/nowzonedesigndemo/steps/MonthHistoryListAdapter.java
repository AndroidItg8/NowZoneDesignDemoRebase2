package itg8.com.nowzonedesigndemo.steps;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.db.tbl.TblStepCount;
import itg8.com.nowzonedesigndemo.steps.stickyHeader.StickyHeaderAdapter;

/**
 * Created by itg_Android on 6/1/2017.
 */

class MonthHistoryListAdapter extends RecyclerView.Adapter<MonthHistoryListAdapter.MainViewHolder> implements StickyHeaderAdapter<MonthHistoryListAdapter.HeaderViewHolder>{


    private final LayoutInflater mInflater;
    private List<TblStepCount> list;

    public MonthHistoryListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        list=new ArrayList<>();
    }

    public void addAll(List<TblStepCount> counts){
        for (TblStepCount count :
                counts){
            add(count);
        }
        notifyDataSetChanged();
    }

    private void add(TblStepCount count) {
        this.list.add(count);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(mInflater.inflate(R.layout.item_rv_month_main_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 30;
    }

    @Override
    public long getHeaderId(int position) {
        return (long) position / 7;
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new HeaderViewHolder(mInflater.inflate(R.layout.item_rv_month_header_layout,parent,false));
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder viewholder, int position) {

    }

    public class MainViewHolder extends RecyclerView.ViewHolder{
        public MainViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
