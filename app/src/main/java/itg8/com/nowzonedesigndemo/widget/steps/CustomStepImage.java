package itg8.com.nowzonedesigndemo.widget.steps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import itg8.com.nowzonedesigndemo.R;

/**
 * Created by Android itg 8 on 7/10/2017.
 */

public class CustomStepImage extends View {
    int[] x;
    int[] y;
    int leftY = 50;
    int rightY = 100;
    int lastX = 50;
    int maxX = 0;
    private Bitmap[] bitmaps;
    private Bitmap rightBitmap, leftBitmap;
    private boolean leftToDraw = true;
    private boolean toStop=false;


    public CustomStepImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initArray();
        leftBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_step_left);
        rightBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_step_right);
        leftBitmap = getResizedBitmap(leftBitmap, 30, 30);
        rightBitmap = getResizedBitmap(rightBitmap, 30, 30);
        movePlayer0Runnable.run();
    }

    Handler handler = new Handler(Looper.getMainLooper());
    private long stepSpeed=500;
    Runnable movePlayer0Runnable = new Runnable(){
        public void run(){
//            invalidate(); //will trigger the onDraw
            if(!toStop) {
                addStep();
                handler.postDelayed(this, stepSpeed); //in 0.5 sec player0 will move again
            }
        }
    };

    public void stopSteps() {
        this.toStop = true;
    }

    public void startSteps(){
        toStop=false;
        try {
            handler.removeCallbacks(movePlayer0Runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        movePlayer0Runnable.run();
    }


    public void setStepSpeed(long stepSpeed) {
        this.stepSpeed = stepSpeed;
    }

    @Override
    protected void onDetachedFromWindow() {
        try {
            handler.removeCallbacks(movePlayer0Runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDetachedFromWindow();
    }

    public void onRemove(){
        try {
            stopSteps();
            handler.removeCallbacks(movePlayer0Runnable);
            handler.removeCallbacksAndMessages(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initArray() {
        bitmaps = new Bitmap[3];
        x = new int[3];
        y = new int[3];
    }


    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
//        leftY = canvas.getHeight()/2-50;
//        rightY = canvas.getHeight()/2+50;

        if (bitmaps[0] != null)
            canvas.drawBitmap(bitmaps[0], x[0], y[0], null);
        if (bitmaps[1] != null)
            canvas.drawBitmap(bitmaps[1], x[1], y[1], null);
        if (bitmaps[2] != null)
            canvas.drawBitmap(bitmaps[2], x[2], y[2], null);
    }

    public void addStep() {
        int i = 0;
        while (i < 3) {
            if (i == 2) {
                x[i] = x[i - 1] + 50;
            } else {
                x[i] = lastX + 30;
            }

            if (leftToDraw) {
                y[i] = leftY;
                bitmaps[i] = leftBitmap;

            } else {
                y[i] = rightY;
                bitmaps[i] = rightBitmap;
            }
            lastX = x[i];
            i++;
            leftToDraw = !leftToDraw;
        }

        invalidate();
        if (x[0] > maxX) {
            lastX=0;
            initArray();
        }

        //lastX = x[0];

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        maxX=parentWidth;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        matrix.postRotate(90);
        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public void addStepTemp() {

        for (int i = 0; i <= 2; i++) {
            if (i == 2) {
                x[i] = x[i - 1] + 45;
            } else {
                x[i] = x[i + 1];

            }

            if (leftToDraw) {
                y[i] = leftY;
                bitmaps[i] = leftBitmap;
            } else {
                y[i] = rightY;
                bitmaps[i] = rightBitmap;
            }
            leftToDraw = !leftToDraw;
        }

        invalidate();

        if (x[0] > maxX) {
            initArray();
        }
    }

}

