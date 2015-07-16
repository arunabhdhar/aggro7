package com.app.address;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by C-ShellWin on 12/11/2014.
 */
public class User implements Parcelable {

    public String address, city, state, country, postalCode;
    /**
     * A constructor that initializes the User object
     */

    public User(String address ,String city, String state, String country, String postalCode) {

        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Storing the User data to Parcel object
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(country);
        dest.writeString(postalCode);
    }

    /**
     * Retrieving User data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     */

    public User(Parcel in) {
        this.address = in.readString();
        this.city = in.readString();
        this.state = in.readString();
        this.country = in.readString();
        this.postalCode = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
