package itg8.com.nowzonedesigndemo.breath;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.db.tbl.TblState;
import itg8.com.nowzonedesigndemo.steps.stickyHeader.StickyHeaderAdapter;
import itg8.com.nowzonedesigndemo.steps.stickyHeader.StickyHeaderDecoration;
import itg8.com.nowzonedesigndemo.steps.widget.CustomFontTextView;
import itg8.com.nowzonedesigndemo.utility.BreathState;
import itg8.com.nowzonedesigndemo.utility.Helper;

import static itg8.com.nowzonedesigndemo.utility.BreathState.CALM;
import static itg8.com.nowzonedesigndemo.utility.BreathState.STRESS;

/**
 * Created by itg_Android on 6/12/2017.
 */

class BreathHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int ITEM_HEADER = 2;
    private static final int ITEM_NO_HEADER = 3;
    private List<TblState> list;

    public BreathHistoryAdapter(List<TblState> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return BreathHistoryAdapter.ITEM_HEADER;
        else
            if(list.get(position).getDate().equalsIgnoreCase(list.get(position-1).getDate()))
                return BreathHistoryAdapter.ITEM_NO_HEADER;
            else
                return BreathHistoryAdapter.ITEM_HEADER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if(viewType==ITEM_HEADER)
            holder =  onCreateHeaderViewHolder(parent);
        else
            holder =new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_breath_state, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        TblState state = list.get(holder.getAdapterPosition());
        if(state!= null) {
            int color = getColorByState(state.getState());
            if (holder instanceof ViewHolder) {
                ViewHolder holder1 = (ViewHolder) holder;
                holder1.txtDateValue.setText(Helper.getDateTimeFromMillies(state.getTimestampEnd()));
                holder1.txtStateValue.setText(state.getState());
                holder1.txtCountValue.setText(String.valueOf(state.getCount()));
                holder1.txtCountValue.setTextColor(color);
            } else if (holder instanceof HeaderViewHolder) {
                ((HeaderViewHolder) holder).headerView.setText(state.getDate());
                ((HeaderViewHolder) holder).txtDateValue.setText(Helper.getDateTimeFromMillies(state.getTimestampEnd()));
                ((HeaderViewHolder) holder).txtStateValue.setText(state.getState());
                ((HeaderViewHolder) holder).txtCountValue.setText(String.valueOf(state.getCount()));
                ((HeaderViewHolder) holder).txtCountValue.setTextColor(color);
            }
        }
    }

    private int getColorByState(String state) {
        if(state.equalsIgnoreCase(BreathState.CALM.name()))
            return Color.parseColor("#FFA5D6A7");
        else if(state.equalsIgnoreCase(BreathState.FOCUSED.name()))
            return Color.parseColor("#9fa8da");
        else if(state.equalsIgnoreCase(STRESS.name()))
            return Color.parseColor("#ef9a9a");

        return Color.WHITE;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_value,parent,false));
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.lbl_date)
        TextView lblDate;
        @BindView(R.id.txt_date_value)
        TextView txtDateValue;
        @BindView(R.id.lbl_state)
        TextView lblState;
        @BindView(R.id.txt_state_value)
        TextView txtStateValue;
        @BindView(R.id.txt_count_value)
        CustomFontTextView txtCountValue;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txt_header_date)
        CustomFontTextView headerView;
        @BindView(R.id.lbl_date)
        TextView lblDate;
        @BindView(R.id.txt_date_value)
        TextView txtDateValue;
        @BindView(R.id.lbl_state)
        TextView lblState;
        @BindView(R.id.txt_state_value)
        TextView txtStateValue;
        @BindView(R.id.txt_count_value)
        CustomFontTextView txtCountValue;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
