package itg8.com.nowzonedesigndemo.alarm.model;



/**
 * Created by Android itg 8 on 6/22/2017.
 */

public class AlarmDaysModel   {


    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    private String days;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;
    private  boolean isChecked = false;

    public AlarmDaysModel() {
    }






    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
