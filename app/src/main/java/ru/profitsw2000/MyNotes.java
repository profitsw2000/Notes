package ru.profitsw2000;

import android.os.Parcel;
import android.os.Parcelable;

public class MyNotes implements Parcelable {
    private String title    ;
    private String description  ;
    private String data ;
    private String text ;

    public MyNotes(String title, String description, String data, String text) {
        this.title = title;
        this.description = description;
        this.data = data;
        this.text = text;
    }

    protected MyNotes(Parcel in) {
        title = in.readString();
        description = in.readString();
        data = in.readString();
        text = in.readString();
    }

    public static final Creator<MyNotes> CREATOR = new Creator<MyNotes>() {
        @Override
        public MyNotes createFromParcel(Parcel in) {
            return new MyNotes(in);
        }

        @Override
        public MyNotes[] newArray(int size) {
            return new MyNotes[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getData() {
        return data;
    }

    public String getText() {
        return text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(data);
        dest.writeString(text);
    }
}
