package itg8.com.nowzonedesigndemo.common;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

import static itg8.com.nowzonedesigndemo.common.CommonMethod.hexArray;

/**
 * Created by itg_Android on 3/2/2017.
 */

public class DataModel implements Parcelable {
    private double pressure;
    private double x;
    private double y;
    private double z;
    private int temprature;
    private int battery;
    private long timestamp;
    private String rawString;
    private static final int halfByte = 0x0F;
    private static final int sizeOfIntInHalfBytes = 8;
    private static final int numberOfBitsInAHalfByte = 4;
    private static final char[] hexDigits = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    public DataModel(int pressure, int x, int y, int z, int temprature, int battery, long timestamp) {
        this.pressure = pressure;
        this.x = x;
        this.y = y;
        this.z = z;
        this.temprature = temprature;
        this.battery = battery;
        this.timestamp = timestamp;
    }

    public DataModel(byte[] data) {
        this.rawString= bytesToHex(data);
    }


    public String getRawString() {
        return rawString;
    }

    public void setRawString(String rawString) {
        this.rawString = rawString;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public int getTemprature() {
        return temprature;
    }

    public void setTemprature(int temprature) {
        this.temprature = temprature;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    public String getAllInHex(){
//        return getDoubleAsHexString(getTimestamp())+" "
//                +getDoubleAsHexString(getPressure())+" "
//                +getDoubleAsHexString(getX())+" "
//                +getDoubleAsHexString(getY())+" "
//                +getDoubleAsHexString(getZ())+" "
//                +getDoubleAsHexString(getTemprature())+" "
//                +getDoubleAsHexString(getBattery());
    return (getTimestamp())+" "
                +((int)getPressure())+" "
                +((int)getX())+" "
                +((int)getY())+" "
                +((int)getZ())+" "
                +(getTemprature())+" "
                +(getBattery());
    }


    public static String bytesToHex(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for(byte b : in) {
            builder.append(String.format("%02X", b));
        }
        return builder.toString();
    }

//    public static String decToHex(double dec) {
//        StringBuilder hexBuilder = new StringBuilder(sizeOfIntInHalfBytes);
//        hexBuilder.setLength(sizeOfIntInHalfBytes);
//        for (int i = sizeOfIntInHalfBytes - 1; i >= 0; --i)
//        {
//            int j =Double.toHexString(dec) dec & halfByte;
//            hexBuilder.setCharAt(i, hexDigits[j]);
//            dec >>= numberOfBitsInAHalfByte;
//        }
//        return hexBuilder.toString();
//    }



    public String getDoubleAsHexString(double input) {
        // Convert the starting value to the equivalent value in a long
        long doubleAsLong = Double.doubleToRawLongBits(input);
        // and then convert the long to a hex string
        return Long.toHexString(doubleAsLong);
    }

    public DataModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.pressure);
        dest.writeDouble(this.x);
        dest.writeDouble(this.y);
        dest.writeDouble(this.z);
        dest.writeInt(this.temprature);
        dest.writeInt(this.battery);
        dest.writeLong(this.timestamp);
        dest.writeString(this.rawString);
    }

    protected DataModel(Parcel in) {
        this.pressure = in.readDouble();
        this.x = in.readDouble();
        this.y = in.readDouble();
        this.z = in.readDouble();
        this.temprature = in.readInt();
        this.battery = in.readInt();
        this.timestamp = in.readLong();
        this.rawString = in.readString();
    }

    public static final Creator<DataModel> CREATOR = new Creator<DataModel>() {
        @Override
        public DataModel createFromParcel(Parcel source) {
            return new DataModel(source);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };
}
