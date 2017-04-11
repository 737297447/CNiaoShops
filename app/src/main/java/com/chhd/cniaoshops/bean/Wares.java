package com.chhd.cniaoshops.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CWQ on 2017/3/15.
 */

public class Wares implements Parcelable {

    private Long id;

    private String name;

    private String imgUrl;

    private String description;

    private Float price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.imgUrl);
        dest.writeString(this.description);
        dest.writeValue(this.price);
    }

    public Wares() {
    }

    protected Wares(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.imgUrl = in.readString();
        this.description = in.readString();
        this.price = (Float) in.readValue(Float.class.getClassLoader());
    }

    public static final Parcelable.Creator<Wares> CREATOR = new Parcelable.Creator<Wares>() {
        @Override
        public Wares createFromParcel(Parcel source) {
            return new Wares(source);
        }

        @Override
        public Wares[] newArray(int size) {
            return new Wares[size];
        }
    };
}
