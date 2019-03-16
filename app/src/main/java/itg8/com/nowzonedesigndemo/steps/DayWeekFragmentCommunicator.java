package itg8.com.nowzonedesigndemo.steps;

import java.util.List;

import itg8.com.nowzonedesigndemo.steps.mvp.WeekStepModel;

/**
 * Created by swapnilmeshram on 02/07/17.
 */

interface DayWeekFragmentCommunicator {
    void onWeekStepModelGot(List<WeekStepModel> models);
}
