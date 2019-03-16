package itg8.com.nowzonedesigndemo.common;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import itg8.com.nowzonedesigndemo.db.DbHelper;

/**
 * Created by itg_Android on 7/1/2017.
 */

public class BaseModuleOrm {

    private DbHelper dbHelper;

    public DbHelper getHelper(Context context) {

        if (dbHelper == null) {
            dbHelper = (DbHelper) OpenHelperManager.getHelper(context, DbHelper.class);
        }
        return dbHelper;
    }

    public  void releaseHelper(){
        if (dbHelper != null) {
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
    }

}
