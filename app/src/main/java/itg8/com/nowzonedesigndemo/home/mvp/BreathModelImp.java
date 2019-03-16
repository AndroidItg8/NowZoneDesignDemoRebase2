package itg8.com.nowzonedesigndemo.home.mvp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itg8.com.nowzonedesigndemo.common.BaseModuleOrm;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.common.DataModel;
import itg8.com.nowzonedesigndemo.common.SharePrefrancClass;
import itg8.com.nowzonedesigndemo.db.DbHelper;
import itg8.com.nowzonedesigndemo.db.tbl.TblState;
import itg8.com.nowzonedesigndemo.utility.BreathState;
import itg8.com.nowzonedesigndemo.utility.DeviceState;
import itg8.com.nowzonedesigndemo.utility.Helper;
import timber.log.Timber;

import static itg8.com.nowzonedesigndemo.connection.BleService.ACTION_STATE_ARRIVED;
import static itg8.com.nowzonedesigndemo.connection.BleService.ACTION_STEP_COUNT;


public class BreathModelImp extends BaseModuleOrm implements BreathFragmentModel {

    private static final long TIME_LIMIT_OF_CONNECT = 30000;
    private final Handler handler;
    private final Runnable r;
    private BreathPresenter.BreathFragmentModelListener listener;

    Dao<TblState,Integer> stateDao=null;
    private DbHelper dbHelper;
    private boolean dbInited;
    private boolean isReceivingStarted=false;


    BreathModelImp(BreathPresenter.BreathFragmentModelListener listener) {
        this.listener = listener;
        handler = new Handler();
        r=new Runnable() {
            @Override
            public void run() {
                if (!isReceivingStarted) {
                    listener.onDeviceNotConnectedInTime();
                } else {
                    listener.onDataReceivingStarted();
                }
            }
        };
        handler.postDelayed(r, TIME_LIMIT_OF_CONNECT);
    }

    @Override
    public void initDB(Context context) {
        try {
            stateDao=getHelper(context).getStateDao();
            dbInited=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        if(dbInited)
        {
            if (dbHelper != null) {
                OpenHelperManager.releaseHelper();
                dbHelper = null;
            }
        }
        if(handler!=null)
            handler.removeCallbacks(r);
        listener=null;
    }

    @Override
    public void dataStarted(boolean b) {
        isReceivingStarted=b;

    }


    @Override
    public void checkBLEConnected(Context context) {
        if(context!=null){
//            String state =SharePrefrancClass.getInstance(context).getPref(CommonMethod.STATE);
//            if(state==null || state.equalsIgnoreCase(DeviceState.DISCONNECTED.name()))
//            {
//                listener.startShowingDevicesList();
//            }
            if(!SharePrefrancClass.getInstance(context).hasSPreference(CommonMethod.DEVICE_ADDRESS)){
                listener.startShowingDevicesList();
            }
        }
    }

    @Override
    public void onInitStateTime() {
        Observable.create((ObservableOnSubscribe<StateTimeModel>) e ->{
            e.onNext(getStateModelClass());
            e.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StateTimeModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(StateTimeModel stateTimeModel) {
                         listener.onStateTimeReceived(stateTimeModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();

                    }

                    @Override
                    public void onComplete() {
                      Timber.d("On Complete");

                    }
                })
                ;


    }

    private StateTimeModel getStateModelClass() {
        StateTimeModel model=null;
        List<TblState> stateList;
        if(stateDao!=null){
            model=new StateTimeModel();
            QueryBuilder<TblState, Integer> queryBuilder=stateDao.queryBuilder();
            Where<TblState, Integer> where;
            try {
                where=queryBuilder.where();
                calmWhereCondition(where,BreathState.CALM);
                stateList=stateDao.query(queryBuilder.prepare());
                if(stateList!=null && stateList.size()>0){
                    model.setCalm(checkMinutesOfHrFromSize(stateList.size()));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                where=queryBuilder.where();
                calmWhereCondition(where,BreathState.FOCUSED);
                stateList=stateDao.query(queryBuilder.prepare());
                if(stateList!=null && stateList.size()>0){
                    model.setFocus(checkMinutesOfHrFromSize(stateList.size()));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


            try {
                where=queryBuilder.where();
                calmWhereCondition(where,BreathState.STRESS);
                stateList=stateDao.query(queryBuilder.prepare());
                if(stateList!=null && stateList.size()>0){
                    model.setStress(checkMinutesOfHrFromSize(stateList.size()));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return model;
    }

    private String checkMinutesOfHrFromSize(int size) {
        int minute=0;
        String hrMinute="";
        int hours = (int)size / 60;
        minute = (int)size%60;

        if(hours>0)
            hrMinute=String.valueOf(hours)+" Hr ";

        if(minute>0)
            hrMinute=hrMinute+String.valueOf(minute)+" Min";

        return hrMinute;
    }

    private void calmWhereCondition(Where<TblState, Integer> where,BreathState state) throws SQLException{
        where.eq(TblState.FIELD_DATE, Helper.getCurrentDate());
        where.and();
        where.eq(TblState.FIELD_STATE,state.name());
    }











}
