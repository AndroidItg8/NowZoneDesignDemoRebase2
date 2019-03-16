package itg8.com.nowzonedesigndemo.steps;

/**
 * Created by itg_Android on 7/1/2017.
 */

interface StepFragmentCommunicator {
    void onTodaysDataReceived(int goal, int steps, int weekTotal, double calBurn);
}
