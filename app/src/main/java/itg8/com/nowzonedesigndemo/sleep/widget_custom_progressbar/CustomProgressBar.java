package itg8.com.nowzonedesigndemo.sleep.widget_custom_progressbar;

import android.graphics.Color;
import android.graphics.RectF;
import android.widget.SeekBar;

/**
 * Created by Android itg 8 on 5/20/2017.
 */

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.SeekBar;

public class CustomProgressBar extends SeekBar {

    private ArrayList<ProgressItem> mProgressItemsList;

    public CustomProgressBar(Context context) {
        super(context);
        mProgressItemsList = new ArrayList<ProgressItem>();
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initData(ArrayList<ProgressItem> progressItemsList) {
        this.mProgressItemsList = progressItemsList;
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec,
                                          int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onDraw(Canvas canvas) {
        if (mProgressItemsList.size() > 0) {
            int progressBarWidth = getWidth();
            int progressBarHeight = getHeight();
            int thumboffset = getThumbOffset();
            int lastProgressX = 0;
            int progressItemWidth, progressItemRight;
            for (int i = 0; i < mProgressItemsList.size(); i++) {
                ProgressItem progressItem = mProgressItemsList.get(i);
                Paint progressPaint = new Paint();
                progressPaint.setColor(getResources().getColor(
                        progressItem.color));

                progressItemWidth = (int) (progressItem.progressItemPercentage
                        * progressBarWidth / 100);

                progressItemRight = lastProgressX + progressItemWidth;

                // for last item give right to progress item to the width
                if (i == mProgressItemsList.size() - 1
                        && progressItemRight != progressBarWidth) {
                    progressItemRight = progressBarWidth;
                }
                RectF progressRect = new RectF();
                progressRect.set(lastProgressX+20, thumboffset / 2,
                        progressItemRight, progressBarHeight - thumboffset / 2);
                canvas.drawRoundRect(progressRect, 50,50,progressPaint);
//                Paint paintText = new Paint();
//                paintText.setColor(Color.WHITE);
//                canvas.drawText("% values",progressRect.width()/2,progressRect.height()/2,paintText);
                lastProgressX = progressItemRight;
            }
            super.onDraw(canvas);
        }

    }

}