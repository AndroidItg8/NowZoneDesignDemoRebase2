package itg8.com.nowzonedesigndemo.db.tbl;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


import java.util.Date;

import static itg8.com.nowzonedesigndemo.db.tbl.TblStepCount.FIELD_CAL_BURN;
import static itg8.com.nowzonedesigndemo.db.tbl.TblStepCount.FIELD_STEPS;

/**
 * Created by itg_Android on 6/20/2017.
 */
@DatabaseTable(tableName = TblStepCount.TABLE_NAME)
public class TblStepCount implements Parcelable {
    public static final String TABLE_NAME = "TABLE_STEP";
    public static final String  FILED_ID="id";
    public static final String FIELD_DATE="date";
    public static final String FIELD_STEPS="steps";
    public static final String FIELD_CAL_BURN="calburn";
    public static final String FIELD_GOAL="goals";

    @DatabaseField(columnName = FILED_ID,generatedId = true)
    private long id;

    @DatabaseField(columnName = FIELD_DATE, dataType = DataType.DATE_STRING, format = "yyyy-MM-dd")
    private Date date;

    @DatabaseField(columnName = FIELD_STEPS)
    private int steps;

    @DatabaseField(columnName = FIELD_CAL_BURN)
    private double calBurn;

    @DatabaseField(columnName = FIELD_GOAL)
    private int goal;



    public TblStepCount() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public double getCalBurn() {
        return calBurn;
    }

    public void setCalBurn(double calBurn) {
        this.calBurn = calBurn;
    }


    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeSerializable(this.date);
        dest.writeInt(this.steps);
        dest.writeDouble(this.calBurn);
        dest.writeInt(this.goal);
    }

    protected TblStepCount(Parcel in) {
        this.id = in.readLong();
        this.date = (Date) in.readSerializable();
        this.steps = in.readInt();
        this.calBurn = in.readDouble();
        this.goal = in.readInt();
    }

    public static final Creator<TblStepCount> CREATOR = new Creator<TblStepCount>() {
        @Override
        public TblStepCount createFromParcel(Parcel source) {
            return new TblStepCount(source);
        }

        @Override
        public TblStepCount[] newArray(int size) {
            return new TblStepCount[size];
        }
    };
}
