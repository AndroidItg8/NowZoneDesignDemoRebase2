package itg8.com.nowzonedesigndemo.utility;

import android.os.Bundle;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;



public class ServiceOnCheck extends JobService {


    private static final String COMPLETE_FILE_PATH = "";
    private Bundle bundle;

    @Override
    public boolean onStartJob(JobParameters param) {
//        Intent intent=new Intent(this, BleService.class);
//        if(param.getJobId()==JOB_CONNECT_DEVICE){
//            if(SharePrefrancClass.getInstance(getBaseContext()).hasBPreference(DEVICE_ADDRESS)){
//                startService(intent);
//            }else {
//                stopService(intent);
//            }
//        }

//        bundle = param.getExtras();
//        String modelString = bundle != null ? bundle.getString(CommonMethod.FINISHED_DATA) : null;
//        Log.d(TAG,"data received : "+modelString);
//        if(modelString!=null) {
//            List<DataModel> models = new Gson().fromJson(modelString,new TypeToken<List<DataModel>>(){}.getType());
//            if (models != null) {
//                storeToFile(models);
//            }
//        }


        return false;
    }


    @Override
    public boolean onStopJob(JobParameters param) {
        return true;
    }


}
