package com.ricardo.wifiservicedemo;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ricardo.wifiservice.IWifiService;
import com.ricardo.wifiservice.Scan_Result;
import com.ricardo.wifiservice.WifiService;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "[MainActivity]";
    private IWifiService mService;

    private List<Scan_Result> ret = new ArrayList<>();

    RecyclerView mRecyclerView;
    MyAdapter mMyAdapter ;


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IWifiService.Stub.asInterface(service);
            Log.d(TAG, "Service connected.");
            try {
                mService.openStation();
                ret = mService.search();
            } catch (RemoteException e) {
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
                    textView.setVisibility(View.GONE);

                    mRecyclerView = findViewById(R.id.recyclerview);
                    mMyAdapter = new MyAdapter();
                    mRecyclerView.setAdapter(mMyAdapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
            }
        });


    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {

        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(MainActivity.this, R.layout.item_list, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            Scan_Result scan_result = ret.get(position);
            holder.mWifiNameTv.setText(scan_result.getSsid());
            holder.mWifiLevel.setText(scan_result.getLevel());
        }

        @Override
        public int getItemCount() {
            return ret.size();
        }
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView mWifiNameTv;
        TextView mWifiLevel;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            mWifiNameTv = itemView.findViewById(R.id.textView);
            mWifiLevel = itemView.findViewById(R.id.textView2);
        }
    }

}