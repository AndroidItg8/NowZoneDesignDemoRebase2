
package itg8.com.nowzonedesigndemo.utility.model.breath;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BreathingModel implements Parcelable
{

    @SerializedName("itemDetails")
    @Expose
    private List<ItemDetail> itemDetails = new ArrayList<ItemDetail>();
    public final static Parcelable.Creator<BreathingModel> CREATOR = new Creator<BreathingModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public BreathingModel createFromParcel(Parcel in) {
            BreathingModel instance = new BreathingModel();
            in.readList(instance.itemDetails, (ItemDetail.class.getClassLoader()));
            return instance;
        }

        public BreathingModel[] newArray(int size) {
            return (new BreathingModel[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The itemDetails
     */
    public List<ItemDetail> getItemDetails() {
        return itemDetails;
    }

    /**
     * 
     * @param itemDetails
     *     The itemDetails
     */
    public void setItemDetails(List<ItemDetail> itemDetails) {
        this.itemDetails = itemDetails;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(itemDetails);
    }

    public int describeContents() {
        return  0;
    }

}
