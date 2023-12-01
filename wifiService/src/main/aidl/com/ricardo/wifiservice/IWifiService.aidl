// IWifiService.aidl
package com.ricardo.wifiservice;

// Declare any non-default types here with import statements
import com.ricardo.wifiservice.Scan_Result;

interface IWifiService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    int openSta();
    int closeSta();
    int scan();
    List<Scan_Result> scan_results();
    int getStaStatus();
    int setStaSSID(String ssid);
    String setStaPassword(String password);
    int connect();
    int disConnect();

    int getApStatus();
    int openAp();
    int closeAp();
    List<String> getApConnectInfo();
    int disConnectAp(String ssid);
    int setApPassward(String password);
}