package com.ricardo.wifiservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import hsae.hal.wifi.V1_0.IWifi;
import hsae.hal.wifi.V1_0.ScanResult;

public class WifiService extends Service {
    private final String TAG = "[WifiServcie]";
    private IWifi mWifi = null;

    private WifiStation mWifiStation = null;
    private WifiAP mWifiAP = null;

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            mWifi =IWifi.getService();
            mWifiStation = new WifiStation(mWifi);
            mWifiAP = new WifiAP(mWifi);
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
        public boolean openStation() throws RemoteException {
            return mWifiStation.openStation();
        }

        @Override
        public boolean closeStation() throws RemoteException {
            return mWifiStation.closeStation();
        }

        @Override
        public List<Scan_Result> search() throws RemoteException {
            List<Scan_Result> tmp = new ArrayList<>();
            List<ScanResult> ret = mWifiStation.search();

            for (ScanResult scanResult : ret) {
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
        public boolean linkWifi(Scan_Result scanResult, String passwd) throws RemoteException {
            return false;
        }

        @Override
        public boolean disLinkWifi() throws RemoteException {
            return false;
        }

        @Override
        public Scan_Result getCurrentStatus() throws RemoteException {
            return null;
        }

        @Override
        public boolean deleteDevice(Scan_Result scanResult) throws RemoteException {
            return false;
        }
    };
}
