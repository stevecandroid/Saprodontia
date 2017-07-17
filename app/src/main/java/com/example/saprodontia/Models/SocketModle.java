package com.example.saprodontia.Models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Environment;

import com.example.saprodontia.Application.App;
import com.example.saprodontia.Utils.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

/**
 * Created by 铖哥 on 2017/7/16.
 */

public class SocketModle {

    private  int len ;
    private  FileInputStream fis = null;
    private  OutputStream fos = null;
    private  Socket socket;
    private WifiManager wm = null;

    public SocketModle() {
        wm = (WifiManager) App.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    public  void shareFile(List<String> paths){

        File file = new File(paths.get(0));
        byte[] data = new byte[1024*8];

        try {
            socket = new Socket(InetAddress.getByName("chanc"),65535);
            fos = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fis = new FileInputStream(file);
            while( (len=fis.read(data))!= -1){
                fos.write(data,0,len);
            }
            fos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  boolean openHotSpot(){


        return true;
    }

    private boolean openWifi(){

        if(!wm.isWifiEnabled()){
            return wm.setWifiEnabled(true);
        }
        return true;
    }

    private List<ScanResult> getScanResult(){
        openWifi();
        wm.startScan();
        return wm.getScanResults();
    }

    public boolean connectWifi(String wifiName){
        List<ScanResult> results = getScanResult();
        List <WifiConfiguration> configs = wm.getConfiguredNetworks();

        int connectId = 0 ;

        for(ScanResult r : results){
            if(r.SSID.equals(wifiName)){
                for(WifiConfiguration c : configs){
                    if( c.SSID == wifiName){
                        connectId = c.networkId;
                        wm.enableNetwork(connectId,true);
                        return true;
                    }
                }
                break;
            }
        }

        return false;
    }

    public WifiConfiguration getconfig(String SSID){
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";
//        config.wepKeys[0] = "";
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//        config.wepTxKeyIndex = 0;
        return config;
    }

    class WifiBrocastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

}
