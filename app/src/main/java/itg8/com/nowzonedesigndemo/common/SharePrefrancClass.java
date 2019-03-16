package itg8.com.nowzonedesigndemo.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharePrefrancClass {

    private static SharePrefrancClass ourInstance ;
    private static Context context;
    SharedPreferences preference;
    private SharedPreferences.Editor editor;


    public static SharePrefrancClass getInstance(Context mcontext) {
        context = mcontext;
        if (ourInstance == null){
            ourInstance = new SharePrefrancClass();
        }
        return ourInstance;
    }

    private SharePrefrancClass() {
        preference = context.getSharedPreferences(CommonMethod.SHARED, Context.MODE_PRIVATE);
    }

    /**
     * savePref()  for save
     * @param key,value  Key value of Shared Prefrance
     * @return
     */
    public void savePref(String key, String val) {
        if (preference != null) {
            editor = preference.edit();
            editor.putString(key, val);
            editor.commit();
        }
    }


    public void savePref(String key,long val){
        if(preference!=null){
            editor=preference.edit();
            editor.putLong(key,val);
            editor.commit();
        }
    }


    /**
     * setBoolean() for set
     * @param key,b  Key value of Shared Prefrance
     * @return
     */
    public void setPrefrance(String key, boolean b) {
        if (preference != null) {
            editor = preference.edit();
            editor.putBoolean(key, b);
            editor.commit();
        }
    }

    /**
     * clearPrefra()  for delete
     * @param key Key value of Shared Prefrance
     * @return
     */
    public void clearPref(String key) {
        if (preference != null) {
            try {
                editor = preference.edit();
                editor.remove(key);
                editor.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * getString()  for use
     * @param key      Key value of Shared Prefrance
     * @return
     */

    public String getPref(String key) {
        if (preference != null) {
            return preference.getString(key, null);
        }
        return null;
    }

    public long getLPref(String key){
        if(preference!=null){
            return preference.getLong(key,0);
        }
        return 0;
    }
    public void  setLPref(String key, long value) {
        if(preference!=null) {
            editor = preference.edit();
            editor.putLong(key, value);
            editor.commit();
        }
    }



    /**
     * getBoolean()  for use
     * @param name Key value of Shared Prefrance
     * @return
     */
    public boolean hasBPreference(String name) {
        if (preference != null) {
            return preference.getBoolean(name, false);
        }
        return false;
    }


    /**
     * getString()  for use
     * @param name Key value of Shared Prefrance
     * @return
     */
    public boolean hasSPreference(String name) {
        if (preference != null) {
            return preference.getString(name, null)!=null;
        }
        Log.d("Pref","Preference null");
        return false;
    }


    public void setIPreference(String key, int value) {
        if(preference!=null) {
            editor = preference.edit();
            editor.putInt(key, value);
            editor.commit();
        }
    }

    public int getIPreference(String key){
        if(preference!=null){
            return preference.getInt(key,0);
        }
        return 0;
    }
}
