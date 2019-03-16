package itg8.com.nowzonedesigndemo.steps.mvp;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import itg8.com.nowzonedesigndemo.common.BaseModuleOrm;
import itg8.com.nowzonedesigndemo.db.tbl.TblStepCount;
import itg8.com.nowzonedesigndemo.utility.Helper;


public class StepModuleImp implements StepMVP.StepModule{

    private WeakReference<StepMVP.StepPresenterListener> listener;
    private Dao<TblStepCount, Integer> stepDao;
    private int woy;
    private String lastWeekLabel;
    private float[] dataToCollect;
    private int i=0;
    private List<TblStepCount> stepList;

    @Override
    public void onListenerReady(StepMVP.StepPresenterListener listener) {
        this.listener = new WeakReference<StepMVP.StepPresenterListener>(listener);
    }

    @Override
    public void onDestroy() {
        listener=null;
    }

    @Override
    public void onDaoReady(Dao<TblStepCount,Integer> dao) {
        this.stepDao=dao;
    }

    @Override
    public void onTodaysFragmentLoaded() {
        startCollectingData(stepDao);
    }

    @Override
    public void onWeeksFragmentLoaded() {
        startCollectingWeekStepData();
    }

    @Override
    public void onMonthFragmentLoaded() {
        startCollectingMonthData();
    }

    private void startCollectingMonthData() {
        try {
            List<TblStepCount> counts=stepDao.queryForAll();
            listener.get().onMonthStep(counts);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void startCollectingWeekStepData() {
        List<WeekStepModel> models=new ArrayList<>();
        WeekStepModel model;
        try {
            List<TblStepCount> counts=stepDao.queryForAll();
//            Collections.sort(counts, new WeekComparator());
//
//            for (TblStepCount date : counts) {
//                if (woy != getWeekOfYear(date.getDate())) {
//                    woy = getWeekOfYear(date.getDate());
//                    week++;
//                    System.out.println("Week " + week + ":");
//                }
//                System.out.println(date);
//            }
            Calendar calender=Calendar.getInstance();
            int lastItemCount=counts.size();
            for (TblStepCount stepDate:counts
                 ) {
                calender.setTime(stepDate.getDate());
                calender.set(Calendar.DAY_OF_WEEK,calender.getFirstDayOfWeek()+1);
                String weekLabel=calender.get(Calendar.DAY_OF_MONTH)+" "+calender.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.UK);
                calender.add(Calendar.DAY_OF_YEAR,6);
                weekLabel+="-"+calender.get(Calendar.DAY_OF_MONTH)+" "+calender.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.UK);
                if(lastWeekLabel==null){
                    lastWeekLabel=weekLabel;
                    model=new WeekStepModel();
                    model.setWeekLabel(lastWeekLabel);
                    stepList=new ArrayList<>();
                    stepList.add(stepDate);
                    model.setStepsCount(stepList);
                    models.add(model);
                    continue;
                }
                if(!lastWeekLabel.equalsIgnoreCase(weekLabel)){
                    lastWeekLabel=weekLabel;
                    model=new WeekStepModel();
                    model.setWeekLabel(lastWeekLabel);
                    stepList=new ArrayList<>();
                    stepList.add(stepDate);
                    model.setStepsCount(stepList);
                    models.add(model);
                }else {
                    stepList.add(stepDate);
                }
            }

            listener.get().onWeekStep(models);
            lastWeekLabel=null;
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static class WeekComparator implements Comparator<TblStepCount> {

        @Override
        public int compare(TblStepCount o1, TblStepCount o2) {
            int result = getWeekOfYear(o1.getDate()) - getWeekOfYear(o2.getDate());
            if (result == 0) {
                result = o1.getDate().compareTo(o2.getDate());
            }
            return result;
        }

    }

    protected static int getWeekOfYear(Date date) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    private void startCollectingData(Dao<TblStepCount, Integer> dao) {
        /**
         * collect todays data
         */
        try {
            List<TblStepCount> counts=dao.query(dao.queryBuilder().where().eq(TblStepCount.FIELD_DATE, Calendar.getInstance().getTime()).prepare());
            if(counts.size()>0 && getListener())
            {
                TblStepCount count=counts.get(counts.size()-1);
                listener.get().onTodaysStepAvailable(count.getGoal(),count.getSteps(),getThisWeekTotal(dao),count.getCalBurn());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getThisWeekTotal(Dao<TblStepCount, Integer> dao) {
        int total=0;
        try {
            GenericRawResults<TblStepCount> countsRaw =dao.queryRaw("select * from "+TblStepCount.TABLE_NAME+" where "+TblStepCount.FIELD_DATE+" BETWEEN "+"date('now', '-6 days') AND date('now', 'localtime')",stepDao.getRawRowMapper());
            List<TblStepCount> counts=countsRaw.getResults();
            if(counts.size()>0){
                for (TblStepCount tblCount :
                        counts) {
                    total+=tblCount.getSteps();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    private boolean getListener() {
        return listener != null;
    }
}
