package com.example.tommy.cpre388game2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tommysch on 11/17/2015.
 */
public class Pair implements Parcelable {
    public int x;
    public int y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pair(Parcel in) {
        x = in.readInt();
        y = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(x);
        out.writeInt(y);
    }

    public static final Parcelable.Creator<Pair> CREATOR
            = new Parcelable.Creator<Pair>() {
        public Pair createFromParcel(Parcel in) {
            return new Pair(in);
        }

        public Pair[] newArray(int size) {
            return new Pair[size];
        }
    };
}
