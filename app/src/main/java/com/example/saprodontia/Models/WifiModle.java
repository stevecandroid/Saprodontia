package com.example.saprodontia.Models;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.example.saprodontia.Application.App;
import com.example.saprodontia.Service.SendService;
import com.example.saprodontia.Utils.LogUtil;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 铖哥 on 2017/7/16.
 */

public class WifiModle {

    private WifiManager wm = null;

    public WifiModle(Context context) {
        wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    public  void shareFile(ArrayList<FileInfo> datas){

        Intent intent = new Intent(App.getContext(),SendService.class);
        intent.putParcelableArrayListExtra("datas",datas);
        App.getContext().startService(intent);
    }



    public boolean openHotSpot(){
        if(wm.isWifiEnabled())
        wm.setWifiEnabled(false);

        WifiConfiguration apConfig = new WifiConfiguration();
        apConfig.SSID = "Saprodontia";

        try {
            Method method = wm.getClass().getMethod(
                    "setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
            return (Boolean) method.invoke(wm, apConfig, true);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }


    }

    public boolean connectWifi(String ssid){

        if(!wm.isWifiEnabled()){
            wm.setWifiEnabled(true);
        }

        wm.startScan();
        List<ScanResult> results = wm.getScanResults();
        List <WifiConfiguration> configs = wm.getConfiguredNetworks();

        int connectId = 0 ;

        for(ScanResult r : results){
            if(r.SSID.contains(ssid)){
                for(WifiConfiguration c : configs){
                    if( c.SSID.contains(ssid)){
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

    public WifiConfiguration addConfig(String SSID){
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

        WifiConfiguration configuration = isExist(SSID);
        if(configuration!=null){
            wm.removeNetwork(configuration.networkId);
        }

        wm.addNetwork(config);

        return config;
    }

    public void openWifi(){
        if(!wm.isWifiEnabled()) {
            wm.setWifiEnabled(true);
        }
    }



    private WifiConfiguration isExist(String ssid) {

        List<WifiConfiguration> configs = wm.getConfiguredNetworks();
        for (WifiConfiguration config : configs) {
            if (config.SSID.equals("\""+ssid+"\"")) {
                return config;
            }
        }
        return null;
    }


//
//    class WifiBrocastReceiver extends BroadcastReceiver{
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//        }
//    }

}
