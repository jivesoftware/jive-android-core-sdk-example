package com.jivesoftware.example.github.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mark.schisler on 9/19/14.
 */
public class Team implements Parcelable {
    public int id;
    public String url;
    public String name;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(url);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Team> CREATOR =
        new Parcelable.Creator<Team>() {
            @Override
            public Team createFromParcel(Parcel source) {
                Team team = new Team();
                team.id = source.readInt();
                team.url = source.readString();
                team.name = source.readString();
                return team;
            }

            @Override
            public Team[] newArray(int size) {
                return new Team[size];
            }
        };
}
