package itg8.com.nowzonedesigndemo.steps.widget;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import itg8.com.nowzonedesigndemo.R;

/**
 * Created by Android itg 8 on 6/20/2017.
 */

public class CustomMarkerView extends MarkerView {

    private static final String TAG = CustomMarkerView.class.getSimpleName();
    private TextView tvContent;
    private MPPointF mOffset;

    public CustomMarkerView (Context context, int layoutResource) {
        super(context, layoutResource);
        // this markerview only displays a textview
        tvContent = (TextView) findViewById(R.id.value);
    }



    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        Log.d(TAG,"In refreshContent:"+e.getX() );

        initMOffset((int) e.getX());
        tvContent.setText("" + e.getY()); // set the entry-value as the display text
        super.refreshContent(e,highlight);
    }

    @Override
    public MPPointF getOffset() {
//        Log.d(TAG,"In getOffset:");
//        if(mOffset== null)
//        {
//          initMOffset();
//        }
        return mOffset;
    }

    private void initMOffset(int posX) {

        if(posX == 1 )
        {
            mOffset =  new MPPointF(-(getWidth()/4),-(getHeight()+30));
        } else if(posX == 7)
        {
            mOffset =  new MPPointF(-(getWidth()),-(getHeight()+30));


        }else
        {
            mOffset = new MPPointF(-(getWidth()/2),-(getHeight()+30));

        }

    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
//        if(mOffset== null)
//        {
//            initMOffset();
//        }
//        Log.d(TAG,"In getOffsetForDrawingAtPoint:"+posX );
//        if(posX == 1 )
//        {
//            mOffset =  new MPPointF((getWidth()),-(getHeight()+30));
//        } else if(posX == 7)
//        {
//            mOffset =  new MPPointF(-(getWidth()),-(getHeight()+30));
//
//
//        }

        return mOffset;
    }

}
