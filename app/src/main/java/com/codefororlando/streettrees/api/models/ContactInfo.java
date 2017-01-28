package com.codefororlando.streettrees.api.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by johnli on 9/24/16.
 */
public class ContactInfo implements Parcelable {

    public static final String TAG = "ContactInfo";

    String name;
    String phoneNumber;
    String email;

    public ContactInfo() {
    }

    protected ContactInfo(Parcel in) {
        name = in.readString();
        phoneNumber = in.readString();
        email = in.readString();
    }

    public static final Creator<ContactInfo> CREATOR = new Creator<ContactInfo>() {
        @Override
        public ContactInfo createFromParcel(Parcel in) {
            return new ContactInfo(in);
        }

        @Override
        public ContactInfo[] newArray(int size) {
            return new ContactInfo[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phoneNumber);
        dest.writeString(email);
    }
}
