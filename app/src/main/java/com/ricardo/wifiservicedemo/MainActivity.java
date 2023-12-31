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
import android.view.View;
import android.widget.TextView;

import com.ricardo.wifiservice.IWifiService;
import com.ricardo.wifiservice.Scan_Result;
import com.ricardo.wifiservice.WifiService;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "[MainActivity]";
    private IWifiService mService;

    private List<Scan_Result> ret;
    
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IWifiService.Stub.asInterface(service);
            Log.d(TAG, "Service connected.");
            try {
                mService.openSta();
                mService.getStaStatus();
//                mService.scan();
                ret = mService.scan_results();
//                Log.d(TAG, ret.toString());

                mService.setStaSSID("Redmi K60");
                mService.setStaPassword("12345678");
                mService.connect();
                try {
                    Thread.currentThread ().sleep (1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mService.getStaStatus();
//                mService.disConnect();
//                mService.closeSta();

//                Log.d(TAG, mService.get().toString());

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

        TextView textView = findViewById(R.id.tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d(TAG, String.valueOf(mService.getStaStatus()));
                    Random random = new Random();
                    int t = random.nextInt(ret.size());
                    textView.setText(ret.get(t).getSsid() + "----->" + ret.get(t).getLevel());

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}