package com.ricardo.wifiservice;

import android.os.RemoteException;
import android.util.Log;

import java.util.List;

import hsae.hal.wifi.V1_0.IWifi;
import hsae.hal.wifi.V1_0.ScanResult;

import com.ricardo.wifiservice.base.LRUCache;

public class WifiStation {
    private static final String TAG = "[WifiStation]";

    private LRUCache<String, String> cache = new LRUCache<>();     // 最多记录8个已连接的设备
    private String current = null;

//    IWifi mWifi = IWifi.getService();
    IWifi mWifi;

    public WifiStation(IWifi wifi) {
        mWifi = wifi;
    }

    public boolean openStation() {
        Log.d(TAG, "Openning Station.");

        int ret = -1;
        try {
            ret = mWifi.openSTA();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return ret == 0;
    }

    public boolean closeStation() {
        Log.d(TAG, "Close Station.");

        int ret = -1;
        try {
            ret = mWifi.closeSTA();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return ret == 0;
    }

    public List<ScanResult> search() {
        Log.d(TAG, "Scanning.");

        try {
            int ret = mWifi.scan();
            if (ret != 0) return null;
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            Thread.currentThread().sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            return mWifi.scan_results();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean linkWifi(String ssid, String passwd) {
        int ret = -1;
        try {
            // 连接之前先查询是否连接过
            cache.get(ssid);
            mWifi.setSTASSID(ssid);
            mWifi.setSTAPassWord(passwd);
            ret = mWifi.connect();
            current = ssid;
            // 每连接一个wiif就记录一个，若超过8个，则删除一个
            cache.put(ssid, passwd);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return ret == 0;
    }

    public boolean disLinkWifi() {
        if (current != null) {
            try {
                mWifi.disconnect();
                current = null;
                return true;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public String getCurrentStatus() {
        return current;
    }

    public boolean deleteDevice(String ssid) {
        if (cache.get(ssid) != null) {
            cache.remove(ssid);
            return true;
        }
        return false;
    }

}
