package itg8.com.nowzonedesigndemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import itg8.com.nowzonedesigndemo.R;


/**
 * Created by itg_Android on 2/21/2017.
 */
public class SignalMeter extends View {

    Context context;
    TypedArray attribs;

    float width;
    float height;
    private int bgColor = Color.argb(127,24,79,135); //Blue 50% opacity
    private int bgColor1 = Color.argb(255,24,79,135); //Blue 100% opacity
    private int prColor = Color.argb(255,24,79,135); //Blue 100% opacity
    private int prColor1 = Color.argb(127,24,79,135); //Blue 50% opacity
    Paint paint;
    private LinearGradient bgGradient;
    private LinearGradient prGradient;
    private int padding = 0;
    private int progress = 4;
    private int maxBars = 8;
    private float barWidth = 10;
    private float spacing = 2f;
    private float rotation = 0;
    private int type = 0; //0 for rectangle, 1 for sloped, 2 full height bar
    final private RectF rectF = new RectF();

    private void setUp(){
        setWillNotDraw(false); //Make sure it calls onDraw();
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null); //this is required for the setShadowLayer();
        this.setPadding(padding,padding,padding,padding);

        paint = new Paint();
        paint.setAlpha(255);
        paint.setStyle(Paint.Style.FILL);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);


        maxBars = attribs.getInt(R.styleable.SignalMeter_maxBars, 4);
        bgColor = attribs.getColor(R.styleable.SignalMeter_bgColor, bgColor);
        bgColor1 = attribs.getColor(R.styleable.SignalMeter_bgColor1, bgColor1);
        prColor = attribs.getColor(R.styleable.SignalMeter_prColor, prColor);
        prColor1 = attribs.getColor(R.styleable.SignalMeter_prColor1, prColor1);
        spacing = attribs.getDimension(R.styleable.SignalMeter_barSpacing, spacing);
        progress = attribs.getInt(R.styleable.SignalMeter_progressValue, progress);
        type = attribs.getInt(R.styleable.SignalMeter_barType, type);
        rotation = attribs.getFloat(R.styleable.SignalMeter_rotation, rotation);

        ViewTreeObserver vto = this.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                width = getWidth() - getPaddingLeft() - getPaddingRight();
                height = getHeight() - getPaddingBottom() - getPaddingTop();
                bgGradient = new LinearGradient(0,height,width,height,bgColor,bgColor1, Shader.TileMode.CLAMP);
                prGradient  = new LinearGradient(0,height,width,height,prColor,prColor1, Shader.TileMode.CLAMP);
                Log.i("output","onGlobalLayout Called");

            }
        });

    }


    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        width = getWidth() - getPaddingLeft() - getPaddingRight();
        height = getHeight() - getPaddingBottom() - getPaddingTop();
        barWidth = (width-(maxBars*spacing)) / maxBars;



        canvas.rotate(rotation,width/2,height/2);

        rectF.setEmpty();

        switch (type) {



            /* Draw sloped scale.*/
            case 1:
                sloped(canvas);
                break;
            /*
                Draw Bar Scale.
                Each bar matches the height of the view,
            */
            case 2:
                fullHeightRectangle(canvas);
                break;

            /*Draw rectangle sloped scale*/
            default:
                rectangle(canvas);
                break;
        }

    }

    private void sloped(Canvas canvas){
        paint.setShader(bgGradient);
        for (int i = maxBars; i >= 0; i--) {

            final float xpos = getXPos(i);
            final float ypos = getYPos(i, maxBars);
            final Path path = new Path();
            path.moveTo(xpos, height); //Bottom left
            path.lineTo(xpos, getYPos(i - 1, maxBars)-spacing);
            path.lineTo(xpos + barWidth, ypos); //top right
            path.lineTo(xpos + barWidth, height); //bottom right
            path.close();
            canvas.drawPath(path, paint);
        }

        paint.setShader(prGradient);
        for (int i = 0; i < progress; i++) {

            final float xpos = getXPos(i);
            final float ypos = getYPos(i, maxBars);
            final Path path = new Path();
            path.moveTo(xpos, height); //Bottom left
            path.lineTo(xpos, getYPos(i - 1, maxBars)-spacing); //top left
            path.lineTo(xpos + barWidth, ypos); //top right
            path.lineTo(xpos + barWidth, height); //bottom right
            path.close();
            canvas.drawPath(path, paint);
        }
    }

    private void fullHeightRectangle(Canvas canvas){
        paint.setShader(bgGradient);
        for (int i = 0; i < maxBars; i+=1) {
            rectF.setEmpty();
            rectF.set(getXPos(i), 0, getXPos(i) + barWidth, height);
            canvas.drawRect(rectF, paint);
        }

        paint.setShader(prGradient);
        for (int i = 0; i < progress; i+=1) {
            rectF.setEmpty();
            rectF.set(getXPos(i), 0, getXPos(i) + barWidth, height);
            canvas.drawRect(rectF, paint);
        }
    }

    private void rectangle(Canvas canvas){

        paint.setShader(bgGradient);
        for (int i = progress; i < maxBars; i++) {
            rectF.set(getXPos(i), height, getXPos(i) + barWidth , getYPos(i, maxBars));
            canvas.drawRect(rectF, paint);
        }

        paint.setShader(prGradient);
        for (int i = 0; i < progress; i++) {
            rectF.set(getXPos(i), height, getXPos(i) + barWidth, getYPos(i, maxBars));
            canvas.drawRect(rectF, paint);
        }
    }

    private float getYPos(float value, float maxValue) {

        float height = this.height - getPaddingTop() - getPaddingBottom();
        float addOn = (1 / maxValue) * height;
        value = (value / maxValue) * height;
        value = height - value;
        value += getPaddingTop();
        value -= addOn;
        return value;
    }

    private float getXPos(float value) {

        float width = this.width - getPaddingLeft() - getPaddingRight();
        float maxValue = maxBars;
        value = (value / maxValue) * width;
        value += getPaddingLeft();
        return value;
    }

    /* constructor */
    public SignalMeter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        attribs = context.obtainStyledAttributes(attrs, R.styleable.SignalMeter, 0 , 0);
        setUp();
    }

    /* constructor */
    public SignalMeter(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        attribs = context.obtainStyledAttributes(attrs, R.styleable.SignalMeter, 0 , 0);
        setUp();
    }

    /* constructor */
    public SignalMeter(Context context) {
        super(context);
        this.context = context;

        setUp();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        /*
            For Edit Mode();
         */
        bgGradient = new LinearGradient(0,height,width,height,bgColor,bgColor1, Shader.TileMode.CLAMP);
        prGradient  = new LinearGradient(0,height,width,height,prColor,prColor1, Shader.TileMode.CLAMP);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void setRotation(float rotation){
        if (rotation > 360 || rotation < 0){
            rotation = 0;
        }
        this.rotation = rotation;
    }

    public void setbgColor(int color){
        this.bgColor = color;
    }

    public void setbgColor1(int color){
        this.bgColor1 = color;
    }

    public void setprColor(int color){
        this.prColor = color;
    }

    public void setprColor1(int color){
        this.prColor1 = color;
    }

    public void setValue(int value){
        /*
            Sets the value of the progress variable,
            This is only used if the passed value is different from the previous value.
            This will stop unnecessary invalidate() calls.
         */
        if (value != this.progress) {
            this.progress = value;
            invalidate();
        }
    }

    @Override
    public float getRotation(){
        return this.getRotation();
    }

    public int getValue(){
        /*
            Gets current value of progress
        */
        return this.progress;
    }

    public int getMax(){
        /*
            Gets value of maxBars;
         */
        return this.maxBars;
    }

    public void setMax(int max){
        /*
            Sets the total number of bars.
         */
        this.maxBars = max;
    }

    public void setType(int type){
        this.type = type;
    }

}