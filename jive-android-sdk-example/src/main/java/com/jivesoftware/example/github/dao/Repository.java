package com.jivesoftware.example.github.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mark.schisler on 9/3/14.
 */
public class Repository implements Parcelable {
    public int id;
    public String name;
    public String fullName;
    public String description;
    public String url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(fullName);
        dest.writeString(description);
        dest.writeString(url);
    }

    public static final Parcelable.Creator<Repository> CREATOR =
        new Parcelable.Creator<Repository>() {
            @Override
            public Repository createFromParcel(Parcel source) {
                Repository repository = new Repository();
                repository.id = source.readInt();
                repository.name = source.readString();
                repository.fullName = source.readString();
                repository.description = source.readString();
                repository.url = source.readString();
                return repository;
            }

            @Override
            public Repository[] newArray(int size) {
                return new Repository[size];
            }
        };
}
