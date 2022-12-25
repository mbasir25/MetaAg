package com.example.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//class ObjConvFieldEntryDesc {
//
//    @SerializedName("Entry")
//    @Expose
//    private ObjectEntryExitUserCreator Entry;
//
//    public ObjectEntryExitUserCreator getEntry() {
//        return Entry;
//    }
//
//    public void setEntry(ObjectEntryExitUserCreator Entry) {
//        this.Entry = Entry;
//    }
//
//}

public class ObjectEntryExitUserCreator {
    String Entry, User, Exit, WorkTime;

    public ObjectEntryExitUserCreator(String entry, String user, String exit, String workTime) {
        Entry = entry;
        User = user;
        Exit = exit;
        WorkTime = workTime;
    }

    public String getEntry() {
        return Entry;
    }

    public void setEntry(String entry) {
        Entry = entry;
    }


    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getExit() {
        return Exit;
    }

    public void setExit(String exit) {
        Exit = exit;
    }

    public String getWorkTime() {
        return WorkTime;
    }

    public void setWorkTime(String workTime) {
        Entry = workTime;
    }
}


