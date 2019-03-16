package itg8.com.nowzonedesigndemo.steps.mvp;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.List;

import itg8.com.nowzonedesigndemo.common.BasePresenter;
import itg8.com.nowzonedesigndemo.db.tbl.TblStepCount;

/**
 * Created by itg_Android on 7/1/2017.
 */

public class StepPresenterImp extends BasePresenter implements StepMVP.StepPresenter,StepMVP.StepPresenterListener {

    WeakReference<StepMVP.StepView> stepViewWeakReference;
    StepMVP.StepModule module;
    Dao<TblStepCount,Integer> stepDao=null;


    public StepPresenterImp(StepMVP.StepView view) {
        super();
        stepViewWeakReference = new WeakReference<StepMVP.StepView>(view);

        setObject(stepViewWeakReference);
        module=new StepModuleImp();
        module.onListenerReady(this);
        try {
            stepDao=getHelper((Context) view).getStepDao();
            module.onDaoReady(stepDao);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTodaysStepReady() {
        module.onTodaysFragmentLoaded();
    }

    @Override
    public void onWeekDayReady() {
        module.onWeeksFragmentLoaded();
    }

    @Override
    public void onMonthReady() {
        module.onMonthFragmentLoaded();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        stepViewWeakReference=null;
        releaseHelper();
        module.onDestroy();
    }

    @Override
    public void onTodaysStepAvailable(int goal, int covered, int weekTotal, double calBurn) {
        if(isNotNull())
            stepViewWeakReference.get().onTodaysStepAvailable(goal,covered,weekTotal,calBurn);
    }

    @Override
    public void onWeekStep(List<WeekStepModel> weekStepModelList) {
        if(isNotNull())
        {
            stepViewWeakReference.get().onWeekStep(weekStepModelList);
        }
    }

    @Override
    public void onMonthStep(List<TblStepCount> monthStepsList) {
        if(isNotNull()){
            stepViewWeakReference.get().onMonthStep(monthStepsList);
        }
    }

    @Override
    public void onDaoError(String msg) {

    }
}
