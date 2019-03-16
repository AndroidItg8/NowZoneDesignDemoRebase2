package itg8.com.nowzonedesigndemo.sanning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.connection.DeviceModel;
import itg8.com.nowzonedesigndemo.widget.SignalMeter;


/**
 * Created by itg_Android on 2/21/2017.
 */

public class DeviceListAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    List<DeviceModel> list;
    ConnectClickedListener listener;

    public interface ConnectClickedListener{
        void connectClicked(DeviceModel model);
    }

    public DeviceListAdapter(Context context,ConnectClickedListener listener) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list = new ArrayList<>();
        this.listener=listener;
    }

    public  synchronized void addItemToList(DeviceModel model) {
        boolean contains = false;
        for (DeviceModel m :
                list) {
            if (m.getAddress().equals(model.getAddress())) {
//                contains=true;
                return;
            }
        }

//        if(!contains)
        list.add(model);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public DeviceModel getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.device_item, viewGroup, false);

        }

        TextView deviceName = (TextView) view.findViewById(R.id.deviceName);
        TextView deviceAddress = (TextView) view.findViewById(R.id.deviceAddress);
        SignalMeter signalMeter = (SignalMeter) view.findViewById(R.id.SignalMeter);
//
        Button button=(Button)view.findViewById(R.id.btn_connect);
        deviceName.setText(list.get(position).getName());
        deviceAddress.setText(list.get(position).getAddress());
        signalMeter.setValue(list.get(position).getRssi());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.connectClicked(list.get(position));
                }
            }
        });

        return view;
    }

    public DeviceModel getSelectedDevice(int i) {
        return list.get(i);
    }
}
