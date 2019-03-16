package itg8.com.nowzonedesigndemo.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CircleWave extends View implements View.OnClickListener {
    private static final int WAVE_COLOR = 0xff178BCA;
    Paint outerPaint = new Paint();
    Paint wavePaint = new Paint();
    Paint textPaint = new Paint();
    Shader shader;
    ColorFilter colorFilter = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
    Matrix localMAtrix = new Matrix();
    float x;

    public CircleWave(Context context) {
        super(context);
        textPaint.setColor(Color.WHITE);
        outerPaint.setColor(WAVE_COLOR);
        outerPaint.setStrokeWidth(20);
        outerPaint.setStyle(Paint.Style.STROKE);

        setOnClickListener(this);
    }

    public CircleWave(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        textPaint.setColor(Color.WHITE);
        outerPaint.setColor(WAVE_COLOR);
        outerPaint.setStrokeWidth(20);
        outerPaint.setStyle(Paint.Style.STROKE);

        setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Path path = new Path();
        float w2 = w / 2f;
        float h2 = h / 2f;
        path.moveTo(0, h2);
        float dx = w / 4f;
        float dy = w / 24f;
        path.rCubicTo(dx, -dy, w2 - dx, -dy, w2, 0);
        path.rCubicTo(dx,  dy, w2 - dx,  dy, w2, 0);
        path.rLineTo(0, h2);
        path.rLineTo(-w, 0);
        path.close();
        Paint p = new Paint();

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        p.setColor(WAVE_COLOR);
        canvas.drawPath(path, p);
        shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        wavePaint.setShader(shader);

        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(Math.min(w, h) * 0.4f);
    }

    protected void onDraw(Canvas canvas) {
        float cx = getWidth() / 2f;
        float cy = getHeight() / 2f;
        float radius = Math.min(cx, cy);

        // this is drawn using no shader
        canvas.drawCircle(cx, cy, radius + outerPaint.getStrokeWidth(), outerPaint);

        localMAtrix.setTranslate(x, 0);
        shader.setLocalMatrix(localMAtrix);
        canvas.drawCircle(cx, cy, radius, wavePaint);

        textPaint.setShader(null);
        canvas.drawText("50%", cx, cy * 1.2f, textPaint);
        textPaint.setShader(shader);
        textPaint.setColorFilter(colorFilter);
        canvas.drawText("50%", cx, cy * 1.2f, textPaint);
        textPaint.setColorFilter(null);
    }

    public void setX(float x) {
        this.x = x;
        invalidate();
    }

    @Override
    public void onClick(View v) {
        ObjectAnimator.ofFloat(this, "x", 0, getWidth() * 12)
                .setDuration(10000)
                .start();
    }
}
