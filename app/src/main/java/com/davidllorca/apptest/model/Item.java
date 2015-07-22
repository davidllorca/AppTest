package com.davidllorca.apptest.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model Item's list object.
 * <p/>
 * Created by David Llorca <davidllorcabaron@gmail.com> on 7/17/15.
 */
public class Item implements Parcelable {

    // Attributes
    private byte[] img;
    private String text;

    // Constructors
    public Item() {
    }

    public Item(String text) {
        this.text = text;
    }

    public Item(byte[] img, String text) {
        this.img = img;
        this.text = text;
    }

    public Item(Parcel in) {
        readFromParcel(in);
    }

    // Getters & Setters

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    // Parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        if (img != null) {
            dest.writeInt(img.length);
            dest.writeByteArray(img);
        }
    }

    public void readFromParcel(Parcel source) {
        this.text = source.readString();
        int lengthImg = source.readInt();
        if (lengthImg > 0) {
            this.img = new byte[lengthImg];
            source.readByteArray(this.img);
        }
    }
}
