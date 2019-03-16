package itg8.com.nowzonedesigndemo.sleep;

import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by itg_Android on 6/6/2017.
 */

abstract class BaseSleepClass extends AppCompatActivity{

    public abstract void onChartReadyToFillData();

    public void initChart(BarChart mChart){

        onChartReadyToFillData();
    }

    class MyAxisValueFormatter implements IAxisValueFormatter {

        public MyAxisValueFormatter() {
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return getLabelFromValue(value);
        }
    }

    private String getLabelFromValue(float value) {
        if (value == 3.0f) {
            return "awake";
        }else if(value == 2.0f)
            return "light";
        else if (value == 1.0f)
            return "deep";
        return "";
    }

}
