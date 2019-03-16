package itg8.com.nowzonedesigndemo.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by itg_Android on 6/21/2017.
 */

public class ProfileModel implements Parcelable {
    private String name;
    //In cm
    private float height;
    //In kg
    private float weight;

    private String profilePic;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public ProfileModel() {
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeFloat(this.height);
        dest.writeFloat(this.weight);
        dest.writeString(this.profilePic);
    }

    private ProfileModel(Parcel in) {
        this.name = in.readString();
        this.height = in.readFloat();
        this.weight = in.readFloat();
        this.profilePic = in.readString();
    }

    public static final Creator<ProfileModel> CREATOR = new Creator<ProfileModel>() {
        @Override
        public ProfileModel createFromParcel(Parcel source) {
            return new ProfileModel(source);
        }

        @Override
        public ProfileModel[] newArray(int size) {
            return new ProfileModel[size];
        }
    };
}
