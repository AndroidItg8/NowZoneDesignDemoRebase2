package itg8.com.nowzonedesigndemo.utility;

import android.content.Context;
import android.util.Log;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itg8.com.nowzonedesigndemo.common.AppApplication;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.common.DataModel;
import itg8.com.nowzonedesigndemo.common.SharePrefrancClass;
import itg8.com.nowzonedesigndemo.tosort.RDataManager;
import itg8.com.nowzonedesigndemo.tosort.RDataManagerListener;
import itg8.com.nowzonedesigndemo.utility.model.breath.BreathingModel;
import itg8.com.nowzonedesigndemo.utility.model.breath.ItemDetail;
import itg8.com.nowzonedesigndemo.utility.model.step.StepsModel;

/**
 * This is responsible for calculation uploading and all.
 */

public class RDataManagerImp implements RDataManager, PAlgoCallback, AccelVerifyListener {

    /**
     * this veriable use to define rolling average window. W=30
     */
    private static final int ROLLING_AVG_SIZE = 25;

    /**
     * this will check if packet receiving is completed.
     */
    private static final int PACKET_READY_TO_IMP = 1200;
    private static final String REMINDER_JOB_TAG = RDataManagerImp.class.getCanonicalName();
    private static final int REMINDER_INTERVAL_SECONDS = 1000;
    private static final int SYNC_FLEXTIME_SECONDS = 3000;
    private static final String TAG = RDataManagerImp.class.getSimpleName();
    private final RDataManagerListener listener;
    private final Observer<DataModel> observer;
    Job dataToFileJob;
    CheckAccelImp accelImp;
    boolean isSleepStarted;
    /**
     * this will be use to send data from service to store in file.
     */
    FirebaseJobDispatcher dispatcher;
    DataModel dataModel;
    private DataModel[] dataStorage;
    private Rolling rolling, rolling2, rolling3;
    private Observable<String> observable;
    private DataModel[] dataStorageRaw;
    private DataModel modelTemp;
    private int indexDataStorage;
    private DataModel[] tempHolder;
    private AlgoAsync async;
    private DataModel[] tempHolderRaw;
    private long startAlarmTime, endAlarmTime;
    private String serverIp;
    private boolean connected;
    private PrintWriter out;
    private DataModel pModel;
    private boolean changed = false;
    private Thread thread;
    private String toSendSocket;

    private StringBuilder sb;
    private NetworkUtility utility;
    private int lastStep;
    private double mSmallestPressure;
    private int mPressureCount;
    private boolean isStillSmallest=false;
    private boolean isNotSmallest=true;
    private boolean attached;

    public RDataManagerImp(RDataManagerListener listener, Context mContext) {
        this.listener = listener;
        sb = new StringBuilder();
        isSleepStarted = false;
        startAlarmTime = 0;
        endAlarmTime = 0;
        rolling = new Rolling(ROLLING_AVG_SIZE);
        rolling2 = new Rolling(ROLLING_AVG_SIZE);
        rolling3 = new Rolling(ROLLING_AVG_SIZE);
        dataStorage = new DataModel[PACKET_READY_TO_IMP];
        tempHolder = new DataModel[PACKET_READY_TO_IMP];
        indexDataStorage = 0;
        dataStorageRaw = new DataModel[PACKET_READY_TO_IMP];
        tempHolderRaw = new DataModel[PACKET_READY_TO_IMP];
        accelImp = new CheckAccelImp(this, SharePrefrancClass.getInstance(mContext).getIPreference(CommonMethod.STEP_COUNT));
        observer = new Observer<DataModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(DataModel dataModel) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

//        Subscription subscription = observable
//                .subscribeOn(Schedulers.io())       //observable will run on IO thread.
//                .observeOn(AndroidSchedulers.mainThread())      //Observer will run on main thread.
//                .subscribe(observer);

    }

    @Override
    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public void setSleepStarted(boolean sleepStarted) {
        isSleepStarted = sleepStarted;
    }

    public void setStartAlarmTime(long startAlarmTime) {
        this.startAlarmTime = startAlarmTime;
    }

    public void setEndAlarmTime(long endAlarmTime) {
        this.endAlarmTime = endAlarmTime;
    }

    @Override
    public void onRawDataModel(final DataModel model, Context context) {
        if (model != null) {
            sb.setLength(0);
            Log.d(TAG, sb.append("pressure max : ").append(model.getPressure()).toString());

            //  Log.d(RDataManagerImp.class.getSimpleName(), "data received:" + model.getPressure());
            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                    if (isSleepStarted) {
                        pushToSleep(model, startAlarmTime,
                                endAlarmTime);
                        return;
                    }

                    processForStepCounting(model);
                    processModelData(model, context);
                }
            }).subscribeOn(Schedulers.computation())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String s) {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
            /**
             * Currently we are working on pressure
             */


        } else {
            Log.d(RDataManagerImp.class.getSimpleName(), "data received: model is null");
        }
    }

    @Override
    public void onSleepStarted(boolean b) {
        setSleepStarted(b);
    }

    @Override
    public void onStartAlarmTime(long startAlarm) {
        setStartAlarmTime(startAlarm);
    }

    @Override
    public void onEndAalrmTime(long endAlarm) {
        setEndAlarmTime(endAlarm);
    }

    private void pushToSleep(DataModel model, long startTime, long endTime) {
        accelImp.onSleepdataAvail(model, startTime, endTime);
    }

    private DataModel copy(DataModel model) {
        modelTemp = new DataModel();
        modelTemp.setRawString(model.getRawString());
        modelTemp.setTimestamp(model.getTimestamp());
        modelTemp.setBattery(model.getBattery());
        modelTemp.setPressure(model.getPressure());
        modelTemp.setTemprature(model.getTemprature());
        modelTemp.setX(model.getX());
        modelTemp.setY(model.getY());
        modelTemp.setZ(model.getZ());
        return modelTemp;
    }

    private void processForStepCounting(DataModel model) {
        //TODO We need to check step and activity in this method
        accelImp.onModelAvail(model);
    }

    private void processModelData(DataModel model, Context context) {
        if (mSmallestPressure <= 0 || mSmallestPressure > model.getPressure())
            mSmallestPressure = model.getPressure();
        if (mPressureCount > 100) {
            if (isStillSmallest && !isNotSmallest) {
                attached=false;
                listener.onDeviceNotAttached();
            }else {
                attached=true;
                listener.onDeviceAttached();
            }
            mPressureCount = 0;
            isStillSmallest=false;
            isNotSmallest=false;
        }
        if(Math.abs(model.getPressure()-mSmallestPressure)<100){
            isStillSmallest=true;
        }else
        {
            isNotSmallest=true;
        }

        mPressureCount+=1;

        dataModel = copy(model);
        rolling.add(model.getPressure());
        model.setPressure(rolling.getaverage());
        listener.onDataProcessed(model);
        rolling2.add(rolling.getaverage());
        model.setPressure(rolling2.getaverage());
        checkIfDataGatheringCompleted(model, context, dataModel);
        pModel = model;
        toSendSocket = "R " + dataModel.getRawString() + " P " + model.getAllInHex();
        Log.i("serverIp", toSendSocket);
        changed = true;


//        executor.execute(new SocketWorker(dataModel,model,serverIp));

//        new Thread(new SocketWorker(dataModel,model,serverIp)).start();

//        rolling3.add(rolling2.getaverage());
//        dataModel=new DataModel();
//        dataModel.setPressure(rolling3.getaverage());
    }

    @Override
    public void startSendingData(String stringExtra) {
        startSocket(stringExtra);

    }

    @Override
    public void onSocketStopped() {
        connected = false;
        if (thread != null && !thread.isInterrupted()) {
            thread.interrupt();
        }
    }

    @Override
    public void arrangeBreathingForServer(String url, int count, long timestamp, BreathState currentState) {
        ItemDetail detail = new ItemDetail();
        detail.setCount(count);
        detail.setTimeStamp(Helper.convertTimestampToTime(timestamp));
        detail.setStateofmindFkid(Helper.getIdByState(currentState));
        BreathingModel model = AppApplication.getInstance().addBreathing(detail);
        utility = new NetworkUtility.NetworkBuilder().setHeader().build();
        utility.saveBreath(url, model, new NetworkUtility.ResponseListener() {
            @Override
            public void onSuccess(Object message) {
//                TODO response
                AppApplication.getInstance().removeBreathing(detail);
            }

            @Override
            public void onFailure(Object err) {
//                TODO response
                ((Throwable) err).printStackTrace();
            }

            @Override
            public void onSomethingWrong(Object e) {
//                TODO response

            }
        });
    }

    @Override
    public void arrangeStepsForServer(String url, int step) {
        if (lastStep == step)
            return;

        lastStep = step;
        StepsModel model = new StepsModel();
        itg8.com.nowzonedesigndemo.utility.model.step.ItemDetail detail = new itg8.com.nowzonedesigndemo.utility.model.step.ItemDetail();
        List<itg8.com.nowzonedesigndemo.utility.model.step.ItemDetail> details = new ArrayList<>();
        detail.setDate(Helper.convertTimestampToTime(System.currentTimeMillis()));
        detail.setSteps(step);
        details.add(detail);
        model.setItemDetails(details);
        if (utility == null)
            utility = new NetworkUtility.NetworkBuilder().setHeader().build();
        utility.saveSteps(url, model, new NetworkUtility.ResponseListener() {
            @Override
            public void onSuccess(Object message) {
                //TODO do something
            }

            @Override
            public void onFailure(Object err) {
                ((Throwable) err).printStackTrace();
            }

            @Override
            public void onSomethingWrong(Object e) {

            }
        });
    }


    @Override
    public void resetCountAndAverage() {
        if(accelImp!=null){
            accelImp.resetStepCount();
        }
    }

    private void startSocket(String ipAddress) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InetAddress serverAddr = InetAddress.getByName(ipAddress);
                    Log.d("ClientActivity", "C: Connecting...");
                    Socket socket = new Socket(serverAddr, 8080);
                    connected = true;
                    while (connected) {
                        try {
                            if (changed) {
                                Log.d("ClientActivity", "C: Sending command.");
                                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                                        .getOutputStream())), true);
                                // WHERE YOU ISSUE THE COMMANDS


//                                out.println
//                                        ("Raw: " + dataModel.getTimestamp() + " " + dataModel.getPressure() + " "
//                                        + dataModel.getX()
//                                        + " "
//                                        + dataModel.getY()
//                                        + " "
//                                        + dataModel.getZ()
//                                        + " Processed: "
//                                        + pModel.getPressure() + " "
//                                        + pModel.getX() + " "
//                                        + pModel.getY() + " "
//                                        + pModel.getZ());

//                                out.println
//                                        ("Raw: " +dataModel.getRawString()
//                                        + " Processed: "
//                                        +pModel.getAllInHex());

                                if (toSendSocket != null)
                                    out.println
                                            (toSendSocket);


                                Log.d("ClientActivity", "C: Sent.");
                                changed = false;
                            }
                        } catch (Exception e) {
                            Log.e("ClientActivity", "S: Error", e);
                        }
                    }
                    socket.close();
                    Log.d("ClientActivity", "C: Closed.");
                } catch (Exception e) {
                    Log.e("ClientActivity", "C: Error", e);
                    listener.onSocketInterrupted();
                    connected = false;
                }
            }
        });
        thread.start();

    }

    private synchronized void checkIfDataGatheringCompleted(DataModel model, Context context, DataModel rawData) {
        dataStorage[indexDataStorage] = model;
        dataStorageRaw[indexDataStorage] = rawData;
        // Log.d(TAG,"Size of dataStorage "+dataStorage.size());

        if (indexDataStorage == PACKET_READY_TO_IMP - 1) {
            Log.d(TAG, "datas  is greater");
            implementStorageProcess(context, dataStorage);
            indexDataStorage = 0;
        } else {
            indexDataStorage++;
        }
    }


    private void implementStorageProcess(Context context, DataModel[] dataStorage) {
        tempHolder = new DataModel[ROLLING_AVG_SIZE];
        tempHolder = dataStorage.clone();

        tempHolderRaw = new DataModel[ROLLING_AVG_SIZE];
        tempHolderRaw = this.dataStorageRaw.clone();

//        resetDataStorage(this.dataStorage);
//        resetDataStorage(this.dataStorageRaw);

        //We will do that after SAAS TODO SAAS
        passForFIleStorage(tempHolderRaw, context);

        passForCalculation(tempHolder);
    }

    private void passForFIleStorage(DataModel[] dataStorage, Context context) {
        //  dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));

        /**
         * Currently we are haulting this TODO uncomment after complete implementaion of breath count
         * v2: we are again starting it for storing data into local storage visible to user. After implementation of this data we will
         * change STORAGE PATH ONLY
         */
        FileAsync async = new FileAsync(SharePrefrancClass.getInstance(context).getPref(CommonMethod.STORAGE_PATH));
        async.execute(this.tempHolderRaw);

//        Bundle bundle=new Bundle();
//        bundle.putString(CommonMethod.FINISHED_DATA, new Gson().toJson(dataStorage));
//        dataToFileJob = dispatcher.newJobBuilder()
//                .setService(ServiceOnCheck.class)
//                .setTag(REMINDER_JOB_TAG)
//                .setLifetime(Lifetime.FOREVER)
//                .setRecurring(true)
//                .setTrigger(Trigger.executionWindow(
//                      1,2
//                ))
//                .setExtras(bundle)
//                .setReplaceCurrent(true)
//                .build();
//
//        dispatcher.schedule(dataToFileJob);
    }

    private String[] getListOfFile(String path) {
        return new String[0];
    }

    private void passForCalculation(DataModel[] dataStorage) {
        Log.d(TAG, "came for calculation");
        async = new AlgoAsync(this);
        async.execute(dataStorage);
    }

    private void resetDataStorage(List<DataModel> dataStorage) {
        dataStorage.clear();
    }

    @Override
    public void onCountResultAvailable(int count, long timestamp) {
        Log.d(TAG, "bpmCount = " + count);
        if (!attached) {
            listener.onDeviceNotAttached();
            return;
        }
        listener.onCountAvailable(count, timestamp);
    }

    @Override
    public void onCountFail() {

    }

    @Override
    public void onMotionStarts() {
        Log.d(TAG, "motion: started");
    }

    @Override
    public void onMotionEnds() {
        Log.d(TAG, "motion: finish");
    }

    @Override
    public void onStep(int step) {
        //    Log.d(TAG,"Step count: "+step);
        listener.onStepCountReceived(step);
    }

    @Override
    public void onSleepInterrupted(long timestamp) {

        listener.onSleepInterrupted(timestamp);
    }

    @Override
    public void startWakeupService() {
        listener.onStartWakeupSevice();
    }

    @Override
    public void onMovement(float mAccel) {
        listener.onMovement(mAccel);
    }

    @Override
    public void onNoMovement(float i) {
        listener.onNoMovement(i);
    }

    @Override
    public void onAngleAvail(double something) {
        listener.onAxisDataAvail(something,0);
    }
}
