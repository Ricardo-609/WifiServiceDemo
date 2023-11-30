package com.ricardo.wifiservice;

import android.app.Service;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;

import hsae.hal.wifi.V1_0.IWifi;

public class WifiService extends Service {
    private final String TAG = "[WifiServcie]";
    private IWifi mWifi = null;

    @Override
    public void onCreate() {
        super.onCreate();

//        try {
//            mWifi =IWifi.getService();
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private IWifiService.Stub mBinder = new IWifiService.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int openSta() throws RemoteException {
            Log.d(TAG, "opening Station.");
//            if (mWifi != null) {
//                mWifi.openSTA();
//            }
            return 0;
        }

        @Override
        public int closeSta() throws RemoteException {
            return 0;
        }

        @Override
        public int scan() throws RemoteException {
            return 0;
        }

        @Override
        public List<ScanResult> scan_results() throws RemoteException {
            return null;
        }

        @Override
        public int getStaStatus() throws RemoteException {
            return 0;
        }

        @Override
        public int setStaSSID() throws RemoteException {
            return 0;
        }

        @Override
        public String setStaPasswor(String password) throws RemoteException {
            return null;
        }

        @Override
        public int connect() throws RemoteException {
            return 0;
        }

        @Override
        public int disConnect() throws RemoteException {
            return 0;
        }

        @Override
        public int getApStatus() throws RemoteException {
            return 0;
        }

        @Override
        public int openAp() throws RemoteException {
            return 0;
        }

        @Override
        public int closeAp() throws RemoteException {
            return 0;
        }

        @Override
        public List<String> getApConnectInfo() throws RemoteException {
            return null;
        }

        @Override
        public int disConnectAp(String ssid) throws RemoteException {
            return 0;
        }

        @Override
        public int setApPassward(String password) throws RemoteException {
            return 0;
        }


    };
}
