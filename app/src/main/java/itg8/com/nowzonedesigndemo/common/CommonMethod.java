package itg8.com.nowzonedesigndemo.common;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import itg8.com.nowzonedesigndemo.alarm.model.AlarmDaysModel;

import static java.lang.Long.parseLong;

/**
 * Created by Android itg 8 on 4/20/2017.
 */

public class CommonMethod {


    static final String TEMPTOKEN="HcENDQvJAk3UG2qIWyXKTS6UYbxg4SxBE98He6Cr29hAA4GaI7ZZ1sf_FPCqfRL3Yvjie8J6Q6370IQm0z628xmcI7Gm_HjdAFinQktoLpDSl_ANma3kA_KNUZT5WJJD-2AQB-wltgbgHVXlOBQRIPVpHZr8ejdRq7QNlDTIY0iwnz10a9Gjkqpu5l0SMWbspcWl1p3w39kZ_6heDMP_0y5rMZ-fI6hd-VrbSiDI_8bMl3JDm7sA2wn9JyMksGkCGrMfzMfqdnIjN_E-I0SFyydsn1_8FBeHXEy87LQnsBFayuytZUNZmjSg_w7N5Xxkn3cp_x_5j2bV0WFGkj23T1nEZHmqaY2Amj7W9OaXeD_0Le3_uCsgR3-L20Lm5WbpjSW9ZEMTOhCFcy3awwEDWrGZWjMw-Doy2WS7mzz-R0pQOxmWYd7wmV9k-I--QRi9liJ3Dd5J3mSrM7As4y0AC2BfmyPUp0EkYQuEKuhpCcI";

    public static final String USER_CURRENT_AVG = "USER_CURRENT_AVG";


    public static final String SAVEDAYS = "SAVEDAYS";

    public static final long CONST_30_MIN=1800000;

    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String DATE_FORMAT_WITH_TIME = "hh:mm a";
    public static final String DATE_FORMAT_SERVER="yyyy-MM-dd HH:mm:ss";
    public static final String SAVEALARMTIME = "SAVEALARMTIME";

    public static final String AVG_MILE_BY_HEIGHT = "avgKmHeight";
    public static final String STEP_COUNT = "stepCount";
    public static final String ENABLE_TO_CONNECT = "enableToConnect";
    public static final String GOAL = "stepGoal";
    public static final String START_ALARM_TIME = "START_ALARM_TIME";
    public static final String END_ALARM_TIME = "END_ALARM_TIME";
    public static final String ALARM_FROMTIMEPICKER = "ALARM_FROMTIMEPICKER";
    public static final String ACTION_ALARM_NOTIFICATION = "ACTION_ALARM_NOTIFICATION";
    public static final String SLEEP_STARTED = "sleepStarted";
    public static final String STEP_GOAL = "stepGoal";
    public static final String ALARM_AP = "ALARM_AP";
    public static final String ENABLE_TO_CONNECT_IN_TIME = "ENABLE_TO_CONNECT_IN_TIME";

    public static final String BREATH ="BREATH" ;

    public static final String ACTION_DATA_LONG = "ACTION_AVAIL_LONG_TSMP";
    public static final String ALARM_END = "ALARM_END";
    public static final String  FROMSMARTALARM ="FROMSMARTALARM" ;
    public static final String FROMMEDITATION = "FROMMEDITATION";
    public static final String SAVE_DAYS_FOR_MEDITATION = "SAVEDAYSFORMEDITATION";
    public static final String BLUETOOTH_OFF = "BLUETOOTH_OFF";
    public static final String FROM_DEVICE = "FROM_DEVICE";
    public static final String FROM_STEP_GOAL = "FROM_STEP_GOAL";
    public static final String FROM_PROFILE = "FROM_PROFILE";
    public static final String FROM_ALARM_SETTING = "FROM_ALARM_SETTING";
    public static final String FROM_MEDITATION = "FROM_MEDITATION";
    public static final String FROM_DEVICE_HISTORY = "FROM_DEVICE_HISTORY";
    public static final String FROM_ALARM_HOME = "FROM_ALARM_HOME";
    public static final String SAVETIMEINMILI = "SAVETIMEINMILI";
    public static final String IP_ADDRESS = "IpAddress";
    public static final String SOCKET_STOP_CLICKED = "stopClicked";
    public static final String DEVICE_STATE = "device state";
    public static final String TOKEN = "MYTOKEN";
    static final String BASE_URL = "http://103.229.24.44:8090";
//    public static final String BASE_URL = "http://192.168.1.58:8090";
    public static final String ACTION_MOVEMENT = "ACTION_MOVEMENT";
    public static final String ACTION_MOVEMENT_STOPPED = "ACTION_MOVEMENT_STOPPED";
    public static final String ISLOGIN = "loginComplete";
    public static final String ACTION_AXIS_ACCEL = "ACTION_AXIS_YZ";
    public static final String STAGE_HEARED = "heardStage";


    private static Typeface typeface;
    public static String FROMWEEk="from_week";
    public static final String SELECTED_DEVICE = "SELECTED_DEVICE";
    public static final String ACTION_GATT_CONNECTED = "DEVICE_CONNECTED";
    public static final String ACTION_GATT_DISCONNECTED = "DEVICE_DISCONNECTED";
    public static final String ACTION_GATT_SERVICES_DISCOVERED="ACTION_GATT_DISCOVERED";
    public static final String ACTION_DATA_AVAILABLE="ACTION_DATA_AVAILABLE";
    public final static UUID DATA_ENABLE = 				UUID.fromString("0000ffa3-0000-1000-8000-00805f9b34fb");
    public final static UUID CLIENT_CHARACTERISTIC_CONFIG = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final int JOB_CONNECT_DEVICE = 1;

    public static final byte[] BYTE_ARRAY_ON = new byte[]{01};
    public static final byte[] BYTE_ARRAY_OFF = new byte[]{00};
    // DAQ Specific UUIDs
    public final static UUID TEMP_SERVICE_UUID = 			UUID.fromString("0000ffa0-0000-1000-8000-00805f9b34fb");
    // BLE UUIDs
    public final static UUID SENSOR_ON_OFF = 			UUID.fromString("0000ffa1-0000-1000-8000-00805f9b34fb");
    public static final String SHARED = "NZPREF";
    public static final String DEVICE_ADDRESS = "device_address";

    public static final String DEVICE_NAME = "device_name";
    public static final String CONNECTED = "connected";
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static final String TIMESTAMP = "time";
    public static final String STATE = "state" ;
    public static final String BPM_COUNT = "bpmCount";
    public static final String FINISHED_DATA = "finishedData";
    public static final String STORAGE_PERM = "storage_perm";
    public static final String STORAGE_PATH = "storage_path";
    public static final String ALARM_END_TIME = "AlarmEndTime";
    private static List<Double> data;
    private static long currentMillies=0;
    private static DataModel model;


    public static Typeface setFontOpenSansSemiBold(Context context) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Semibold.ttf");
        return typeface;
    }public static Typeface setFontRobotoLight(Context context) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/robotolight.ttf");
        return typeface;
    }

    public CommonMethod() {
    }

    public static DataModel getArragedData(byte[] data){
        if (data != null && data.length > 0) {
            byte value1 = data[8]; //Convert to double [Higher bit of pressure]
            byte value2 = data[9]; //Convert to double [Lower bit of pressure]
//            createFile(String.valueOf(value1)+" "+String.valueOf(value2));
            byte xHValue = data[0];
            byte xLValue = data[1];
            byte yHValue = data[2];
            byte yLValue = data[3];
            byte zHValue = data[4];
            byte zLValue = data[5];
            byte valueBattery1 = data[6];
            byte valueBattery2 = data[7];

//            String batteryBytes = new byte[]{valueBattery1,valueBattery2};
//            String pressure= bytesToHex(new byte[]{value1,value2});
//            String valueForX=bytesToHex(new byte[]{xHValue,xLValue});
//            String valueForY=bytesToHex(new byte[]{yHValue,yLValue});
//            String valueForZ=bytesToHex(new byte[]{zHValue,zLValue});
            model=new DataModel(data);
            try {
                model.setPressure(bytesToHex(new byte[]{value1,value2}));
                model.setX(bytesToHex(new byte[]{xHValue,xLValue}));
                model.setY(bytesToHex(new byte[]{yHValue,yLValue}));
                model.setZ(bytesToHex(new byte[]{zHValue,zLValue}));
                model.setBattery(bytesToHex(new byte[]{valueBattery1,valueBattery2}));
//                Log.i("TAGCOMMON",model.getRawString()+" "+Double.toHexString(model.getPressure()));
                Log.i("TAGCOMMON",model.getRawString()+" "+model.getAllInHex());
//                if(currentMillies<=0)
//                else
//                    currentMillies+=50;

                model.setTimestamp(System.currentTimeMillis());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return model;
    }

    private static void createFile(String log) {
        File completeFileStructure = new File(Environment.getExternalStorageDirectory()+File.separator+"nowzone","raw data1.txt");
        try {
            FileWriter fWriter;
            if(completeFileStructure.exists()) {
                fWriter = new FileWriter(completeFileStructure, true);
                fWriter.append(log).append("\n");
            }else {
                fWriter = new FileWriter(completeFileStructure, true);
                fWriter.write(log);
            }
            fWriter.flush();
            fWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static int bytesToHex(byte[] bytes) throws NumberFormatException {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return convertHexToInt(new String(hexChars));
    }

    private static int convertHexToInt(String s) {
//        BigInteger bigInt = new BigInteger(s, 16);
//        Log.d("asasas","bigInt: "+bigInt
//        );
//        int i = (short) Integer.parseInt("FFFF", 16);
//        Log.d("asasas","bigInt: "+ (short) Integer.parseInt(s,16));
        try {
            return (short) Integer.parseInt(s,16);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<Map<Integer, Double>> countBPM(DataModel[] models, double delta){
        data=new ArrayList<>();
        for (DataModel model :
                models) {
            data.add(model.getPressure());
        }
        return peak_detection(data,delta);
    }

    public static <U> List<Map<U, Double>> peak_detection(List<Double> values, Double delta, List<U> indices) {
//        assert (indices != null);
//        assert (values.size() != indices.size());

        LinkedHashMap<U, Double> maxima = new LinkedHashMap<U, Double>();
        LinkedHashMap<U, Double> minima = new LinkedHashMap<U, Double>();
        List<Map<U, Double>> peaks = new ArrayList<Map<U, Double>>();
        peaks.add(maxima);
        peaks.add(minima);

        Double maximum = null;
        Double minimum = null;
        U maximumPos = null;
        U minimumPos = null;

        boolean lookForMax = true;

        Integer pos = 0;
        for (Double value : values) {
            if (maximum == null || value > maximum) {
                maximum = value;
                maximumPos = indices.get(pos);
            }

            if (minimum == null || value < minimum) {
                minimum = value;
                minimumPos = indices.get(pos);
            }

            if (lookForMax) {
                if (value < maximum - delta) {
                    maxima.put(maximumPos, value);
                    minimum = value;
                    minimumPos = indices.get(pos);
                    lookForMax = false;
                }
            } else {
                if (value > minimum + delta) {
                    minima.put(minimumPos, value);
                    maximum = value;
                    maximumPos = indices.get(pos);
                    lookForMax = true;
                }
            }

            pos++;
        }

        return peaks;
    }

    public static List<Map<Integer, Double>> peak_detection(List<Double> values, Double delta) {
        List<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < values.size(); i++) {
            indices.add(i);
        }

        return peak_detection(values, delta, indices);
    }

    public static String getTimeFromTMP(long timeStamp) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("hh:mm:ss",Locale.getDefault());
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        String ss="";
        try {
            ss=dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ss;
    }

    public static Calendar ConvertTime(Context mContext, int hourOfDay, int minute) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.add(Calendar.MINUTE, 30);
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        SharePrefrancClass.getInstance(mContext).setLPref(CommonMethod.START_ALARM_TIME, c.getTimeInMillis());
        c.add(Calendar.MINUTE, 30);
        SharePrefrancClass.getInstance(mContext).setLPref(CommonMethod.END_ALARM_TIME, c.getTimeInMillis());
        c.add(Calendar.MINUTE, -30);

        return c;


    }

    public static void resetTmpstmp() {
        currentMillies=0;
    }



    public interface alarmListener{
        void onAlarmListener(List<AlarmDaysModel> abc, String from);
    }

    public static   CharSequence calculateHours(long startTime, long endTime) {
        String hourses;

        long diff = endTime - startTime;

        long seconds = diff / 1000; // seconds is milliseconds / 1000
     //   long milliseconds = (endTime - startTime) % 1000; // remainder is milliseconds that are not composing seconds.
        long minutes = seconds / 60;
        seconds = seconds % 60;
        long hours = minutes / 60;
        minutes = minutes % 60;

        long hr = 0;
        long min = 0;
        if (hours < 0) {
            hr = hours * 60;
            Log.d("CommonMethod", "diff:" + diff);

        }
//        if(hours==0 && minutes <0)
//        {
//            minutes = (60+minutes) * -1;
//        }
        if (minutes < 0) {
            hr = hr + minutes;
        }

        hr = 1440 + hr;
        hours = hr / 60;
        minutes = hr % 60;
        Log.d("CommonMethod", "diff:" + hr + "min" + min);
        if (hours < 0 || minutes < 0) {
//            hr = hours*60;
//            hr= hr+minutes;
//            hr+=1440;
//             min=  hr%60;
//           hr= hr/60;

            Log.d("CommonMethod", "diff:" + hours + "min" + min);
            return hr + ":" + min;
        }
        hourses = hours + ":" + minutes;


        return hourses;
    }



}
