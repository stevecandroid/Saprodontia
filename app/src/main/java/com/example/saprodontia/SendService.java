package com.example.saprodontia;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.Nullable;
import android.text.format.Formatter;

import com.example.saprodontia.Models.AppInfo;
import com.example.saprodontia.Utils.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SendService extends IntentService {

    private ArrayList<AppInfo> datas;
    private Socket socket;
    private FileInputStream fis;
    private OutputStream os;
    private  int len = 0;

    public SendService() {
        super("SendService");
    }




    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        datas = intent.getParcelableArrayListExtra("datas");



        if(datas.size()>0) {
            byte[] buffer = new byte[1024];
            WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            try {
                socket = new Socket(Formatter.formatIpAddress(wm.getDhcpInfo().serverAddress), 65535);
                fis = new FileInputStream(new File(datas.get(0).getLocation()));
                os = socket.getOutputStream();

                byte[] name = datas.get(0).getName().getBytes();
                os.write(name);
                os.flush();
                while ((len = fis.read(buffer)) != -1) {
                    LogUtil.e("SENDING");
                    os.write(buffer, 0, len);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        try {
            if(fis != null && socket != null) {
                fis.close();
                os.close();
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        super.onDestroy();
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
}
