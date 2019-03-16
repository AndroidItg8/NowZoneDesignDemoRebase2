
package itg8.com.nowzonedesigndemo.utility.model.breath;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemDetail implements Parcelable
{

    @SerializedName("pkid")
    @Expose
    private int pkid;
    @SerializedName("User_fkid")
    @Expose
    private String UserFkid;
    @SerializedName("TimeStamp")
    @Expose
    private String TimeStamp;
    @SerializedName("Count")
    @Expose
    private int Count;
    @SerializedName("Stateofmind_fkid")
    @Expose
    private int StateofmindFkid;
    public final static Parcelable.Creator<ItemDetail> CREATOR = new Creator<ItemDetail>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ItemDetail createFromParcel(Parcel in) {
            ItemDetail instance = new ItemDetail();
            instance.pkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.UserFkid = ((String) in.readValue((String.class.getClassLoader())));
            instance.TimeStamp = ((String) in.readValue((String.class.getClassLoader())));
            instance.Count = ((int) in.readValue((int.class.getClassLoader())));
            instance.StateofmindFkid = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public ItemDetail[] newArray(int size) {
            return (new ItemDetail[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The pkid
     */
    public int getPkid() {
        return pkid;
    }

    /**
     * 
     * @param pkid
     *     The pkid
     */
    public void setPkid(int pkid) {
        this.pkid = pkid;
    }

    /**
     * 
     * @return
     *     The UserFkid
     */
    public String getUserFkid() {
        return UserFkid;
    }

    /**
     * 
     * @param UserFkid
     *     The User_fkid
     */
    public void setUserFkid(String UserFkid) {
        this.UserFkid = UserFkid;
    }

    /**
     * 
     * @return
     *     The TimeStamp
     */
    public String getTimeStamp() {
        return TimeStamp;
    }

    /**
     * 
     * @param TimeStamp
     *     The TimeStamp
     */
    public void setTimeStamp(String TimeStamp) {
        this.TimeStamp = TimeStamp;
    }

    /**
     * 
     * @return
     *     The Count
     */
    public int getCount() {
        return Count;
    }

    /**
     * 
     * @param Count
     *     The Count
     */
    public void setCount(int Count) {
        this.Count = Count;
    }

    /**
     * 
     * @return
     *     The StateofmindFkid
     */
    public int getStateofmindFkid() {
        return StateofmindFkid;
    }

    /**
     * 
     * @param StateofmindFkid
     *     The Stateofmind_fkid
     */
    public void setStateofmindFkid(int StateofmindFkid) {
        this.StateofmindFkid = StateofmindFkid;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pkid);
        dest.writeValue(UserFkid);
        dest.writeValue(TimeStamp);
        dest.writeValue(Count);
        dest.writeValue(StateofmindFkid);
    }

    public int describeContents() {
        return  0;
    }

}
