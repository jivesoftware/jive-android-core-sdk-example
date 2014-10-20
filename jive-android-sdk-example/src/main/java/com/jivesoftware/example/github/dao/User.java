package com.jivesoftware.example.github.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mark.schisler on 8/26/14.
 */
public class User implements Parcelable {
    public int id;
    public String name;
    public String login;
    public String avatarUrl;
    public String location;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(login);
        dest.writeString(avatarUrl);
        dest.writeString(location);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<User> CREATOR =
        new Parcelable.Creator<User>() {
            @Override
            public User createFromParcel(Parcel source) {
                User user = new User();
                user.id = source.readInt();
                user.name = source.readString();
                user.login = source.readString();
                user.avatarUrl = source.readString();
                user.location = source.readString();
                return user;
            }

            @Override
            public User[] newArray(int size) {
                return new User[size];
            }
        };
}
