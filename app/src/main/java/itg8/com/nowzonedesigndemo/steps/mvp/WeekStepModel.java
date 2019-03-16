package itg8.com.nowzonedesigndemo.steps.mvp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import itg8.com.nowzonedesigndemo.db.tbl.TblStepCount;

/**
 * Created by itg_Android on 7/1/2017.
 */

public class WeekStepModel implements Parcelable {
    String weekLabel;
    int stepCount;
    float calBurn;
    List<TblStepCount> stepsCount;


    public String getWeekLabel() {
        return weekLabel;
    }

    public void setWeekLabel(String weekLabel) {
        this.weekLabel = weekLabel;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public float getCalBurn() {
        return calBurn;
    }

    public void setCalBurn(float calBurn) {
        this.calBurn = calBurn;
    }

    public List<TblStepCount> getStepsCount() {
        return stepsCount;
    }

    public void setStepsCount(List<TblStepCount> stepsCount) {
        this.stepsCount = stepsCount;
    }

    public WeekStepModel() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.weekLabel);
        dest.writeInt(this.stepCount);
        dest.writeFloat(this.calBurn);
        dest.writeTypedList(this.stepsCount);
    }

    protected WeekStepModel(Parcel in) {
        this.weekLabel = in.readString();
        this.stepCount = in.readInt();
        this.calBurn = in.readFloat();
        this.stepsCount = in.createTypedArrayList(TblStepCount.CREATOR);
    }

    public static final Creator<WeekStepModel> CREATOR = new Creator<WeekStepModel>() {
        @Override
        public WeekStepModel createFromParcel(Parcel source) {
            return new WeekStepModel(source);
        }

        @Override
        public WeekStepModel[] newArray(int size) {
            return new WeekStepModel[size];
        }
    };
}
