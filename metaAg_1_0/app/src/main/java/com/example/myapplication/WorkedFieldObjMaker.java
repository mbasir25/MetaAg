package com.example.myapplication;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkedFieldObjMaker {
    @SerializedName("first")
    @Expose
    private String first;
    @SerializedName("second")
    @Expose
    private String second;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

}
