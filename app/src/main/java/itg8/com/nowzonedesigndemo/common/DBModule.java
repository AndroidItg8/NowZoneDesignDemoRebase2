package itg8.com.nowzonedesigndemo.common;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import itg8.com.nowzonedesigndemo.breath.mvp.BreathHistoryMVP;
import itg8.com.nowzonedesigndemo.db.DbHelper;
import itg8.com.nowzonedesigndemo.db.tbl.TblBreathCounter;
import itg8.com.nowzonedesigndemo.db.tbl.TblState;
import itg8.com.nowzonedesigndemo.db.tbl.TblStepCount;

/**
 * Created by itg_Android on 6/10/2017.
 */

public abstract class DBModule{

    Dao<TblState,Integer> stateDao=null;
//    Dao<TblBreath>

    private final Context context;
    private DbHelper dbHelper;
    public BreathHistoryMVP.BreathHistoryListener listener;

    public DBModule(Context context) {
        this.context=context;
    }

    public DbHelper getHelper() {
        if (dbHelper == null) {
            dbHelper = (DbHelper) OpenHelperManager.getHelper(context, DbHelper.class);
        }
        return dbHelper;
    }

    void onDestroy(){
        if (dbHelper != null) {
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
    }


    public void onRequestStateDao(){
        try {
            stateDao=getHelper().getStateDao();
            initStateDao();
        } catch (SQLException e) {
            e.printStackTrace();
            initStateDao(false,"Error : "+e.getErrorCode()+":"+e.getMessage());
        }
    }



    public abstract void initStateDao();
    public abstract void initStateDao(boolean b, String issue);

    public Dao<TblState, Integer> getStateDao() {
        return stateDao;
    }
}
