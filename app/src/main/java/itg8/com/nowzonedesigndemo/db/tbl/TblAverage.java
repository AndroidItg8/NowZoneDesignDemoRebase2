package itg8.com.nowzonedesigndemo.db.tbl;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by itg_Android on 6/7/2017.
 */
@DatabaseTable(tableName = TblAverage.TABLE_NAME_BREATH_COUNTER)
public class TblAverage implements Parcelable {
    public static final String TABLE_NAME_BREATH_COUNTER = "average";

    private static final String FIELD_ID="id";
    private static final String FIELD_AVERAGE="average";
    private static final String FIELD_TIMESTAMP="timestamp";

    @DatabaseField(columnName = FIELD_ID, generatedId = true)
    private long id;

    @DatabaseField(columnName = FIELD_AVERAGE)
    private int average;

    @DatabaseField(columnName = FIELD_TIMESTAMP)
    private long timeStamp;

    public TblAverage() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeInt(this.average);
        dest.writeLong(this.timeStamp);
    }

    protected TblAverage(Parcel in) {
        this.id = in.readLong();
        this.average = in.readInt();
        this.timeStamp = in.readLong();
    }

    public static final Parcelable.Creator<TblAverage> CREATOR = new Parcelable.Creator<TblAverage>() {
        @Override
        public TblAverage createFromParcel(Parcel source) {
            return new TblAverage(source);
        }

        @Override
        public TblAverage[] newArray(int size) {
            return new TblAverage[size];
        }
    };
}
