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
        public Scan_Result get() throws RemoteException {
            return new Scan_Result("1", "2", "3", "4", "5");
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
//            Log.d(TAG, "=========================");
//            Log.d(TAG, "scan_results" + " --> " + ret.size());

//            Log.d(TAG, ret.toString());
            for (ScanResult scanResult : ret) {
//                Log.d(TAG, scanResult.toString());
                Scan_Result scan_result = new Scan_Result();
                scan_result.setBssid(scanResult.bssid);
                scan_result.setFrequency(scanResult.frequency);
                scan_result.setFlag(scanResult.flags);
                scan_result.setLevel(scanResult.level);
                scan_result.setSsid(scanResult.ssid);
                tmp.add(scan_result);
            }
            return tmp;
        }

        @Override
        public int openSta() throws RemoteException {
            Log.d(TAG, "opening Station.");
            mWifi.openSTA();
            mWifi.scan();
            try {
                Thread.currentThread ().sleep (1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            mWifi.scan_results();
            return 0;
        }

        @Override
        public int closeSta() throws RemoteException {
            Log.d(TAG, "close station.");
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
            mWifi.openAP();
            return 0;
        }

        @Override
        public int closeAp() throws RemoteException {
            mWifi.closeAP();
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
