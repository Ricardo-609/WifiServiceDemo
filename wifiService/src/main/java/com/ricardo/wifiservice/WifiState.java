package com.ricardo.wifiservice;

public class WifiState {
    enum wifi_state_type {
        WIFI_STATE_OPEN,
        WIFI_STATE_CLOSE,
        WIFI_STATE_SCAN,

        WIFI_STATE_CONNECTING,
        WIFI_STATE_CONNECT_FAILED,
        WIFI_STATE_DISCONNECT
    };

    enum ap_state_type {
        AP_STATE_OPEN,
        AP_STATE_CLOSE
    };
}
