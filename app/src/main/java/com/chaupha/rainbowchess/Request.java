package com.chaupha.rainbowchess;

import android.os.Parcel;
import android.os.Parcelable;

public class Request implements Parcelable {

    private String sourceUid;
    private String targetUid;
    private String time;


    public Request(String sourceUid, String targetUid, String time) {
        this.sourceUid = sourceUid;
        this.targetUid = targetUid;
        this.time = time;
    }


    protected Request(Parcel in) {
        sourceUid = in.readString();
        targetUid = in.readString();
        time = in.readString();
    }


    public String getSourceUid() {
        return sourceUid;
    }


    public String getTargetUid() {
        return targetUid;
    }


    public String getTime() {
        return time;
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(sourceUid);
        dest.writeString(targetUid);
        dest.writeString(time);
    }


    public static final Creator<Request> CREATOR = new Creator<Request>() {

        @Override
        public Request createFromParcel(Parcel parcel) {
            return new Request(parcel);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };
}

