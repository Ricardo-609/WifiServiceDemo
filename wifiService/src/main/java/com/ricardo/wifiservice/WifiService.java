package com.ricardo.wifiservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

public class WifiService extends Service {
    private final String TAG = "[WifiServcie]";

    @Override
    public void onCreate() {
        super.onCreate();
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
            return 0;
        }
    };
}
