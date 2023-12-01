package com.ricardo.wifiservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import hsae.hal.wifi.V1_0.IWifi;
import hsae.hal.wifi.V1_0.ScanResult;

public class WifiService extends Service {
    private final String TAG = "[WifiServcie]";
    private IWifi mWifi = null;

    // sta和ap不同同时打开，默认为false，一旦开启一个，设置为true
    private boolean conflict = false;

    @Override
    public void onCreate() {
        super.onCreate();

        try { mWifi =IWifi.getService();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
        public int setStaSSID(String ssid) throws RemoteException {
            Log.d(TAG, "Setting SSID.");
            //  ssid格式判断 ？？
            mWifi.setSTASSID(ssid);
            return 0;
        }


        @Override
        public List<Scan_Result> scan_results() throws RemoteException {
            List<Scan_Result> tmp = new ArrayList<>();
            // ScanResutl为hidl服务传过来的数据类型
            List<ScanResult> ret = mWifi.scan_results();
            Log.d(TAG, "scan_results." + " --> " + ret.size());

            Log.d(TAG, ret.toString());
//            for (ScanResult scanResult : ret) {
//                Log.d(TAG, scanResult.toString());
//                tmp.add(scanResult.toString());
//            }
            return null;
        }

        @Override
        public int openSta() throws RemoteException {
            if (conflict)  return 1;
            Log.d(TAG, "opening Station.");
            conflict = true;
            return mWifi.openSTA();
        }

        @Override
        public int closeSta() throws RemoteException {
            Log.d(TAG, "close station.");
            conflict = false;
            return mWifi.closeSTA();
        }

        @Override
        public int scan() throws RemoteException {
            Log.d(TAG, "scanning.");
            return mWifi.scan();
        }



        @Override
        public int getStaStatus() throws RemoteException {
            Log.d(TAG, "get Station status.");
            return mWifi.getSTAStatus();
        }



        @Override
        public String setStaPassword(String password) throws RemoteException {
            Log.d(TAG, "Setting password.");
            mWifi.setSTAPassWord(password);
            return null;
        }

        @Override
        public int connect() throws RemoteException {
            Log.d(TAG, "connecting.");
            return mWifi.connect();
        }

        @Override
        public int disConnect() throws RemoteException {
            Log.d(TAG, "disConnecting.");
            return mWifi.disconnect();
        }

        @Override
        public int getApStatus() throws RemoteException {
            Log.d(TAG, "Get Ap Status.");
            return mWifi.getAPStatus();
        }

        @Override
        public int openAp() throws RemoteException {
            if (conflict)  return 1;
            mWifi.openAP();
            conflict = true;
            return 0;
        }

        @Override
        public int closeAp() throws RemoteException {
            mWifi.closeAP();
            conflict = false;
            return 0;
        }

        @Override
        public List<String> getApConnectInfo() throws RemoteException {
            mWifi.getAPConnectInfo();
            return null;
        }

        @Override
        public int disConnectAp(String ssid) throws RemoteException {
            mWifi.disconnectAP(ssid);
            return 0;
        }

        @Override
        public int setApPassward(String password) throws RemoteException {
            mWifi.setAPPassword(password);
            return 0;
        }


    };
}
