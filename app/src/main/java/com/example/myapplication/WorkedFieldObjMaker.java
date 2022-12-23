package com.example.myapplication;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkedFieldObjMaker {
    @SerializedName("field")
    @Expose
    private Field field;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

}
class Field {

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("entry")
    @Expose
    private Long entry;
    @SerializedName("exit")
    @Expose
    private Long exit;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getEntry() {
        return entry;
    }

    public void setEntry(Long entry) {
        this.entry = entry;
    }

    public Long getExit() {
        return exit;
    }

    public void setExit(Long exit) {
        this.exit = exit;
    }

}