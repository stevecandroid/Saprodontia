package com.example.saprodontia.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.saprodontia.Models.SocketModle;
import com.example.saprodontia.R;
import com.example.saprodontia.Utils.LogUtil;
import com.example.saprodontia.Utils.ToastUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        List<String> l = SearchUtil.search("apk",new File("/data/app"));
//        for(String s : l){
//            LogUtil.e(s);
//        }

//        File[] files = file.canRead();


//        for(File f : file.listFiles()){
//            LogUtil.e(f.getPath());
//        }

//        PackageManager t = getPackageManager();
//        List<PackageInfo> p = t.getInstalledPackages(0);
//        for(PackageInfo a : p){
//            LogUtil.e(a.packageName);
//        }
//
//        byte[] data = new byte[1024];
//        FileInputStream fis = null;
//        FileOutputStream fos = null;
//
//        File file = new File("data/app/com.tencent.mm-1/base.apk");
//        File out = new File(Environment.getExternalStorageDirectory().getPath()+"/out.apk");
//        if(!out.exists()){
//            try {
//                out.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        LogUtil.e(file.canRead());
//        try {
//
//            fos = new FileOutputStream(out);
//            fis = new FileInputStream(file);
//            while(fis.read(data)!=-1)
//            fos.write(data);
//
//
//            LogUtil.e("FOUND");
//        } catch (IOException e) {
//            LogUtil.e("NOTFOUND");
//            e.printStackTrace();
//        }

//        try {
//            fis = new FileInputStream(file);
//            fos = new FileOutputStream(out);
//            fis.read(data);
//            LogUtil.e(data.length);
//            while( fis.read(data) != -1){
//                System.out.println(data);
//                fos.write(data);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                PackageManager pm = getPackageManager();
//                List<ApplicationInfo> in = pm.getInstalledApplications(0);
//                FileInputStream fis = null;
//                Socket socket = null;
//
//                File file = new File(in.get(12).sourceDir);
//                LogUtil.e(in.get(12).sourceDir);
//                byte[] data = new byte[1024*8];
//
//                LogUtil.e(Environment.getExternalStorageDirectory());
//
//                OutputStream fos = null;
//                try {
//                    socket = new Socket(InetAddress.getByName("chanc"),65535);
//                    fos = socket.getOutputStream();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                int len ;
//
//                try {
//                    fis = new FileInputStream(file);
//                    while( (len=fis.read(data))!= -1){
//                        fos.write(data,0,len);
//                    }
//                    fos.close();
//                    socket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Button bt_receive = (Button) findViewById(R.id.bt_receive);
        Button bt_send = (Button) findViewById(R.id.bt_send);
        bt_receive.setOnClickListener(this);
        bt_send.setOnClickListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiConfiguration config = new WifiConfiguration();
//        config.SSID = "Saprodontia";
//        config.preSharedKey="123456789";
//        try {
//            Method method = wm.getClass().getMethod("setWifiApEnabled",WifiConfiguration.class,Boolean.TYPE);
//            method.invoke(wm,config,true);
//        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
//            LogUtil.e("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
//            e.printStackTrace();
//        }
//        wm.setWifiEnabled(true);
//        wm.startScan();
//        List<ScanResult> result = wm.getScanResults();
////        List <WifiConfiguration> configs = wm.getConfiguredNetworks();
//
//        LogUtil.e(result.size());
//

//


//        wm.enableNetwork(,true)

//        SocketModle socketModle = new SocketModle();
//        LogUtil.e(socketModle.connectWifi("KNT-AL20"));

        wm.startScan();
//        wm.setWifiEnabled(true);
        List<ScanResult> results = wm.getScanResults();
        List <WifiConfiguration> configs = wm.getConfiguredNetworks();


        wm.disconnect();


        WifiConfiguration con = new WifiConfiguration();


//        for(ScanResult c : results){
//            if(c.SSID.contains("Saprodontia")){
//                con.SSID = c.SSID;
//                con.BSSID = c.BSSID;
//                wm.addNetwork(con);
//                LogUtil.e("ENABLE");
//            }
//        }
//        wm.addNetwork(getconfig("Saprodontia"));

//        for(WifiConfiguration c: configs){
//            LogUtil.e(c.SSID +" " + c.networkId);
//        }

        wm.enableNetwork(42,true);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_receive:{
                ToastUtil.showToast("RECEIVE");
                // TODO: 2017/7/16
                break;
            }
            case R.id.bt_send :{
                ToastUtil.showToast("SEND");
                startActivity(new Intent(MainActivity.this,SendActivity.class));
                // TODO: 2017/7/16
                break;
            }
        }
    }
}
