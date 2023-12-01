package com.ricardo.wifiservice;

import android.os.Parcel;
import android.os.Parcelable;

public class Scan_Result implements Parcelable {
    String bssid;
    String frequency;
    String level;
    String flag;
    String ssid;

    protected Scan_Result(Parcel in) {
        bssid = in.readString();
        frequency = in.readString();
        level = in.readString();
        flag = in.readString();
        ssid = in.readString();
    }

    public Scan_Result(String bssid, String frequency, String level, String flag, String ssid) {
        this.bssid = bssid;
        this.frequency = frequency;
        this.level = level;
        this.flag = flag;
        this.ssid = ssid;
    }

    public Scan_Result() {}

    public static final Creator<Scan_Result> CREATOR = new Creator<Scan_Result>() {
        @Override
        public Scan_Result createFromParcel(Parcel source) {
            return new Scan_Result(source);
        }

        @Override
        public Scan_Result[] newArray(int size) {
            return new Scan_Result[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bssid);
        dest.writeString(frequency);
        dest.writeString(level);
        dest.writeString(flag);
        dest.writeString(ssid);
    }

    public void readFromParcel(Parcel source) {
        bssid = source.readString();
        frequency = source.readString();
        level = source.readString();
        flag = source.readString();
        ssid = source.readString();
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getLevel() {
        return level;
    }

    public String getFlag() {
        return flag;
    }

    public String getSsid() {
        return ssid;
    }
}
