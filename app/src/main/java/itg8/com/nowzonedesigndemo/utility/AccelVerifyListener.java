package itg8.com.nowzonedesigndemo.utility;

/**
 * Created by itg_Android on 5/18/2017.
 */

 interface AccelVerifyListener {
    void onMotionStarts();

    void onMotionEnds();

    void onStep(int step);

    void onSleepInterrupted(long timestamp);

    void startWakeupService();

    void onMovement(float mAccel);

   void onNoMovement(float i);

    void onAngleAvail(double something);
}
