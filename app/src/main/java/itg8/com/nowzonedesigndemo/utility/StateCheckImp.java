package itg8.com.nowzonedesigndemo.utility;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.common.SharePrefrancClass;
import itg8.com.nowzonedesigndemo.db.tbl.TblAverage;
import itg8.com.nowzonedesigndemo.db.tbl.TblBreathCounter;


public class StateCheckImp  {

    /**
     * this will help to find all one day average
     */

    private static final String AVG_30_MIN_CALCULATED = "Avg30min";
    private static final String AVG_1_HR_CALCULATED = "Avg1hr";
    private static final String AVG_3_HR_CALCULATED = "Avg3hr";
    private static final String AVG_6_HR_CALCULATED = "Avg6hr";
    private static final String AVG_12_HR_CALCULATED = "Avg12hr";
    private static final String AVG_24_HR_CALCULATED = "Avg24hr";

    private static final int AVG_30_MIN=30;
    private static final int AVG_1_HR=AVG_30_MIN*2;
    private static final int AVG_3_HR=AVG_1_HR*3;
    private static final int AVG_6_HR=AVG_3_HR*2;
    private static final int AVG_12_HR=AVG_6_HR*2;
    private static final int AVG_24_HR=AVG_12_HR*2;
    private static final String TAG = StateCheckImp.class.getSimpleName();

    private final Context context;
    private final Observer<TblAverage> observer;
    private TblAverage tblAverage;
    public StateCheckImp(Context context, final OnStateAvailableListener listener){

        this.context = context;
        observer = new Observer<TblAverage>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TblAverage model) {
//                if (model)
//                    listener.onMotionStarts();
//                else
//                    listener.onMotionEnds();
                if(listener!=null) {
                    listener.onStateAvailable(model);
                }
            }

            @Override
            public void onError(Throwable e) {
                    e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        };

    }


    public void calculateNewBreathAvgForState(long timestamp, Dao<TblBreathCounter, Integer> userDao){


        if(userDao!=null) {
            int sizeOfBreathCount=0;
            try {
                sizeOfBreathCount = userDao.queryForAll().size();
                Log.d(StateCheckImp.class.getSimpleName(),"count size:"+sizeOfBreathCount);
                checkMovement(sizeOfBreathCount,userDao,timestamp).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(observer);
            } catch (SQLException e) {
                Log.d(TAG,e.getMessage());
                e.printStackTrace();
            }

        }

    }

    private Observable<TblAverage> checkMovement(int sizeOfBreathCount, final Dao<TblBreathCounter, Integer> userDao, long timestamp) {
        return Observable.create(e -> {
            if (sizeOfBreathCount > 0){
                boolean shouldCalculateAvg=false;
                if(!SharePrefrancClass.getInstance(context).hasBPreference(AVG_30_MIN_CALCULATED)){
                    if(sizeOfBreathCount>AVG_30_MIN) {
                        Log.d(TAG,"Size AVG_30_MIN");
                        shouldCalculateAvg=true;
                        SharePrefrancClass.getInstance(context).setPrefrance(AVG_30_MIN_CALCULATED, true);
                    }
                }else if(!SharePrefrancClass.getInstance(context).hasBPreference(AVG_1_HR_CALCULATED)){
                    if(sizeOfBreathCount>AVG_1_HR) {
                        Log.d(TAG,"Size AVG_1_HR");
                        shouldCalculateAvg=true;
                        SharePrefrancClass.getInstance(context).setPrefrance(AVG_1_HR_CALCULATED, true);
                    }
                }else if(!SharePrefrancClass.getInstance(context).hasBPreference(AVG_3_HR_CALCULATED)){
                    if(sizeOfBreathCount>AVG_3_HR) {
                        Log.d(TAG,"Size AVG_3_HR");
                        shouldCalculateAvg=true;
                        SharePrefrancClass.getInstance(context).setPrefrance(AVG_3_HR_CALCULATED, true);
                    }
                }else if(!SharePrefrancClass.getInstance(context).hasBPreference(AVG_6_HR_CALCULATED)){
                    if(sizeOfBreathCount>AVG_6_HR) {
                        Log.d(TAG,"Size AVG_6_HR");
                        shouldCalculateAvg=true;
                        SharePrefrancClass.getInstance(context).setPrefrance(AVG_6_HR_CALCULATED, true);
                    }
                }else if(!SharePrefrancClass.getInstance(context).hasBPreference(AVG_12_HR_CALCULATED)){
                    if(sizeOfBreathCount>AVG_12_HR) {
                        Log.d(TAG,"Size AVG_12_HR");
                        shouldCalculateAvg=true;
                        SharePrefrancClass.getInstance(context).setPrefrance(AVG_12_HR_CALCULATED, true);
                    }
                }else if(!SharePrefrancClass.getInstance(context).hasBPreference(AVG_24_HR_CALCULATED)){
                    if(sizeOfBreathCount>AVG_24_HR) {
                        Log.d(TAG,"Size AVG_24_HR");
                        shouldCalculateAvg=true;
                        SharePrefrancClass.getInstance(context).setPrefrance(AVG_24_HR_CALCULATED, true);
                    }
                }

                if(shouldCalculateAvg)
                {
                    tblAverage=new TblAverage();
                    tblAverage.setAverage((int)calculateAverage(userDao.queryForAll()));
                    tblAverage.setTimeStamp(timestamp);
                    e.onNext(tblAverage);
                }
            }
        });


    }

    private double calculateAverage(List<TblBreathCounter> tblBreathCounters) {
        int avg=0;
        for(TblBreathCounter counter:tblBreathCounters)
            avg+=counter.getCount();

        Log.d(TAG,"Average:"+avg);
        return avg/tblBreathCounters.size();
    }


}
