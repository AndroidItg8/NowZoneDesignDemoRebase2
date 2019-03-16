package itg8.com.nowzonedesigndemo.sleep.widget_custom_progressbar;

import android.content.res.Resources;

/**
 * Created by Android itg 8 on 5/20/2017.
 */
// this class  is used in the sleep page for showing goals of sleep
public class Utils {

    private Utils() {
    }

    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return  dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp){
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }
}
