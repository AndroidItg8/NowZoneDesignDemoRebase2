package itg8.com.nowzonedesigndemo.utility;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.j256.ormlite.stmt.query.In;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.common.DataModel;



class AlgoAsync extends AsyncTask<DataModel[],Void,Integer> {


    private static final double DELTA = 2.0;
    private static final String TAG = AlgoAsync.class.getSimpleName();
    private static final long ONE_MINUTE = 60000;
    private PAlgoCallback callback;
    private int bpmInMinute;
    private File completeFileStructure;

    AlgoAsync(PAlgoCallback callback) {
        this.callback = callback;
    }

    @SafeVarargs
    @Override
    protected final Integer doInBackground(DataModel[]... lists) {
        List<Map<Integer, Double>> data = CommonMethod.countBPM(lists[0], DELTA);
     //   Log.d(TAG,"size : "+data.size());
        if(data.size()>0) {
            int count= Math.max(data.get(0).size(),data.get(1).size());
            DataModel[] listModel = lists[0];
            if (listModel.length > 0) {
                Log.d(TAG,"StartTime "+listModel[0].getTimestamp()+" EndTime "+ listModel[listModel.length - 1].getTimestamp() +" diff: "+(listModel[listModel.length - 1].getTimestamp()-listModel[0].getTimestamp())+" count:"+count);
                long timeTaken = listModel[listModel.length - 1].getTimestamp() - listModel[0].getTimestamp();
                if (timeTaken > 1000) {
                    bpmInMinute = (int) ((ONE_MINUTE * count) / timeTaken);
//                    callback.onCountResultAvailable(bpmInMinute, listModel.get(listModel.size() - 1).getTimestamp());
                }
                    /**
                     * as we cakculated that a count in one minute is 2000. so we will directly send count
                     */
                Log.d(TAG,"hashmap : "+new Gson().toJson(data));
                Log.d(TAG,"hashmap BPMInMinute: "+bpmInMinute);
                Log.d(TAG,"hashmap timestamp : "+listModel[0].getTimestamp()+" "+listModel[listModel.length-1].getTimestamp());
                    writeEverythingInFile(data,bpmInMinute,listModel[0].getTimestamp(),listModel[listModel.length-1].getTimestamp());
                    callback.onCountResultAvailable(bpmInMinute, listModel[listModel.length-1].getTimestamp());
//                    callback.onCountResultAvailable(count, listModel.get(listModel.size() - 1).getTimestamp());
            }
        }
        return null;
    }

    private void writeEverythingInFile(List<Map<Integer, Double>> data, int bpmInMinute, long startTime, long endTime) {
        String content="";
        int count=0;
        TreeMap<Integer,Double> peaks=new TreeMap<>(data.get(0));
        TreeMap<Integer,Double> trough=new TreeMap<>(data.get(1));
        List<Integer> indexPeak=new ArrayList<>();
        List<Integer> indexTrough=new ArrayList<>();
        List<Integer> diffPeak=new ArrayList<>();
        List<Integer> diffThrogh=new ArrayList<>();
        TreeMap<Integer,Double> all=new TreeMap<>();

        all.putAll(peaks);
        all.putAll(trough);
        String header="Count:"+bpmInMinute+" StartTime:"+startTime+" EndTime:"+endTime+"\n";
        for (TreeMap.Entry<Integer, Double> map :
                all.entrySet()) {
            header+=map.getKey()+" "+map.getValue()+"\n";
            if(count%2==0){
                indexPeak.add(map.getKey());
            }else {
                indexTrough.add(map.getKey());
            }
            count++;
        }
        for(int i=1; i<indexPeak.size();i++){
            diffPeak.add((indexPeak.get(i)-indexPeak.get(i-1)));
        }
        for(int i=1; i<indexTrough.size();i++){
            diffThrogh.add((indexTrough.get(i)-indexTrough.get(i-1)));
        }

        for(int i=0; i<diffPeak.size() && i<diffThrogh.size(); i++){
            content+=Math.abs(diffPeak.get(i)-diffThrogh.get(i))+"\n";
        }

        createFile(header+content);
    }

    private void createFile(String log) {
        completeFileStructure = new File(Environment.getExternalStorageDirectory() + File.separator + "nowzone", Helper.getCurrentDate()+"breathingPeakTrough.txt");
        try {
            FileWriter fWriter;
            if (completeFileStructure.exists()) {
                fWriter = new FileWriter(completeFileStructure, true);
                fWriter.append(log);
            } else {
                fWriter = new FileWriter(completeFileStructure, true);
                fWriter.write(log);
            }
            fWriter.flush();
            fWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }
}