package com.ricardo.wifiservice;

import android.os.RemoteException;

import java.util.List;

import hsae.hal.wifi.V1_0.IWifi;

public class WifiAP {
    private static final String TAG = "[WifiAP]";

//    IWifi mWifi = IWifi.getService();
    IWifi mWifi;

    public WifiAP(IWifi wifi) {
        mWifi = wifi;
    }

    public boolean openAP() {
        int ret = -1;
        try {
            if (mWifi.getAPStatus() == 0)  return true;        // 避免多次打开
            try {
                ret = mWifi.openAP();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return ret == 0;
    }

    public boolean closeAP() {
        int ret = -1;
        try {
            if (mWifi.getAPStatus() != 0) return true;
            ret = mWifi.closeAP();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return ret == 0;
    }

    public List<String> connectedDeviceInfo() {
        try {
            return mWifi.getAPConnectInfo();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void disConnectedDevice(String ssid) {
        try {
            mWifi.disconnectAP(ssid);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public int getAPstatus() {
        try {
            return mWifi.getAPStatus();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void createConfiguration(String ssid, String passwd) {
        try {
            mWifi.setAPSSID(ssid);
            mWifi.setAPPassword(passwd);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
