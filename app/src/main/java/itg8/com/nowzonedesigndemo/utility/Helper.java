package itg8.com.nowzonedesigndemo.utility;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.common.ProfileModel;


public class Helper {

    private final static double walkingFactor = 0.57;
    public static SimpleDateFormat dateFormat = new SimpleDateFormat(CommonMethod.DATE_FORMAT, Locale.getDefault());
    public static SimpleDateFormat dateFormatWithTime = new SimpleDateFormat(CommonMethod.DATE_FORMAT_WITH_TIME, Locale.getDefault());
    public static final SimpleDateFormat dateFormatForServer=
            new SimpleDateFormat(CommonMethod.DATE_FORMAT_SERVER,Locale.getDefault());
    static NumberFormat formatter = new DecimalFormat("#0.00");
    private static Calendar calendar;

//    static double distance;

    public static boolean checkBLE(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String getCurrentDate() {
        String newDate = "";
        try {
            newDate = dateFormat.format(Calendar.getInstance().getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;
    }

    public static String convertTimestampToTime(long timestamp) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        return dateFormatForServer.format(calendar.getTime());
//        return calendar.getTime().toString();
    }

    public static String getDateTimeFromMillies(long timestampEnd) {
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestampEnd);
        String date = "";
        try {
            date = dateFormatWithTime.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static double calculateCalBurnByStepCount(int stepsCount, ProfileModel model) {
        if(model==null)
            return 0;
        double caloriesBurnedPerMile = walkingFactor * (model.getWeight() * 2.2);

        double strip = model.getHeight() * 0.415;

        double stepCountMile = 160934.4 / strip;

        double conversationFactor = caloriesBurnedPerMile / stepCountMile;

        //        System.out.println("Calories burned: "
//                + formatter.format(CaloriesBurned) + " cal");
//
//        distance = (stepsCount * strip) / 100000;
        return stepsCount * conversationFactor;
    }


    public static float poundToKg(float pound) {
        return (float) (pound * 0.453592);
    }

    public static float feetToInch(int feet) {
        return feet * 12;
    }

    public static float inchToCm(float inch) {
        return (float) (inch * 2.54);
    }

    public static String feetToCentimeter(String feet){
        double dCentimeter = 0d;
        if(!TextUtils.isEmpty(feet)){
            if(feet.contains("'")){
                String tempfeet = feet.substring(0, feet.indexOf("'"));
                if(!TextUtils.isEmpty(tempfeet)){
                    dCentimeter += ((Double.valueOf(tempfeet))*30.48);
                }
            }if(feet.contains("\"")){
                String tempinch = feet.substring(feet.indexOf("'")+1, feet.indexOf("\""));
                if(!TextUtils.isEmpty(tempinch)){
                    dCentimeter += ((Double.valueOf(tempinch))*2.54);
                }
            }
        }
        return String.valueOf(dCentimeter);
        //Format to decimal digit as per your requirement
    }

    public static float centimeterToFeet(String centemeter) {
        int feetPart = 0;
        int inchesPart = 0;
        float feet = 0;
        if (!TextUtils.isEmpty(centemeter)) {
            double dCentimeter = Double.valueOf(centemeter);
            feetPart = (int) Math.floor((dCentimeter / 2.54) / 12);
            System.out.println((dCentimeter / 2.54) - (feetPart * 12));
            inchesPart = (int) Math.ceil((dCentimeter / 2.54) - (feetPart * 12));
            feet = (float) (feetPart + (0.1 * inchesPart));
        }
        return feet;
    }


    public static int getIdByState(BreathState currentState) {
        switch (currentState){
            case CALM:
                return 1;
            case FOCUSED:
                return 2;
            case STRESS:
                return 3;
            case UNKNOWN:
                return 4;
        }
        return 0;
    }
}
