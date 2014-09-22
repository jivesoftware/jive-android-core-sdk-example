package com.jivesoftware.example.github.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class Owner implements Parcelable {
    public int id;
    public String type;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(type);
    }

    public static final Parcelable.Creator<Owner> CREATOR =
        new Parcelable.Creator<Owner>() {
            @Override
            public Owner createFromParcel(Parcel source) {
                Owner owner = new Owner();
                owner.id = source.readInt();
                owner.type = source.readString();
                return owner;
            }

            @Override
            public Owner[] newArray(int size) {
                return new Owner[size];
            }
        };
}
