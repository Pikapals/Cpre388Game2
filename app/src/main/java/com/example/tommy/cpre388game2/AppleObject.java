package com.example.tommy.cpre388game2;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by tommysch on 11/17/2015.
 */
public class AppleObject extends GameCharacter implements Parcelable {

    public AppleObject(int x, int y) {
        super(x, y, 0, 1, 0);
    }

    private AppleObject(Parcel in) {
        super(in.readInt(), in.readInt(), 0, 1, 0);
    }

    public ArrayList<Pair> drawToBoard() {
        ArrayList<Pair> arr = new ArrayList<Pair>();
        arr.add(new Pair(x, y));
        return arr;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(x);
        out.writeInt(y);
    }

    public static final Parcelable.Creator<AppleObject> CREATOR
            = new Parcelable.Creator<AppleObject>() {
        public AppleObject createFromParcel(Parcel in) {
            return new AppleObject(in);
        }

        public AppleObject[] newArray(int size) {
            return new AppleObject[size];
        }
    };
}
