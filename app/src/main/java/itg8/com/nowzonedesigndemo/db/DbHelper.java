package itg8.com.nowzonedesigndemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import itg8.com.nowzonedesigndemo.db.tbl.TblAverage;
import itg8.com.nowzonedesigndemo.db.tbl.TblBreathCounter;
import itg8.com.nowzonedesigndemo.db.tbl.TblState;
import itg8.com.nowzonedesigndemo.db.tbl.TblStepCount;


public class DbHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME="Nowzone.db";
    private static final int DB_VERSION=2;

    private Dao<TblBreathCounter,Integer> breathDao=null;
    private Dao<TblState,Integer> stateDao=null;
    private Dao<TblAverage,Integer> avgDao=null;
    private Dao<TblStepCount,Integer> stepDao=null;




    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, TblBreathCounter.class);
            TableUtils.createTable(connectionSource, TblState.class);
            TableUtils.createTable(connectionSource, TblAverage.class);
            TableUtils.createTable(connectionSource, TblStepCount.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, TblBreathCounter.class, true);
            TableUtils.dropTable(connectionSource, TblState.class, true);
            TableUtils.dropTable(connectionSource, TblAverage.class, true);
            TableUtils.dropTable(connectionSource, TblStepCount.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

     /* User */

    public Dao<TblBreathCounter, Integer> getCountDao() throws SQLException {
        if (breathDao == null) {
            breathDao = getDao(TblBreathCounter.class);
        }

        return breathDao;
    }

    public Dao<TblState,Integer> getStateDao() throws SQLException{
        if(stateDao == null){
            stateDao=getDao(TblState.class);
        }
        return stateDao;
    }

    public Dao<TblAverage, Integer> getAvgDao() throws SQLException{
        if(avgDao == null)
            avgDao=getDao(TblAverage.class);

        return avgDao;
    }

    public Dao<TblStepCount, Integer> getStepDao() throws SQLException{
        if(stepDao == null)
            stepDao=getDao(TblStepCount.class);

        return stepDao;
    }

    @Override
    public void close() {
        breathDao = null;
        stateDao = null;
        avgDao = null;
        stepDao = null;
        super.close();
    }

    public void trucate() throws SQLException {
        TableUtils.dropTable(getConnectionSource(),TblAverage.class,false);
        TableUtils.dropTable(getConnectionSource(),TblState.class,false);
        TableUtils.dropTable(getConnectionSource(),TblStepCount.class,false);
        TableUtils.dropTable(getConnectionSource(),TblBreathCounter.class,false);

        TableUtils.createTable(getConnectionSource(),TblAverage.class);
        TableUtils.createTable(getConnectionSource(),TblState.class);
        TableUtils.createTable(getConnectionSource(),TblStepCount.class);
        TableUtils.createTable(getConnectionSource(),TblBreathCounter.class);


    }
}
