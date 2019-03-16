package itg8.com.nowzonedesigndemo.utility.model.step;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemDetail implements Parcelable {

    public final static Parcelable.Creator<ItemDetail> CREATOR = new Creator<ItemDetail>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ItemDetail createFromParcel(Parcel in) {
            ItemDetail instance = new ItemDetail();
            instance.pkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.UserId = ((String) in.readValue((String.class.getClassLoader())));
            instance.Date = ((String) in.readValue((String.class.getClassLoader())));
            instance.Steps = ((int) in.readValue((int.class.getClassLoader())));
            instance.calriesBurn = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public ItemDetail[] newArray(int size) {
            return (new ItemDetail[size]);
        }

    };
    @SerializedName("pkid")
    @Expose
    private int pkid;
    @SerializedName("UserId")
    @Expose
    private String UserId;
    @SerializedName("Date")
    @Expose
    private String Date;
    @SerializedName("Steps")
    @Expose
    private int Steps;
    @SerializedName("calriesBurn")
    @Expose
    private int calriesBurn;

    /**
     * @return The pkid
     */
    public int getPkid() {
        return pkid;
    }

    /**
     * @param pkid The pkid
     */
    public void setPkid(int pkid) {
        this.pkid = pkid;
    }

    /**
     * @return The UserId
     */
    public String getUserId() {
        return UserId;
    }

    /**
     * @param UserId The UserId
     */
    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    /**
     * @return The Date
     */
    public String getDate() {
        return Date;
    }

    /**
     * @param Date The Date
     */
    public void setDate(String Date) {
        this.Date = Date;
    }

    /**
     * @return The Steps
     */
    public int getSteps() {
        return Steps;
    }

    /**
     * @param Steps The Steps
     */
    public void setSteps(int Steps) {
        this.Steps = Steps;
    }

    /**
     * @return The calriesBurn
     */
    public int getCalriesBurn() {
        return calriesBurn;
    }

    /**
     * @param calriesBurn The calriesBurn
     */
    public void setCalriesBurn(int calriesBurn) {
        this.calriesBurn = calriesBurn;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pkid);
        dest.writeValue(UserId);
        dest.writeValue(Date);
        dest.writeValue(Steps);
        dest.writeValue(calriesBurn);
    }

    public int describeContents() {
        return 0;
    }

}
