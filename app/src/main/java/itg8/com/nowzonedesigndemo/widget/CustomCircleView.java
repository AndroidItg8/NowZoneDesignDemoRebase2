package itg8.com.nowzonedesigndemo.widget;

        import android.content.Context;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.support.annotation.Nullable;
        import android.util.AttributeSet;
        import android.view.View;


/**
 * Created by Android itg 8 on 7/7/2017.
 */

public class CustomCircleView extends View {
    private final Paint paint;
    private float mCx;
    private float mCy;
    private float mRadius;


    public CustomCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        mCx = 200;
        mCy= 200;
        mRadius= 200;
        paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
    }

    public CustomCircleView(Context context) {
        super(context);

        // create the Paint and set its color
        paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCx = canvas.getWidth()/2;
        mCy = canvas.getHeight()/2;
        canvas.drawCircle(mCx, mCy, mRadius, paint);

        invalidate();
    }

    public void setmRadius(float mRadius) {
        this.mRadius = mRadius;
    }
}