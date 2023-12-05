package com.ricardo.wifiservice;

import android.os.RemoteException;

import java.util.List;

import hsae.hal.wifi.V1_0.IWifi;
import hsae.hal.wifi.V1_0.ScanResult;

import com.ricardo.wifiservice.base.LRUCache;

public class WifiStation {
    private static final String TAG = "[WifiStation]";

    private LRUCache<ScanResult, String> cache = new LRUCache<>();     // 最多记录8个已连接的设备
    private ScanResult current = null;

//    IWifi mWifi = IWifi.getService();
    IWifi mWifi;

    public WifiStation(IWifi wifi) {
        mWifi = wifi;
    }

    public boolean openStation() {
        int ret = -1;
        try {
            if (mWifi.getSTAStatus() == 0)  return true;        // 避免多次打开
            ret = mWifi.openSTA();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return ret == 0;
    }

    public boolean closeStation() {
        int ret = -1;
        try {
            if (mWifi.getSTAStatus() != 0) return true;
            ret = mWifi.closeSTA();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return ret == 0;
    }

    public List<ScanResult> search() {
        try {
            if (mWifi.getSTAStatus() != 0) return null;
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

    public boolean linkWifi(ScanResult scanResult, String passwd) {
        int ret = -1;
        try {
            if (mWifi.getSTAStatus() != 0)  return false;
            cache.get(scanResult);                  // 连接之前先查询是否连接过
            mWifi.setSTASSID(scanResult.ssid);
            mWifi.setSTAPassWord(passwd);
            ret = mWifi.connect();
            current = scanResult;
            // 每连接一个wiif就记录一个，若超过8个，则删除一个
            cache.put(scanResult, passwd);
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

    public ScanResult getCurrentStatus() {
        return current;
    }

    public boolean deleteDevice(ScanResult scanResult) {
        if (cache.get(scanResult) != null) {
            cache.remove(scanResult);
            return true;
        }
        return false;
    }

}
