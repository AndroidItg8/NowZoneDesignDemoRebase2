package itg8.com.nowzonedesigndemo.utility;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import itg8.com.nowzonedesigndemo.common.DataModel;



public class SocketWorker implements Runnable{

    private static final int SERVERPORT = 8080;
    private String SERVER_IP;
    private DataModel model;
    private final DataModel modelPro;
    private Socket socket;
    private boolean connected;

    public SocketWorker(DataModel modelRaw,DataModel modelPro,String servetrIp){
        model = modelRaw;
        this.modelPro = modelPro;
        SERVER_IP=servetrIp;
    }

    @Override
    public void run() {
//        try {
//            if(SERVER_IP==null)
//                return;
//            socket = new Socket(SERVERPORT);
//            BufferedWriter obf = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//
//            obf.write("Raw: "+model.getTimestamp()+" "+model.getPressure()+" "
//            +model.getX()
//            +" "
//            +model.getY()
//            +" "
//            +model.getZ()
//            +" Processed"
//            +model.getX()+" "
//            +model.getY()+" "
//            +model.getZ());
//            obf.flush();
//            obf.close();
//
//
//        } catch (UnknownHostException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        try {
            InetAddress serverAddr = InetAddress.getByName("192.168.1.6");
            Log.d("ClientActivity", "C: Connecting...");
            Socket socket = new Socket(serverAddr, 8080);
            connected = true;
            while (connected) {
                try {
                    Log.d("ClientActivity", "C: Sending command.");
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                            .getOutputStream())), true);
                    // WHERE YOU ISSUE THE COMMANDS



                    out.println("Raw: "+model.getTimestamp()+" "+model.getPressure()+" "
                            +model.getX()
                            +" "
                            +model.getY()
                            +" "
                            +model.getZ()
                            +" Processed: "
                            +model.getX()+" "
                            +model.getY()+" "
                            +model.getZ());
                    Log.d("ClientActivity", "C: Sent.");
                } catch (Exception e) {
                    Log.e("ClientActivity", "S: Error", e);
                }
            }
            socket.close();
            Log.d("ClientActivity", "C: Closed.");
        } catch (Exception e) {
            Log.e("ClientActivity", "C: Error", e);
            connected = false;
        }
    }

}
