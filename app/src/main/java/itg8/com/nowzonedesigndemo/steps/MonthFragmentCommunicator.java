package itg8.com.nowzonedesigndemo.steps;

import java.util.List;

import itg8.com.nowzonedesigndemo.db.tbl.TblStepCount;

/**
 * Created by itg_Android on 7/3/2017.
 */

interface MonthFragmentCommunicator {
    void onMonthStepHistoryGot(List<TblStepCount> counts);
}
