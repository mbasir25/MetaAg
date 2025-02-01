package com.example.myapplication;

public class FieldListDataModel {
    String fieldname, entry, exit;

    public FieldListDataModel(String fieldname, String entry, String exit) {
        this.fieldname = fieldname;
        this.entry = entry;
        this.exit = exit;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getExit() {
        return exit;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }
}
