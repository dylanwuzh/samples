package com.wuzhen.scanqrcode.net;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author wuzhen
 * @version Version 1.0, 2016-04-19
 */
public class NetworkResponse implements Parcelable {

    public String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "NetworkResponse{" +
                "result='" + result + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.result);
    }

    public NetworkResponse() {
    }

    protected NetworkResponse(Parcel in) {
        this.result = in.readString();
    }

    public static final Parcelable.Creator<NetworkResponse> CREATOR = new Parcelable.Creator<NetworkResponse>() {
        @Override
        public NetworkResponse createFromParcel(Parcel source) {
            return new NetworkResponse(source);
        }

        @Override
        public NetworkResponse[] newArray(int size) {
            return new NetworkResponse[size];
        }
    };
}
