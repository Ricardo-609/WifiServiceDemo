package com.ricardo.wifiservicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.ricardo.wifiservice.IWifiService;
import com.ricardo.wifiservice.WifiService;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "[MainActivity]";
    private IWifiService mService;

//    private Handler mHandler;
    
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IWifiService.Stub.asInterface(service);
            Log.d(TAG, "Service connected.");
            try {
                mService.openSta();
            } catch(RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, WifiService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);

//        startSubThread();
    }

//    private void startSubThread() {
//        mHandler = new Handler(Looper.getMainLooper());
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//                    if (mService != null) mService.openSta();
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//
//                // 每隔300毫秒再次调用自身，实现定时任务
//                mHandler.postDelayed(this, 300);
//            }
//        }, 0);
//    }

}