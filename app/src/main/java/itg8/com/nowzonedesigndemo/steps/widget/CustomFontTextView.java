package itg8.com.nowzonedesigndemo.steps.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.TextView;

import itg8.com.nowzonedesigndemo.R;

/**
 * Created by Android itg 8 on 5/24/2017.
 */

public class CustomFontTextView extends TextView {

//    public void setFontType(int fontType) {
//        this.fontType = fontType;
//    }

    private  static final  int  ROBOTO_LIGHT =0;
    private  static final  int  JURA_MEDIUM =1;
    private  static final  int  OXYNGEN_REGULAR =2;
    private  static final  int ROBOTO_MEDIUM =3;

    int fontType=0;
    public CustomFontTextView(Context context) {
        super(context);
        setFont(context);
    }

    public CustomFontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initeDef(context, attrs);
    }

    public CustomFontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initeDef(context, attrs);

    }





    private void initeDef(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);
        fontType = a.getInt(R.styleable.CustomFontTextView_font_value, 0);
        setFont(context);
        a.recycle();


    }

    public void setFont(Context context) {

        Typeface customFont = null;
        switch (fontType)
        {
            case ROBOTO_LIGHT:
                customFont = Typeface.createFromAsset(context.getAssets(),"fonts/robotolight.ttf");
                break;

            case OXYNGEN_REGULAR:
                customFont = Typeface.createFromAsset(context.getAssets(),"fonts/Oxygen-Regular.ttf");
                break;
             case JURA_MEDIUM:
                customFont = Typeface.createFromAsset(context.getAssets(),"fonts/Jura-Medium.ttf");
                break;
            case ROBOTO_MEDIUM:
                customFont = Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Medium.ttf");
                break;

            default:
                customFont = Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans-Regular.ttf");
                break;
        }

        setTypeface(customFont);
    }





}
