package itg8.com.nowzonedesigndemo.widget.wave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.os.SystemClock;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static itg8.com.nowzonedesigndemo.home.HomeActivity.CONST_1;
import static itg8.com.nowzonedesigndemo.home.HomeActivity.CONST_2;

public class BreathwaveView extends View {
    //    private static final int BREATHVIEW_CROP = 200;
    private static final int BREATHVIEW_DURATION = 10000;
//    private static final int BREATHVIEW_MAX_INTERVAL = 200;
//    private static final double BREATHVIEW_MINMAX_MARGIN = 0.02d;

    private static final float BREATHVIEW_SHADOW_SHIFT = 1.5f;
    private static final float BREATHVIEW_STROKE_WIDTH = 2.75f;
    private static final String TAG = BreathwaveView.class.getSimpleName();
    //    private static final int MAX_SIZE = 1300*2+64;
    private static final int MAX_SIZE = 1;
    private static final long DURATION = 400;
    //    private static final String TAG = BreathwaveView.class.getSimpleName();
    private float mDensity;
    private List<BreathSample> mSamples;
    private float[] mVert;
    //    private ArrayList<Float> mVert;
    private Paint mWavePaint;
    private List<BreathSample> mWaveSamples;
    private Paint mWaveShadowPaint;
    private double lastMax = 0;
    private double lastMin = 0;
    private int w;
    private int h;
    private int i2;
    private boolean render = false;
    private long j2;

    public BreathwaveView(Context context) {
        super(context);
        init();
    }

    public BreathwaveView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    @SuppressWarnings("unchecked")
    private void init() {
        this.mDensity = getResources().getDisplayMetrics().density;
        this.mSamples = new ArrayList();
        this.mWaveSamples = new ArrayList();
//        this.mVert = new ArrayList<>();
        this.mVert = new float[MAX_SIZE];
        this.mWavePaint = new Paint();
        this.mWavePaint.setAntiAlias(true);
        this.mWavePaint.setStyle(Style.STROKE);
        this.mWavePaint.setColor(Color.parseColor("#FFFFFF"));
        this.mWavePaint.setStrokeWidth(BREATHVIEW_STROKE_WIDTH * this.mDensity);
        this.mWavePaint.setStrokeJoin(Join.ROUND);
        this.mWavePaint.setStrokeCap(Cap.ROUND);
        this.mWaveShadowPaint = new Paint(this.mWavePaint);
        this.mWaveShadowPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.mWaveShadowPaint.setAlpha(15);


        setLayerType(2, null);
    }


    private float getX(BreathSample breathSample, int width, long elapsedRealtime) {
         j2 = elapsedRealtime - BREATHVIEW_DURATION;
        return (float) (((breathSample.getTimestamp() - j2) * ((long) width)) / (elapsedRealtime - j2));
    }

    private float getY(BreathSample breathSample, int height, double dConst1, double dConst2) {
        //                                                    i                   d                       d2
        return (float) (((double) height) - (((breathSample.getValue() - dConst1) * ((double) height)) / (dConst2 - dConst1)));
        //      return (float) (((double) i) - (((breathSample.getValue() - d) * ((double) i)) / (d2 - d)));

//        return (float) (((double) height) * (((breathSample.getValue() - dConst1)) / (dConst2 - dConst1)));
    }

    private void findWaveSamples(long j) {
        long j2 = j - DURATION;
        long j3 = (j - BREATHVIEW_DURATION) + DURATION;
        BreathSample breathSample = null;
        BreathSample breathSample2 = null;
        this.mWaveSamples.clear();
        BreathSample breathSample32;
        for (BreathSample breathSample3 : this.mSamples) {
            breathSample32 = breathSample3;
            long timestamp = breathSample32.getTimestamp();
            if (timestamp >= j3) {
                if (timestamp > j2) {
                    breathSample2 = breathSample32;
                    break;
                } else {
                    this.mWaveSamples.add(breathSample32);
                    breathSample32 = breathSample;
                }
            }
            breathSample = breathSample32;
        }
        if (breathSample != null) {
            while (this.mSamples.get(0) != breathSample) {
                this.mSamples.remove(0);
            }
        }

        if (this.mWaveSamples.size() >= 2) {
            breathSample32 = this.mWaveSamples.get(0);
            if (breathSample != null && breathSample.getTimestamp() + DURATION >= breathSample32.getTimestamp()) {
                this.mWaveSamples.add(0, breathSample.interpolate(breathSample32, j3));
            }
            breathSample32 = this.mWaveSamples.get(this.mWaveSamples.size() - 1);
            if (breathSample2 != null && breathSample32.getTimestamp() + DURATION >= breathSample2.getTimestamp()) {
                this.mWaveSamples.add(this.mWaveSamples.size(), breathSample32.interpolate(breathSample2, j2));
            }
        }
    }

    protected void onDraw(Canvas canvas) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        findWaveSamples(elapsedRealtime);
        if (this.mWaveSamples.size() >= 2) {
            int size = (this.mWaveSamples.size() - 1) * 4;
            //   Log.d(TAG, "Size of mWaveSamples:" + size);
            if (this.mVert.length < size * 2) {
                this.mVert = new float[((size * 2) + 64)];
//                Arrays.fill(mVert, 0);
            }
//            if (this.mVert.size()< size * 2) {
//                this.mVert.clear();
//            }
            long j = 0;
            int i = 0;
            int i2 = size;
            int i3 = size;
            for (BreathSample breathSample : this.mWaveSamples) {
                int i4;
                float x = getX(breathSample, canvas.getWidth(), elapsedRealtime);
//                float y = getY(breathSample, canvas.getHeight(), 3000, 6000);
                float y = getY(breathSample, canvas.getHeight(), CONST_1, CONST_2);
                int i5 = i + 1;
//                this.mVert.add(i,x);
                this.mVert[i] = x;
                int i6 = i5 + 1;
//                this.mVert.add(i5,y);
                this.mVert[i5] = y;
                int i7 = i3 + 1;
//                this.mVert.add(i3,x);
                this.mVert[i3] = x;
                i3 = i7 + 1;
//                this.mVert.add(i7,(BREATHVIEW_SHADOW_SHIFT * this.mDensity) + y);
                this.mVert[i7] = (BREATHVIEW_SHADOW_SHIFT * this.mDensity) + y;
                if (j == 0 || DURATION + j >= breathSample.getTimestamp()) {
                    i5 = i3;
                    i4 = i6;
                    i7 = i2;
                } else {
                    i5 = i3 - 4;
                    i4 = i6 - 4;
                    i7 = i2 - 4;
                }
                long timestamp = breathSample.getTimestamp();
                if (!(i4 == 2 || i4 == i7)) {
                    int i8 = i4 + 1;
//                    this.mVert.add(i4,x);
                    this.mVert[i4] = x;
                    i4 = i8 + 1;
//                    this.mVert.add(i8,y);
                    this.mVert[i8] = y;
                    i8 = i5 + 1;
//                    this.mVert.add(i5,x);
                    this.mVert[i5] = x;
                    i5 = i8 + 1;
                    this.mVert[i8] = y + (BREATHVIEW_SHADOW_SHIFT * this.mDensity);
                }
                j = timestamp;
                i = i4;
                i2 = i7;
                i3 = i5;
            }
            canvas.drawLines(this.mVert, i2, i2, this.mWaveShadowPaint);
            canvas.drawLines(this.mVert, 0, i2, this.mWavePaint);
        }
        invalidate();

    }

    public void reset() {
        this.mSamples.clear();
    }

    public void addSample(long timeStamp, double value) {
        this.mSamples.add(new BreathSample(timeStamp, value));
        Log.d(TAG,"sample:"+value);

    }

//    private void createRenderer(long timeStamp, double value) {
//        render = false;
//        mSamples.add(new BreathSample(timeStamp, value));
//        long elapsedRealtime = SystemClock.elapsedRealtime();
//        findWaveSamples(elapsedRealtime);
//        if (mWaveSamples.size() >= 2) {
//            int size = (mWaveSamples.size() - 1) * 4;
//            if (mVert.length < size * 2) {
//                mVert = new float[((size * 2) + 64)];
////                Arrays.fill(mVert, 0);
//            }
////            if (this.mVert.size()< size * 2) {
////                this.mVert.clear();
////            }
//            long j = 0;
//            int i = 0;
//            int i2 = size;
//            int i3 = size;
//            int i5;
//            int i6;
//            int i7;
//            float x;
//            float y;
//            long timestamp;
//            int i4;
//
//            for (BreathSample breathSample : mWaveSamples) {
//                x = getX(breathSample, w, elapsedRealtime);
////                float y = getY(breathSample, canvas.getHeight(), 3000, 6000);
//                y = getY(breathSample, h, CONST_1, CONST_2);
////                Log.d(TAG,"Y:"+y+" Breath: "+breathSample.getValue());
//                 i5 = i + 1;
////                this.mVert.add(i,x);
//                mVert[i] = x;
//                i6 = i5 + 1;
////                this.mVert.add(i5,y);
//                mVert[i5] = y;
//                i7 = i3 + 1;
////                this.mVert.add(i3,x);
//                mVert[i3] = x;
//                i3 = i7 + 1;
////                this.mVert.add(i7,(BREATHVIEW_SHADOW_SHIFT * this.mDensity) + y);
//                mVert[i7] = (BREATHVIEW_SHADOW_SHIFT * mDensity) + y;
//                if (j == 0 || 200 + j >= breathSample.getTimestamp()) {
//                    i5 = i3;
//                    i4 = i6;
//                    i7 = i2;
//                } else {
//                    i5 = i3 - 4;
//                    i4 = i6 - 4;
//                    i7 = i2 - 4;
//                }
//                timestamp = breathSample.getTimestamp();
//                if (!(i4 == 2 || i4 == i7)) {
//                    int i8 = i4 + 1;
////                    this.mVert.add(i4,x);
//                    mVert[i4] = x;
//                    i4 = i8 + 1;
////                    this.mVert.add(i8,y);
//                    mVert[i8] = y;
//                    i8 = i5 + 1;
////                    this.mVert.add(i5,x);
//                    mVert[i5] = x;
//                    i5 = i8 + 1;
//                    mVert[i8] = y + (BREATHVIEW_SHADOW_SHIFT * mDensity);
//                }
//                j = timestamp;
//                i = i4;
//                i2 = i7;
//                i3 = i5;
//            }
//
//            this.i2 = i2;
//            render = true;
//        }
//    }
}
