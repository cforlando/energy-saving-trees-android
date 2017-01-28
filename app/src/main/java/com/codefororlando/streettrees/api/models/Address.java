package com.codefororlando.streettrees.api.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by johnli on 9/24/16.
 */
public class Address implements Parcelable {

    public static final String TAG = "Address";

    private String streetAddress;
    private String streetAddressExtra;
    private String city;
    private String state;
    private String zip;

    public Address() {
    }

    protected Address(Parcel in) {
        streetAddress = in.readString();
        streetAddressExtra = in.readString();
        city = in.readString();
        state = in.readString();
        zip = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getStreetAddressExtra() {
        return streetAddressExtra;
    }

    public void setStreetAddressExtra(String streetAddressExtra) {
        this.streetAddressExtra = streetAddressExtra;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(streetAddress);
        dest.writeString(streetAddressExtra);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(zip);
    }
}
