package itg8.com.nowzonedesigndemo.alarm.model;

import java.util.List;

/**
 * Created by Android itg 8 on 7/19/2017.
 */

public class AlarmModel {

    public AlarmModel() {

    }

    public AlarmModel(boolean vibration, int sound, long alarmTime, List<AlarmDaysModel> model) {
        this.vibration = vibration;
        this.sound = sound;
        this.alarmTime = alarmTime;
        this.model = model;
    }

    public boolean isVibration() {
        return vibration;
    }

    public void setVibration(boolean vibration) {
        this.vibration = vibration;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }





    private boolean vibration;
    private  int sound;


    public long getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(long alarmTime) {
        this.alarmTime = alarmTime;
    }

    private long alarmTime;

    public List<AlarmDaysModel> getModel() {
        return model;
    }

    public void setModel(List<AlarmDaysModel> model) {
        this.model = model;
    }

    private List<AlarmDaysModel> model;

}
