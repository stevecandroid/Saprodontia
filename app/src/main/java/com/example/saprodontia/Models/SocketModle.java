package com.example.saprodontia.Models;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import com.example.saprodontia.Application.App;
import com.example.saprodontia.Service.SendService;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by 铖哥 on 2017/7/16.
 */

public class SocketModle {

    private  int len ;
    private  FileInputStream fis = null;
    private  OutputStream fos = null;
    private  Socket socket;
    private WifiManager wm = null;

    public SocketModle(Context context) {
        wm = (WifiManager) App.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    public  void shareFile(ArrayList<FileInfo> datas){

        Intent intent = new Intent(App.getContext(),SendService.class);
        intent.putParcelableArrayListExtra("datas",datas);
        App.getContext().startService(intent);

    }




//    public  boolean openHotSpot(){
//
//
//        return true;
//    }
//
//    private boolean openWifi(){
//
//        if(!wm.isWifiEnabled()){
//            return wm.setWifiEnabled(true);
//        }
//        return true;
//    }
//
//    private List<ScanResult> getScanResult(){
//        openWifi();
//        wm.startScan();
//        return wm.getScanResults();
//    }
//
//    public boolean connectWifi(String wifiName){
//        List<ScanResult> results = getScanResult();
//        List <WifiConfiguration> configs = wm.getConfiguredNetworks();
//
//        int connectId = 0 ;
//
//        for(ScanResult r : results){
//            if(r.SSID.equals(wifiName)){
//                for(WifiConfiguration c : configs){
//                    if( c.SSID == wifiName){
//                        connectId = c.networkId;
//                        wm.enableNetwork(connectId,true);
//                        return true;
//                    }
//                }
//                break;
//            }
//        }
//
//        return false;
//    }
//
//    public WifiConfiguration getconfig(String SSID){
//        WifiConfiguration config = new WifiConfiguration();
//        config.allowedAuthAlgorithms.clear();
//        config.allowedGroupCiphers.clear();
//        config.allowedKeyManagement.clear();
//        config.allowedPairwiseCiphers.clear();
//        config.allowedProtocols.clear();
//        config.SSID = "\"" + SSID + "\"";
////        config.wepKeys[0] = "";
//        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
////        config.wepTxKeyIndex = 0;
//        return config;
//    }
//
//    class WifiBrocastReceiver extends BroadcastReceiver{
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//        }
//    }

}
