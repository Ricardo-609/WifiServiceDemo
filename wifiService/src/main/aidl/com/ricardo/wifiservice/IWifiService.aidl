// IWifiService.aidl
package com.ricardo.wifiservice;

// Declare any non-default types here with import statements
import com.ricardo.wifiservice.Scan_Result;

interface IWifiService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    // wifi station
    boolean openStation();
    boolean closeStation();
    List<Scan_Result> search();
    boolean linkWifi(String ssid, String passwd);
    boolean disLinkWifi();
    String getCurrentStatus();
    boolean deleteDevice(String ssid);

    // wifi ap
    boolean openAP();
    boolean closeAP();
    List<String> connectedDeviceInfo();
    boolean disConnectedDevice(String ssid);
    int getAPstatus();
    void createConfiguration(String ssid, String passwd);

    // test Scan_Result
    Scan_Result get();
}