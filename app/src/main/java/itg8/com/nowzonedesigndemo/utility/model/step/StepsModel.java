package itg8.com.nowzonedesigndemo.utility.model.step;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class StepsModel implements Parcelable {

    public final static Parcelable.Creator<StepsModel> CREATOR = new Creator<StepsModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public StepsModel createFromParcel(Parcel in) {
            StepsModel instance = new StepsModel();
            in.readList(instance.itemDetails, (itg8.com.nowzonedesigndemo.utility.model.step.ItemDetail.class.getClassLoader()));
            return instance;
        }

        public StepsModel[] newArray(int size) {
            return (new StepsModel[size]);
        }

    };
    @SerializedName("itemDetails")
    @Expose
    private List<ItemDetail> itemDetails = new ArrayList<ItemDetail>();

    /**
     * @return The itemDetails
     */
    public List<ItemDetail> getItemDetails() {
        return itemDetails;
    }

    /**
     * @param itemDetails The itemDetails
     */
    public void setItemDetails(List<ItemDetail> itemDetails) {
        this.itemDetails = itemDetails;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(itemDetails);
    }

    public int describeContents() {
        return 0;
    }

}
