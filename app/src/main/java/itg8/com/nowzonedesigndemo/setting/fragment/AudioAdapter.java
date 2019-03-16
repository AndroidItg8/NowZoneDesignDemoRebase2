package itg8.com.nowzonedesigndemo.setting.fragment;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.nowzonedesigndemo.R;

/**
 * Created by itg_Android on 9/13/2017.
 */

class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewHolder> {


    private Context context;
    private AudioFileClicked listener;

    public interface AudioFileClicked {
        void onAudioClicked(int position, Object o);
    }

    public void setListener(AudioFileClicked listener) {
        this.listener = listener;
    }

    public AudioAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_audio, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTxtStageCount.setText(String.valueOf(position + 1));
        ViewCompat.setTransitionName(holder.mImgBgLogo,String.valueOf(position + 1));
        ViewCompat.setTransitionName(holder.mTxtStageCount,"count"+String.valueOf(position + 1));
    }


    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.img_bg_logo)
        ImageView mImgBgLogo;
        @BindView(R.id.txtStageCount)
        TextView mTxtStageCount;
        @BindView(R.id.txt_stage)
        TextView mTxtStage;

        public ViewHolder(final View root) {
            super(root);
            ButterKnife.bind(this, root);
            // bind focus listener

            mImgBgLogo.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.onAudioClicked(getAdapterPosition(),new View[]{mImgBgLogo,mTxtStageCount});
        }
    }
}
